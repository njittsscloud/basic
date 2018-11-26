package com.tss.basic.site.util;


import com.tss.basic.site.exception.AbstractBusinessException;
import com.tss.basic.site.exception.BusinessAssertLog;
import com.tss.basic.site.exception.BusinessException;
import com.tss.basic.site.exception.ErrorCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

/**
 * 断言工具类，目标是减少代码圈复杂度
 *
 * @author MQG
 */
public class TSSAssert {

    /**
     * 判断对象是否null
     *
     * @param object
     * @param errorCode
     */
    public static void isNull(Object object, ErrorCode errorCode) {
        if (object != null) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断对象是否非null
     */
    public static void isNotNull(Object object, ErrorCode errorCode) {
        if (object == null) {
            throw new BusinessException(errorCode);
        }
    }


    /**
     * 判断对象是否null或者""
     */
    public static void isEmpty(String str, ErrorCode errorCode) {
        if (!StringUtils.isEmpty(str)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断字符串是否为不为 null 且 ""
     */
    public static void isNotEmpty(String str, ErrorCode errorCode) {
        if (StringUtils.isEmpty(str)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断集合是否为空
     */
    public static void isEmpty(Collection collection, ErrorCode errorCode) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断集合是否非空
     */
    public static void notEmpty(Collection collection, ErrorCode errorCode) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断条件是否为true
     */
    public static void isTrue(Boolean isTrue, ErrorCode errorCode) {
        if (!isTrue) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * 判断对象是否null
     *
     * @param object
     */
    public static void isNull(Object object, AbstractBusinessException ex) {
        if (object != null) {
            throw ex;
        }
    }

    /**
     * 判断对象是否非null
     */
    public static void isNotNull(Object object, AbstractBusinessException ex) {
        if (object == null) {
            throw ex;
        }
    }


    /**
     * 判断对象是否null或者""
     */
    public static void isEmpty(String str, AbstractBusinessException ex) {
        if (!StringUtils.isEmpty(str)) {
            throw ex;
        }
    }

    /**
     * 判断字符串是否为不为 null 且 ""
     */
    public static void isNotEmpty(String str, AbstractBusinessException ex) {
        if (StringUtils.isEmpty(str)) {
            throw ex;
        }
    }

    /**
     * 判断集合是否为空
     */
    public static void isEmpty(Collection collection, AbstractBusinessException ex) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw ex;
        }
    }

    /**
     * 判断集合是否非空
     */
    public static void isNotEmpty(Collection collection, AbstractBusinessException ex) {
        if (CollectionUtils.isEmpty(collection)) {
            throw ex;
        }
    }

    /**
     * 判断条件是否为true
     */
    public static void isTrue(Boolean isTrue, AbstractBusinessException ex) {
        if (!isTrue) {
            throw ex;
        }
    }

    /**
     * 判断对象是否null
     *
     * @param object
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断对象是否非null
     */
    public static void isNotNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断对象是否非null
     */
    public static void isNotNull(Object object, String message, BusinessAssertLog log) {
        if (object == null) {
            if (log != null) {
                log.doLog();
            }
            throw new BusinessException(message);
        }
    }


    /**
     * 判断对象是否null或者""
     */
    public static void isEmpty(String str, String message) {
        if (!StringUtils.isEmpty(str)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断字符串是否为不为 null 且 ""
     */
    public static void isNotEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断集合是否为空
     */
    public static void isEmpty(Collection collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断集合是否非空
     */
    public static void isNotEmpty(Collection collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断条件是否为true
     */
    public static void isTrue(Boolean isTrue, String message) {
        if (!isTrue) {
            throw new BusinessException(message);
        }
    }

    /**
     * 判断条件是否为true
     */
    public static void isTrue(Boolean isTrue, String message, BusinessAssertLog log) {
        if (!isTrue) {
            if (log != null) {
                log.doLog();
            }
            throw new BusinessException(message);
        }
    }
}
