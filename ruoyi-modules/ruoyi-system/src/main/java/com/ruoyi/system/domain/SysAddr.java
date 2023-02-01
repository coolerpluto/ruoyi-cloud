package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.TreeEntity;

/**
 * 地址联动对象 sys_addr
 * 
 * @author eszhang
 * @date 2023-02-01
 */
public class SysAddr extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录主键 */
    private String id;

    /** 唯一code */
    @Excel(name = "唯一code")
    private String code;

    /** 地址名字 */
    @Excel(name = "地址名字")
    private String name;

    /** 父级地址code */
    @Excel(name = "父级地址code")
    private String parent;

    /** 地址级别 */
    @Excel(name = "地址级别")
    private String level;

    /** 地址记录状态 */
    @Excel(name = "地址记录状态")
    private String status;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal lat;

    /** 纬度 */
    private BigDecimal lng;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
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
    public void setParent(String parent) 
    {
        this.parent = parent;
    }

    public String getParent() 
    {
        return parent;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setSort(Integer sort) 
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }
    public void setLat(BigDecimal lat) 
    {
        this.lat = lat;
    }

    public BigDecimal getLat() 
    {
        return lat;
    }
    public void setLng(BigDecimal lng) 
    {
        this.lng = lng;
    }

    public BigDecimal getLng() 
    {
        return lng;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("parent", getParent())
            .append("level", getLevel())
            .append("status", getStatus())
            .append("sort", getSort())
            .append("lat", getLat())
            .append("lng", getLng())
            .toString();
    }
}
