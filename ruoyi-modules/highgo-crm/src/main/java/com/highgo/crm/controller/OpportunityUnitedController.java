package com.highgo.crm.controller;

import com.highgo.crm.domain.OpportunityUnited;
import com.highgo.crm.service.IOpportunityUnitedService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.Logical;
import com.ruoyi.common.security.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商机管理Controller
 *
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppUnited")
public class OpportunityUnitedController extends BaseController
{
    @Autowired
    private IOpportunityUnitedService opportunityService;

    /**
     * 查询商机统一管理列表
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @GetMapping("/list")
    public TableDataInfo list(OpportunityUnited opportunity)
    {
        startPage();
        List<OpportunityUnited> list = opportunityService.selectOpportunityUnitedList(opportunity);
        return getDataTable(list);
    }

    /**
     * 导出商机统一管理列表
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityUnited opportunity)
    {
        List<OpportunityUnited> list = opportunityService.selectOpportunityUnitedList(opportunity);
        ExcelUtil<OpportunityUnited> util = new ExcelUtil<OpportunityUnited>(OpportunityUnited.class);
        util.exportExcel(response, list, "商机管理数据");
    }

    /**
     * 获取商机统一管理详细信息
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityService.selectOpportunityUnitedById(id));
    }

    /**
     * 新增商机统一管理
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityUnited opportunity)
    {
        return toAjax(opportunityService.insertOpportunityUnited(opportunity));
    }

    /**
     * 修改商机统一管理
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityUnited opportunity)
    {
        return toAjax(opportunityService.updateOpportunityUnited(opportunity));
    }

    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.TRANSFER)
    @PutMapping("/transfer")
    public AjaxResult transfer(@RequestBody OpportunityUnited opportunity)
    {
        return toAjax(opportunityService.updateOpportunityUnited(opportunity));
    }

    /**
     * 删除商机统一管理
     */
    @RequiresRoles(value = {"crm:opportunity", "crm:mangment"}, logical = Logical.OR)
    @Log(title = "商机统一管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityService.deleteOpportunityUnitedByIds(ids));
    }
}
