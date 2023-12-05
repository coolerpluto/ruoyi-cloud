package com.highgo.company;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通讯模块
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@MapperScan("com.highgo.**.mapper")
public class HighGoCompanyApplication
{
    public static void main(String[] args) {
        SpringApplication.run(HighGoCompanyApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  公司信息查询模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
