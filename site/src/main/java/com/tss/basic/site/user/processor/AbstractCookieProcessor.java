package com.tss.basic.site.user.processor;

import com.tss.basic.site.user.item.CookieItem;
import org.springframework.core.MethodParameter;

/**
 * @author MQG
 * @date 2018/11/29
 */
public abstract class AbstractCookieProcessor {
    
    public abstract String getCookieName();

    public abstract Object getLoginUserInfo(CookieItem cookieItem, MethodParameter parameter);

}
