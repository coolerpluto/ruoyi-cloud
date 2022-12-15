package com.highgo.medium.controller;

import com.highgo.medium.domain.MediumFileInfo;
import com.highgo.medium.service.IMFileInfoService;
import com.highgo.medium.service.IMediumFileInfoService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 介质文件信息记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/medium")
public class MediumFileInfoController extends BaseController
{
    @Autowired
    private IMediumFileInfoService mediumFileInfoService;
    @Autowired
    private IMFileInfoService fileInfoService;

    /**
     * 查询介质文件信息记录列表
     */
    @RequiresPermissions("medium:medium:list")
    @GetMapping("/list")
    public TableDataInfo list(MediumFileInfo mediumFileInfo)
    {
        startPage();
        List<MediumFileInfo> list = mediumFileInfoService.selectMediumFileInfoList(mediumFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出介质文件信息记录列表
     */
    @RequiresPermissions("medium:medium:export")
    @Log(title = "介质文件信息记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MediumFileInfo mediumFileInfo)
    {
        List<MediumFileInfo> list = mediumFileInfoService.selectMediumFileInfoList(mediumFileInfo);
        ExcelUtil<MediumFileInfo> util = new ExcelUtil<MediumFileInfo>(MediumFileInfo.class);
        util.exportExcel(response, list, "介质文件信息记录数据");
    }

    @RequiresPermissions("medium:medium:download")
    @Log(title = "介质文件信息记录", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/download")
    public void download(HttpServletResponse response, MediumFileInfo mediumFileInfo) {
        mediumFileInfoService.download(response, mediumFileInfo);
    }
    /**
     * 获取介质文件信息记录详细信息
     */
    @RequiresPermissions("medium:medium:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mediumFileInfoService.selectMediumFileInfoById(id));
    }

    /**
     * 新增介质文件信息记录
     */
    @RequiresPermissions("medium:medium:add")
    @Log(title = "介质信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setCreateBy(SecurityUtils.getUsername());
        mediumFileInfo.setCreateId(SecurityUtils.getUserId());
        return toAjax(mediumFileInfoService.insertMediumFileInfo(mediumFileInfo));
    }

    @RequiresPermissions("medium:medium:add")
    @Log(title = "介质文件信息记录", businessType = BusinessType.INSERT)
    @PostMapping("/addWithFile")
    public AjaxResult addWithFile(MediumFileInfo mediumFileInfo,
                                  MultipartFile mediumFile,
                                  MultipartFile mediumFileMd5) {
        mediumFileInfo.setCreateBy(SecurityUtils.getUsername());
        mediumFileInfo.setCreateId(SecurityUtils.getUserId());
        mediumFileInfo.setCreateTime(new Date());
        return toAjax(mediumFileInfoService.insertMediumFileInfoWithFile(mediumFileInfo,mediumFile,mediumFileMd5));
    }
    /**
     * 修改介质文件信息记录
     */
    @RequiresPermissions("medium:medium:edit")
    @Log(title = "介质信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediumFileInfo mediumFileInfo)
    {
        mediumFileInfo.setUpdateBy(SecurityUtils.getUsername());
        mediumFileInfo.setUpdateId(SecurityUtils.getUserId());
        return toAjax(mediumFileInfoService.updateMediumFileInfo(mediumFileInfo));
    }
    /**
     * 修改介质文件信息记录
     */
    @RequiresPermissions("medium:medium:edit")
    @Log(title = "介质文件信息记录", businessType = BusinessType.UPDATE)
    @PutMapping("/editWithFile")
    public AjaxResult editWithFile(MediumFileInfo mediumFileInfo,
                                   MultipartFile mediumFile,
                                   MultipartFile mediumFileMd5)
    {
        mediumFileInfo.setUpdateBy(SecurityUtils.getUsername());
        mediumFileInfo.setUpdateId(SecurityUtils.getUserId());
        return toAjax(mediumFileInfoService.updateMediumFileInfoWithFile(mediumFileInfo,mediumFile,mediumFileMd5));
    }
    /**
     * 删除介质文件信息记录
     */
    @RequiresPermissions("medium:medium:remove")
    @Log(title = "介质文件信息记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mediumFileInfoService.deleteMediumFileInfoByIds(ids));
    }
}
