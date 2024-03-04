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
import com.highgo.crm.domain.OpportunityStageChangeHis;
import com.highgo.crm.service.IOpportunityStageChangeHisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机阶段历史Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppStageChangeHis")
public class OpportunityStageChangeHisController extends BaseController
{
    @Autowired
    private IOpportunityStageChangeHisService opportunityStageChangeHisService;

    /**
     * 查询商机阶段历史列表
     */
    @RequiresPermissions("crm:oppStageChangeHis:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityStageChangeHis opportunityStageChangeHis)
    {
        startPage();
        List<OpportunityStageChangeHis> list = opportunityStageChangeHisService.selectOpportunityStageChangeHisList(opportunityStageChangeHis);
        return getDataTable(list);
    }

    /**
     * 导出商机阶段历史列表
     */
    @RequiresPermissions("crm:oppStageChangeHis:export")
    @Log(title = "商机阶段历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityStageChangeHis opportunityStageChangeHis)
    {
        List<OpportunityStageChangeHis> list = opportunityStageChangeHisService.selectOpportunityStageChangeHisList(opportunityStageChangeHis);
        ExcelUtil<OpportunityStageChangeHis> util = new ExcelUtil<OpportunityStageChangeHis>(OpportunityStageChangeHis.class);
        util.exportExcel(response, list, "商机阶段历史数据");
    }

    /**
     * 获取商机阶段历史详细信息
     */
    @RequiresPermissions("crm:oppStageChangeHis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityStageChangeHisService.selectOpportunityStageChangeHisById(id));
    }

    /**
     * 新增商机阶段历史
     */
    @RequiresPermissions("crm:oppStageChangeHis:add")
    @Log(title = "商机阶段历史", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityStageChangeHis opportunityStageChangeHis)
    {
        return toAjax(opportunityStageChangeHisService.insertOpportunityStageChangeHis(opportunityStageChangeHis));
    }

    /**
     * 修改商机阶段历史
     */
    @RequiresPermissions("crm:oppStageChangeHis:edit")
    @Log(title = "商机阶段历史", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityStageChangeHis opportunityStageChangeHis)
    {
        return toAjax(opportunityStageChangeHisService.updateOpportunityStageChangeHis(opportunityStageChangeHis));
    }

    /**
     * 删除商机阶段历史
     */
    @RequiresPermissions("crm:oppStageChangeHis:remove")
    @Log(title = "商机阶段历史", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityStageChangeHisService.deleteOpportunityStageChangeHisByIds(ids));
    }
}
