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
import com.highgo.message.domain.EmailRecordEntity;
import com.highgo.message.service.IEmailRecordEntityService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 邮件记录Controller
 * 
 * @author eszhang
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/email")
public class EmailRecordEntityController extends BaseController
{
    @Autowired
    private IEmailRecordEntityService emailRecordEntityService;

    /**
     * 查询邮件记录列表
     */
    @RequiresPermissions("message:email:list")
    @GetMapping("/list")
    public TableDataInfo list(EmailRecordEntity emailRecordEntity)
    {
        startPage();
        List<EmailRecordEntity> list = emailRecordEntityService.selectEmailRecordEntityList(emailRecordEntity);
        return getDataTable(list);
    }

    /**
     * 导出邮件记录列表
     */
    @RequiresPermissions("message:email:export")
    @Log(title = "邮件记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmailRecordEntity emailRecordEntity)
    {
        List<EmailRecordEntity> list = emailRecordEntityService.selectEmailRecordEntityList(emailRecordEntity);
        ExcelUtil<EmailRecordEntity> util = new ExcelUtil<EmailRecordEntity>(EmailRecordEntity.class);
        util.exportExcel(response, list, "邮件记录数据");
    }

    /**
     * 获取邮件记录详细信息
     */
    @RequiresPermissions("message:email:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(emailRecordEntityService.selectEmailRecordEntityById(id));
    }

    /**
     * 新增邮件记录
     */
    @RequiresPermissions("message:email:add")
    @Log(title = "邮件记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmailRecordEntity emailRecordEntity)
    {
        return toAjax(emailRecordEntityService.insertEmailRecordEntity(emailRecordEntity));
    }

    /**
     * 修改邮件记录
     */
    @RequiresPermissions("message:email:edit")
    @Log(title = "邮件记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmailRecordEntity emailRecordEntity)
    {
        return toAjax(emailRecordEntityService.updateEmailRecordEntity(emailRecordEntity));
    }

    /**
     * 删除邮件记录
     */
    @RequiresPermissions("message:email:remove")
    @Log(title = "邮件记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(emailRecordEntityService.deleteEmailRecordEntityByIds(ids));
    }
}
