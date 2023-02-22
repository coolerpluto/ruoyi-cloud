package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机支持政策对象 t_opportunity_policy
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityPolicy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商机Code */
    @Excel(name = "商机Code")
    private String opportunityCode;

    /** 文件ID */
    @Excel(name = "文件ID")
    private Long policyId;

    /** 文件名字 */
    @Excel(name = "文件名字")
    private String policyName;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseDate;

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
    public void setPolicyId(Long policyId) 
    {
        this.policyId = policyId;
    }

    public Long getPolicyId() 
    {
        return policyId;
    }
    public void setPolicyName(String policyName) 
    {
        this.policyName = policyName;
    }

    public String getPolicyName() 
    {
        return policyName;
    }
    public void setReleaseDate(Date releaseDate) 
    {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() 
    {
        return releaseDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("opportunityCode", getOpportunityCode())
            .append("policyId", getPolicyId())
            .append("policyName", getPolicyName())
            .append("releaseDate", getReleaseDate())
            .toString();
    }
}
