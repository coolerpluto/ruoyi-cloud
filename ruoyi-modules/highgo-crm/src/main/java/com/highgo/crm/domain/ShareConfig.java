package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 共享规则配置对象 t_share_config
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public class ShareConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 数据模块 */
    @Excel(name = "数据模块")
    private String dataModel;

    /** 共享源 */
    @Excel(name = "共享源")
    private String sourceIds;

    /** 共享源类型 */
    @Excel(name = "共享源类型")
    private String sourceType;

    /** 共享目标 */
    @Excel(name = "共享目标")
    private String targetIds;

    /** 共享目标类型 */
    @Excel(name = "共享目标类型")
    private String targetType;

    /** 共享目标权限 */
    @Excel(name = "共享目标权限")
    private String permission;

    /** 共享失效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "共享失效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expTime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setDataModel(String dataModel) 
    {
        this.dataModel = dataModel;
    }

    public String getDataModel() 
    {
        return dataModel;
    }
    public void setSourceIds(String sourceIds) 
    {
        this.sourceIds = sourceIds;
    }

    public String getSourceIds() 
    {
        return sourceIds;
    }
    public void setSourceType(String sourceType) 
    {
        this.sourceType = sourceType;
    }

    public String getSourceType() 
    {
        return sourceType;
    }
    public void setTargetIds(String targetIds) 
    {
        this.targetIds = targetIds;
    }

    public String getTargetIds() 
    {
        return targetIds;
    }
    public void setTargetType(String targetType) 
    {
        this.targetType = targetType;
    }

    public String getTargetType() 
    {
        return targetType;
    }
    public void setPermission(String permission) 
    {
        this.permission = permission;
    }

    public String getPermission() 
    {
        return permission;
    }
    public void setExpTime(Date expTime) 
    {
        this.expTime = expTime;
    }

    public Date getExpTime() 
    {
        return expTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dataModel", getDataModel())
            .append("sourceIds", getSourceIds())
            .append("sourceType", getSourceType())
            .append("targetIds", getTargetIds())
            .append("targetType", getTargetType())
            .append("permission", getPermission())
            .append("expTime", getExpTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
