package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 转移日志对象 t_transfer_log
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public class TransferLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 数据模块 */
    @Excel(name = "数据模块")
    private String model;

    /** 来源 */
    @Excel(name = "来源")
    private String userFrom;

    /** 目标 */
    @Excel(name = "目标")
    private String userTo;

    /** 操作的记录的ID */
    @Excel(name = "操作的记录的ID")
    private String recordIds;

    /** 单次转移数量 */
    @Excel(name = "单次转移数量")
    private Integer quantity;

    /** 转移原因 */
    @Excel(name = "转移原因")
    private String reason;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actionTime;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setUserFrom(String userFrom) 
    {
        this.userFrom = userFrom;
    }

    public String getUserFrom() 
    {
        return userFrom;
    }
    public void setUserTo(String userTo) 
    {
        this.userTo = userTo;
    }

    public String getUserTo() 
    {
        return userTo;
    }
    public void setRecordIds(String recordIds) 
    {
        this.recordIds = recordIds;
    }

    public String getRecordIds() 
    {
        return recordIds;
    }
    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setActionTime(Date actionTime) 
    {
        this.actionTime = actionTime;
    }

    public Date getActionTime() 
    {
        return actionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("model", getModel())
            .append("userFrom", getUserFrom())
            .append("userTo", getUserTo())
            .append("recordIds", getRecordIds())
            .append("quantity", getQuantity())
            .append("reason", getReason())
            .append("actionTime", getActionTime())
            .toString();
    }
}
