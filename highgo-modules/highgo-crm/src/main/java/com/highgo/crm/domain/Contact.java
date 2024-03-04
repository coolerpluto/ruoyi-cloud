package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 联系人对象 t_contact
 *
 * @author eszhang
 * @date 2023-02-02
 */
public class Contact extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private String id;

    /**
     * 联系人名称
     */
    @Excel(name = "联系人名称")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 所属公司
     */
    @Excel(name = "所属公司")
    private String company;
    private String companyCode;
    private String companyProperty;

    /**
     * 公司内角色
     */
    @Excel(name = "公司内角色")
    private String roleInCompany;
    private String roleInOppo;

    /**
     * 公司内职务
     */
    @Excel(name = "公司内职务")
    private String postInCompany;

    /**
     * 公司内部门
     */
    @Excel(name = "公司内部门")
    private String deptInCompany;

    /**
     * 手机(主)
     */
    @Excel(name = "手机(主)")
    private String priPhone;

    /**
     * 手机(副)
     */
    @Excel(name = "手机(副)")
    private String secPhone;

    /**
     * 邮件(主)
     */
    @Excel(name = "邮件(主)")
    private String priEmail;

    /**
     * 邮件(副)
     */
    @Excel(name = "邮件(副)")
    private String secEmail;

    /**
     * 联系人地址
     */
    @Excel(name = "联系人地址")
    private String addr;

    /**
     * 联系人类型
     */
    @Excel(name = "联系人类型")
    private String category;

    /**
     * 数据归类
     */
    @Excel(name = "数据归类")
    private String sourceType;

    /**
     * 数据归属
     */
    @Excel(name = "数据归属")
    private String sourceId;

    /**
     * 所有者
     */
    private Long ownerId;
    @Excel(name = "所有者")
    private String ownerName;

    /**
     * 所有者部门
     */
    private Long ownerDeptId;
    @Excel(name = "所有者部门")
    private String ownerDeptName;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 更新者ID
     */
    private Long updateId;

    public String getRoleInOppo()
    {
        return roleInOppo;
    }

    public void setRoleInOppo(String roleInOppo)
    {
        this.roleInOppo = roleInOppo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerDeptId()
    {
        return ownerDeptId;
    }

    public void setOwnerDeptId(Long ownerDeptId)
    {
        this.ownerDeptId = ownerDeptId;
    }

    public String getOwnerDeptName()
    {
        return ownerDeptName;
    }

    public void setOwnerDeptName(String ownerDeptName)
    {
        this.ownerDeptName = ownerDeptName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyCode()
    {
        return companyCode;
    }

    public void setCompanyCode(String companyCode)
    {
        this.companyCode = companyCode;
    }

    public String getCompanyProperty()
    {
        return companyProperty;
    }

    public void setCompanyProperty(String companyProperty)
    {
        this.companyProperty = companyProperty;
    }

    public void setRoleInCompany(String roleInCompany) {
        this.roleInCompany = roleInCompany;
    }

    public String getRoleInCompany() {
        return roleInCompany;
    }

    public void setPostInCompany(String postInCompany) {
        this.postInCompany = postInCompany;
    }

    public String getPostInCompany() {
        return postInCompany;
    }

    public void setDeptInCompany(String deptInCompany) {
        this.deptInCompany = deptInCompany;
    }

    public String getDeptInCompany() {
        return deptInCompany;
    }

    public void setPriPhone(String priPhone) {
        this.priPhone = priPhone;
    }

    public String getPriPhone() {
        return priPhone;
    }

    public void setSecPhone(String secPhone) {
        this.secPhone = secPhone;
    }

    public String getSecPhone() {
        return secPhone;
    }

    public void setPriEmail(String priEmail) {
        this.priEmail = priEmail;
    }

    public String getPriEmail() {
        return priEmail;
    }

    public void setSecEmail(String secEmail) {
        this.secEmail = secEmail;
    }

    public String getSecEmail() {
        return secEmail;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
                .append("name", getName())
                .append("sex", getSex())
                .append("company", getCompany())
                .append("roleInCompany", getRoleInCompany())
                .append("postInCompany", getPostInCompany())
                .append("deptInCompany", getDeptInCompany())
                .append("priPhone", getPriPhone())
                .append("secPhone", getSecPhone())
                .append("priEmail", getPriEmail())
                .append("secEmail", getSecEmail())
                .append("addr", getAddr())
                .append("category", getCategory())
                .append("sourceType", getSourceType())
                .append("remark", getRemark())
                .append("sourceId", getSourceId())
                .append("ownerId", getOwnerId())
                .append("createId", getCreateId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateId", getUpdateId())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
