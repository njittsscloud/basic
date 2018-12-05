/*
 * Copyright (C) 2017-2018 Qy All rights reserved
 * Author: Liu Tong
 * Date: 2018/12/5
 * Description:AdminUserMethodArgumentResolver.java
 */
package com.tss.basic.site.user.argumentresolver;

import com.tss.basic.site.user.annotation.AdminLoginUser;
import com.tss.basic.site.user.annotation.AdminUser;
import com.tss.basic.site.user.processor.AbstractCookieProcessor;
import com.tss.basic.site.user.processor.AdminCookieProcessor;
import org.springframework.core.MethodParameter;

/**
 * @author Liu Tong
 */
public class AdminUserMethodArgumentResolver extends AbstractUserMethodArgumentResolver {
    private AdminCookieProcessor adminCookieProcessor;

    public AdminUserMethodArgumentResolver(AdminCookieProcessor adminCookieProcessor) {
        this.adminCookieProcessor = adminCookieProcessor;
    }

    @Override
    public AbstractCookieProcessor getCookieProcessor() {
        return this.adminCookieProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(AdminUser.class);
    }

    @Override
    public boolean thrNotLoginException(MethodParameter parameter) {
        AdminLoginUser adminLoginUser = parameter.getParameterAnnotation(AdminLoginUser.class);
        if (adminLoginUser != null && adminLoginUser.required()) {
            return true;
        } else {
            AdminLoginUser annotation = parameter.getContainingClass().getAnnotation(AdminLoginUser.class);
            if (annotation != null && annotation.required()) {
                return true;
            }
        }
        return false;
    }
}
