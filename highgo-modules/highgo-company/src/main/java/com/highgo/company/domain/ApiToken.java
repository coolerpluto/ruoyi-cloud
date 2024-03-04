package com.highgo.company.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 接口授权token对象 api_token
 *
 *
 * @date 2023-12-05
 */
public class ApiToken extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String tokenId;

    /** token值 */
    @Excel(name = "token值")
    private String value;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 权限 */
    @Excel(name = "权限")
    private String scope;

    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }

    public String getTokenId()
    {
        return tokenId;
    }
    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getScope()
    {
        return scope;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("tokenId", getTokenId())
                .append("value", getValue())
                .append("status", getStatus())
                .append("scope", getScope())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}
