package com.ruoyi.rocketmq.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReg
{
    private String topic;
    private String tag;
    private String key;
    private String value;
    private int delayTimeLevel;
}
