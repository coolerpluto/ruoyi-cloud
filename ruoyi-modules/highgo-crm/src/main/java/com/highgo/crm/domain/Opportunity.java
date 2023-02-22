package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 商机管理对象 t_opportunity
 * 
 * @author eszhang
 * @date 2023-02-13
 */
public class Opportunity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private String code;

    /** $column.columnComment */
    private String name;

    /** $column.columnComment */
    private Long custId;

    /** $column.columnComment */
    private Long deptId;

    /** $column.columnComment */
    private Long ownerId;

    /** $column.columnComment */
    private String sharedId;

    /** $column.columnComment */
    private String sharedDeptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCustId(Long custId) 
    {
        this.custId = custId;
    }

    public Long getCustId() 
    {
        return custId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setSharedId(String sharedId) 
    {
        this.sharedId = sharedId;
    }

    public String getSharedId() 
    {
        return sharedId;
    }
    public void setSharedDeptId(String sharedDeptId) 
    {
        this.sharedDeptId = sharedDeptId;
    }

    public String getSharedDeptId() 
    {
        return sharedDeptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("custId", getCustId())
            .append("deptId", getDeptId())
            .append("ownerId", getOwnerId())
            .append("sharedId", getSharedId())
            .append("sharedDeptId", getSharedDeptId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
