package com.tss.basic.site.user.item;

/**
 * @author MQG
 * @date 2018/11/29
 */
public enum CookieName {
    STUDENT("studentsid", "学生用户登录session id"),
    TEACHER("teachersid", "教师用户登录session id"),
    SYY("syysid", "实验员用户登录session id"),
    ADMIn("adminsid", "管理员用户登录session id");

    String cookieName;
    String desc;

    CookieName(String cookieName, String desc) {
        this.cookieName = cookieName;
        this.desc = desc;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
