package com.highgo.company.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tianyancha")
public class TianYanChaConfig
{
    private String token = "871bf862-2598-4161-bf48-4a3205637722";
    private String baseInfoNormal_1116 = "http://open.api.tianyancha.com/services/open/ic/baseinfo/normal?keyword={keyword}";
    private String search_816 = "http://open.api.tianyancha.com/services/open/search/2.0?word={key}";
}
