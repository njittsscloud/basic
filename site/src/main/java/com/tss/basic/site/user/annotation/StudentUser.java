package com.tss.basic.site.user.annotation;

import com.tss.basic.site.user.item.AbstractUser;

import java.io.Serializable;

/**
 * @author MQG
 * @date 2018/12/02
 */
public class StudentUser extends AbstractUser implements Serializable {
    @Override
    public Integer getUserType() {
        return UserTypeEnum.STUDENT.getValue();
    }

    private long studentId;
    private String studentName;
    private String studentNo;
    private long classId;
    private String className;
    private long academicId;
    private String academicName;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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
