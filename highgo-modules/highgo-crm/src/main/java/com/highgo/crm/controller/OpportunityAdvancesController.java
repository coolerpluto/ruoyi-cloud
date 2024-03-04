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
import com.highgo.crm.domain.OpportunityAdvances;
import com.highgo.crm.service.IOpportunityAdvancesService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机进展Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppAdvances")
public class OpportunityAdvancesController extends BaseController
{
    @Autowired
    private IOpportunityAdvancesService opportunityAdvancesService;

    /**
     * 查询商机进展列表
     */
    @RequiresPermissions("crm:oppAdvances:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityAdvances opportunityAdvances)
    {
        startPage();
        List<OpportunityAdvances> list = opportunityAdvancesService.selectOpportunityAdvancesList(opportunityAdvances);
        return getDataTable(list);
    }

    /**
     * 导出商机进展列表
     */
    @RequiresPermissions("crm:oppAdvances:export")
    @Log(title = "商机进展", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityAdvances opportunityAdvances)
    {
        List<OpportunityAdvances> list = opportunityAdvancesService.selectOpportunityAdvancesList(opportunityAdvances);
        ExcelUtil<OpportunityAdvances> util = new ExcelUtil<OpportunityAdvances>(OpportunityAdvances.class);
        util.exportExcel(response, list, "商机进展数据");
    }

    /**
     * 获取商机进展详细信息
     */
    @RequiresPermissions("crm:oppAdvances:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityAdvancesService.selectOpportunityAdvancesById(id));
    }

    /**
     * 新增商机进展
     */
    @RequiresPermissions("crm:oppAdvances:add")
    @Log(title = "商机进展", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityAdvances opportunityAdvances)
    {
        return toAjax(opportunityAdvancesService.insertOpportunityAdvances(opportunityAdvances));
    }

    /**
     * 修改商机进展
     */
    @RequiresPermissions("crm:oppAdvances:edit")
    @Log(title = "商机进展", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityAdvances opportunityAdvances)
    {
        return toAjax(opportunityAdvancesService.updateOpportunityAdvances(opportunityAdvances));
    }

    /**
     * 删除商机进展
     */
    @RequiresPermissions("crm:oppAdvances:remove")
    @Log(title = "商机进展", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityAdvancesService.deleteOpportunityAdvancesByIds(ids));
    }
}
