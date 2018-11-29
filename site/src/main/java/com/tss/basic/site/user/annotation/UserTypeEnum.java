package com.tss.basic.site.user.annotation;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MQG
 * @date 2018/11/29
 */
public enum UserTypeEnum {

    OTHER(0, "其他"),
    STUDENT(1, "学生"),
    TEACHER(2, "教师"),
    SYY(3, "实验员"),
    ADMIN(4, "管理员"),;

    private int value;
    private String desc;

    private static Map<Integer, UserTypeEnum> userTypeMap =
            Arrays.stream(UserTypeEnum.values()).collect(Collectors.toMap(UserTypeEnum::getValue, e -> e));

    UserTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public static UserTypeEnum valueOf(int value) {
        UserTypeEnum userTypeEnum = userTypeMap.get(value);
        return userTypeEnum == null ? OTHER : userTypeEnum;
    }
}
