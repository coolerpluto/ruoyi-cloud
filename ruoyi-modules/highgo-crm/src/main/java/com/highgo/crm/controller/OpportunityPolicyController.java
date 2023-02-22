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
import com.highgo.crm.domain.OpportunityPolicy;
import com.highgo.crm.service.IOpportunityPolicyService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机支持政策Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppPolicy")
public class OpportunityPolicyController extends BaseController
{
    @Autowired
    private IOpportunityPolicyService opportunityPolicyService;

    /**
     * 查询商机支持政策列表
     */
    @RequiresPermissions("crm:oppPolicy:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityPolicy opportunityPolicy)
    {
        startPage();
        List<OpportunityPolicy> list = opportunityPolicyService.selectOpportunityPolicyList(opportunityPolicy);
        return getDataTable(list);
    }

    /**
     * 导出商机支持政策列表
     */
    @RequiresPermissions("crm:oppPolicy:export")
    @Log(title = "商机支持政策", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityPolicy opportunityPolicy)
    {
        List<OpportunityPolicy> list = opportunityPolicyService.selectOpportunityPolicyList(opportunityPolicy);
        ExcelUtil<OpportunityPolicy> util = new ExcelUtil<OpportunityPolicy>(OpportunityPolicy.class);
        util.exportExcel(response, list, "商机支持政策数据");
    }

    /**
     * 获取商机支持政策详细信息
     */
    @RequiresPermissions("crm:oppPolicy:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityPolicyService.selectOpportunityPolicyById(id));
    }

    /**
     * 新增商机支持政策
     */
    @RequiresPermissions("crm:oppPolicy:add")
    @Log(title = "商机支持政策", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityPolicy opportunityPolicy)
    {
        return toAjax(opportunityPolicyService.insertOpportunityPolicy(opportunityPolicy));
    }

    /**
     * 修改商机支持政策
     */
    @RequiresPermissions("crm:oppPolicy:edit")
    @Log(title = "商机支持政策", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityPolicy opportunityPolicy)
    {
        return toAjax(opportunityPolicyService.updateOpportunityPolicy(opportunityPolicy));
    }

    /**
     * 删除商机支持政策
     */
    @RequiresPermissions("crm:oppPolicy:remove")
    @Log(title = "商机支持政策", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityPolicyService.deleteOpportunityPolicyByIds(ids));
    }
}
