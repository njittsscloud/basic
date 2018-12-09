package com.tss.basic.site.user.argumentresolver;

import com.tss.basic.site.exception.UserNotLoginException;
import com.tss.basic.site.user.item.CookieItem;
import com.tss.basic.site.user.processor.AbstractCookieProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * @author MQG
 * @date 2018/11/29
 */
public abstract class AbstractUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserMethodArgumentResolver.class);
    private static final Pattern sessionPattern = Pattern.compile("[\\da-zA-Z_]{30,40}");

    public abstract AbstractCookieProcessor getCookieProcessor();

    public abstract boolean thrNotLoginException(MethodParameter parameter);

//    public static boolean isSessionId(String value) {
//        return sessionPattern.matcher(value).find();
//    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CookieItem cookieItem = new CookieItem();
        cookieItem.setName(getCookieProcessor().getCookieName());
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
            Cookie[] currCookies = httpServletRequest.getCookies();
            if (currCookies == null || currCookies.length == 0) {
                LOGGER.warn("no cookie name user not login.");
                throw new UserNotLoginException();
            }
            for (Cookie cookie : currCookies) {
                try {
                    if (cookie.getName().toLowerCase().equals(getCookieProcessor().getCookieName())) {
                        cookieItem.setValue(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                        break;
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.warn("bad cookie charset encode.", e);
                    throw new UserNotLoginException();
                }
            }
            // if (!isSessionId(cookieItem.getValue())) {
            //    throw new UserNotLoginException();
            // }
            Object data = getCookieProcessor().getLoginUserInfo(cookieItem, parameter);
            if (data == null) {
                throw new UserNotLoginException();
            }
            return data;
        } catch (Exception e) {
            LOGGER.warn("convert access_token to user info failed. {}", e.getMessage());
            if (thrNotLoginException(parameter)) {
                throw new UserNotLoginException();
            }
        }
        return null;
    }
    
    
}
