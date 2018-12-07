package com.tss.basic.site.util;

import com.alibaba.fastjson.JSON;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.item.CookieItem;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author MQG
 * @date 2018/11/29
 */
public class LoginHttpManager {
    protected static final String ENCODING = "UTF-8";
    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginHttpManager.class);

    protected static RequestConfig getRequestConfig(HttpCtrl ctrl) {
        return RequestConfig.custom()
                .setSocketTimeout(ctrl != null ? ctrl.getSocketTimeOut() : 5000)
                .setConnectTimeout(ctrl != null ? ctrl.getConnectTimeOut() : 5000)
                .setConnectionRequestTimeout(ctrl != null ? ctrl.getConnectionRequestTimeOut() : 5000)
                .build();
    }

    protected static String encode(Object request) throws UnsupportedEncodingException {
        if (request == null) {
            return "";
        }

        return URLEncoder.encode(JSON.toJSONString(request), ENCODING);
    }

    protected static <T> T decode(String response, Type clazz) {
        return JSON.parseObject(response, clazz);
    }

    /**
     * Get请求 获取用户基本信息
     * 
     * @param url 
     * @param userAcc
     * @param clazz
     * @param ctrl
     * @return
     */
    public static <T> DefaultResponse<T> getLoginUserInfo(String url, String userAcc, Type clazz, HttpCtrl ctrl) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + "/userAcc=" + userAcc);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , userAcc : {}, request: {}.", url, userAcc, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return null;
    }

    /**
     * Get请求 根据access_token获取用户基本信息
     *
     * @param url
     * @param access_token
     * @param clazz
     * @param ctrl
     * @return
     */
    public static <T> DefaultResponse<T> getLoginUserAuthInfo(String url, String access_token, Type clazz, HttpCtrl ctrl) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + "?access_token=" + access_token);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url={} , access_token={}, request={}.", url, access_token, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return null;
    }
}
