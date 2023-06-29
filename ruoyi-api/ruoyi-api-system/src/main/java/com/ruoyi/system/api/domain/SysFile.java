package com.ruoyi.system.api.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件信息
 * @author ruoyi
 */
public class SysFile
{
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件所在服务器的资源目录根节点
     */
    private String homeDir;

    /**
     * 文件所在服务器
     */
    private String serverIp;

    public String getHomeDir()
    {
        return homeDir;
    }

    public void setHomeDir(String homeDir)
    {
        this.homeDir = homeDir;
    }

    public String getServerIp()
    {
        return serverIp;
    }

    public void setServerIp(String serverIp)
    {
        this.serverIp = serverIp;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", getName())
                .append("url", getUrl())
                .append("homeDir", getHomeDir())
                .append("serverIp", getServerIp())
                .toString();
    }
}
