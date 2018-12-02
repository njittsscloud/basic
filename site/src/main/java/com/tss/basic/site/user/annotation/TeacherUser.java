package com.tss.basic.site.user.annotation;

import com.tss.basic.site.user.item.AbstractUser;

/**
 * @author MQG
 * @date 2018/12/02
 */
public class TeacherUser extends AbstractUser {
    @Override
    public Integer getUserType() {
        return UserTypeEnum.STUDENT.getValue();
    }

    private long teacherId;
    private String teacherName;
    private String jobNo;
    private long academicId;
    private String academicName;

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
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
