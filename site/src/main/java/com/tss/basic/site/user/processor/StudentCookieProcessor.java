package com.tss.basic.site.user.processor;

import com.alibaba.fastjson.TypeReference;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.annotation.StudentLoginUser;
import com.tss.basic.site.user.annotation.StudentUser;
import com.tss.basic.site.user.annotation.UserAuthInfo;
import com.tss.basic.site.user.config.StudentUserConfig;
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
 * @author MQG
 * @date 2018/11/29
 */
@Configuration
@EnableConfigurationProperties(StudentUserConfig.class)
public class StudentCookieProcessor extends AbstractCookieProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(StudentCookieProcessor.class);

    @Autowired
    private AccessTokenProcessor accessTokenProcessor;
    @Autowired
    private StudentUserConfig studentConfig;

    private static final Type type;

    static {
        type = new TypeReference<DefaultResponse<StudentUser>>() {
        }.getType();
    }

    @Override
    public String getCookieName() {
         return CookieName.ACCESS_TOKEN.getCookieName();
    }

    @Override
    public Object getLoginUserInfo(CookieItem cookieItem, MethodParameter parameter) {
        StudentLoginUser studentLoginUser = parameter.getParameterAnnotation(StudentLoginUser.class);
        if (studentLoginUser != null && studentLoginUser.required() && parameter.getParameterType().equals(StudentUser.class)) {
            // 用户认证信息
            UserAuthInfo userAuthInfo = accessTokenProcessor.getLoginUserAuthInfo(cookieItem.getValue());
            if (userAuthInfo == null || StringUtils.isBlank(userAuthInfo.getUserAcc())) {
                LOG.info("student user not login, {}", cookieItem);
                return null;
            }
            // 用户基本信息
            DefaultResponse<StudentUser> response = LoginHttpManager.getLoginUserInfo(studentConfig.getInfoUrl(), userAuthInfo.getUserAcc(), type, null);
            if (response == null || !response.isSuccess() || response.getData() == null) {
                LOG.info("student user not login, {}", cookieItem);
                return null;
            } else {
                response.getData().setCookieItem(cookieItem);
                return response.getData();
            }
        } else {
            return null;
        }
    }
}
