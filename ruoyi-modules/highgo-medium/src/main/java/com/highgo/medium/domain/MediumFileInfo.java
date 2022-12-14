package com.highgo.medium.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * 介质文件信息记录对象 t_medium_info
 *
 * @author eshzhang
 * @date 2022-12-01
 */
public class MediumFileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 介质别名
     */
    @Excel(name = "介质名称")
    private String mediumName;
    /**
     * 版本类型
     */
    @Excel(name = "版本类型")
    private String mediumType;

    /**
     * 数据库版本
     */
    @Excel(name = "数据库版本")
    private String dbVersion;

    /**
     * CPU版本
     */
    @Excel(name = "CPU版本")
    private String cpuType;

    /**
     * 打包方式
     */
    @Excel(name = "打包方式")
    private String packageType;

    /**
     * 介质文件
     */
    @Excel(name = "介质文件")
    private Long mediumFileId;
    private String mediumFileName;

    /**
     * 介质MD5文件
     */
    @Excel(name = "介质MD5文件")
    private Long mediumMd5FileId;
    private String mediumMd5FileName;

    /**
     * 文件状态
     */
    @Excel(name = "文件状态")
    private String status;

    /**
     * 创建者ID
     */
    private Long createId;

    /**
     * 更新者ID
     */
    private Long updateId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMediumFileName() {
        return mediumFileName;
    }

    public void setMediumFileName(String mediumFileName) {
        this.mediumFileName = mediumFileName;
    }

    public String getMediumMd5FileName() {
        return mediumMd5FileName;
    }

    public void setMediumMd5FileName(String mediumMd5FileName) {
        this.mediumMd5FileName = mediumMd5FileName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumType(String mediumType) {
        this.mediumType = mediumType;
    }

    public String getMediumType() {
        return mediumType;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    public String getCpuType() {
        return cpuType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setMediumFileId(Long mediumFileId) {
        this.mediumFileId = mediumFileId;
    }

    public Long getMediumFileId() {
        return mediumFileId;
    }

    public void setMediumMd5FileId(Long mediumMd5FileId) {
        this.mediumMd5FileId = mediumMd5FileId;
    }

    public Long getMediumMd5FileId() {
        return mediumMd5FileId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("mediumName", getMediumName())
                .append("mediumType", getMediumType())
                .append("dbVersion", getDbVersion())
                .append("cpuType", getCpuType())
                .append("packageType", getPackageType())
                .append("mediumFileId", getMediumFileId())
                .append("mediumMd5FileId", getMediumMd5FileId())
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
