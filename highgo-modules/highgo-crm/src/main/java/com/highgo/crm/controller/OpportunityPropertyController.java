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
import com.highgo.crm.domain.OpportunityProperty;
import com.highgo.crm.service.IOpportunityPropertyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机属性Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppProperty")
public class OpportunityPropertyController extends BaseController
{
    @Autowired
    private IOpportunityPropertyService opportunityPropertyService;

    /**
     * 查询商机属性列表
     */
    @RequiresPermissions("crm:oppProperty:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityProperty opportunityProperty)
    {
        startPage();
        List<OpportunityProperty> list = opportunityPropertyService.selectOpportunityPropertyList(opportunityProperty);
        return getDataTable(list);
    }

    /**
     * 导出商机属性列表
     */
    @RequiresPermissions("crm:oppProperty:export")
    @Log(title = "商机属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityProperty opportunityProperty)
    {
        List<OpportunityProperty> list = opportunityPropertyService.selectOpportunityPropertyList(opportunityProperty);
        ExcelUtil<OpportunityProperty> util = new ExcelUtil<OpportunityProperty>(OpportunityProperty.class);
        util.exportExcel(response, list, "商机属性数据");
    }

    /**
     * 获取商机属性详细信息
     */
    @RequiresPermissions("crm:oppProperty:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityPropertyService.selectOpportunityPropertyById(id));
    }

    /**
     * 新增商机属性
     */
    @RequiresPermissions("crm:oppProperty:add")
    @Log(title = "商机属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityProperty opportunityProperty)
    {
        return toAjax(opportunityPropertyService.insertOpportunityProperty(opportunityProperty));
    }

    /**
     * 修改商机属性
     */
    @RequiresPermissions("crm:oppProperty:edit")
    @Log(title = "商机属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityProperty opportunityProperty)
    {
        return toAjax(opportunityPropertyService.updateOpportunityProperty(opportunityProperty));
    }

    /**
     * 删除商机属性
     */
    @RequiresPermissions("crm:oppProperty:remove")
    @Log(title = "商机属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityPropertyService.deleteOpportunityPropertyByIds(ids));
    }
}
