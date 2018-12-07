package com.tss.basic.site.user.processor;

/**
 * @author: MQG
 * @date: 2018/12/7
 */

import com.alibaba.fastjson.TypeReference;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.annotation.UserAuthInfo;
import com.tss.basic.site.user.config.AccessTokenConfig;
import com.tss.basic.site.util.LoginHttpManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
@EnableConfigurationProperties(AccessTokenConfig.class)
public class AccessTokenProcessor {

    private static Logger LOG = LoggerFactory.getLogger(AdminCookieProcessor.class);

    @Autowired
    private AccessTokenConfig accessTokenConfig;

    private static Type type;

    static {
        type = new TypeReference<DefaultResponse<UserAuthInfo>>() {
        }.getType();
    }

    public UserAuthInfo getLoginUserAuthInfo(String access_token) {
        DefaultResponse<UserAuthInfo> response = LoginHttpManager.getLoginUserAuthInfo(accessTokenConfig.getInfoUrl(), access_token, type, null);
        if (response == null || !response.isSuccess() || response.getData() == null) {
            LOG.info("admin user not login ,{}", access_token);
            return null;
        } else {
            response.getData().setAccess_token(access_token);
            return response.getData();
        }
    }
}
