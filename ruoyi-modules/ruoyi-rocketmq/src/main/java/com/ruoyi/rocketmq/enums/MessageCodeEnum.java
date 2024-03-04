package com.ruoyi.rocketmq.enums;


import lombok.Getter;

/**
 * 用于传递topic和 tag
 * 也用于接收消息后判断不同的消息处理不同的业务
 */
@Getter
public enum  MessageCodeEnum {

    /**
     * 系统消息
     */
    NOTE_MESSAGE_TOPIC("system-message","系统消息服务模块topic名称"),
    /**
     * 用户消息
     */
    USER_MESSAGE_TOPIC("user-message","用户消息服务模块topic名称"),

    /**
     * 订单消息
     */
    ORDER_MESSAGE_TOPIC("order-message","订单消息服务模块topic名称"),

    /**
     * 用户消息tag
     */
    USER_MESSAGE_TAG("user_message_tag","用户消息推送"),

    /**
     * 系统消息tag
     */
    NOTE_MESSAGE_TAG("system_message_tag","系统消息推送"),

    /**
     * 订单消息
     */
    ORDER_MESSAGE_TAG("order_message_tag","订单消息推送"),

    /**
     * 订单处理编号
     */
    ORDER_TIMEOUT_TAG("order_timeout_tag","订单超时处理");

    private final String code;
    private final String msg;

    MessageCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public static String valuesOfType(String code) {
        String value = "";
        for (MessageCodeEnum e : MessageCodeEnum.values()) {
            if (code.equals(e.code)) {
                value = e.msg;
            }
        }
        return value;
    }
}
