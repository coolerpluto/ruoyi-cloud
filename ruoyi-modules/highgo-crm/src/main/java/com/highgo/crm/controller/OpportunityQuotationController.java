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
import com.highgo.crm.domain.OpportunityQuotation;
import com.highgo.crm.service.IOpportunityQuotationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机产品报价Controller
 *
 * @author eszhang
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/opportunityQuotation")
public class OpportunityQuotationController extends BaseController
{
    @Autowired
    private IOpportunityQuotationService opportunityQuotationService;

    /**
     * 查询商机产品报价列表
     */
    @RequiresPermissions("crm:opportunityQuotation:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityQuotation opportunityQuotation)
    {
        startPage();
        List<OpportunityQuotation> list = opportunityQuotationService.selectOpportunityQuotationList(opportunityQuotation);
        return getDataTable(list);
    }

    /**
     * 导出商机产品报价列表
     */
    @RequiresPermissions("crm:opportunityQuotation:export")
    @Log(title = "商机产品报价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityQuotation opportunityQuotation)
    {
        List<OpportunityQuotation> list = opportunityQuotationService.selectOpportunityQuotationList(opportunityQuotation);
        ExcelUtil<OpportunityQuotation> util = new ExcelUtil<OpportunityQuotation>(OpportunityQuotation.class);
        util.exportExcel(response, list, "商机产品报价数据");
    }

    /**
     * 获取商机产品报价详细信息
     */
    @RequiresPermissions("crm:opportunityQuotation:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityQuotationService.selectOpportunityQuotationById(id));
    }

    /**
     * 新增商机产品报价
     */
    @RequiresPermissions("crm:opportunityQuotation:add")
    @Log(title = "商机产品报价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityQuotation opportunityQuotation)
    {
        return toAjax(opportunityQuotationService.insertOpportunityQuotation(opportunityQuotation));
    }

    /**
     * 修改商机产品报价
     */
    @RequiresPermissions("crm:opportunityQuotation:edit")
    @Log(title = "商机产品报价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityQuotation opportunityQuotation)
    {
        return toAjax(opportunityQuotationService.updateOpportunityQuotation(opportunityQuotation));
    }

    /**
     * 删除商机产品报价
     */
    @RequiresPermissions("crm:opportunityQuotation:remove")
    @Log(title = "商机产品报价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityQuotationService.deleteOpportunityQuotationByIds(ids));
    }
}
