package com.tss.basic.site.argumentresolver;

import java.lang.annotation.*;

/**
 * @author MQG
 * @date 2018/11/22
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {
    boolean required() default true;

    boolean validation() default false;
}
