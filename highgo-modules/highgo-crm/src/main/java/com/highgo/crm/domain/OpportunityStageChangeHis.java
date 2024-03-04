package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机阶段历史对象 t_opportunity_stage_change_his
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityStageChangeHis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 老阶段 */
    @Excel(name = "老阶段")
    private String sourceStage;

    /** 新阶段 */
    @Excel(name = "新阶段")
    private String targetStage;

    /** 转变时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "转变时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date changeTime;

    /** 操作员ID */
    private Long operatorId;

    /** 操作员名 */
    @Excel(name = "操作员名")
    private String operatorName;

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
    public void setSourceStage(String sourceStage) 
    {
        this.sourceStage = sourceStage;
    }

    public String getSourceStage() 
    {
        return sourceStage;
    }
    public void setTargetStage(String targetStage) 
    {
        this.targetStage = targetStage;
    }

    public String getTargetStage() 
    {
        return targetStage;
    }
    public void setChangeTime(Date changeTime) 
    {
        this.changeTime = changeTime;
    }

    public Date getChangeTime() 
    {
        return changeTime;
    }
    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }
    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunityCode", getOpportunityCode())
            .append("sourceStage", getSourceStage())
            .append("targetStage", getTargetStage())
            .append("changeTime", getChangeTime())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .toString();
    }
}
