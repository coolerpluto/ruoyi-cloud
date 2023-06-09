package com.ruoyi.gen.controller;

import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.gen.domain.GenDbSource;
import com.ruoyi.gen.service.IGenDbSourceService;
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
 * 代码生成数据源配置Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/gen/dbSource")
public class GenDbSourceController extends BaseController
{
    @Autowired
    private IGenDbSourceService genDbSourceService;

    /**
     * 查询代码生成数据源配置列表
     */
    @GetMapping("/list")
    public TableDataInfo list(GenDbSource genDbSource)
    {
        startPage();
        List<GenDbSource> list = genDbSourceService.selectGenDbSourceList(genDbSource);
        return getDataTable(list);
    }

    /**
     * 导出代码生成数据源配置列表
     */
    @Log(title = "代码生成数据源配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GenDbSource genDbSource)
    {
        List<GenDbSource> list = genDbSourceService.selectGenDbSourceList(genDbSource);
        ExcelUtil<GenDbSource> util = new ExcelUtil<GenDbSource>(GenDbSource.class);
        util.exportExcel(response, list, "代码生成数据源配置数据");
    }

    /**
     * 获取代码生成数据源配置详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(genDbSourceService.selectGenDbSourceById(id));
    }

    /**
     * 新增代码生成数据源配置
     */
    @Log(title = "代码生成数据源配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GenDbSource genDbSource)
    {
        return toAjax(genDbSourceService.insertGenDbSource(genDbSource));
    }

    /**
     * 修改代码生成数据源配置
     */
    @Log(title = "代码生成数据源配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GenDbSource genDbSource)
    {
        return toAjax(genDbSourceService.updateGenDbSource(genDbSource));
    }

    /**
     * 删除代码生成数据源配置
     */
    @Log(title = "代码生成数据源配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(genDbSourceService.deleteGenDbSourceByIds(ids));
    }
}
