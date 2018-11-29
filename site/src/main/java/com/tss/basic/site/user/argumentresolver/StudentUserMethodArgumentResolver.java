package com.tss.basic.site.user.argumentresolver;

import com.tss.basic.site.user.annotation.StudentLoginUser;
import com.tss.basic.site.user.annotation.StudentUser;
import com.tss.basic.site.user.processor.AbstractCookieProcessor;
import com.tss.basic.site.user.processor.StudentCookieProcessor;
import org.springframework.core.MethodParameter;

/**
 * @author MQG
 * @date 2018/11/29
 */
public class StudentUserMethodArgumentResolver extends AbstractUserMethodArgumentResolver {
    private StudentCookieProcessor studentCookieProcessor;

    public StudentUserMethodArgumentResolver(StudentCookieProcessor studentCookieProcessor) {
        this.studentCookieProcessor = studentCookieProcessor;
    }

    @Override
    public AbstractCookieProcessor getCookieProcessor() {
        return studentCookieProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(StudentUser.class);
    }

    @Override
    public boolean thrNotLoginException(MethodParameter parameter) {
        StudentLoginUser studentLoginUser = parameter.getParameterAnnotation(StudentLoginUser.class);
        if (studentLoginUser != null && studentLoginUser.required()) {
            return true;
        } else {
            studentLoginUser = parameter.getContainingClass().getAnnotation(StudentLoginUser.class);
            if (studentLoginUser != null && studentLoginUser.required()) {
                return true;
            }
        }
        return false;
    }

}
