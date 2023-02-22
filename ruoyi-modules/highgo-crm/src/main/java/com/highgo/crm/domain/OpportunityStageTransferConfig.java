package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机阶段流转配置对象 t_opportunity_stage_transfer_config
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityStageTransferConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 源阶段 */
    @Excel(name = "源阶段")
    private String sourceStage;

    /** 目标阶段 */
    @Excel(name = "目标阶段")
    private String targetStage;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String status;

    /** 显示条件 */
    @Excel(name = "显示条件")
    private String conditions;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
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
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setConditions(String conditions)
    {
        this.conditions = conditions;
    }

    public String getConditions()
    {
        return conditions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sourceStage", getSourceStage())
            .append("targetStage", getTargetStage())
            .append("status", getStatus())
            .append("conditions", getConditions())
            .toString();
    }
}
