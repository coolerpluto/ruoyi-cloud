package com.ruoyi.rocketmq.consumer;

import com.ruoyi.rocketmq.enums.MessageCodeEnum;
import com.ruoyi.rocketmq.producer.ConsumeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 消息监听
 */
@Slf4j
@Component
public class RocketMsgListener implements MessageListenerConcurrently {

    /**
     * 消费消息
     * @param list    msgs.size() >= 1
     *                DefaultMQPushConsumer.consumeMessageBatchMaxSize=1，you can modify here
     *                这里只设置为1，当设置为多个时，list中只要有一条消息消费失败，就会整体重试
     * @param consumeConcurrentlyContext 上下文信息
     * @return 消费状态  成功（CONSUME_SUCCESS）或者 重试 (RECONSUME_LATER)
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
       try{
           //消息不等于空情况
           if (!CollectionUtils.isEmpty(list)) {
               //获取topic
               for (MessageExt messageExt : list) {
                   // 解析消息内容
                   String body = new String(messageExt.getBody());
                   log.info("接受到的消息为：{}", body);
                   String tags = messageExt.getTags();
                   String topic = messageExt.getTopic();
                   String msgId = messageExt.getMsgId();
                   String keys = messageExt.getKeys();
                   int reConsume = messageExt.getReconsumeTimes();
                   // 消息已经重试了3次，如果不需要再次消费，则返回成功
                   if (reConsume == 3) {
                       // TODO 补偿信息
                       return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//根据业务返回是否正常
                   }
                   // 根据不同的topic处理不同的业务 这里以订单消息为例子
                   if (MessageCodeEnum.ORDER_MESSAGE_TOPIC.getCode().equals(topic)) {
                       if (MessageCodeEnum.ORDER_TIMEOUT_TAG.getCode().equals(tags)) {
                           //处理你的业务
                           return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//业务处理成功
                       } else {
                           log.info("未匹配到Tag【{}】" + tags);
                       }
                   }
               }
           }
           // 消息消费失败
           //broker会根据设置的messageDelayLevel发起重试，默认16次
           return ConsumeConcurrentlyStatus.RECONSUME_LATER;
       } catch (Exception e) {
           // 调用 handleException 方法处理异常并返回处理结果
           return handleException(e);
       }
    }

    /**
     * 异常处理
     *
     * @param e 捕获的异常
     * @return 消息消费结果
     */
    private static ConsumeConcurrentlyStatus handleException(final Exception e) {
        Class exceptionClass = e.getClass();
        if (exceptionClass.equals(UnsupportedEncodingException.class)) {
            log.error(e.getMessage());
        } else if (exceptionClass.equals(ConsumeException.class)) {
            log.error(e.getMessage());
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
