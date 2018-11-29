package com.tss.basic.site.user.annotation;

import com.tss.basic.site.user.item.AbstractUser;

/**
 * Created by lzm on 2017/8/21.
 */
public class StudentUser extends AbstractUser {
    @Override
    public Integer getUserType() {
        return UserTypeEnum.STUDENT.getValue();
    }

    private long retailId;
    private String retailName;
    private long parentId;
    private String path;
    private boolean isRoot;

    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public long getRetailId() {
        return retailId;
    }

    public void setRetailId(long retailId) {
        this.retailId = retailId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
