package com.ruoyi.rocketmq.controller;

import com.ruoyi.rocketmq.producer.MessageProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息测试类Controller
 */
@RestController
@RequestMapping("/rocketMessage")
public class RocketMqController
{

    /**
     * 发送同步消息
     */
    @PostMapping("/sendSynchronizeMessage")
    private Map sendSynchronizeMessage()
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法
        SendResult sendResult = messageProducer.sendSynchronizeMessage("order-message", "order_message_tag", "title", "content");
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送单向消息
     */
    @PostMapping("/sendOnewayMessage")
    private Map sendOnewayMessage(@RequestParam("topic") String topic, @RequestParam("tag") String tag, @RequestParam("key") String key, @RequestParam("value") String value)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        messageProducer.sendOnewayMessage("order-message", "order_timeout_tag", "title", "content");
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "发送成功");
        result.put("code", 200);
        return result;
    }


    /**
     * 批量发送消息
     */
    @PostMapping("/sendBatchMessage")
    private Map sendBatchMessage()
    {
        // 根据实际需求创建消息列表并返回
        List<Message> messages = new ArrayList<>();
        // 添加消息到列表
        messages.add(new Message("order-message", "order_timeout_tag", "Message 1".getBytes()));
        messages.add(new Message("order-message", "order_timeout_tag", "Message 2".getBytes()));
        messages.add(new Message("order-message", "order_timeout_tag", "Message 3".getBytes()));
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendBatchMessage(messages);
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送事物消息
     */
    @PostMapping("/sendThingMessage")
    private Map sendThingMessage(@RequestParam("topic") String topic, @RequestParam("tag") String tag, @RequestParam("key") String key, @RequestParam("value") String value)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendThingMessage("order-message", "order_timeout_tag", "title", "content");
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送有序的消息
     */
    @PostMapping("/sendOrderlyMessage")
    private Map sendOrderlyMessage()
    {
        // 根据实际需求创建消息列表并返回
        List<Message> messages = new ArrayList<>();
        // 添加消息到列表
        messages.add(new Message("order-message", "order_timeout_tag", "Message 1".getBytes()));
        messages.add(new Message("order-message", "order_timeout_tag", "Message 2".getBytes()));
        messages.add(new Message("order-message", "order_timeout_tag", "Message 3".getBytes()));
        Integer messageQueueNumber = 3;
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendOrderlyMessage(messages, messageQueueNumber);
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }

    /**
     * 发送延迟消息
     */
    @PostMapping("/sendDelayMessage")
    private Map sendDelayMessage(@RequestParam("topic") String topic, @RequestParam("tag") String tag, @RequestParam("key") String key, @RequestParam("value") String value)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendDelayMessage("order-message", "order_timeout_tag", "title", "content");
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送异步的消息
     */
    @PostMapping("/sendAsyncProducerMessage")
    private Map sendAsyncProducerMessage(@RequestParam("topic") String topic, @RequestParam("tag") String tag, @RequestParam("key") String key, @RequestParam("value") String value)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendAsyncProducerMessage("order-message", "order_timeout_tag", "title", "content");
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }

}
