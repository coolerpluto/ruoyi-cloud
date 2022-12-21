package com.highgo.medium.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 介质安全文件记录对象 t_medium_security_info
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public class MediumSecurityInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String id;

    /** 介质ID */
    @Excel(name = "对应的介质ID")
    private String mediumId;
    @Excel(name = "对应的介质")
    private String mediumName;
    @Excel(name = "附件别名")
    private String securityName;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件ID */
    @Excel(name = "文件ID")
    private String fileId;
    @Excel(name = "文件名")
    private String fileName;

    /** md5 ID */
    @Excel(name = "md5 ID")
    private String md5FileId;
    @Excel(name = "md5 文件名")
    private String md5FileName;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private String status;

    /** 创建者ID */
    private Long createId;

    /** 更新者ID */
    private Long updateId;

    public String getMediumName() {
        return mediumName;
    }

    public void setMediumName(String mediumName) {
        this.mediumName = mediumName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5FileName() {
        return md5FileName;
    }

    public void setMd5FileName(String md5FileName) {
        this.md5FileName = md5FileName;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setMediumId(String mediumId)
    {
        this.mediumId = mediumId;
    }

    public String getMediumId()
    {
        return mediumId;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileId()
    {
        return fileId;
    }
    public void setMd5FileId(String md5FileId)
    {
        this.md5FileId = md5FileId;
    }

    public String getMd5FileId()
    {
        return md5FileId;
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

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mediumId", getMediumId())
            .append("fileType", getFileType())
            .append("fileId", getFileId())
            .append("md5FileId", getMd5FileId())
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
