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
import com.highgo.crm.domain.Opportunity;
import com.highgo.crm.service.IOpportunityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机管理Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/opportunity")
public class OpportunityController extends BaseController
{
    @Autowired
    private IOpportunityService opportunityService;

    /**
     * 查询商机管理列表
     */
    @RequiresPermissions("crm:opportunity:list")
    @GetMapping("/list")
    public TableDataInfo list(Opportunity opportunity)
    {
        startPage();
        List<Opportunity> list = opportunityService.selectOpportunityList(opportunity);
        return getDataTable(list);
    }

    /**
     * 导出商机管理列表
     */
    @RequiresPermissions("crm:opportunity:export")
    @Log(title = "商机管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Opportunity opportunity)
    {
        List<Opportunity> list = opportunityService.selectOpportunityList(opportunity);
        ExcelUtil<Opportunity> util = new ExcelUtil<Opportunity>(Opportunity.class);
        util.exportExcel(response, list, "商机管理数据");
    }

    /**
     * 获取商机管理详细信息
     */
    @RequiresPermissions("crm:opportunity:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityService.selectOpportunityById(id));
    }

    /**
     * 新增商机管理
     */
    @RequiresPermissions("crm:opportunity:add")
    @Log(title = "商机管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Opportunity opportunity)
    {
        return toAjax(opportunityService.insertOpportunity(opportunity));
    }

    /**
     * 修改商机管理
     */
    @RequiresPermissions("crm:opportunity:edit")
    @Log(title = "商机管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Opportunity opportunity)
    {
        return toAjax(opportunityService.updateOpportunity(opportunity));
    }

    /**
     * 删除商机管理
     */
    @RequiresPermissions("crm:opportunity:remove")
    @Log(title = "商机管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityService.deleteOpportunityByIds(ids));
    }
}
