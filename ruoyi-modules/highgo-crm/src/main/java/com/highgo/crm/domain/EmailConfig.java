package com.highgo.crm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 邮箱设置对象 t_mail_config
 * 
 * @author eszhang
 * @date 2023-01-17
 */
public class EmailConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 邮件发送服务器 */
    @Excel(name = "邮件发送服务器")
    private String server;

    /** 服务器端口 */
    private Integer port;

    /** 邮箱账户 */
    @Excel(name = "邮箱账户")
    private String secretKey;

    /** 账户密码 */
    private String accessKey;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String status;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setServer(String server) 
    {
        this.server = server;
    }

    public String getServer() 
    {
        return server;
    }
    public void setPort(Integer port)
    {
        this.port = port;
    }

    public Integer getPort()
    {
        return port;
    }
    public void setSecretKey(String secretKey) 
    {
        this.secretKey = secretKey;
    }

    public String getSecretKey() 
    {
        return secretKey;
    }
    public void setAccessKey(String accessKey) 
    {
        this.accessKey = accessKey;
    }

    public String getAccessKey() 
    {
        return accessKey;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
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
            .append("server", getServer())
            .append("post", getPort())
            .append("secretKey", getSecretKey())
            .append("accessKey", getAccessKey())
            .append("nickName", getNickName())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
