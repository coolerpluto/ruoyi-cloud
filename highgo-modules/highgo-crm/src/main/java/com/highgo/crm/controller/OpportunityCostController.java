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
import com.highgo.crm.domain.OpportunityCost;
import com.highgo.crm.service.IOpportunityCostService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机花费Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppCost")
public class OpportunityCostController extends BaseController
{
    @Autowired
    private IOpportunityCostService opportunityCostService;

    /**
     * 查询商机花费列表
     */
    @RequiresPermissions("crm:oppCost:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunityCost opportunityCost)
    {
        startPage();
        List<OpportunityCost> list = opportunityCostService.selectOpportunityCostList(opportunityCost);
        return getDataTable(list);
    }

    /**
     * 导出商机花费列表
     */
    @RequiresPermissions("crm:oppCost:export")
    @Log(title = "商机花费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunityCost opportunityCost)
    {
        List<OpportunityCost> list = opportunityCostService.selectOpportunityCostList(opportunityCost);
        ExcelUtil<OpportunityCost> util = new ExcelUtil<OpportunityCost>(OpportunityCost.class);
        util.exportExcel(response, list, "商机花费数据");
    }

    /**
     * 获取商机花费详细信息
     */
    @RequiresPermissions("crm:oppCost:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunityCostService.selectOpportunityCostById(id));
    }

    /**
     * 新增商机花费
     */
    @RequiresPermissions("crm:oppCost:add")
    @Log(title = "商机花费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunityCost opportunityCost)
    {
        return toAjax(opportunityCostService.insertOpportunityCost(opportunityCost));
    }

    /**
     * 修改商机花费
     */
    @RequiresPermissions("crm:oppCost:edit")
    @Log(title = "商机花费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunityCost opportunityCost)
    {
        return toAjax(opportunityCostService.updateOpportunityCost(opportunityCost));
    }

    /**
     * 删除商机花费
     */
    @RequiresPermissions("crm:oppCost:remove")
    @Log(title = "商机花费", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunityCostService.deleteOpportunityCostByIds(ids));
    }
}
