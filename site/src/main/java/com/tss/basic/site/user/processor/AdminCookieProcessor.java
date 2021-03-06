package com.tss.basic.site.user.processor;

import com.alibaba.fastjson.TypeReference;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.annotation.AdminLoginUser;
import com.tss.basic.site.user.annotation.AdminUser;
import com.tss.basic.site.user.annotation.UserAuthInfo;
import com.tss.basic.site.user.config.AdminUserConfig;
import com.tss.basic.site.user.item.CookieItem;
import com.tss.basic.site.user.item.CookieName;
import com.tss.basic.site.util.LoginHttpManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Type;

/**
 * @author Liu Tong
 */
@Configuration
@EnableConfigurationProperties(AdminUserConfig.class)
public class AdminCookieProcessor extends AbstractCookieProcessor {

    private static Logger LOG = LoggerFactory.getLogger(AdminCookieProcessor.class);

    @Autowired
    private AccessTokenProcessor accessTokenProcessor;
    @Autowired
    AdminUserConfig adminUserConfig;

    private static Type type;

    static {
        type = new TypeReference<DefaultResponse<AdminUser>>() {
        }.getType();
    }

    @Override
    public String getCookieName() {
        return CookieName.ACCESS_TOKEN.getCookieName();
    }

    @Override
    public Object getLoginUserInfo(CookieItem cookieItem, MethodParameter parameter) {
        AdminLoginUser adminLoginUser = parameter.getParameterAnnotation(AdminLoginUser.class);
        if (adminLoginUser != null && adminLoginUser.required() && parameter.getParameterType().equals(AdminUser.class)) {
            // 用户认证信息
            UserAuthInfo userAuthInfo = accessTokenProcessor.getLoginUserAuthInfo(cookieItem.getValue());
            if (userAuthInfo == null || StringUtils.isBlank(userAuthInfo.getUserAcc())) {
                LOG.info("admin user not login, {}", cookieItem);
                return null;
            }
            // 用户基本信息
            DefaultResponse<AdminUser> response = LoginHttpManager.getLoginUserInfo(adminUserConfig.getInfoUrl(), userAuthInfo.getUserAcc(), type, null);
            if (response == null || !response.isSuccess() || response.getData() == null) {
                LOG.info("admin user not login ,{}" + cookieItem);
                return null;
            } else {
                response.getData().setCookieItem(cookieItem);
                return response.getData();
            }
        }
        return null;
    }
}
