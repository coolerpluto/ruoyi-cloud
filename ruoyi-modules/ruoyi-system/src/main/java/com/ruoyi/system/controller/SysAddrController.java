package com.ruoyi.system.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.system.domain.SysConfig;
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
import com.ruoyi.system.domain.SysAddr;
import com.ruoyi.system.service.ISysAddrService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;

/**
 * 地址联动Controller
 * 
 * @author eszhang
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/addr")
public class SysAddrController extends BaseController
{
    @Autowired
    private ISysAddrService sysAddrService;

    /**
     * 查询地址联动列表
     */
    @RequiresPermissions("system:addr:list")
    @GetMapping("/list")
    public AjaxResult list(SysAddr sysAddr)
    {
        List<SysAddr> list = sysAddrService.selectSysAddrList(sysAddr);
        return success(list);
    }
    /**
     * 获取参数配置列表
     */
    @RequiresPermissions("system:addr:list")
    @GetMapping("/listPage")
    public TableDataInfo listPage(SysAddr sysAddr)
    {
        startPage();
        List<SysAddr> list = sysAddrService.selectSysAddrList(sysAddr);
        return getDataTable(list);
    }
    /**
     * 导出地址联动列表
     */
    @RequiresPermissions("system:addr:export")
    @Log(title = "地址联动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysAddr sysAddr)
    {
        List<SysAddr> list = sysAddrService.selectSysAddrList(sysAddr);
        ExcelUtil<SysAddr> util = new ExcelUtil<SysAddr>(SysAddr.class);
        util.exportExcel(response, list, "地址联动数据");
    }

    /**
     * 获取地址联动详细信息
     */
    @RequiresPermissions("system:addr:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(sysAddrService.selectSysAddrById(id));
    }

    /**
     * 新增地址联动
     */
    @RequiresPermissions("system:addr:add")
    @Log(title = "地址联动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysAddr sysAddr)
    {
        return toAjax(sysAddrService.insertSysAddr(sysAddr));
    }

    /**
     * 修改地址联动
     */
    @RequiresPermissions("system:addr:edit")
    @Log(title = "地址联动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysAddr sysAddr)
    {
        return toAjax(sysAddrService.updateSysAddr(sysAddr));
    }

    /**
     * 删除地址联动
     */
    @RequiresPermissions("system:addr:remove")
    @Log(title = "地址联动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sysAddrService.deleteSysAddrByIds(ids));
    }
}
