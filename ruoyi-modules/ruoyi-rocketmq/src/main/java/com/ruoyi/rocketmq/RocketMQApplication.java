package com.ruoyi.rocketmq;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 平台管理模块
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableAsync
public class RocketMQApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RocketMQApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RocketMQ模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
