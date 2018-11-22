package com.tss.basic.site.response;

/**
 * 默认的接口响应数据格式
 *
 * @author MQG
 * @date 2018/11/22
 */
public class DefaultResponse<T> extends AbstractResponse {

    public static final int OK_CODE = 200000;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
