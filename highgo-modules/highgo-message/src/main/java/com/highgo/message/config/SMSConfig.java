package com.highgo.message.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "sms")
public class SMSConfig {
    private String captchaExpiration;
    private String signature;
    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private List<Map<String, String>> templates;

    public String getCaptchaExpiration() {
        return captchaExpiration;
    }

    public void setCaptchaExpiration(String captchaExpiration) {
        this.captchaExpiration = captchaExpiration;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public List<Map<String, String>> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Map<String, String>> templates) {
        this.templates = templates;
    }
    @Bean
    public com.aliyun.dysmsapi20170525.Client getSMSClient()  {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        try {
            return new com.aliyun.dysmsapi20170525.Client(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
