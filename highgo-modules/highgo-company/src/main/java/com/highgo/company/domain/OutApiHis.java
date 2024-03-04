package com.highgo.company.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 接口调用对象 out_api_his
 *
 * @author eszhanga
 * @date 2023-12-06
 */
public class OutApiHis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 请求url */
    @Excel(name = "请求url")
    private String url;

    /** 请求入参 */
    @Excel(name = "请求入参")
    private String reqParam;

    /** 返回结果 */
    @Excel(name = "返回结果")
    private String resResult;

    /** 请求IP */
    @Excel(name = "请求IP")
    private String reqHost;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setReqParam(String reqParam)
    {
        this.reqParam = reqParam;
    }

    public String getReqParam()
    {
        return reqParam;
    }
    public void setResResult(String resResult)
    {
        this.resResult = resResult;
    }

    public String getResResult()
    {
        return resResult;
    }
    public void setReqHost(String reqHost)
    {
        this.reqHost = reqHost;
    }

    public String getReqHost()
    {
        return reqHost;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("url", getUrl())
                .append("reqParam", getReqParam())
                .append("resResult", getResResult())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("reqHost", getReqHost())
                .toString();
    }
}
