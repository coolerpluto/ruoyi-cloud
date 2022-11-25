package com.highgo.medium.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 介质文件信息记录对象 t_medium_info
 * 
 * @author eshzhang
 * @date 2022-12-01
 */
public class MediumFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 版本类型 */
    @Excel(name = "版本类型")
    private String mediumType;

    /** 数据库版本 */
    @Excel(name = "数据库版本")
    private String dbVersion;

    /** CPU版本 */
    @Excel(name = "CPU版本")
    private String cpuType;

    /** 打包方式 */
    @Excel(name = "打包方式")
    private String packageType;

    /** 介质文件 */
    @Excel(name = "介质文件")
    private Long mediumFileId;

    /** 介质MD5文件 */
    @Excel(name = "介质MD5文件")
    private Long mediumMd5FileId;

    /** PostGis文件 */
    @Excel(name = "PostGis文件")
    private Long postgisFileId;

    /** PostGis MD5文件 */
    @Excel(name = "PostGis MD5文件")
    private Long postgisMd5FileId;

    /** 签名文件 */
    @Excel(name = "签名文件")
    private Long signFileId;

    /** 签名MD5文件 */
    @Excel(name = "签名MD5文件")
    private Long signMd5FileId;

    /** 其他文件 */
    @Excel(name = "其他文件")
    private Long otherFileId;

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
    public void setMediumType(String mediumType) 
    {
        this.mediumType = mediumType;
    }

    public String getMediumType() 
    {
        return mediumType;
    }
    public void setDbVersion(String dbVersion) 
    {
        this.dbVersion = dbVersion;
    }

    public String getDbVersion() 
    {
        return dbVersion;
    }
    public void setCpuType(String cpuType) 
    {
        this.cpuType = cpuType;
    }

    public String getCpuType() 
    {
        return cpuType;
    }
    public void setPackageType(String packageType) 
    {
        this.packageType = packageType;
    }

    public String getPackageType() 
    {
        return packageType;
    }
    public void setMediumFileId(Long mediumFileId) 
    {
        this.mediumFileId = mediumFileId;
    }

    public Long getMediumFileId() 
    {
        return mediumFileId;
    }
    public void setMediumMd5FileId(Long mediumMd5FileId) 
    {
        this.mediumMd5FileId = mediumMd5FileId;
    }

    public Long getMediumMd5FileId() 
    {
        return mediumMd5FileId;
    }
    public void setPostgisFileId(Long postgisFileId) 
    {
        this.postgisFileId = postgisFileId;
    }

    public Long getPostgisFileId() 
    {
        return postgisFileId;
    }
    public void setPostgisMd5FileId(Long postgisMd5FileId) 
    {
        this.postgisMd5FileId = postgisMd5FileId;
    }

    public Long getPostgisMd5FileId() 
    {
        return postgisMd5FileId;
    }
    public void setSignFileId(Long signFileId) 
    {
        this.signFileId = signFileId;
    }

    public Long getSignFileId() 
    {
        return signFileId;
    }
    public void setSignMd5FileId(Long signMd5FileId) 
    {
        this.signMd5FileId = signMd5FileId;
    }

    public Long getSignMd5FileId() 
    {
        return signMd5FileId;
    }
    public void setOtherFileId(Long otherFileId) 
    {
        this.otherFileId = otherFileId;
    }

    public Long getOtherFileId() 
    {
        return otherFileId;
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
            .append("mediumType", getMediumType())
            .append("dbVersion", getDbVersion())
            .append("cpuType", getCpuType())
            .append("packageType", getPackageType())
            .append("mediumFileId", getMediumFileId())
            .append("mediumMd5FileId", getMediumMd5FileId())
            .append("postgisFileId", getPostgisFileId())
            .append("postgisMd5FileId", getPostgisMd5FileId())
            .append("signFileId", getSignFileId())
            .append("signMd5FileId", getSignMd5FileId())
            .append("otherFileId", getOtherFileId())
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