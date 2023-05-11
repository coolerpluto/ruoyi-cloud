package com.highgo.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 短信记录对象 sms_record
 * 
 * @author eszhang
 * @date 2023-05-11
 */
public class SMSRecordEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 模板Code */
    @Excel(name = "模板Code")
    private String templateCode;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phoneNumber;

    /** 模板其他参数 */
    @Excel(name = "模板其他参数")
    private String templateParam;

    /** 发送内容 */
    @Excel(name = "发送内容")
    private String value;

    /** 缓存key */
    @Excel(name = "缓存key")
    private String cacheKey;

    /** 返回信息 */
    @Excel(name = "返回信息")
    private String responseJson;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTemplateCode(String templateCode) 
    {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() 
    {
        return templateCode;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    public void setTemplateParam(String templateParam) 
    {
        this.templateParam = templateParam;
    }

    public String getTemplateParam() 
    {
        return templateParam;
    }
    public void setValue(String value) 
    {
        this.value = value;
    }

    public String getValue() 
    {
        return value;
    }
    public void setCacheKey(String cacheKey) 
    {
        this.cacheKey = cacheKey;
    }

    public String getCacheKey() 
    {
        return cacheKey;
    }
    public void setResponseJson(String responseJson) 
    {
        this.responseJson = responseJson;
    }

    public String getResponseJson() 
    {
        return responseJson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateCode", getTemplateCode())
            .append("phoneNumber", getPhoneNumber())
            .append("templateParam", getTemplateParam())
            .append("value", getValue())
            .append("cacheKey", getCacheKey())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("responseJson", getResponseJson())
            .toString();
    }
}
