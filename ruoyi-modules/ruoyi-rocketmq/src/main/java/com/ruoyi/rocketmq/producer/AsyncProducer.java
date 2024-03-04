package com.ruoyi.rocketmq.producer;

import com.ruoyi.rocketmq.config.ProducerConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

public class AsyncProducer {

    @Autowired
    private ProducerConfig producerConfig;

    /**
     * 发送异步的消息
     * @param topic 主题
     * @param tag 标签
     * @param key 自定义的key，根据业务来定
     * @param value 消息的内容
     * @return org.apache.rocketmq.client.producer.SendResult
     */
    public SendResult sendAsyncProducerMessage(String topic, String tag, String key, String value) throws UnsupportedEncodingException
    {
        try {
            DefaultMQProducer defaultMQProducer = producerConfig.producer;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(topic, tag, key,value.getBytes(RemotingHelper.DEFAULT_CHARSET));
            defaultMQProducer.send(msg, new SendCallback() {
                // 异步回调的处理
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d 异步发送消息成功 %s %n", msg, sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d 异步发送消息失败 %s %n", msg, e);
                    e.printStackTrace();
                }
            });
        } catch (MQClientException | RemotingException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
