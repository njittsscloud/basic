package com.tss.basic.site.exception;

/**
 * @author MQG
 * @date 2018/11/23
 */
public class DataCheckException extends AbstractBusinessException {

    public static final int ERROR_CODE = 700000;

    public DataCheckException(String message) {
        super(message, ERROR_CODE);
    }

    public DataCheckException(String message, Throwable throwable) {
        super(message, ERROR_CODE, throwable);
    }

    public DataCheckException(Object data, String message) {
        super(data, message, ERROR_CODE);
    }

    public DataCheckException(Object data, String message, Throwable throwable) {
        super(data, message, ERROR_CODE, throwable);
    }
}
