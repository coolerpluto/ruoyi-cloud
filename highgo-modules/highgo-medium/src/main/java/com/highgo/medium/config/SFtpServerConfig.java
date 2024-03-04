package com.highgo.medium.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author ruoyi
 */
@Configuration
@ConfigurationProperties(prefix = "sftp")
public class SFtpServerConfig {
    /**
     * 服务地址 ip 192.168.1.1
     */
    private String ip;
    /**
     * 使用的端口 22
     */
    private Integer port = 22;

    /**
     * 用户名timezone
     */
    private String accessKey;

    /**
     * 密码
     */
    private String secretKey;

    /**
     * 定义文件生成的根目录
     */
    private String homeDir;
    /**
     * 命令文件所在的根目录
     */
    private String commandDir;
    private String hgdbCommand;
    private String hgdwCommand;
    private String v9Command;
    /**
     * 执行命令时所在的路径
     */
    private String execHome;
    /**
     * License 文件下载连接模板
     */
    private String licUrlTemplate;

    public String getLicUrlTemplate() {
        return licUrlTemplate;
    }

    public void setLicUrlTemplate(String licUrlTemplate) {
        this.licUrlTemplate = licUrlTemplate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public String getCommandDir() {
        return commandDir;
    }

    public void setCommandDir(String commandDir) {
        this.commandDir = commandDir;
    }

    public String getHgdbCommand()
    {
        return hgdbCommand;
    }

    public void setHgdbCommand(String hgdbCommand)
    {
        this.hgdbCommand = hgdbCommand;
    }

    public String getHgdwCommand()
    {
        return hgdwCommand;
    }

    public void setHgdwCommand(String hgdwCommand)
    {
        this.hgdwCommand = hgdwCommand;
    }

    public String getV9Command()
    {
        return v9Command;
    }

    public void setV9Command(String v9Command)
    {
        this.v9Command = v9Command;
    }

    public String getExecHome() {
        return execHome;
    }

    public void setExecHome(String execHome) {
        this.execHome = execHome;
    }
}
