package com.highgo.crm.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.highgo.crm.domain.Company;
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
import com.highgo.crm.domain.Application;
import com.highgo.crm.service.IApplicationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 应用信息Controller
 *
 * @author eszhang
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController
{
    @Autowired
    private IApplicationService applicationService;

    /**
     * 查询应用信息列表
     */
    @RequiresPermissions("crm:application:list")
    @GetMapping("/list")
    public TableDataInfo list(Application application)
    {
        startPage();
        List<Application> list = applicationService.selectApplicationList(application);
        return getDataTable(list);
    }

    /**
     * 导出应用信息列表
     */
    @RequiresPermissions("crm:application:export")
    @Log(title = "应用信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Application application)
    {
        List<Application> list = applicationService.selectApplicationList(application);
        ExcelUtil<Application> util = new ExcelUtil<Application>(Application.class);
        util.exportExcel(response, list, "应用信息数据");
    }

    /**
     * 获取应用信息详细信息
     */
    @RequiresPermissions("crm:application:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(applicationService.selectApplicationById(id));
    }

    /**
     * 新增应用信息
     */
    @RequiresPermissions("crm:application:add")
    @Log(title = "应用信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Application application)
    {
        return toAjax(applicationService.insertApplication(application));
    }

    /**
     * 修改应用信息
     */
    @RequiresPermissions("crm:application:edit")
    @Log(title = "应用信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Application application)
    {
        return toAjax(applicationService.updateApplication(application));
    }
    /**
     * 转移联系人归属人
     */
    @RequiresPermissions("crm:application:transfer")
    @Log(title = "联系人", businessType = BusinessType.UPDATE)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody Application application) {
        return toAjax(applicationService.transfer(application));
    }
    /**
     * 删除应用信息
     */
    @RequiresPermissions("crm:application:remove")
    @Log(title = "应用信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(applicationService.deleteApplicationByIds(ids));
    }
}
