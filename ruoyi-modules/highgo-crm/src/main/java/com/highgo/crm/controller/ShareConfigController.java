package com.highgo.crm.controller;

import com.highgo.crm.domain.ShareConfig;
import com.highgo.crm.service.IShareConfigService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
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
 * 共享规则配置Controller
 *
 * @author eszhang
 * @date 2023-01-17
 */
@RestController
@RequestMapping("/shareconfig")
public class ShareConfigController extends BaseController {
    @Autowired
    private IShareConfigService shareConfigService;

    /**
     * 查询共享规则配置列表
     */
    @RequiresPermissions("crm:shareconfig:list")
    @GetMapping("/list")
    public TableDataInfo list(ShareConfig shareConfig) {
        startPage();
        List<ShareConfig> list = shareConfigService.selectShareConfigList(shareConfig);
        return getDataTable(list);
    }

    /**
     * 导出共享规则配置列表
     */
    @RequiresPermissions("crm:shareconfig:export")
    @Log(title = "共享规则配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShareConfig shareConfig) {
        List<ShareConfig> list = shareConfigService.selectShareConfigList(shareConfig);
        ExcelUtil<ShareConfig> util = new ExcelUtil<ShareConfig>(ShareConfig.class);
        util.exportExcel(response, list, "共享规则配置数据");
    }

    /**
     * 获取共享规则配置详细信息
     */
    @RequiresPermissions("crm:shareconfig:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(shareConfigService.selectShareConfigById(id));
    }

    /**
     * 新增共享规则配置
     */
    @RequiresPermissions("crm:shareconfig:add")
    @Log(title = "共享规则配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShareConfig shareConfig) {
        shareConfig.setCreateBy(SecurityUtils.getUsername());
        return toAjax(shareConfigService.insertShareConfig(shareConfig));
    }

    /**
     * 修改共享规则配置
     */
    @RequiresPermissions("crm:shareconfig:edit")
    @Log(title = "共享规则配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShareConfig shareConfig) {
        shareConfig.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(shareConfigService.updateShareConfig(shareConfig));
    }

    /**
     * 删除共享规则配置
     */
    @RequiresPermissions("crm:shareconfig:remove")
    @Log(title = "共享规则配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(shareConfigService.deleteShareConfigByIds(ids));
    }
}
