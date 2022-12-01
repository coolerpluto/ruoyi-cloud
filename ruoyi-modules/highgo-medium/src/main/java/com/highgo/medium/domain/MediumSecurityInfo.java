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
    private Long id;

    /** 介质ID */
    @Excel(name = "介质ID")
    private Long mediumId;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件ID */
    @Excel(name = "文件ID")
    private Long fileId;

    /** md5 ID */
    @Excel(name = "md5 ID")
    private Long md5FileId;

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
    public void setMediumId(Long mediumId) 
    {
        this.mediumId = mediumId;
    }

    public Long getMediumId() 
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
    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setMd5FileId(Long md5FileId) 
    {
        this.md5FileId = md5FileId;
    }

    public Long getMd5FileId() 
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
