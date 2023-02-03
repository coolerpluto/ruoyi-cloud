package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 应用信息对象 t_application
 *
 * @author eszhang
 * @date 2023-02-02
 */
public class Application extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private String id;

    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    private String name;

    /**
     * 应用当前版本
     */
    @Excel(name = "应用当前版本")
    private String version;

    /**
     * 应归属门类
     */
    @Excel(name = "应归属门类")
    private String category;

    /**
     * 软件开发商
     */

    private Long isv;
    @Excel(name = "软件开发商")
    private String isvName;

    /**
     * isv联系人
     */

    private Long isvContactId;
    @Excel(name = "isv联系人")
    private String isvContactName;

    /**
     * 基础环境
     */
    @Excel(name = "基础环境")
    private String baseEnviron;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String opeartSys;

    /**
     * 中间件
     */
    @Excel(name = "中间件")
    private String middleSoft;

    /**
     * 当前数据库
     */
    @Excel(name = "当前数据库")
    private String dataBase;

    /**
     * DB选型
     */
    @Excel(name = "DB选型")
    private String dbSelectionModel;

    /**
     * 部署类型
     */
    @Excel(name = "部署类型")
    private String deployType;

    /**
     * DB部署级别
     */
    @Excel(name = "DB部署级别")
    private String deployLevel;

    /**
     * 建设资金来源
     */
    @Excel(name = "建设资金来源")
    private String sysBuildMoney;

    /**
     * 数据归属
     */
    @Excel(name = "数据归属")
    private Long sourceId;

    /**
     * 数据归类
     */
    @Excel(name = "数据归类")
    private String sourceType;

    /**
     * 应用开发进度
     */
    @Excel(name = "应用开发进度")
    private String devStatus;

    /**
     * HGDB适配进度
     */
    @Excel(name = "HGDB适配进度")
    private String dbAdaptStatus;

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

    public String getIsvName() {
        return isvName;
    }

    public void setIsvName(String isvName) {
        this.isvName = isvName;
    }

    public String getIsvContactName() {
        return isvContactName;
    }

    public void setIsvContactName(String isvContactName) {
        this.isvContactName = isvContactName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setIsv(Long isv) {
        this.isv = isv;
    }

    public Long getIsv() {
        return isv;
    }

    public void setIsvContactId(Long isvContactId) {
        this.isvContactId = isvContactId;
    }

    public Long getIsvContactId() {
        return isvContactId;
    }

    public void setBaseEnviron(String baseEnviron) {
        this.baseEnviron = baseEnviron;
    }

    public String getBaseEnviron() {
        return baseEnviron;
    }

    public void setOpeartSys(String opeartSys) {
        this.opeartSys = opeartSys;
    }

    public String getOpeartSys() {
        return opeartSys;
    }

    public void setMiddleSoft(String middleSoft) {
        this.middleSoft = middleSoft;
    }

    public String getMiddleSoft() {
        return middleSoft;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDbSelectionModel(String dbSelectionModel) {
        this.dbSelectionModel = dbSelectionModel;
    }

    public String getDbSelectionModel() {
        return dbSelectionModel;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployLevel(String deployLevel) {
        this.deployLevel = deployLevel;
    }

    public String getDeployLevel() {
        return deployLevel;
    }

    public void setSysBuildMoney(String sysBuildMoney) {
        this.sysBuildMoney = sysBuildMoney;
    }

    public String getSysBuildMoney() {
        return sysBuildMoney;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public String getDevStatus() {
        return devStatus;
    }

    public void setDbAdaptStatus(String dbAdaptStatus) {
        this.dbAdaptStatus = dbAdaptStatus;
    }

    public String getDbAdaptStatus() {
        return dbAdaptStatus;
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
                .append("name", getName())
                .append("version", getVersion())
                .append("category", getCategory())
                .append("isv", getIsv())
                .append("isvContactId", getIsvContactId())
                .append("baseEnviron", getBaseEnviron())
                .append("opeartSys", getOpeartSys())
                .append("middleSoft", getMiddleSoft())
                .append("dataBase", getDataBase())
                .append("dbSelectionModel", getDbSelectionModel())
                .append("deployType", getDeployType())
                .append("deployLevel", getDeployLevel())
                .append("sysBuildMoney", getSysBuildMoney())
                .append("sourceId", getSourceId())
                .append("sourceType", getSourceType())
                .append("devStatus", getDevStatus())
                .append("dbAdaptStatus", getDbAdaptStatus())
                .append("remark", getRemark())
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
