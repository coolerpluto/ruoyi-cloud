package com.highgo.medium.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * License文件记录对象 t_license_info
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public class MLicenseFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** License序列号 */
    @Excel(name = "License序列号")
    private String serial;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 商机编号 */
    @Excel(name = "商机编号")
    private String opportunityNum;

    /** 授权方式 */
    @Excel(name = "授权方式")
    private String authType;

    /** 使用客户 */
    @Excel(name = "使用客户")
    private String customerName;

    /** 使用用途 */
    @Excel(name = "使用用途")
    private String purpose;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String prodType;

    /** 数据库版本 */
    @Excel(name = "数据库版本")
    private String dbVersion;

    /** 下载连接 */
    @Excel(name = "下载连接")
    private String serverUrl;

    /** 其他参数 */
    @Excel(name = "其他参数")
    private String otherParam;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private String status;

    /** 创建者ID */
    private Long createId;

    /** 更新者ID */
    private Long updateId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSerial(String serial) 
    {
        this.serial = serial;
    }

    public String getSerial() 
    {
        return serial;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setOpportunityNum(String opportunityNum) 
    {
        this.opportunityNum = opportunityNum;
    }

    public String getOpportunityNum() 
    {
        return opportunityNum;
    }
    public void setAuthType(String authType) 
    {
        this.authType = authType;
    }

    public String getAuthType() 
    {
        return authType;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setPurpose(String purpose) 
    {
        this.purpose = purpose;
    }

    public String getPurpose() 
    {
        return purpose;
    }
    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }
    public void setProdType(String prodType) 
    {
        this.prodType = prodType;
    }

    public String getProdType() 
    {
        return prodType;
    }
    public void setDbVersion(String dbVersion) 
    {
        this.dbVersion = dbVersion;
    }

    public String getDbVersion() 
    {
        return dbVersion;
    }
    public void setServerUrl(String serverUrl) 
    {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() 
    {
        return serverUrl;
    }
    public void setOtherParam(String otherParam) 
    {
        this.otherParam = otherParam;
    }

    public String getOtherParam() 
    {
        return otherParam;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("serial", getSerial())
            .append("applyTime", getApplyTime())
            .append("opportunityNum", getOpportunityNum())
            .append("authType", getAuthType())
            .append("customerName", getCustomerName())
            .append("purpose", getPurpose())
            .append("expireTime", getExpireTime())
            .append("prodType", getProdType())
            .append("dbVersion", getDbVersion())
            .append("serverUrl", getServerUrl())
            .append("otherParam", getOtherParam())
            .append("status", getStatus())
            .append("createId", getCreateId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateId", getUpdateId())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
