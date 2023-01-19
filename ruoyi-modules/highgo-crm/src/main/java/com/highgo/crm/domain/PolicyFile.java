package com.highgo.crm.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 政策文件对象 t_policy_file
 *
 * @author eszhang
 * @date 2023-01-19
 */
public class PolicyFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 文件名 */
    @Excel(name = "文件名")
    private String name;

    /** 发文字号 */
    @Excel(name = "发文字号")
    private String fileNum;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseDate;

    /** 发文机构 */
    @Excel(name = "发文机构")
    private String releaseOffice;

    /** 发布年分 */
    @Excel(name = "发布年分")
    private Integer releaseYear;

    /** 适用省份 */
    @Excel(name = "适用省份")
    private String province;

    /** 适用市 */
    @Excel(name = "适用市")
    private String city;

    /** 当前拥有者部门 */
    @Excel(name = "当前拥有者部门")
    private String deptId;

    /** 当前拥有者 */
    @Excel(name = "当前拥有者")
    private String ownerId;

    /** 文件可用状态 */
    @Excel(name = "文件可用状态")
    private String status;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setFileNum(String fileNum)
    {
        this.fileNum = fileNum;
    }

    public String getFileNum()
    {
        return fileNum;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate()
    {
        return releaseDate;
    }
    public void setReleaseOffice(String releaseOffice)
    {
        this.releaseOffice = releaseOffice;
    }

    public String getReleaseOffice()
    {
        return releaseOffice;
    }
    public void setReleaseYear(Integer releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public Integer getReleaseYear()
    {
        return releaseYear;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getOwnerId()
    {
        return ownerId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("fileNum", getFileNum())
                .append("filePath", getFilePath())
                .append("releaseDate", getReleaseDate())
                .append("releaseOffice", getReleaseOffice())
                .append("releaseYear", getReleaseYear())
                .append("province", getProvince())
                .append("city", getCity())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("deptId", getDeptId())
                .append("ownerId", getOwnerId())
                .append("status", getStatus())
                .toString();
    }
}
