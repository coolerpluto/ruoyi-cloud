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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.highgo.medium.domain.TLicToolInfo;
import com.highgo.medium.service.ITLicToolInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * License工具信息Controller
 * 
 * @author esz
 * @date 2023-06-28
 */
@RestController
@RequestMapping("/lic_tool")
public class TLicToolInfoController extends BaseController
{
    @Autowired
    private ITLicToolInfoService tLicToolInfoService;

    /**
     * 查询License工具信息列表
     */
    @RequiresPermissions("medium:lic_tool:list")
    @GetMapping("/list")
    public TableDataInfo list(TLicToolInfo tLicToolInfo)
    {
        startPage();
        List<TLicToolInfo> list = tLicToolInfoService.selectTLicToolInfoList(tLicToolInfo);
        return getDataTable(list);
    }

    /**
     * 导出License工具信息列表
     */
    @RequiresPermissions("medium:lic_tool:export")
    @Log(title = "License工具信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TLicToolInfo tLicToolInfo)
    {
        List<TLicToolInfo> list = tLicToolInfoService.selectTLicToolInfoList(tLicToolInfo);
        ExcelUtil<TLicToolInfo> util = new ExcelUtil<TLicToolInfo>(TLicToolInfo.class);
        util.exportExcel(response, list, "License工具信息数据");
    }

    /**
     * 获取License工具信息详细信息
     */
    @RequiresPermissions("medium:lic_tool:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tLicToolInfoService.selectTLicToolInfoById(id));
    }

    /**
     * 新增License工具信息 涉及到文件 去除
     */
    @RequiresPermissions("medium:lic_tool:add")
    @Log(title = "License工具信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TLicToolInfo tLicToolInfo)
    {
        return toAjax(tLicToolInfoService.insertTLicToolInfo(tLicToolInfo));
    }
    @RequiresPermissions("medium:lic_tool:add")
    @Log(title = "License工具信息", businessType = BusinessType.INSERT)
    @PostMapping("/withFile")
    public AjaxResult addWithFile(TLicToolInfo tLicToolInfo,
                                  MultipartFile macFile,
                                  MultipartFile toolFile)
    {
        return toAjax(tLicToolInfoService.insertTLicToolInfoWithFile(tLicToolInfo,toolFile,macFile));
    }
    /**
     * 修改License工具信息
     */
    @RequiresPermissions("medium:lic_tool:edit")
    @Log(title = "License工具信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TLicToolInfo tLicToolInfo)
    {
        return toAjax(tLicToolInfoService.updateTLicToolInfo(tLicToolInfo));
    }
    @RequiresPermissions("medium:lic_tool:edit")
    @Log(title = "License工具信息", businessType = BusinessType.UPDATE)
    @PutMapping("/withFile")
    public AjaxResult editWithFile(TLicToolInfo tLicToolInfo,
                                   MultipartFile macFile,
                                   MultipartFile toolFile)
    {
        return toAjax(tLicToolInfoService.updateTLicToolInfoWithFile(tLicToolInfo,toolFile,macFile));
    }
    /**
     * 删除License工具信息
     */
    @RequiresPermissions("medium:lic_tool:remove")
    @Log(title = "License工具信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tLicToolInfoService.deleteTLicToolInfoByIds(ids));
    }
}
