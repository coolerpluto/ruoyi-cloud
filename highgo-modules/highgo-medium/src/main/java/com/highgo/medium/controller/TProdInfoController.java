package com.highgo.medium.controller;

import com.highgo.medium.domain.TProdInfo;
import com.highgo.medium.service.ITProdInfoService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 产品信息Controller
 * 
 * @author esz
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/prod")
public class TProdInfoController extends BaseController
{
    @Autowired
    private ITProdInfoService tProdInfoService;

    /**
     * 查询产品信息列表
     */
    @RequiresPermissions("medium:prod:list")
    @GetMapping("/list")
    public TableDataInfo list(TProdInfo tProdInfo)
    {
        startPage();
        List<TProdInfo> list = tProdInfoService.selectTProdInfoList(tProdInfo);
        return getDataTable(list);
    }

    /**
     * 导出产品信息列表
     */
    @RequiresPermissions("medium:prod:export")
    @Log(title = "产品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TProdInfo tProdInfo)
    {
        List<TProdInfo> list = tProdInfoService.selectTProdInfoList(tProdInfo);
        ExcelUtil<TProdInfo> util = new ExcelUtil<TProdInfo>(TProdInfo.class);
        util.exportExcel(response, list, "产品信息数据");
    }

    /**
     * 获取产品信息详细信息
     */
    @RequiresPermissions("medium:prod:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tProdInfoService.selectTProdInfoById(id));
    }

    /**
     * 获取下拉树列表
     */
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(TProdInfo tProdInfo)
    {
        List<TProdInfo> prodInfos = tProdInfoService.selectListAsTree(tProdInfo.getSearchValue());
        return success(tProdInfoService.buildTreeSelect(prodInfos));
    }

    /**
     * 新增产品信息
     */
    @RequiresPermissions("medium:prod:add")
    @Log(title = "产品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TProdInfo tProdInfo)
    {
        return toAjax(tProdInfoService.insertTProdInfo(tProdInfo));
    }

    /**
     * 修改产品信息
     */
    @RequiresPermissions("medium:prod:edit")
    @Log(title = "产品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TProdInfo tProdInfo)
    {
        return toAjax(tProdInfoService.updateTProdInfo(tProdInfo));
    }

    /**
     * 删除产品信息
     */
    @RequiresPermissions("medium:prod:remove")
    @Log(title = "产品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tProdInfoService.deleteTProdInfoByIds(ids));
    }
}
