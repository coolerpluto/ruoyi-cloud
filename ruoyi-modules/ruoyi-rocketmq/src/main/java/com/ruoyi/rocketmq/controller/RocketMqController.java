package com.ruoyi.rocketmq.controller;

import com.ruoyi.rocketmq.entity.MessageReg;
import com.ruoyi.rocketmq.producer.MessageProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息测试类Controller
 */
@RestController
@RequestMapping("/rocketMq")
public class RocketMqController
{

    /**
     * 发送同步消息
     */
    @PostMapping("/sendSynchronizeMessage")
    private Map sendSynchronizeMessage(@RequestBody MessageReg messageReg)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法
        SendResult sendResult = messageProducer.sendSynchronizeMessage(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue());
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送单向消息
     */
    @PostMapping("/sendOnewayMessage")
    private Map sendOnewayMessage(@RequestBody MessageReg messageReg)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        messageProducer.sendOnewayMessage(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue());
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "发送成功");
        result.put("code", 200);
        return result;
    }


    /**
     * 批量发送消息
     */
    @PostMapping("/sendBatchMessage")
    private Map sendBatchMessage(@RequestBody List<MessageReg> messageRegList)
    {
        // 根据实际需求创建消息列表并返回
        List<Message> messages = new ArrayList<>();
        // 添加消息到列表
        for (MessageReg messageReg : messageRegList)
        {
            //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
            messages.add(new Message(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue().getBytes()));
        }
        MessageProducer messageProducer = new MessageProducer();
        SendResult sendResult = messageProducer.sendBatchMessage(messages);
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送事物消息
     */
    @PostMapping("/sendThingMessage")
    private Map sendThingMessage(@RequestBody MessageReg messageReg)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendThingMessage(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue());
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送有序的消息
     */
    @PostMapping("/sendOrderlyMessage")
    private Map sendOrderlyMessage(@RequestBody List<MessageReg> messageRegList)
    {
        // 根据实际需求创建消息列表并返回
        List<Message> messages = new ArrayList<>();
        // 添加消息到列表
        for (MessageReg messageReg : messageRegList)
        {
            //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
            messages.add(new Message(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue().getBytes()));
        }
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
    private Map sendDelayMessage(@RequestBody MessageReg messageReg)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendDelayMessage(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue(), messageReg.getDelayTimeLevel());
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }


    /**
     * 发送异步的消息
     */
    @PostMapping("/sendAsyncProducerMessage")
    private Map sendAsyncProducerMessage(@RequestBody MessageReg messageReg)
    {
        MessageProducer messageProducer = new MessageProducer();
        //调用MessageProducer配置好的消息方法 topic需要你根据你们业务定制相应的
        SendResult sendResult = messageProducer.sendAsyncProducerMessage(messageReg.getTopic(), messageReg.getTag(), messageReg.getKey(), messageReg.getValue());
        Map<String, Object> result = new HashMap<>();
        result.put("data", sendResult);
        return result;
    }

}
