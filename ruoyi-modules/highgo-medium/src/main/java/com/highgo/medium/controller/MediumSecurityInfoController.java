package com.highgo.medium.controller;

import java.util.Date;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.highgo.medium.domain.MediumSecurityInfo;
import com.highgo.medium.service.IMediumSecurityInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 介质安全文件记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/mediumsecurity")
public class MediumSecurityInfoController extends BaseController
{
    @Autowired
    private IMediumSecurityInfoService mediumSecurityInfoService;

    /**
     * 查询介质安全文件记录列表
     */
    @RequiresPermissions("medium:mediumsecurity:list")
    @GetMapping("/list")
    public TableDataInfo list(MediumSecurityInfo mediumSecurityInfo)
    {
        startPage();
        List<MediumSecurityInfo> list = mediumSecurityInfoService.selectMediumSecurityInfoList(mediumSecurityInfo);
        return getDataTable(list);
    }

    /**
     * 导出介质安全文件记录列表
     */
    @RequiresPermissions("medium:mediumsecurity:export")
    @Log(title = "介质安全文件记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MediumSecurityInfo mediumSecurityInfo)
    {
        List<MediumSecurityInfo> list = mediumSecurityInfoService.selectMediumSecurityInfoList(mediumSecurityInfo);
        ExcelUtil<MediumSecurityInfo> util = new ExcelUtil<MediumSecurityInfo>(MediumSecurityInfo.class);
        util.exportExcel(response, list, "介质安全文件记录数据");
    }

    @RequiresPermissions("medium:mediumsecurity:download")
    @Log(title = "介质安全文件记录", businessType = BusinessType.DOWNLOAD)
    @PostMapping("/download")
    public void download(HttpServletResponse response, MediumSecurityInfo mediumSecurityInfo)
    {
        mediumSecurityInfoService.download(response, mediumSecurityInfo);
    }
    /**
     * 获取介质安全文件记录详细信息
     */
    @RequiresPermissions("medium:mediumsecurity:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mediumSecurityInfoService.selectMediumSecurityInfoById(id));
    }

    /**
     * 新增介质安全文件记录
     */
    @RequiresPermissions("medium:mediumsecurity:add")
    @Log(title = "介质安全文件记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediumSecurityInfo mediumSecurityInfo)
    {
        return toAjax(mediumSecurityInfoService.insertMediumSecurityInfo(mediumSecurityInfo));
    }
    @RequiresPermissions("medium:mediumsecurity:add")
    @Log(title = "介质安全文件记录", businessType = BusinessType.INSERT)
    @PostMapping("/addWithFile")
    public AjaxResult addWithFile(MediumSecurityInfo mediumSecurityInfo,
                                  MultipartFile securityFile, MultipartFile securityFileMd5)
    {
        mediumSecurityInfo.setCreateBy(SecurityUtils.getUsername());
        mediumSecurityInfo.setCreateId(SecurityUtils.getUserId());
        mediumSecurityInfo.setCreateTime(new Date());
        return toAjax(mediumSecurityInfoService.insertMediumSecurityWithFile(mediumSecurityInfo,securityFile,securityFileMd5));
    }
    /**
     * 修改介质安全文件记录
     */
    @RequiresPermissions("medium:mediumsecurity:edit")
    @Log(title = "介质安全文件记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediumSecurityInfo mediumSecurityInfo)
    {
        return toAjax(mediumSecurityInfoService.updateMediumSecurityInfo(mediumSecurityInfo));
    }
    @RequiresPermissions("medium:mediumsecurity:edit")
    @Log(title = "介质安全文件记录", businessType = BusinessType.UPDATE)
    @PutMapping("/editWithFile")
    public AjaxResult editWithFile(MediumSecurityInfo mediumSecurityInfo,
                                   MultipartFile securityFile, MultipartFile securityFileMd5)
    {
        mediumSecurityInfo.setUpdateBy(SecurityUtils.getUsername());
        mediumSecurityInfo.setUpdateId(SecurityUtils.getUserId());
        mediumSecurityInfo.setUpdateTime(new Date());
        return toAjax(mediumSecurityInfoService.updateMediumSecurityWithFile(mediumSecurityInfo,securityFile,securityFileMd5));
    }
    /**
     * 删除介质安全文件记录
     */
    @RequiresPermissions("medium:mediumsecurity:remove")
    @Log(title = "介质安全文件记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mediumSecurityInfoService.deleteMediumSecurityInfoByIds(ids));
    }
}
