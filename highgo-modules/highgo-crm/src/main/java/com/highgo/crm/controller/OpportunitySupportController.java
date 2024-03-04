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
import com.highgo.crm.domain.OpportunitySupport;
import com.highgo.crm.service.IOpportunitySupportService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机支持人Controller
 *
 * @author eszhang
 * @date 2023-03-14
 */
@RestController
@RequestMapping("/opportunitySupport")
public class OpportunitySupportController extends BaseController
{
    @Autowired
    private IOpportunitySupportService opportunitySupportService;

    /**
     * 查询商机支持人列表
     */
    @RequiresPermissions("crm:opportunitySupport:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunitySupport opportunitySupport)
    {
        startPage();
        List<OpportunitySupport> list = opportunitySupportService.selectOpportunitySupportList(opportunitySupport);
        return getDataTable(list);
    }

    /**
     * 导出商机支持人列表
     */
    @RequiresPermissions("crm:opportunitySupport:export")
    @Log(title = "商机支持人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunitySupport opportunitySupport)
    {
        List<OpportunitySupport> list = opportunitySupportService.selectOpportunitySupportList(opportunitySupport);
        ExcelUtil<OpportunitySupport> util = new ExcelUtil<OpportunitySupport>(OpportunitySupport.class);
        util.exportExcel(response, list, "商机支持人数据");
    }

    /**
     * 获取商机支持人详细信息
     */
    @RequiresPermissions("crm:opportunitySupport:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunitySupportService.selectOpportunitySupportById(id));
    }

    /**
     * 新增商机支持人
     */
    @RequiresPermissions("crm:opportunitySupport:add")
    @Log(title = "商机支持人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunitySupport opportunitySupport)
    {
        return toAjax(opportunitySupportService.insertOpportunitySupport(opportunitySupport));
    }

    /**
     * 修改商机支持人
     */
    @RequiresPermissions("crm:opportunitySupport:edit")
    @Log(title = "商机支持人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunitySupport opportunitySupport)
    {
        return toAjax(opportunitySupportService.updateOpportunitySupport(opportunitySupport));
    }

    /**
     * 删除商机支持人
     */
    @RequiresPermissions("crm:opportunitySupport:remove")
    @Log(title = "商机支持人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunitySupportService.deleteOpportunitySupportByIds(ids));
    }
}
