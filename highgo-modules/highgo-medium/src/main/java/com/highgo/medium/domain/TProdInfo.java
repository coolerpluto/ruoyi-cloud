package com.highgo.medium.domain;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 产品信息对象 t_prod_info
 * 
 * @author esz
 * @date 2023-06-28
 */
public class TProdInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String prodName;

    /** 简称 */
    @Excel(name = "简称")
    private String prodSname;

    /** 性质 */
    @Excel(name = "性质")
    private String property;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 状态（0正常 1失效） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=失效")
    private String status;

    /** 创建者id */
    @Excel(name = "创建者id")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long createId;

    /** 更新者id */
    @JsonSerialize(using=ToStringSerializer.class)
    @Excel(name = "更新者id")
    private Long updateId;

    /** 产品版本信息信息 */
    private List<TProdVersionInfo> tProdVersionInfoList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProdName(String prodName) 
    {
        this.prodName = prodName;
    }

    public String getProdName() 
    {
        return prodName;
    }
    public void setProdSname(String prodSname) 
    {
        this.prodSname = prodSname;
    }

    public String getProdSname() 
    {
        return prodSname;
    }
    public void setProperty(String property) 
    {
        this.property = property;
    }

    public String getProperty() 
    {
        return property;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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

    public List<TProdVersionInfo> getTProdVersionInfoList()
    {
        return tProdVersionInfoList;
    }

    public void setTProdVersionInfoList(List<TProdVersionInfo> tProdVersionInfoList)
    {
        this.tProdVersionInfoList = tProdVersionInfoList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("prodName", getProdName())
            .append("prodSname", getProdSname())
            .append("property", getProperty())
            .append("type", getType())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createId", getCreateId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateId", getUpdateId())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("tProdVersionInfoList", getTProdVersionInfoList())
            .toString();
    }
}
