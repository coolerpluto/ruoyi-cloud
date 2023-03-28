package com.highgo.crm.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 竞争对手对象 t_opportunity_competitor
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityCompetitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 关联商机code */
    @Excel(name = "关联商机code")
    private String opportunity_code;

    /** 竞争对手ID */
    private Long competitorId;

    /** 竞争对手名称 */
    @Excel(name = "竞争对手名称")
    private String competitorName;

    /** 竟品名称 */
    @Excel(name = "竟品名称")
    private String product;

    /** 产品单价 */
    @Excel(name = "产品单价")
    private BigDecimal unitPrice;

    /** 对方优势 */
    @Excel(name = "对方优势")
    private String advantage;

    /** 具备相同优势 */
    @Excel(name = "具备相同优势")
    private String hasSameAdvantage;

    /** 备注 */
    @Excel(name = "备注")
    private String memo;

    /** 投标价格 */
    @Excel(name = "投标价格")
    private BigDecimal tenderPrice;
    @Excel(name = "投标总价")
    private BigDecimal tenderTotalPrice;

    public BigDecimal getTenderTotalPrice()
    {
        return tenderTotalPrice;
    }

    public void setTenderTotalPrice(BigDecimal tenderTotalPrice)
    {
        this.tenderTotalPrice = tenderTotalPrice;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOpportunity_code(String opportunity_code) 
    {
        this.opportunity_code = opportunity_code;
    }

    public String getOpportunity_code() 
    {
        return opportunity_code;
    }
    public void setCompetitorId(Long competitorId) 
    {
        this.competitorId = competitorId;
    }

    public Long getCompetitorId() 
    {
        return competitorId;
    }
    public void setCompetitorName(String competitorName) 
    {
        this.competitorName = competitorName;
    }

    public String getCompetitorName() 
    {
        return competitorName;
    }
    public void setProduct(String product) 
    {
        this.product = product;
    }

    public String getProduct() 
    {
        return product;
    }
    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }
    public void setAdvantage(String advantage) 
    {
        this.advantage = advantage;
    }

    public String getAdvantage() 
    {
        return advantage;
    }
    public void setHasSameAdvantage(String hasSameAdvantage) 
    {
        this.hasSameAdvantage = hasSameAdvantage;
    }

    public String getHasSameAdvantage() 
    {
        return hasSameAdvantage;
    }
    public void setMemo(String memo) 
    {
        this.memo = memo;
    }

    public String getMemo() 
    {
        return memo;
    }
    public void setTenderPrice(BigDecimal tenderPrice) 
    {
        this.tenderPrice = tenderPrice;
    }

    public BigDecimal getTenderPrice() 
    {
        return tenderPrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunity_code", getOpportunity_code())
            .append("competitorId", getCompetitorId())
            .append("competitorName", getCompetitorName())
            .append("product", getProduct())
            .append("unitPrice", getUnitPrice())
            .append("advantage", getAdvantage())
            .append("hasSameAdvantage", getHasSameAdvantage())
            .append("memo", getMemo())
            .append("tenderPrice", getTenderPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
