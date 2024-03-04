package com.highgo.medium.controller;

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
import com.highgo.medium.domain.TProdVersionInfo;
import com.highgo.medium.service.ITProdVersionInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 产品版本信息Controller
 * 
 * @author esz
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/prod_v")
public class TProdVersionInfoController extends BaseController
{
    @Autowired
    private ITProdVersionInfoService tProdVersionInfoService;

    /**
     * 查询产品版本信息列表
     */
    @RequiresPermissions("medium:prod_v:list")
    @GetMapping("/list")
    public TableDataInfo list(TProdVersionInfo tProdVersionInfo)
    {
        startPage();
        List<TProdVersionInfo> list = tProdVersionInfoService.selectTProdVersionInfoList(tProdVersionInfo);
        return getDataTable(list);
    }

    /**
     * 导出产品版本信息列表
     */
    @RequiresPermissions("medium:prod_v:export")
    @Log(title = "产品版本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TProdVersionInfo tProdVersionInfo)
    {
        List<TProdVersionInfo> list = tProdVersionInfoService.selectTProdVersionInfoList(tProdVersionInfo);
        ExcelUtil<TProdVersionInfo> util = new ExcelUtil<TProdVersionInfo>(TProdVersionInfo.class);
        util.exportExcel(response, list, "产品版本信息数据");
    }

    /**
     * 获取产品版本信息详细信息
     */
    @RequiresPermissions("medium:prod_v:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tProdVersionInfoService.selectTProdVersionInfoById(id));
    }

    /**
     * 新增产品版本信息
     */
    @RequiresPermissions("medium:prod_v:add")
    @Log(title = "产品版本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProdVersionInfo tProdVersionInfo)
    {
        return toAjax(tProdVersionInfoService.insertTProdVersionInfo(tProdVersionInfo));
    }

    /**
     * 修改产品版本信息
     */
    @RequiresPermissions("medium:prod_v:edit")
    @Log(title = "产品版本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProdVersionInfo tProdVersionInfo)
    {
        return toAjax(tProdVersionInfoService.updateTProdVersionInfo(tProdVersionInfo));
    }

    /**
     * 删除产品版本信息
     */
    @RequiresPermissions("medium:prod_v:remove")
    @Log(title = "产品版本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tProdVersionInfoService.deleteTProdVersionInfoByIds(ids));
    }
}
