package com.highgo.medium.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 产品版本信息对象 t_prod_version_info
 *
 * @author esz
 * @date 2023-06-28
 */
public class TProdVersionInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 产品ID
     */
    @Excel(name = "产品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long prodId;
    @Excel(name = "产品名称")
    private String prodName;
    private String prodSname;

    /**
     * 版本
     */
    @Excel(name = "版本")
    private String version;

    /**
     * 产品经理
     */
    @Excel(name = "产品经理")
    private String prodPic;

    /**
     * 项目经理
     */
    @Excel(name = "项目经理")
    private String pmPic;

    /**
     * 归属部门
     */
    @Excel(name = "归属部门ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    @Excel(name = "归属部门名称")
    private String deptName;

    /**
     * 软登记ID
     */
    @Excel(name = "软著登记ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long registrationId;
    @Excel(name = "软著登记号")
    private String registrationNum;
    @Excel(name = "软著登记名称")
    private String registrationName;

    /**
     * Lic工具ID
     */
    @Excel(name = "Lic工具ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long licToolId;
    @Excel(name = "Lic工具名称")
    private String licToolName;
    private String licToolFileName;

    /**
     * 机器码工具ID
     */
    @Excel(name = "机器码工具ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long macToolFileId;
    @Excel(name = "机器码工具")
    private String macToolFileName;

    /**
     * 状态（0正常 1失效）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=失效")
    private String status;

    /**
     * 是否签名
     */
    @Excel(name = "是否签名")
    private String signFlag;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createId;

    /**
     * 更新者id
     */
    @Excel(name = "更新者id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateId;

    // 非表中字段 开始

    public String getProdName()
    {
        return prodName;
    }

    public void setProdName(String prodName)
    {
        this.prodName = prodName;
    }

    public String getProdSname()
    {
        return prodSname;
    }

    public void setProdSname(String prodSname)
    {
        this.prodSname = prodSname;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getRegistrationNum()
    {
        return registrationNum;
    }

    public void setRegistrationNum(String registrationNum)
    {
        this.registrationNum = registrationNum;
    }

    public String getRegistrationName()
    {
        return registrationName;
    }

    public void setRegistrationName(String registrationName)
    {
        this.registrationName = registrationName;
    }

    public String getLicToolName()
    {
        return licToolName;
    }

    public void setLicToolName(String licToolName)
    {
        this.licToolName = licToolName;
    }

    public String getLicToolFileName()
    {
        return licToolFileName;
    }

    public void setLicToolFileName(String licToolFileName)
    {
        this.licToolFileName = licToolFileName;
    }

    public String getMacToolFileName()
    {
        return macToolFileName;
    }

    public void setMacToolFileName(String macToolFileName)
    {
        this.macToolFileName = macToolFileName;
    }

    // 非表中字段 结束

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setProdId(Long prodId)
    {
        this.prodId = prodId;
    }

    public Long getProdId()
    {
        return prodId;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }

    public void setProdPic(String prodPic)
    {
        this.prodPic = prodPic;
    }

    public String getProdPic()
    {
        return prodPic;
    }

    public void setPmPic(String pmPic)
    {
        this.pmPic = pmPic;
    }

    public String getPmPic()
    {
        return pmPic;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setRegistrationId(Long registrationId)
    {
        this.registrationId = registrationId;
    }

    public Long getRegistrationId()
    {
        return registrationId;
    }

    public void setLicToolId(Long licToolId)
    {
        this.licToolId = licToolId;
    }

    public Long getLicToolId()
    {
        return licToolId;
    }

    public void setMacToolFileId(Long macToolFileId)
    {
        this.macToolFileId = macToolFileId;
    }

    public Long getMacToolFileId()
    {
        return macToolFileId;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setSignFlag(String signFlag)
    {
        this.signFlag = signFlag;
    }

    public String getSignFlag()
    {
        return signFlag;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("prodId", getProdId())
                .append("version", getVersion())
                .append("prodPic", getProdPic())
                .append("pmPic", getPmPic())
                .append("deptId", getDeptId())
                .append("registrationId", getRegistrationId())
                .append("licToolId", getLicToolId())
                .append("macToolFileId", getMacToolFileId())
                .append("status", getStatus())
                .append("signFlag", getSignFlag())
                .append("remark", getRemark())
                .append("createId", getCreateId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateId", getUpdateId())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
