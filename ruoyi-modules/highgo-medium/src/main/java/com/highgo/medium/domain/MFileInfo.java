package com.highgo.medium.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 文件记录对象 t_file_info
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public class MFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String id;

    /** 文件服务器 */
    @Excel(name = "文件服务器")
    private String fileServer;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 记录状态 */
    @Excel(name = "记录状态")
    private String status;

    /** 创建者ID */
    private Long createId;

    /** 更新者ID */
    private Long updateId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setFileServer(String fileServer) 
    {
        this.fileServer = fileServer;
    }

    public String getFileServer() 
    {
        return fileServer;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
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
            .append("fileServer", getFileServer())
            .append("filePath", getFilePath())
            .append("fileName", getFileName())
            .append("fileType", getFileType())
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
