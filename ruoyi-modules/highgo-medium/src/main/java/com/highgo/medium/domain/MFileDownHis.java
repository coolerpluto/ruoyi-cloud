package com.highgo.medium.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 下载记录对象 t_file_down_his
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public class MFileDownHis extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 商机编号 */
    @Excel(name = "商机编号")
    private String opportunityNum;

    /** 文件ID */
    @Excel(name = "文件ID")
    private Long fileId;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

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
    public void setOpportunityNum(String opportunityNum) 
    {
        this.opportunityNum = opportunityNum;
    }

    public String getOpportunityNum() 
    {
        return opportunityNum;
    }
    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
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
            .append("opportunityNum", getOpportunityNum())
            .append("fileId", getFileId())
            .append("fileType", getFileType())
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
