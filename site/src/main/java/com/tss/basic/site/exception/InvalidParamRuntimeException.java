package com.tss.basic.site.exception;

/**
 * 接口参数不合法
 *
 * @author MQG
 * @date 2018/11/23
 */
public class InvalidParamRuntimeException extends AbstractBusinessException {

    public static final int ERROR_CODE = 400000;

    public InvalidParamRuntimeException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidParamRuntimeException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }

    public InvalidParamRuntimeException(Object data, String message) {
        super(data, message, ERROR_CODE);
    }

    public InvalidParamRuntimeException(Object data, String message, Throwable throwable) {
        super(data, message, ERROR_CODE, throwable);
    }
}
