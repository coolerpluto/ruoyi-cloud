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
import com.highgo.crm.domain.OpportunitySoftwareOperation;
import com.highgo.crm.service.IOpportunitySoftwareOperationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 商机应用运营信息Controller
 * 
 * @author eszhang
 * @date 2023-02-22
 */
@RestController
@RequestMapping("/oppSoftwareOperation")
public class OpportunitySoftwareOperationController extends BaseController
{
    @Autowired
    private IOpportunitySoftwareOperationService opportunitySoftwareOperationService;

    /**
     * 查询商机应用运营信息列表
     */
    @RequiresPermissions("crm:oppSoftwareOperation:list")
    @GetMapping("/list")
    public TableDataInfo list(OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        startPage();
        List<OpportunitySoftwareOperation> list = opportunitySoftwareOperationService.selectOpportunitySoftwareOperationList(opportunitySoftwareOperation);
        return getDataTable(list);
    }

    /**
     * 导出商机应用运营信息列表
     */
    @RequiresPermissions("crm:oppSoftwareOperation:export")
    @Log(title = "商机应用运营信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        List<OpportunitySoftwareOperation> list = opportunitySoftwareOperationService.selectOpportunitySoftwareOperationList(opportunitySoftwareOperation);
        ExcelUtil<OpportunitySoftwareOperation> util = new ExcelUtil<OpportunitySoftwareOperation>(OpportunitySoftwareOperation.class);
        util.exportExcel(response, list, "商机应用运营信息数据");
    }

    /**
     * 获取商机应用运营信息详细信息
     */
    @RequiresPermissions("crm:oppSoftwareOperation:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(opportunitySoftwareOperationService.selectOpportunitySoftwareOperationById(id));
    }

    /**
     * 新增商机应用运营信息
     */
    @RequiresPermissions("crm:oppSoftwareOperation:add")
    @Log(title = "商机应用运营信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        return toAjax(opportunitySoftwareOperationService.insertOpportunitySoftwareOperation(opportunitySoftwareOperation));
    }

    /**
     * 修改商机应用运营信息
     */
    @RequiresPermissions("crm:oppSoftwareOperation:edit")
    @Log(title = "商机应用运营信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OpportunitySoftwareOperation opportunitySoftwareOperation)
    {
        return toAjax(opportunitySoftwareOperationService.updateOpportunitySoftwareOperation(opportunitySoftwareOperation));
    }

    /**
     * 删除商机应用运营信息
     */
    @RequiresPermissions("crm:oppSoftwareOperation:remove")
    @Log(title = "商机应用运营信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(opportunitySoftwareOperationService.deleteOpportunitySoftwareOperationByIds(ids));
    }
}
