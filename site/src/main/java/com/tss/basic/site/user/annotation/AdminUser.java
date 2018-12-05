/*
 * Copyright (C) 2017-2018 Qy All rights reserved
 * Author: Liu Tong
 * Date: 2018/12/5
 * Description:AdminUser.java
 */
package com.tss.basic.site.user.annotation;

import com.tss.basic.site.user.item.AbstractUser;

/**
 * @author Liu Tong
 */
public class AdminUser extends AbstractUser {
    
    @Override
    public Integer getUserType() {
        return UserTypeEnum.ADMIN.getValue();
    }

    private long adminId;
    private String adminAcc;
    private String adminName;
    private long academicId;
    private String academicName;

    public String getAdminAcc() {
        return adminAcc;
    }

    public void setAdminAcc(String adminAcc) {
        this.adminAcc = adminAcc;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public long getAcademicId() {
        return academicId;
    }

    public void setAcademicId(long academicId) {
        this.academicId = academicId;
    }

    public String getAcademicName() {
        return academicName;
    }

    public void setAcademicName(String academicName) {
        this.academicName = academicName;
    }
}
