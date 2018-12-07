package com.tss.basic.site.user.annotation;

/**
 * @author MQG
 * @date 2018/12/07
 */
public class UserAuthInfo {
    
    private String userAcc;
    private String access_token;

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
