package com.tss.basic.site.response;

/**
 * 返回响应数据的基本格式
 *
 * @author MQG
 * @date 2018/11/22
 */
public abstract class AbstractResponse {

    private boolean success;

    private int errorCode;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
