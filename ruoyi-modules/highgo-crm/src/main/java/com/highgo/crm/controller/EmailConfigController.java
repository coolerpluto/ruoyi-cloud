package com.highgo.crm.controller;

import com.highgo.crm.domain.EmailConfig;
import com.highgo.crm.service.IEmailConfigService;
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
 * 邮箱设置Controller
 *
 * @author eszhang
 * @date 2023-01-17
 */
@RestController
@RequestMapping("/mailconfig")
public class EmailConfigController extends BaseController {
    @Autowired
    private IEmailConfigService emailConfigService;

    /**
     * 查询邮箱设置列表
     */
    @RequiresPermissions("crm:mailconfig:list")
    @GetMapping("/list")
    public TableDataInfo list(EmailConfig emailConfig) {
        startPage();
        List<EmailConfig> list = emailConfigService.selectEmailConfigList(emailConfig);
        return getDataTable(list);
    }

    /**
     * 导出邮箱设置列表
     */
    @RequiresPermissions("crm:mailconfig:export")
    @Log(title = "邮箱设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmailConfig emailConfig) {
        List<EmailConfig> list = emailConfigService.selectEmailConfigList(emailConfig);
        ExcelUtil<EmailConfig> util = new ExcelUtil<EmailConfig>(EmailConfig.class);
        util.exportExcel(response, list, "邮箱设置数据");
    }

    /**
     * 获取邮箱设置详细信息
     */
    @RequiresPermissions("crm:mailconfig:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(emailConfigService.selectEmailConfigById(id));
    }

    /**
     * 新增邮箱设置
     */
    @RequiresPermissions("crm:mailconfig:add")
    @Log(title = "邮箱设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmailConfig emailConfig) {
        emailConfig.setCreateBy(SecurityUtils.getUsername());
        return toAjax(emailConfigService.insertEmailConfig(emailConfig));
    }

    /**
     * 修改邮箱设置
     */
    @RequiresPermissions("crm:mailconfig:edit")
    @Log(title = "邮箱设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmailConfig emailConfig) {
        emailConfig.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(emailConfigService.updateEmailConfig(emailConfig));
    }

    /**
     * 删除邮箱设置
     */
    @RequiresPermissions("crm:mailconfig:remove")
    @Log(title = "邮箱设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(emailConfigService.deleteEmailConfigByIds(ids));
    }
}
