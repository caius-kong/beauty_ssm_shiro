package com.yingjun.ssm.exception;
/**
 *  业务异常
 *  Service层出现业务异常，throw new BizException(errorMsg), 交由Controller层统一try...catch
 *
 *  注意必须extends RuntimeException，因为Spring异常回滚规则是: 默认情况下，只在方法抛出运行时异常的时候才回滚
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message,  cause);
    }
}
