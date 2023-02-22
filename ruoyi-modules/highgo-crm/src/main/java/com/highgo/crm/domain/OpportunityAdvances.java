package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机进展对象 t_opportunity_advances
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityAdvances extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 当前阶段 */
    @Excel(name = "当前阶段")
    private String stage;

    /** 进展内容 */
    @Excel(name = "进展内容")
    private String advances;

    /** 进展时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进展时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date advancesDate;

    /** 提报人 */
    @Excel(name = "提报人")
    private Long creator;

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
    public void setStage(String stage) 
    {
        this.stage = stage;
    }

    public String getStage() 
    {
        return stage;
    }
    public void setAdvances(String advances) 
    {
        this.advances = advances;
    }

    public String getAdvances() 
    {
        return advances;
    }
    public void setAdvancesDate(Date advancesDate) 
    {
        this.advancesDate = advancesDate;
    }

    public Date getAdvancesDate() 
    {
        return advancesDate;
    }
    public void setCreator(Long creator) 
    {
        this.creator = creator;
    }

    public Long getCreator() 
    {
        return creator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunityCode", getOpportunityCode())
            .append("stage", getStage())
            .append("advances", getAdvances())
            .append("advancesDate", getAdvancesDate())
            .append("creator", getCreator())
            .append("createTime", getCreateTime())
            .toString();
    }
}
