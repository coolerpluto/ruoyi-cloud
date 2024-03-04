package com.ruoyi.rocketmq.config;

import com.ruoyi.rocketmq.model.ProducerMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * mq搭建地址连接
 * 生产者初者连接信息 具体看nacos配置
 */
@Configuration
@Slf4j
public class ProducerConfig {

    /**
     * 远程调用连接信息
     */
    public static DefaultMQProducer producer;

    /**
     * 连接客户端信息配置  具体看nacos配置
     */
    @Autowired
    private ProducerMode producerMode;

    @Bean
    public DefaultMQProducer getRocketMQProducer() {
        producer = new DefaultMQProducer(producerMode.getGroupName());
        producer.setNamesrvAddr(producerMode.getNamesrvAddr());
        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        if(producerMode.getMaxMessageSize()!=null){
            producer.setMaxMessageSize(producerMode.getMaxMessageSize());
        }
        if(producerMode.getSendMsgTimeout()!=null){
            producer.setSendMsgTimeout(producerMode.getSendMsgTimeout());
        }
        //如果发送消息失败，设置重试次数，默认为2次
        if(producerMode.getRetryTimesWhenSendFailed()!=null){
            producer.setRetryTimesWhenSendFailed(producerMode.getRetryTimesWhenSendFailed());
        }
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            log.info("生产者初始化成功：{}",producer.toString());
        } catch (MQClientException e) {
            log.error("生产者初始化失败：{}",e.getMessage());
        }
        return producer;
    }

}
