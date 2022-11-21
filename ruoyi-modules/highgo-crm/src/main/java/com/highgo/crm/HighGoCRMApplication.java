package com.highgo.crm;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.highgo.**.mapper")
public class HighGoCRMApplication {
    public static void main(String[] args) {
        SpringApplication.run(HighGoCRMApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  CRM模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
