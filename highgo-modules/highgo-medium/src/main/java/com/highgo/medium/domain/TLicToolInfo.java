package com.highgo.medium.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * License工具信息对象 t_lic_tool_info
 *
 * @author esz
 * @date 2023-06-28
 */
public class TLicToolInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /**
     * 工具名称
     */
    @Excel(name = "工具名称")
    private String name;

    /**
     * 适用系统
     */
    @Excel(name = "适用系统")
    private String systemAdapter;

    /**
     * 需机器码(0需要 1不需)
     */
    @Excel(name = "需机器码", readConverterExp = "Y=需要,N=不需")
    private String macNeed;

    /**
     * 最大连接数
     */
    @Excel(name = "最大连接数", readConverterExp = "Y=需要,N=不需")
    private String maxConnectNum;

    /**
     * CPU数量
     */
    @Excel(name = "CPU数量", readConverterExp = "Y=需要,N=不需")
    private String cpuNum;

    /**
     * 集群节点数
     */
    @Excel(name = "集群节点数", readConverterExp = "Y=需要,N=不需")
    private String clusterNodes;

    /**
     * 部署IP
     */
    @Excel(name = "部署IP")
    private String deployServer;

    /**
     * 部署路径
     */
    @Excel(name = "部署路径")
    private String deployPath;

    /**
     * Lic工具文件 ID
     */
    @Excel(name = "Lic工具文件 ID")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long toolFileId;
    @Excel(name = "Lic工具文件名称")
    private String toolFileName;

    /**
     * 机器码文件ID
     */
    @Excel(name = "机器码文件ID")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long macFileId;
    @Excel(name = "机器码文件名称")
    private String macFileName;

    /**
     * 状态
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=失效")
    private String status;

    /**
     * 创建者id
     */
    @Excel(name = "创建者id")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long createId;

    /**
     * 更新者id
     */
    @Excel(name = "更新者id")
    @JsonSerialize(using=ToStringSerializer.class)
    private Long updateId;

    public String getToolFileName()
    {
        return toolFileName;
    }

    public void setToolFileName(String toolFileName)
    {
        this.toolFileName = toolFileName;
    }

    public String getMacFileName()
    {
        return macFileName;
    }

    public void setMacFileName(String macFileName)
    {
        this.macFileName = macFileName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
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

    public void setSystemAdapter(String systemAdapter)
    {
        this.systemAdapter = systemAdapter;
    }

    public String getSystemAdapter()
    {
        return systemAdapter;
    }

    public void setMacNeed(String macNeed)
    {
        this.macNeed = macNeed;
    }

    public String getMacNeed()
    {
        return macNeed;
    }

    public void setMaxConnectNum(String maxConnectNum)
    {
        this.maxConnectNum = maxConnectNum;
    }

    public String getMaxConnectNum()
    {
        return maxConnectNum;
    }

    public void setCpuNum(String cpuNum)
    {
        this.cpuNum = cpuNum;
    }

    public String getCpuNum()
    {
        return cpuNum;
    }

    public void setClusterNodes(String clusterNodes)
    {
        this.clusterNodes = clusterNodes;
    }

    public String getClusterNodes()
    {
        return clusterNodes;
    }

    public void setDeployServer(String deployServer)
    {
        this.deployServer = deployServer;
    }

    public String getDeployServer()
    {
        return deployServer;
    }

    public void setDeployPath(String deployPath)
    {
        this.deployPath = deployPath;
    }

    public String getDeployPath()
    {
        return deployPath;
    }

    public void setToolFileId(Long toolFileId)
    {
        this.toolFileId = toolFileId;
    }

    public Long getToolFileId()
    {
        return toolFileId;
    }

    public void setMacFileId(Long macFileId)
    {
        this.macFileId = macFileId;
    }

    public Long getMacFileId()
    {
        return macFileId;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("systemAdapter", getSystemAdapter())
                .append("macNeed", getMacNeed())
                .append("maxConnectNum", getMaxConnectNum())
                .append("cpuNum", getCpuNum())
                .append("clusterNodes", getClusterNodes())
                .append("deployServer", getDeployServer())
                .append("deployPath", getDeployPath())
                .append("toolFileId", getToolFileId())
                .append("macFileId", getMacFileId())
                .append("status", getStatus())
                .append("createId", getCreateId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateId", getUpdateId())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
