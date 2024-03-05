package com.ruoyi.rocketmq.producer;

import com.alibaba.fastjson.JSON;
import com.ruoyi.rocketmq.consumer.RocketMsgTransactionListenerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.ruoyi.rocketmq.config.ProducerConfig.producer;

/**
 * 消息发送
 */
@Slf4j
public class MessageProducer
{
    /**
     * 同步发送消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param key   自定义的key，根据业务来定
     * @param value 消息的内容
     *              通过调用 send() 方法发送消息，阻塞等待服务器响应。
     */
    public SendResult sendSynchronizeMessage(String topic, String tag, String key, String value)
    {
        String body = "topic：【" + topic + "】, tag：【" + tag + "】, key：【" + key + "】, value：【" + value + "】";
        try
        {
            Message msg = new Message(topic, tag, key, value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            log.debug("生产者发送消息内容:{}", JSON.toJSONString(value));
            SendResult result = producer.send(msg);
            log.debug("生产者发送消息结果:{}", JSON.toJSONString(result));
            return result;
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("消息初始化失败！body：{}", body);
        }
        catch (MQClientException | InterruptedException | RemotingException | MQBrokerException e)
        {
            log.error("消息发送失败! body：{}", body);
        }
        return null;
    }

    /**
     * 单向发送消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param key   自定义的key，根据业务来定
     * @param value 消息的内容
     *              单向发送：通过调用 sendOneway() 方法发送消息，不关心发送结果，适用于对可靠性要求不高的场景。
     */
    public void sendOnewayMessage(String topic, String tag, String key, String value)
    {
        String body = "topic：【" + topic + "】, tag：【" + tag + "】, key：【" + key + "】, value：【" + value + "】";
        try
        {
            Message msg = new Message(topic, tag, key, value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            log.debug("生产者发送消息:{}", JSON.toJSONString(value));
            producer.sendOneway(msg);
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("消息初始化失败！body：{}", body);
        }
        catch (MQClientException | InterruptedException | RemotingException e)
        {
            log.error("消息发送失败! body：{}", body);
        }
    }


    /**
     * 批量发送消息
     *
     * @param messages 消息列表
     *                 批量发送：通过调用 send() 方法并传入多条消息，实现批量发送消息。
     */
    public SendResult sendBatchMessage(List<Message> messages)
    {
        String body = messages.toString();
        try
        {
            log.debug("生产者发送消息内容:{}", JSON.toJSONString(messages));
            // 发送批量消息
            SendResult sendResult = producer.send(messages);
            log.debug("生产者发送消息结果:{}", JSON.toJSONString(sendResult));
            return sendResult;
        }
        catch (MQClientException | InterruptedException | RemotingException e)
        {
            log.error("消息发送失败! body：{}", body);
        }
        catch (MQBrokerException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * 事务消息发送
     *
     * @param topic 主题
     * @param tag   标签
     * @param key   自定义的key，根据业务来定
     * @param value 消息的内容
     *              事务消息发送：通过使用事务监听器实现本地事务执行和消息发送的一致性。
     */
    public SendResult sendThingMessage(String topic, String tag, String key, String value)
    {
        String body = "topic：【" + topic + "】, tag：【" + tag + "】, key：【" + key + "】, value：【" + value + "】";
        try
        {
            // 实例化事务生产者
            TransactionMQProducer transactionMQProducer = new TransactionMQProducer(producer.getProducerGroup());
            transactionMQProducer.setNamesrvAddr(producer.getNamesrvAddr());
            // 设置事务监听器
            transactionMQProducer.setTransactionListener(new RocketMsgTransactionListenerImpl());
            Message msg = new Message(topic, tag, key, value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            log.debug("生产者发送消息内容:{}", JSON.toJSONString(value));
            // 发送事务消息
            TransactionSendResult sendResult = transactionMQProducer.sendMessageInTransaction(msg, null);
            log.debug("生产者发送消息结果:{}", JSON.toJSONString(sendResult));
            return sendResult;
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("消息初始化失败！body：{}", body);
        }
        catch (MQClientException e)
        {
            log.error("消息发送失败! body：{}", body);
        }
        return null;
    }


    /**
     * 发送有序的消息
     *
     * @param messagesList       Message集合
     * @param messageQueueNumber 消息队列数量，根据实际情况设定
     *                           顺序发送： messageQueueNumber 表示消息的业务标识，可以根据具体需求进行设置来保证消息按顺序发送。
     */
    public SendResult sendOrderlyMessage(List<Message> messagesList, Integer messageQueueNumber)
    {
        SendResult result = null;
        for (Message message : messagesList)
        {
            try
            {
                result = producer.send(message, (list, msg, arg) ->
                {
                    Integer queueNumber = (Integer) arg;
                    //int queueIndex = queueNumber % list.size();
                    return list.get(queueNumber);
                }, messageQueueNumber);//根据编号取模，选择消息队列
            }
            catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e)
            {
                log.error("发送有序消息失败");
                return result;
            }
        }
        return result;
    }

    /**
     * 发送延迟消息
     *
     * @param topic          主题
     * @param tag            标签
     * @param key            自定义的key，根据业务来定
     * @param value          消息的内容
     * @param delayTimeLevel 消息延迟级别
     *                       延迟发送：通过设置延迟级别来实现延迟发送消息。
     */
    public SendResult sendDelayMessage(String topic, String tag, String key, String value, int delayTimeLevel)
    {
        SendResult result = null;
        try
        {
            Message msg = new Message(topic, tag, key, value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //设置消息延迟级别，我这里设置5，对应就是延时一分钟
            // "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
            msg.setDelayTimeLevel(delayTimeLevel);
            // 发送消息到一个Broker
            result = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            log.info("发送延迟消息结果：======sendResult：{}", result);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.info("发送时间：{}", format.format(new Date()));
            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("延迟消息队列推送消息异常:{},推送内容:{}", e.getMessage(), result);
        }
        return result;
    }

    /**
     * 发送异步的消息
     *
     * @param topic 主题
     * @param tag   标签
     * @param key   自定义的key，根据业务来定
     * @param value 消息的内容
     *              通过调用 send() 方法，并传入一个 SendCallback 对象，在发送消息的同时可以继续处理其他逻辑，消息发送结果通过回调函数通知。
     */
    public SendResult sendAsyncProducerMessage(String topic, String tag, String key, String value)
    {
        try
        {
            //创建一个消息实例，指定主题、标签和消息体。
            Message msg = new Message(topic, tag, key, value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            System.out.println("生产者发送消息:" + JSON.toJSONString(value));
            producer.send(msg, new SendCallback()
            {
                // 异步回调的处理
                @Override
                public void onSuccess(SendResult sendResult)
                {
                    System.out.printf("%-10d 异步发送消息成功 %s %n", msg, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e)
                {
                    System.out.printf("%-10d 异步发送消息失败 %s %n", msg, e);
                    e.printStackTrace();
                }
            });
        }
        catch (MQClientException | RemotingException | InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }
}
