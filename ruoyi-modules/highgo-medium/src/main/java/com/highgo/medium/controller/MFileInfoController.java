package com.highgo.medium.controller;

import com.highgo.medium.domain.MFileInfo;
import com.highgo.medium.service.IMFileInfoService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/file")
public class MFileInfoController extends BaseController
{
    @Autowired
    private IMFileInfoService mFileInfoService;

    /**
     * 查询文件记录列表
     */
    @RequiresPermissions("medium:file:list")
    @GetMapping("/list")
    public TableDataInfo list(MFileInfo mFileInfo)
    {
        startPage();
        List<MFileInfo> list = mFileInfoService.selectMFileInfoList(mFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件记录列表
     */
    @RequiresPermissions("medium:file:export")
    @Log(title = "文件记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MFileInfo mFileInfo)
    {
        List<MFileInfo> list = mFileInfoService.selectMFileInfoList(mFileInfo);
        ExcelUtil<MFileInfo> util = new ExcelUtil<MFileInfo>(MFileInfo.class);
        util.exportExcel(response, list, "文件记录数据");
    }

    @RequiresPermissions("medium:file:download")
    @Log(title = "文件记录", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/download")
    public void download(HttpServletResponse response, MFileInfo mFileInfo)
    {
        mFileInfoService.download(response,mFileInfo);
    }
    /**
     * 获取文件记录详细信息
     */
    @RequiresPermissions("medium:file:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mFileInfoService.selectMFileInfoById(id));
    }

    @GetMapping(value = "mediumIds/{ids}")
    public AjaxResult getMFileInfoByMediumIds(@PathVariable("ids") List<String> ids) {
        return success(mFileInfoService.getMFileInfoByMediumIds(ids));
    }

    @GetMapping(value = "securityIds/{ids}")
    public AjaxResult getMFileInfoBySecurityIds(@PathVariable("ids") List<String> ids) {
        return success(mFileInfoService.getMFileInfoBySecurityIds(ids));
    }
    @GetMapping(value = "association/{id}")
    public AjaxResult getAssociationStatus(@PathVariable("id") Long id)
    {
        return success(mFileInfoService.getMFileAssociationStatus(id));
    }
    /**
     * 新增文件记录
     */
    @RequiresPermissions("medium:file:add")
    @Log(title = "文件记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MFileInfo mFileInfo)
    {
        return toAjax(mFileInfoService.insertMFileInfo(mFileInfo));
    }

    @RequiresPermissions("medium:file:add")
    @Log(title = "文件记录", businessType = BusinessType.INSERT)
    @PostMapping("/addWithFile")
    public AjaxResult addWithFile(MFileInfo mFileInfo, MultipartFile file) {
        return toAjax(mFileInfoService.insertMFileInfo(mFileInfo));
    }

    /**
     * 修改文件记录
     */
    @RequiresPermissions("medium:file:edit")
    @Log(title = "文件记录", businessType = BusinessType.UPDATE)
    @PutMapping("/editWithFile")
    public AjaxResult editWithFile(MFileInfo mFileInfo, MultipartFile file) {
        return toAjax(mFileInfoService.updateMFileInfo(mFileInfo));
    }
    /**
     * 修改文件记录
     */
    @RequiresPermissions("medium:file:edit")
    @Log(title = "文件记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MFileInfo mFileInfo)
    {
        return toAjax(mFileInfoService.updateMFileInfo(mFileInfo));
    }

    /**
     * 删除文件记录
     */
    @RequiresPermissions("medium:file:remove")
    @Log(title = "文件记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mFileInfoService.deleteMFileInfoByIds(ids));
    }
}
