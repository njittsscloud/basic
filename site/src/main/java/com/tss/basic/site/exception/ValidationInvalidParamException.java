package com.tss.basic.site.exception;

/**
 * 接口参数不合法
 *
 * @author MQG
 * @date 2018/11/23
 */
public class ValidationInvalidParamException extends InvalidParamRuntimeException {

    public ValidationInvalidParamException(Object data, String message) {
        super(data, message);
    }
}
