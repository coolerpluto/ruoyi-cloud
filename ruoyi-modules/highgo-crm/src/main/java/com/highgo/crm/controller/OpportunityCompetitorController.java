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
import com.highgo.crm.domain.OpportunityCompetitor;
import com.highgo.crm.service.IOpportunityCompetitorService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 竞争对手Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppCompetitor")
public class OpportunityCompetitorController extends BaseController
{
    @Autowired
    private IOpportunityCompetitorService opportunityCompetitorService;

    /**
     * 查询竞争对手列表
     */
    @RequiresPermissions("crm:oppCompetitor:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityCompetitor opportunityCompetitor)
    {
        startPage();
        List<OpportunityCompetitor> list = opportunityCompetitorService.selectOpportunityCompetitorList(opportunityCompetitor);
        return getDataTable(list);
    }

    /**
     * 导出竞争对手列表
     */
    @RequiresPermissions("crm:oppCompetitor:export")
    @Log(title = "竞争对手", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityCompetitor opportunityCompetitor)
    {
        List<OpportunityCompetitor> list = opportunityCompetitorService.selectOpportunityCompetitorList(opportunityCompetitor);
        ExcelUtil<OpportunityCompetitor> util = new ExcelUtil<OpportunityCompetitor>(OpportunityCompetitor.class);
        util.exportExcel(response, list, "竞争对手数据");
    }

    /**
     * 获取竞争对手详细信息
     */
    @RequiresPermissions("crm:oppCompetitor:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityCompetitorService.selectOpportunityCompetitorById(id));
    }

    /**
     * 新增竞争对手
     */
    @RequiresPermissions("crm:oppCompetitor:add")
    @Log(title = "竞争对手", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityCompetitor opportunityCompetitor)
    {
        return toAjax(opportunityCompetitorService.insertOpportunityCompetitor(opportunityCompetitor));
    }

    /**
     * 修改竞争对手
     */
    @RequiresPermissions("crm:oppCompetitor:edit")
    @Log(title = "竞争对手", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityCompetitor opportunityCompetitor)
    {
        return toAjax(opportunityCompetitorService.updateOpportunityCompetitor(opportunityCompetitor));
    }

    /**
     * 删除竞争对手
     */
    @RequiresPermissions("crm:oppCompetitor:remove")
    @Log(title = "竞争对手", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityCompetitorService.deleteOpportunityCompetitorByIds(ids));
    }
}
