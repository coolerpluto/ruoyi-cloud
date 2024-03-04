package com.highgo.crm.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
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
import com.highgo.crm.domain.OpportunityStageTransferConfig;
import com.highgo.crm.service.IOpportunityStageTransferConfigService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机阶段流转配置Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppStageTransferConfig")
public class OpportunityStageTransferConfigController extends BaseController
{
    @Autowired
    private IOpportunityStageTransferConfigService opportunityStageTransferConfigService;

    /**
     * 查询商机阶段流转配置列表
     */
    @RequiresPermissions("crm:oppStageTransferConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        startPage();
        List<OpportunityStageTransferConfig> list = opportunityStageTransferConfigService.selectOpportunityStageTransferConfigList(opportunityStageTransferConfig);
        return getDataTable(list);
    }

    /**
     * 导出商机阶段流转配置列表
     */
    @RequiresPermissions("crm:oppStageTransferConfig:export")
    @Log(title = "商机阶段流转配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        List<OpportunityStageTransferConfig> list = opportunityStageTransferConfigService.selectOpportunityStageTransferConfigList(opportunityStageTransferConfig);
        ExcelUtil<OpportunityStageTransferConfig> util = new ExcelUtil<OpportunityStageTransferConfig>(OpportunityStageTransferConfig.class);
        util.exportExcel(response, list, "商机阶段流转配置数据");
    }

    /**
     * 获取商机阶段流转配置详细信息
     */
    @RequiresPermissions("crm:oppStageTransferConfig:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(opportunityStageTransferConfigService.selectOpportunityStageTransferConfigById(id));
    }

    @GetMapping(value = "/stageTransferConfig")
    public AjaxResult stageTransferConfig(OpportunityStageTransferConfig oppStageTransferConfig)
    {
        Map<Integer,String> activeConfig = opportunityStageTransferConfigService.stageTransferConfig();
        return success(activeConfig);
    }

    /**
     * 新增商机阶段流转配置
     */
    @RequiresPermissions("crm:oppStageTransferConfig:add")
    @Log(title = "商机阶段流转配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        return toAjax(opportunityStageTransferConfigService.insertOpportunityStageTransferConfig(opportunityStageTransferConfig));
    }

    /**
     * 修改商机阶段流转配置
     */
    @RequiresPermissions("crm:oppStageTransferConfig:edit")
    @Log(title = "商机阶段流转配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityStageTransferConfig opportunityStageTransferConfig)
    {
        return toAjax(opportunityStageTransferConfigService.updateOpportunityStageTransferConfig(opportunityStageTransferConfig));
    }

    /**
     * 删除商机阶段流转配置
     */
    @RequiresPermissions("crm:oppStageTransferConfig:remove")
    @Log(title = "商机阶段流转配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(opportunityStageTransferConfigService.deleteOpportunityStageTransferConfigByIds(ids));
    }
}
