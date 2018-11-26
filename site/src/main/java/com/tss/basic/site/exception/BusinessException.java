package com.tss.basic.site.exception;


/**
 * 业务异常
 *
 * @author MQG
 * @date 2018/11/26
 */
public class BusinessException extends AbstractBusinessException {

    /**
     * 序列化使用
     */
    private static final long serialVersionUID = 1L;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getErrMessage(), errorCode.getErrCode());
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getErrMessage(), errorCode.getErrCode(), cause);
    }

    public BusinessException(String message) {
        super(message, 71005);
    }

    public BusinessException(String message, int code) {
        super(message, code);
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, 71005, throwable);
    }

    public BusinessException(Object data, String message, int code) {
        super(data, message, code);
    }

    public BusinessException(Object data, String message, int code, Throwable throwable) {
        super(data, message, code, throwable);
    }

}
