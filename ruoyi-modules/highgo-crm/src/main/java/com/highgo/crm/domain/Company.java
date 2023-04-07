package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 公司对象 t_company
 *
 * @author eszhang
 * @date 2023-02-02
 */
public class Company extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private String id;
    @Excel(name = "客户编码")
    private String code;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司性质
     */
    @Excel(name = "公司性质")
    private String properties;

    /**
     * 行业归属
     */
    @Excel(name = "行业归属")
    private String industry;

    /**
     * 公司法人
     */
    @Excel(name = "公司法人")
    private String legal;

    /**
     * 注册资金
     */
    @Excel(name = "注册资金")
    private String capitalReg;

    /**
     * 缴纳方式
     */
    @Excel(name = "缴纳方式")
    private String capitalPayType;

    /**
     * 数据归类
     */
    @Excel(name = "数据归类")
    private String sourceType;

    /**
     * 公司官网
     */
    @Excel(name = "公司官网")
    private String website;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String addr;
    @Excel(name = "详细地址")
    private String addrDetail;
    private String zipCode;

    /**
     * 公司业务
     */
    @Excel(name = "公司业务")
    private String businessScope;

    /**
     * 数据归属
     */
    @Excel(name = "数据归属")
    private Long sourceId;

    /**
     * 所有者
     */
    private Long ownerId;
    @Excel(name = "所有者")
    private String ownerName;

    /**
     * 所有者部门
     */
    private Long deptId;
    @Excel(name = "所有者部门")
    private String deptName;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 更新者ID
     */
    private Long updateId;

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getProperties() {
        return properties;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustry() {
        return industry;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getLegal() {
        return legal;
    }

    public void setCapitalReg(String capitalReg) {
        this.capitalReg = capitalReg;
    }

    public String getCapitalReg() {
        return capitalReg;
    }

    public void setCapitalPayType(String capitalPayType) {
        this.capitalPayType = capitalPayType;
    }

    public String getCapitalPayType() {
        return capitalPayType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("companyName", getCompanyName())
                .append("properties", getProperties())
                .append("industry", getIndustry())
                .append("legal", getLegal())
                .append("capitalReg", getCapitalReg())
                .append("capitalPayType", getCapitalPayType())
                .append("sourceType", getSourceType())
                .append("website", getWebsite())
                .append("addr", getAddr())
                .append("businessScope", getBusinessScope())
                .append("remark", getRemark())
                .append("sourceId", getSourceId())
                .append("ownerId", getOwnerId())
                .append("deptId", getDeptId())
                .append("createId", getCreateId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateId", getUpdateId())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
