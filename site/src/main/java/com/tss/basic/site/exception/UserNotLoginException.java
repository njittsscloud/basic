package com.tss.basic.site.exception;

/**
 * @author MQG
 * @date 2018/11/29
 */
public class UserNotLoginException extends AbstractBusinessException {
    public UserNotLoginException() {
        super("用户未登录!", 430000);
    }
}
