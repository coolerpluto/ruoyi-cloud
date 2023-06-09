package com.ruoyi.gen.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 代码生成数据源配置对象 gen_db_source
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public class GenDbSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据源主键 */
    private Long id;

    /** 数据库名称 */
    @Excel(name = "数据库名称")
    private String name;

    /** 数据库类型 */
    @Excel(name = "数据库类型")
    private String dbType;

    /** oracle连接方式 */
    @Excel(name = "oracle连接方式")
    private String oracleConnMode;

    /** oracle连接服务名或SID */
    @Excel(name = "oracle连接服务名或SID")
    private String serviceNameOrSid;

    /** 主机地址 */
    @Excel(name = "主机地址")
    private String host;

    /** 主机端口号 */
    @Excel(name = "主机端口号")
    private Integer port;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    /** 模式 */
    @Excel(name = "模式")
    private String schemaName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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
    public void setDbType(String dbType) 
    {
        this.dbType = dbType;
    }

    public String getDbType() 
    {
        return dbType;
    }
    public void setOracleConnMode(String oracleConnMode) 
    {
        this.oracleConnMode = oracleConnMode;
    }

    public String getOracleConnMode() 
    {
        return oracleConnMode;
    }
    public void setServiceNameOrSid(String serviceNameOrSid) 
    {
        this.serviceNameOrSid = serviceNameOrSid;
    }

    public String getServiceNameOrSid() 
    {
        return serviceNameOrSid;
    }
    public void setHost(String host) 
    {
        this.host = host;
    }

    public String getHost() 
    {
        return host;
    }
    public void setPort(Integer port) 
    {
        this.port = port;
    }

    public Integer getPort() 
    {
        return port;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setSchemaName(String schemaName) 
    {
        this.schemaName = schemaName;
    }

    public String getSchemaName() 
    {
        return schemaName;
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
            .append("dbType", getDbType())
            .append("oracleConnMode", getOracleConnMode())
            .append("serviceNameOrSid", getServiceNameOrSid())
            .append("host", getHost())
            .append("port", getPort())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("schemaName", getSchemaName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
