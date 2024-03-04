package com.highgo.message.controller;

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
import com.highgo.message.domain.WebsiteUrlRecord;
import com.highgo.message.service.IWebsiteUrlRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 内部网站收藏Controller
 * 
 * @author esz
 * @date 2023-07-05
 */
@RestController
@RequestMapping("/website")
public class WebsiteUrlRecordController extends BaseController
{
    @Autowired
    private IWebsiteUrlRecordService websiteUrlRecordService;

    /**
     * 查询内部网站收藏列表
     */
    @RequiresPermissions("message:website:list")
    @GetMapping("/list")
    public TableDataInfo list(WebsiteUrlRecord websiteUrlRecord)
    {
        startPage();
        List<WebsiteUrlRecord> list = websiteUrlRecordService.selectWebsiteUrlRecordList(websiteUrlRecord);
        return getDataTable(list);
    }

    /**
     * 导出内部网站收藏列表
     */
    @RequiresPermissions("message:website:export")
    @Log(title = "内部网站收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WebsiteUrlRecord websiteUrlRecord)
    {
        List<WebsiteUrlRecord> list = websiteUrlRecordService.selectWebsiteUrlRecordList(websiteUrlRecord);
        ExcelUtil<WebsiteUrlRecord> util = new ExcelUtil<WebsiteUrlRecord>(WebsiteUrlRecord.class);
        util.exportExcel(response, list, "内部网站收藏数据");
    }

    /**
     * 获取内部网站收藏详细信息
     */
    @RequiresPermissions("message:website:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(websiteUrlRecordService.selectWebsiteUrlRecordById(id));
    }

    /**
     * 新增内部网站收藏
     */
    @RequiresPermissions("message:website:add")
    @Log(title = "内部网站收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WebsiteUrlRecord websiteUrlRecord)
    {
        return toAjax(websiteUrlRecordService.insertWebsiteUrlRecord(websiteUrlRecord));
    }

    /**
     * 修改内部网站收藏
     */
    @RequiresPermissions("message:website:edit")
    @Log(title = "内部网站收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WebsiteUrlRecord websiteUrlRecord)
    {
        return toAjax(websiteUrlRecordService.updateWebsiteUrlRecord(websiteUrlRecord));
    }

    /**
     * 删除内部网站收藏
     */
    @RequiresPermissions("message:website:remove")
    @Log(title = "内部网站收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(websiteUrlRecordService.deleteWebsiteUrlRecordByIds(ids));
    }
}
