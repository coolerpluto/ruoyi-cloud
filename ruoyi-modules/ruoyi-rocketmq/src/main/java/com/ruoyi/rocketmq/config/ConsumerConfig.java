package com.ruoyi.rocketmq.config;

import com.ruoyi.rocketmq.consumer.RocketMsgListener;
import com.ruoyi.rocketmq.enums.MessageTopic;
import com.ruoyi.rocketmq.model.ConsumerMode;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 消费者配置
 */
@RefreshScope
@Configuration
@Slf4j
public class ConsumerConfig {

    @Autowired
    private ConsumerMode consumerMode;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() {
        //构建客户端连接
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerMode.getGroupName());
        //
        consumer.setNamesrvAddr(consumerMode.getNamesrvAddr());
        consumer.setConsumeThreadMin(consumerMode.getConsumeThreadMin());
        consumer.setConsumeThreadMax(consumerMode.getConsumeThreadMax());
        consumer.registerMessageListener(new RocketMsgListener());
        /**
         * 1. CONSUME_FROM_LAST_OFFSET：第一次启动从队列最后位置消费，后续再启动接着上次消费的进度开始消费
         * 2. CONSUME_FROM_FIRST_OFFSET：第一次启动从队列初始位置消费，后续再启动接着上次消费的进度开始消费
         * 3. CONSUME_FROM_TIMESTAMP：第一次启动从指定时间点位置消费，后续再启动接着上次消费的进度开始消费
         *  以上所说的第一次启动是指从来没有消费过的消费者，如果该消费者消费过，那么会在broker端记录该消费者的消费位置，如果该消费者挂了再启动，那么自动从上次消费的进度开始
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * CLUSTERING (集群模式) ：默认模式，同一个ConsumerGroup(groupName相同)每个consumer只消费所订阅消息的一部分内容，同一个ConsumerGroup里所有的Consumer消息加起来才是所
         *  订阅topic整体，从而达到负载均衡的目的
         * BROADCASTING (广播模式) ：同一个ConsumerGroup每个consumer都消费到所订阅topic所有消息，也就是一个消费会被多次分发，被多个consumer消费。
         * 需要注意的是，在广播模式下，每个Consumer都会独立地处理相同的消息副本。这可能会导致一些潜在的问题，例如消息重复处理或者资源浪费。因此，在使用广播模式时，请确保消息的处理逻辑是幂等的，并仔细考虑系统资源的消耗。
         */
        //        consumer.setMessageModel(MessageModel.BROADCASTING);

        consumer.setVipChannelEnabled(false);
        consumer.setConsumeMessageBatchMaxSize(consumerMode.getConsumeMessageBatchMaxSize());
        try {
            /**
             * 订阅topic，可以对指定消息进行过滤，例如："TopicTest","tagl||tag2||tag3",*或null表示topic所有消息
             * 由于官方并没有给直接订阅全部消息示例 所以使用list列表循环订阅所有topic
             */
            // 获取所有topic列表
            MessageTopic messageTopic = new MessageTopic();
            List<String> allTopics = messageTopic.RocketMQTopicList();
            //订阅所有topic
            for (String topic : allTopics) {
                consumer.subscribe(topic,"*");
            }
            consumer.start();
            log.info("消费者初始化成功：{}", consumer);
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("消费者初始化失败：{}",e.getMessage());
        }
        return consumer;
    }
}
