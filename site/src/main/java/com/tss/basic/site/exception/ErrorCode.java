package com.tss.basic.site.exception;

/**
 * 错误码
 *
 * @author MQG
 */
public class ErrorCode {

    private int errCode;

    private String errMessage;

    public ErrorCode(int errCode) {
        this.errCode = errCode;
    }

    public ErrorCode(int errCode, String errMessage) {
        this.errMessage = errMessage;
    }

    /**
     * @return the errCode
     */
    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    /*
     * (non-Javadoc)
     */
    @Override
    public String toString() {
        return errCode + "-" + errMessage;
    }
}
