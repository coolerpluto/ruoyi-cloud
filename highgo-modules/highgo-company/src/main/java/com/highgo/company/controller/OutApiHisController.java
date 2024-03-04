package com.highgo.company.controller;

import java.util.List;
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
import com.highgo.company.domain.OutApiHis;
import com.highgo.company.service.IOutApiHisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 接口调用Controller
 *
 * @author eszhanga
 * @date 2023-12-06
 */
@RestController
@RequestMapping("/apiHis")
public class OutApiHisController extends BaseController
{
    @Autowired
    private IOutApiHisService outApiHisService;

    /**
     * 查询接口调用列表
     */
    @RequiresPermissions("company:apiHis:list")
    @GetMapping("/list")
    public TableDataInfo list(OutApiHis outApiHis)
    {
        startPage();
        List<OutApiHis> list = outApiHisService.selectOutApiHisList(outApiHis);
        return getDataTable(list);
    }

    /**
     * 导出接口调用列表
     */
    @RequiresPermissions("company:apiHis:export")
    @Log(title = "接口调用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutApiHis outApiHis)
    {
        List<OutApiHis> list = outApiHisService.selectOutApiHisList(outApiHis);
        ExcelUtil<OutApiHis> util = new ExcelUtil<OutApiHis>(OutApiHis.class);
        util.exportExcel(response, list, "接口调用数据");
    }

    /**
     * 获取接口调用详细信息
     */
    @RequiresPermissions("company:apiHis:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(outApiHisService.selectOutApiHisById(id));
    }

    /**
     * 新增接口调用
     */
    @RequiresPermissions("company:apiHis:add")
    @Log(title = "接口调用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutApiHis outApiHis)
    {
        return toAjax(outApiHisService.insertOutApiHis(outApiHis));
    }

    /**
     * 修改接口调用
     */
    @RequiresPermissions("company:apiHis:edit")
    @Log(title = "接口调用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutApiHis outApiHis)
    {
        return toAjax(outApiHisService.updateOutApiHis(outApiHis));
    }

    /**
     * 删除接口调用
     */
    @RequiresPermissions("company:apiHis:remove")
    @Log(title = "接口调用", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(outApiHisService.deleteOutApiHisByIds(ids));
    }
}
