package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机属性对象 t_opportunity_property
 * 
 * @author eszhang
 * @date 2023-02-22
 */
public class OpportunityProperty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 字段名 */
    @Excel(name = "字段名")
    private String propertyKey;

    /** 字段值 */
    @Excel(name = "字段值")
    private String propertyVal;

    /** 商机code */
    @Excel(name = "商机code")
    private String opportunityCode;

    /** 模块归属 */
    @Excel(name = "模块归属")
    private String model;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPropertyKey(String propertyKey) 
    {
        this.propertyKey = propertyKey;
    }

    public String getPropertyKey() 
    {
        return propertyKey;
    }
    public void setPropertyVal(String propertyVal) 
    {
        this.propertyVal = propertyVal;
    }

    public String getPropertyVal() 
    {
        return propertyVal;
    }
    public void setOpportunityCode(String opportunityCode) 
    {
        this.opportunityCode = opportunityCode;
    }

    public String getOpportunityCode() 
    {
        return opportunityCode;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("propertyKey", getPropertyKey())
            .append("propertyVal", getPropertyVal())
            .append("opportunityCode", getOpportunityCode())
            .append("model", getModel())
            .append("status", getStatus())
            .toString();
    }
}
