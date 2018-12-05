package com.tss.basic.site.user.annotation;

import java.lang.annotation.*;

/**
 * 添加到类上表示数据注入
 * 参数上添加此注解只用作消除具体参数影响 如 required = false 不做注入判断
 * 
 * @author liutong
 * @date 2018/12/05
 */

@Target({ElementType.TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLoginUser {
    boolean required() default true;
}
