package com.highgo.crm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * license申请 主对象 t_license_apply
 *
 * @author eszhang
 * @date 2023-04-26
 */
public class LicenseApply extends BaseEntity
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
    private String code;

    /**
     * 商机名称
     */
    @Excel(name = "商机名称")
    private String name;

    /**
     * 客户Code
     */
    @Excel(name = "客户Code")
    private String custCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String custName;

    /**
     * 用户需求时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "用户需求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date useTime;

    /**
     * 用途
     */
    @Excel(name = "用途")
    private String purposes;

    /**
     * 商机所在部门
     */
    @Excel(name = "商机所在部门")
    private String deptName;
    private Long deptId;

    /**
     * 商机负责人员
     */
    @Excel(name = "商机负责人员")
    private String ownerName;
    private Long ownerId;

    /**
     * 商机共享到人
     */
    @Excel(name = "商机共享到人")
    private String sharedId;

    /**
     * 商机共享到部门
     */
    @Excel(name = "商机共享到部门")
    private String sharedDeptId;

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

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

    public void setCustCode(String custCode)
    {
        this.custCode = custCode;
    }

    public String getCustCode()
    {
        return custCode;
    }

    public void setCustName(String custName)
    {
        this.custName = custName;
    }

    public String getCustName()
    {
        return custName;
    }

    public void setUseTime(Date useTime)
    {
        this.useTime = useTime;
    }

    public Date getUseTime()
    {
        return useTime;
    }

    public void setPurposes(String purposes)
    {
        this.purposes = purposes;
    }

    public String getPurposes()
    {
        return purposes;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("code", getCode())
                .append("name", getName())
                .append("custCode", getCustCode())
                .append("custName", getCustName())
                .append("useTime", getUseTime())
                .append("purposes", getPurposes())
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
