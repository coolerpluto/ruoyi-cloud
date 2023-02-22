package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机应用运营信息对象 t_opportunity_software_operation
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunitySoftwareOperation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机Code */
    @Excel(name = "商机Code")
    private String opportunityCode;

    /** 应用软件Id */
    private Long applicationId;

    /** 应用软件名字 */
    @Excel(name = "应用软件名字")
    private String applicationName;

    /** 是否需要适配 */
    @Excel(name = "是否需要适配")
    private String isvNeedAdapted;

    /** 招标前目标进度 */
    @Excel(name = "招标前目标进度")
    private String targetAdaptedProgress;

    /** 当前适配进度 */
    @Excel(name = "当前适配进度")
    private String currentAdaptedProgress;

    /** 运作主体Id */
    private Long operationalId;

    /** 运作主体名字 */
    @Excel(name = "运作主体名字")
    private String operationalName;

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
    public void setApplicationId(Long applicationId) 
    {
        this.applicationId = applicationId;
    }

    public Long getApplicationId() 
    {
        return applicationId;
    }
    public void setApplicationName(String applicationName) 
    {
        this.applicationName = applicationName;
    }

    public String getApplicationName() 
    {
        return applicationName;
    }
    public void setIsvNeedAdapted(String isvNeedAdapted) 
    {
        this.isvNeedAdapted = isvNeedAdapted;
    }

    public String getIsvNeedAdapted() 
    {
        return isvNeedAdapted;
    }
    public void setTargetAdaptedProgress(String targetAdaptedProgress) 
    {
        this.targetAdaptedProgress = targetAdaptedProgress;
    }

    public String getTargetAdaptedProgress() 
    {
        return targetAdaptedProgress;
    }
    public void setCurrentAdaptedProgress(String currentAdaptedProgress) 
    {
        this.currentAdaptedProgress = currentAdaptedProgress;
    }

    public String getCurrentAdaptedProgress() 
    {
        return currentAdaptedProgress;
    }
    public void setOperationalId(Long operationalId) 
    {
        this.operationalId = operationalId;
    }

    public Long getOperationalId() 
    {
        return operationalId;
    }
    public void setOperationalName(String operationalName) 
    {
        this.operationalName = operationalName;
    }

    public String getOperationalName() 
    {
        return operationalName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunityCode", getOpportunityCode())
            .append("applicationId", getApplicationId())
            .append("applicationName", getApplicationName())
            .append("isvNeedAdapted", getIsvNeedAdapted())
            .append("targetAdaptedProgress", getTargetAdaptedProgress())
            .append("currentAdaptedProgress", getCurrentAdaptedProgress())
            .append("operationalId", getOperationalId())
            .append("operationalName", getOperationalName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
