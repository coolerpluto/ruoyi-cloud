package com.highgo.medium.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 软著登记记录对象 t_soft_registration
 * 
 * @author esz
 * @date 2023-06-28
 */
public class TSoftRegistration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 软件名称 */
    @Excel(name = "软件名称")
    private String name;

    /** 软著登记号 */
    @Excel(name = "软著登记号")
    private String registrationNum;

    /** 创建者id */
    @Excel(name = "创建者id")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long createId;

    /** 更新者id */
    @Excel(name = "更新者id")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long updateId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRegistrationNum(String registrationNum) 
    {
        this.registrationNum = registrationNum;
    }

    public String getRegistrationNum() 
    {
        return registrationNum;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setUpdateId(Long updateId) 
    {
        this.updateId = updateId;
    }

    public Long getUpdateId() 
    {
        return updateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("registrationNum", getRegistrationNum())
            .append("createId", getCreateId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateId", getUpdateId())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
