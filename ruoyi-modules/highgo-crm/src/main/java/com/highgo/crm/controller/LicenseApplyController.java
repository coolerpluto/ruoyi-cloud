package com.highgo.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
import com.highgo.crm.domain.LicenseApply;
import com.highgo.crm.service.ILicenseApplyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * license申请Controller
 *
 * @author eszhang
 * @date 2023-04-26
 */
@RestController
@RequestMapping("/licenseApply")
public class LicenseApplyController extends BaseController
{
    @Autowired
    private ILicenseApplyService licenseApplyService;

    /**
     * 查询license申请列表
     */
    @RequiresPermissions("crm:licenseApply:list")
    @GetMapping("/list")
    public TableDataInfo list(LicenseApply licenseApply)
    {
        startPage();
        List<LicenseApply> list = licenseApplyService.selectLicenseApplyList(licenseApply);
        return getDataTable(list);
    }

    /**
     * 导出license申请列表
     */
    @RequiresPermissions("crm:licenseApply:export")
    @Log(title = "license申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LicenseApply licenseApply)
    {
        List<LicenseApply> list = licenseApplyService.selectLicenseApplyList(licenseApply);
        ExcelUtil<LicenseApply> util = new ExcelUtil<LicenseApply>(LicenseApply.class);
        util.exportExcel(response, list, "license申请数据");
    }

    /**
     * 获取license申请详细信息
     */
    @RequiresPermissions("crm:licenseApply:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(licenseApplyService.selectLicenseApplyById(id));
    }

    /**
     * 新增license申请
     */
    @RequiresPermissions("crm:licenseApply:add")
    @Log(title = "license申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LicenseApply licenseApply)
    {
        return toAjax(licenseApplyService.insertLicenseApply(licenseApply));
    }

    /**
     * 修改license申请
     */
    @RequiresPermissions("crm:licenseApply:edit")
    @Log(title = "license申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LicenseApply licenseApply)
    {
        return toAjax(licenseApplyService.updateLicenseApply(licenseApply));
    }

    /**
     * 删除license申请
     */
    @RequiresPermissions("crm:licenseApply:remove")
    @Log(title = "license申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(licenseApplyService.deleteLicenseApplyByIds(ids));
    }
}
