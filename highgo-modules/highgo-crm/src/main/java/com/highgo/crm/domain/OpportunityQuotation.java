package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机产品报价对象 t_opportunity_quotation
 *
 * @author eszhang
 * @date 2023-03-14
 */
public class OpportunityQuotation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 报价信息json */
    @Excel(name = "报价信息json")
    private String quotationJson;

    /** 商机所在阶段 */
    @Excel(name = "商机所在阶段")
    private String opportunityStage;

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
    public void setQuotationJson(String quotationJson)
    {
        this.quotationJson = quotationJson;
    }

    public String getQuotationJson()
    {
        return quotationJson;
    }
    public void setOpportunityStage(String opportunityStage)
    {
        this.opportunityStage = opportunityStage;
    }

    public String getOpportunityStage()
    {
        return opportunityStage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("opportunityCode", getOpportunityCode())
                .append("quotationJson", getQuotationJson())
                .append("opportunityStage", getOpportunityStage())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
