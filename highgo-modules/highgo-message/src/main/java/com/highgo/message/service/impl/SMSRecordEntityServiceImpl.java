package com.highgo.message.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.highgo.message.config.SMSConfig;
import com.highgo.message.domain.SMSRecordEntity;
import com.highgo.message.domain.SmsReq;
import com.highgo.message.mapper.SMSRecordEntityMapper;
import com.highgo.message.service.ISMSRecordEntityService;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.RandomUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 短信记录Service业务层处理
 *
 * @author eszhang
 * @date 2023-05-11
 */
@Service
public class SMSRecordEntityServiceImpl implements ISMSRecordEntityService
{
    @Autowired
    private SMSRecordEntityMapper sMSRecordEntityMapper;

    /**
     * 查询短信记录
     *
     * @param id 短信记录主键
     * @return 短信记录
     */
    @Override
    public SMSRecordEntity selectSMSRecordEntityById(Long id)
    {
        return sMSRecordEntityMapper.selectSMSRecordEntityById(id);
    }

    /**
     * 查询短信记录列表
     *
     * @param sMSRecordEntity 短信记录
     * @return 短信记录
     */
    @Override
    public List<SMSRecordEntity> selectSMSRecordEntityList(SMSRecordEntity sMSRecordEntity)
    {
        return sMSRecordEntityMapper.selectSMSRecordEntityList(sMSRecordEntity);
    }

    /**
     * 新增短信记录
     *
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    @Override
    public int insertSMSRecordEntity(SMSRecordEntity sMSRecordEntity)
    {
        sMSRecordEntity.setCreateTime(DateUtils.getNowDate());
        return sMSRecordEntityMapper.insertSMSRecordEntity(sMSRecordEntity);
    }

    /**
     * 修改短信记录
     *
     * @param sMSRecordEntity 短信记录
     * @return 结果
     */
    @Override
    public int updateSMSRecordEntity(SMSRecordEntity sMSRecordEntity)
    {
        return sMSRecordEntityMapper.updateSMSRecordEntity(sMSRecordEntity);
    }

    /**
     * 批量删除短信记录
     *
     * @param ids 需要删除的短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSMSRecordEntityByIds(Long[] ids)
    {
        return sMSRecordEntityMapper.deleteSMSRecordEntityByIds(ids);
    }

    /**
     * 删除短信记录信息
     *
     * @param id 短信记录主键
     * @return 结果
     */
    @Override
    public int deleteSMSRecordEntityById(Long id)
    {
        return sMSRecordEntityMapper.deleteSMSRecordEntityById(id);
    }

    @Override
    public JSONObject message(SmsReq smsReq)
    {
        SMSRecordEntity entity = packageInsertEntity(smsReq);
        JSONObject res = new JSONObject();
        try
        {
            SendSmsResponse sendSmsResponse = aliSmsSend(smsReq.getTel(), smsReq.getTemplateCode(), smsReq.getParams());
            packageAfterSend(entity, res, sendSmsResponse);
        }
        catch (Exception e)
        {
            entity.setResMessage(e.getMessage().substring(99));
            entity.setResCode("1");
            res.put("message", "发送失败");
        }
        sMSRecordEntityMapper.insertSMSRecordEntity(entity);
        return res;
    }

    private void packageAfterSend(SMSRecordEntity entity, JSONObject res, SendSmsResponse sendSmsResponse)
    {
        SendSmsResponseBody body = sendSmsResponse.getBody();
        entity.setResMessage(body.getMessage());
        entity.setResCode(body.getCode());
        entity.setResponseJson(body.toString());
        if (Objects.equals(body.getCode(), "OK"))
        {
            res.put("message", "验证码发送成功");
        }
        else
        {
            res.put("message", "验证码发送失败");
        }
    }

    @Autowired
    private RedisService redisService;
    @Autowired
    private Client client;
    @Autowired
    private SMSConfig smsConfig;

    @Override
    public JSONObject captcha(SmsReq smsReq)
    {
        if (StringUtils.isEmpty(smsReq.getTemplateCode()))
        {
            JSONObject res = new JSONObject();
            res.put("操作失败", "未正确配置短信模板");
            return res;
        }
        SMSRecordEntity entity = packageInsertEntity(smsReq);

        String code = RandomStringUtils.randomNumeric(6);
        JSONObject smsCaptcha = new JSONObject();
        smsCaptcha.put("tel", smsReq.getTel());
        smsCaptcha.put("code", code);
        String cacheKey = smsReq.getCacheKey();
        if (StringUtils.isEmpty(cacheKey))
        {
            cacheKey = RandomUtils.generateString(8);
        }
        redisService.setCacheObject(getCacheKey(cacheKey), smsCaptcha, Long.valueOf(smsConfig.getCaptchaExpiration()), TimeUnit.SECONDS);

        JSONObject res = new JSONObject();
        if (StringUtils.equals("0", smsReq.getNeedCodeBack()))
        {
            // 默认返回去
            res.put("expiration", smsConfig.getCaptchaExpiration());
            res.put("cacheKey", cacheKey);
            res.put("captchaCode", code);
        }
        try
        {
            SendSmsResponse sendSmsResponse = aliSmsSend(smsReq.getTel(), smsReq.getTemplateCode(), smsCaptcha);
            packageAfterSend(entity, res, sendSmsResponse);
        }
        catch (Exception e)
        {
            entity.setResMessage(e.getMessage().substring(499));
            entity.setResCode("1");
            res.put("message", "验证码发送失败");
        }
        sMSRecordEntityMapper.insertSMSRecordEntity(entity);
        return res;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey)
    {
        return CacheConstants.CAPTCHA_CODE_KEY + configKey;
    }

    private SMSRecordEntity packageInsertEntity(SmsReq smsReq)
    {
        SMSRecordEntity entity = new SMSRecordEntity();
        entity.setTemplateCode(smsReq.getTemplateCode());
        entity.setPhoneNumber(smsReq.getTel());

        entity.setCacheKey(smsReq.getCacheKey());
        entity.setCreateTime(DateUtils.getNowDate());
        entity.setCreateBy(smsReq.getOperator());
        if (null != smsReq.getParams())
        {
            entity.setTemplateParam(smsReq.getParams().toJSONString());
        }

        return entity;
    }


    private SendSmsResponse aliSmsSend(String tel, String TemplateCode, JSONObject templateParams) throws Exception
    {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(tel)
                .setSignName(smsConfig.getSignature())
                .setTemplateCode(TemplateCode);
        if (StringUtils.isNotEmpty(templateParams))
        {
            sendSmsRequest.setTemplateParam(templateParams.toJSONString());
        }
        return client.sendSms(sendSmsRequest);
    }

    @Override
    public JSONObject captchaValidate(SmsReq smsReq)
    {
        JSONObject validateRes = new JSONObject();
        validateRes.put("result", false);
        if (null == smsReq.getCacheKey())
        {
            return validateRes;
        }
        JSONObject param = smsReq.getParams();
        if (null == param)
        {
            return validateRes;
        }
        String captchaValue = (String) param.get("captchaValue");
        if (StringUtils.isEmpty(captchaValue))
        {
            return validateRes;
        }
        JSONObject smsCaptcha = redisService.getCacheObject(getCacheKey(smsReq.getCacheKey()));
        if (null == smsCaptcha)
        {
            return validateRes;
        }
        String captchaCache = (String) smsCaptcha.get("code");
        validateRes.put("result", StringUtils.equals(captchaCache, captchaValue));
        redisService.deleteObject(smsReq.getCacheKey());
        return validateRes;
    }
}
