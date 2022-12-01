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
import com.highgo.medium.domain.MediumFileInfo;
import com.highgo.medium.service.IMediumFileInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 介质文件信息记录Controller
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/medium")
public class MediumFileInfoController extends BaseController
{
    @Autowired
    private IMediumFileInfoService mediumFileInfoService;

    /**
     * 查询介质文件信息记录列表
     */
    @RequiresPermissions("medium:medium:list")
    @GetMapping("/list")
    public TableDataInfo list(MediumFileInfo mediumFileInfo)
    {
        startPage();
        List<MediumFileInfo> list = mediumFileInfoService.selectMediumFileInfoList(mediumFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出介质文件信息记录列表
     */
    @RequiresPermissions("medium:medium:export")
    @Log(title = "介质文件信息记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MediumFileInfo mediumFileInfo)
    {
        List<MediumFileInfo> list = mediumFileInfoService.selectMediumFileInfoList(mediumFileInfo);
        ExcelUtil<MediumFileInfo> util = new ExcelUtil<MediumFileInfo>(MediumFileInfo.class);
        util.exportExcel(response, list, "介质文件信息记录数据");
    }

    /**
     * 获取介质文件信息记录详细信息
     */
    @RequiresPermissions("medium:medium:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mediumFileInfoService.selectMediumFileInfoById(id));
    }

    /**
     * 新增介质文件信息记录
     */
    @RequiresPermissions("medium:medium:add")
    @Log(title = "介质文件信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MediumFileInfo mediumFileInfo)
    {
        return toAjax(mediumFileInfoService.insertMediumFileInfo(mediumFileInfo));
    }

    /**
     * 修改介质文件信息记录
     */
    @RequiresPermissions("medium:medium:edit")
    @Log(title = "介质文件信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MediumFileInfo mediumFileInfo)
    {
        return toAjax(mediumFileInfoService.updateMediumFileInfo(mediumFileInfo));
    }

    /**
     * 删除介质文件信息记录
     */
    @RequiresPermissions("medium:medium:remove")
    @Log(title = "介质文件信息记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mediumFileInfoService.deleteMediumFileInfoByIds(ids));
    }
}
