package com.tss.basic.site.user.argumentresolver;

import com.tss.basic.site.user.annotation.StudentUser;
import com.tss.basic.site.user.annotation.TeacherLoginUser;
import com.tss.basic.site.user.processor.AbstractCookieProcessor;
import com.tss.basic.site.user.processor.TeacherCookieProcessor;
import org.springframework.core.MethodParameter;

/**
 * @author MQG
 * @date 2018/11/29
 */
public class TeacherUserMethodArgumentResolver extends AbstractUserMethodArgumentResolver {
    private TeacherCookieProcessor teacherCookieProcessor;

    public TeacherUserMethodArgumentResolver(TeacherCookieProcessor teacherCookieProcessor) {
        this.teacherCookieProcessor = teacherCookieProcessor;
    }

    @Override
    public AbstractCookieProcessor getCookieProcessor() {
        return teacherCookieProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(StudentUser.class);
    }

    @Override
    public boolean thrNotLoginException(MethodParameter parameter) {
        TeacherLoginUser teacherLoginUser = parameter.getParameterAnnotation(TeacherLoginUser.class);
        if (teacherLoginUser != null && teacherLoginUser.required()) {
            return true;
        } else {
            teacherLoginUser = parameter.getContainingClass().getAnnotation(TeacherLoginUser.class);
            if (teacherLoginUser != null && teacherLoginUser.required()) {
                return true;
            }
        }
        return false;
    }

}
