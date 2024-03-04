package com.ruoyi.rocketmq.producer;

/**
 * @author 影子
 * 用于捕捉异常非受检异常（unchecked exception）
 * RuntimeException 和其子类的异常在编译时不需要进行强制性的异常处理，可以选择在运行时进行捕获和处理
 * 可选择使用
 */
public class ConsumeException extends RuntimeException{
    private static final long serialVersionUID = 4093867789628938836L;

    public ConsumeException(String message) {
        super(message);
    }

    public ConsumeException(Throwable cause) {
        super(cause);
    }

    public ConsumeException(String message, Throwable cause) {
        super(message, cause);
    }
}
