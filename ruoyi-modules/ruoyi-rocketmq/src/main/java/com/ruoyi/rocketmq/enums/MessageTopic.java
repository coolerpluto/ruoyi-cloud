package com.ruoyi.rocketmq.enums;


import java.util.ArrayList;
import java.util.List;

/**
 * 定义topic列表
 */
public class MessageTopic {

    //在这里添加topic 用于批量订阅
    public List<String> RocketMQTopicList(){
        List<String> getTopicLists=new ArrayList<>();
        //系统消息
        getTopicLists.add("system-message");
        //用户消息
        getTopicLists.add("user-message");
        //订单消息
        getTopicLists.add("order-message");
        return getTopicLists;
    }

}
