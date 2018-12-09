package com.tss.basic.site.user.processor;

import com.alibaba.fastjson.TypeReference;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.annotation.TeacherLoginUser;
import com.tss.basic.site.user.annotation.TeacherUser;
import com.tss.basic.site.user.annotation.UserAuthInfo;
import com.tss.basic.site.user.config.TeacherUserConfig;
import com.tss.basic.site.user.item.CookieItem;
import com.tss.basic.site.user.item.CookieName;
import com.tss.basic.site.util.HttpManager;
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
@EnableConfigurationProperties(TeacherUserConfig.class)
public class TeacherCookieProcessor extends AbstractCookieProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(TeacherCookieProcessor.class);

    @Autowired
    private AccessTokenProcessor accessTokenProcessor;
    @Autowired
    private TeacherUserConfig teacherUserConfig;

    private static final Type type;

    static {
        type = new TypeReference<DefaultResponse<TeacherUser>>() {
        }.getType();
    }

    @Override
    public String getCookieName() {
        return CookieName.TEACHER.getCookieName();
    }

    @Override
    public Object getLoginUserInfo(CookieItem cookieItem, MethodParameter parameter) {
        TeacherLoginUser teacherLoginUser = parameter.getParameterAnnotation(TeacherLoginUser.class);
        if (teacherLoginUser != null && teacherLoginUser.required() && parameter.getParameterType().equals(TeacherUser.class)) {
            // 用户认证信息
            UserAuthInfo userAuthInfo = accessTokenProcessor.getLoginUserAuthInfo(cookieItem.getValue());
            if (userAuthInfo == null || StringUtils.isBlank(userAuthInfo.getUserAcc())) {
                LOG.info("teacher user not login, {}", cookieItem);
                return null;
            }
            // 用户基本信息
            DefaultResponse<TeacherUser> response = LoginHttpManager.getLoginUserInfo(teacherUserConfig.getInfoUrl(), userAuthInfo.getUserAcc(), type, null);
            if (response == null || !response.isSuccess() || response.getData() == null) {
                LOG.info("teacher user not login, {}", cookieItem);
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
