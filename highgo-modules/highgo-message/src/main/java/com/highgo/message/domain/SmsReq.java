package com.highgo.message.domain;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.utils.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class SmsReq
{
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号码格式错误")
    @Pattern(regexp = "^[0-9]{11}$", message = "手机号码格式错误")
    private String tel;
    /**
     * 操作者
     */
    @NotBlank
    private String operator;

    /**
     * 缓存唯一标识
     */
    private String cacheKey;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 自定义参数
     */
    private JSONObject params;
    /**
     * 是否需要返回code
     */
    private String needCodeBack;

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getCacheKey()
    {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey)
    {
        this.cacheKey = cacheKey;
    }

    public String getTemplateCode()
    {
        return templateCode;
    }

    public void setTemplateCode(String templateCode)
    {
        this.templateCode = templateCode;
    }

    public JSONObject getParams()
    {
        return params;
    }

    public void setParams(JSONObject params)
    {
        this.params = params;
    }

    public String getNeedCodeBack()
    {
        return StringUtils.isEmpty(needCodeBack) ? "0" : needCodeBack;
    }

    public void setNeedCodeBack(String needCodeBack)
    {
        this.needCodeBack = needCodeBack;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }
}
