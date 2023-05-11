package com.highgo.message.controller;

import com.highgo.message.domain.SMSRecordEntity;
import com.highgo.message.domain.SmsReq;
import com.highgo.message.service.ISMSRecordEntityService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
 * 短信记录Controller
 *
 * @author eszhang
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/sms")
public class SMSRecordEntityController extends BaseController
{
    @Autowired
    private ISMSRecordEntityService sMSRecordEntityService;

    /**
     * 查询短信记录列表
     */
    @RequiresPermissions("message:sms:list")
    @GetMapping("/list")
    public TableDataInfo list(SMSRecordEntity sMSRecordEntity)
    {
        startPage();
        List<SMSRecordEntity> list = sMSRecordEntityService.selectSMSRecordEntityList(sMSRecordEntity);
        return getDataTable(list);
    }

    /**
     * 导出短信记录列表
     */
    @RequiresPermissions("message:sms:export")
    @Log(title = "短信记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SMSRecordEntity sMSRecordEntity)
    {
        List<SMSRecordEntity> list = sMSRecordEntityService.selectSMSRecordEntityList(sMSRecordEntity);
        ExcelUtil<SMSRecordEntity> util = new ExcelUtil<SMSRecordEntity>(SMSRecordEntity.class);
        util.exportExcel(response, list, "短信记录数据");
    }

    /**
     * 获取短信记录详细信息
     */
    @RequiresPermissions("message:sms:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sMSRecordEntityService.selectSMSRecordEntityById(id));
    }

    /**
     * 新增短信记录
     */
    @RequiresPermissions("message:sms:add")
    @Log(title = "短信记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SMSRecordEntity sMSRecordEntity)
    {
        return toAjax(sMSRecordEntityService.insertSMSRecordEntity(sMSRecordEntity));
    }

    /**
     * 修改短信记录
     */
    @RequiresPermissions("message:sms:edit")
    @Log(title = "短信记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SMSRecordEntity sMSRecordEntity)
    {
        return toAjax(sMSRecordEntityService.updateSMSRecordEntity(sMSRecordEntity));
    }

    /**
     * 删除短信记录
     */
    @RequiresPermissions("message:sms:remove")
    @Log(title = "短信记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sMSRecordEntityService.deleteSMSRecordEntityByIds(ids));
    }

    /**
     * 发短信
     */
    @PostMapping("/message")
    public AjaxResult message(@RequestBody @Validated SmsReq smsReq)
    {
        return AjaxResult.success(sMSRecordEntityService.message(smsReq));
    }

    /**
     * 发验证
     */
    @PostMapping("/captcha")
    public AjaxResult captcha(@RequestBody @Validated SmsReq smsReq)
    {
        return AjaxResult.success(sMSRecordEntityService.captcha(smsReq));
    }

    /**
     * 发短信
     */
    @PostMapping("/captchaValidate")
    public AjaxResult captchaValidate(@RequestBody @Validated SmsReq smsReq)
    {
        return AjaxResult.success(sMSRecordEntityService.captchaValidate(smsReq));
    }
}
