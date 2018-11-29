package com.tss.basic.site.user.processor;

import com.alibaba.fastjson.TypeReference;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.annotation.StudentLoginUser;
import com.tss.basic.site.user.annotation.StudentUser;
import com.tss.basic.site.user.config.UserStudentConfig;
import com.tss.basic.site.user.item.CookieItem;
import com.tss.basic.site.user.item.CookieName;
import com.tss.basic.site.util.HttpManager;
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
@EnableConfigurationProperties(UserStudentConfig.class)
public class StudentCookieProcessor extends AbstractCookieProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(StudentCookieProcessor.class);

    @Autowired
    private UserStudentConfig studentConfig;

    private static final Type type;

    static {
        type = new TypeReference<DefaultResponse<StudentUser>>() {
        }.getType();
    }

    @Override
    public String getCookieName() {
        return CookieName.STUDENT.getCookieName();
    }

    @Override
    public Object getLoginUserInfo(CookieItem cookieItem, MethodParameter parameter) {
        StudentLoginUser studentLoginUser = parameter.getContainingClass().getAnnotation(StudentLoginUser.class);
        if (studentLoginUser != null && parameter.getParameterType().equals(StudentUser.class)) {
            DefaultResponse<StudentUser> response = HttpManager.getLoginUserInfo(studentConfig.getInfoUrl(), cookieItem, type, null);
            if (response == null || !response.isSuccess()) {
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
