package com.highgo.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机花费对象 t_opportunity_cost
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityCost extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 花费金额 */
    @Excel(name = "花费金额")
    private BigDecimal cost;

    /** 进入人员 */
    private Long inputUserId;
    private String inputUserName;

    /** 使用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "使用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date useTime;

    /** 使用原因 */
    @Excel(name = "使用原因")
    private String reason;

    /** 填报人员Id */
    private Long creatorId;

    public String getInputUserName()
    {
        return inputUserName;
    }

    public void setInputUserName(String inputUserName)
    {
        this.inputUserName = inputUserName;
    }

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
    public void setCost(BigDecimal cost) 
    {
        this.cost = cost;
    }

    public BigDecimal getCost() 
    {
        return cost;
    }
    public void setInputUserId(Long inputUserId) 
    {
        this.inputUserId = inputUserId;
    }

    public Long getInputUserId() 
    {
        return inputUserId;
    }
    public void setUseTime(Date useTime) 
    {
        this.useTime = useTime;
    }

    public Date getUseTime() 
    {
        return useTime;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setCreatorId(Long creatorId) 
    {
        this.creatorId = creatorId;
    }

    public Long getCreatorId() 
    {
        return creatorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunityCode", getOpportunityCode())
            .append("cost", getCost())
            .append("inputUserId", getInputUserId())
            .append("useTime", getUseTime())
            .append("reason", getReason())
            .append("createTime", getCreateTime())
            .append("creatorId", getCreatorId())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
