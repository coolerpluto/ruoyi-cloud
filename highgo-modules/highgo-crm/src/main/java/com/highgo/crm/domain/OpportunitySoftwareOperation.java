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

    /**
     * 主键
     */
    private Long id;

    /**
     * 商机Code
     */
    @Excel(name = "商机Code")
    private String opportunityCode;

    /**
     * 应用软件Id
     */
    private Long applicationId;

    /**
     * 应用软件名字
     */
    @Excel(name = "应用软件名字")
    private String applicationName;
    private String dbBase;
    /**
     * 是否需要适配
     */
    @Excel(name = "是否需要适配")
    private String isvNeedAdapted;

    /**
     * 招标前目标进度
     */
    @Excel(name = "招标前目标进度")
    private String targetAdaptedProgress;

    /**
     * 当前适配进度
     */
    @Excel(name = "当前适配进度")
    private String currentAdaptedProgress;

    /**
     * 运作主体Id
     */
    private Long operationalId;

    /**
     * 运作主体名字
     */
    @Excel(name = "运作主体名字")
    private String operationalName;

    private String operationalTender;

    private String operationalSupportDb;

    /**
     * 系统开发商ID
     */
    private String isvId;

    /**
     * 系统开发商
     */
    private String isv;

    /**
     * 软件分类
     */
    private String category;
    private String categoryL1;
    private String categoryL2;
    private String categoryL3;
    private String categoryL4;


    public String getOperationalTender()
    {
        return operationalTender;
    }

    public void setOperationalTender(String operationalTender)
    {
        this.operationalTender = operationalTender;
    }

    public String getOperationalSupportDb()
    {
        return operationalSupportDb;
    }

    public void setOperationalSupportDb(String operationalSupportDb)
    {
        this.operationalSupportDb = operationalSupportDb;
    }

    public String getIsvId()
    {
        return isvId;
    }

    public void setIsvId(String isvId)
    {
        this.isvId = isvId;
    }

    public String getIsv()
    {
        return isv;
    }

    public void setIsv(String isv)
    {
        this.isv = isv;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategoryL1()
    {
        return categoryL1;
    }

    public void setCategoryL1(String categoryL1)
    {
        this.categoryL1 = categoryL1;
    }

    public String getCategoryL2()
    {
        return categoryL2;
    }

    public void setCategoryL2(String categoryL2)
    {
        this.categoryL2 = categoryL2;
    }

    public String getCategoryL3()
    {
        return categoryL3;
    }

    public void setCategoryL3(String categoryL3)
    {
        this.categoryL3 = categoryL3;
    }

    public String getCategoryL4()
    {
        return categoryL4;
    }

    public void setCategoryL4(String categoryL4)
    {
        this.categoryL4 = categoryL4;
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

    public String getDbBase()
    {
        return dbBase;
    }

    public void setDbBase(String dbBase)
    {
        this.dbBase = dbBase;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
