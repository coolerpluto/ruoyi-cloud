package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机支持人对象 t_opportunity_support
 *
 * @author eszhang
 * @date 2023-03-14
 */
public class OpportunitySupport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 商机所在阶段 */
    @Excel(name = "商机所在阶段")
    private String opportunityStage;

    /** 支持人ID */
    private Long supportId;

    /** 支持人名称 */
    @Excel(name = "支持人名称")
    private String supportName;

    /** 支持内容 */
    @Excel(name = "支持内容")
    private String supportVal;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOpportunityCode(String opportunityCode)
    {
        this.opportunityCode = opportunityCode;
    }

    public String getOpportunityCode()
    {
        return opportunityCode;
    }
    public void setOpportunityStage(String opportunityStage)
    {
        this.opportunityStage = opportunityStage;
    }

    public String getOpportunityStage()
    {
        return opportunityStage;
    }
    public void setSupportId(Long supportId)
    {
        this.supportId = supportId;
    }

    public Long getSupportId()
    {
        return supportId;
    }
    public void setSupportName(String supportName)
    {
        this.supportName = supportName;
    }

    public String getSupportName()
    {
        return supportName;
    }
    public void setSupportVal(String supportVal)
    {
        this.supportVal = supportVal;
    }

    public String getSupportVal()
    {
        return supportVal;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("opportunityCode", getOpportunityCode())
                .append("opportunityStage", getOpportunityStage())
                .append("supportId", getSupportId())
                .append("supportName", getSupportName())
                .append("supportVal", getSupportVal())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
