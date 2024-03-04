package com.ruoyi.rocketmq.consumer;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事物消息监听
 */
public class RocketMsgTransactionListenerImpl implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 在这里执行本地事务，比如数据库操作等
        // 如果本地事务执行成功，返回 COMMIT_MESSAGE
        // 如果本地事务执行失败，返回 ROLLBACK_MESSAGE
        // 如果本地事务执行中，可以返回 UNKNOW，RocketMQ 将会检查事务状态，并根据状态处理消息
        return LocalTransactionState.COMMIT_MESSAGE; // 根据实际情况返回对应的状态
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        // 检查本地事务状态，如果本地事务执行成功，返回 COMMIT_MESSAGE
        // 如果本地事务执行失败，返回 ROLLBACK_MESSAGE
        // 如果本地事务仍在执行中，返回 UNKNOW，RocketMQ 将会继续检查事务状态
        return LocalTransactionState.COMMIT_MESSAGE; // 根据实际情况返回对应的状态
    }

}
