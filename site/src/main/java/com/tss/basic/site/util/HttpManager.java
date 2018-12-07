package com.tss.basic.site.util;

import com.alibaba.fastjson.JSON;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.user.item.AbstractUser;
import com.tss.basic.site.user.item.CookieItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
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
public class HttpManager {
    protected static final String ENCODING = "UTF-8";
    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpManager.class);

    public static final HttpCtrl tenSecondsTimeout;
    public static final HttpCtrl twentySecondsTimeout;
    public static final HttpCtrl thirtySecondsTimeout;
    public static final HttpCtrl oneMinuteTimeout;

    static {
        tenSecondsTimeout = new HttpCtrl();
        tenSecondsTimeout.setConnectionRequestTimeOut(10000);
        tenSecondsTimeout.setConnectTimeOut(10000);
        tenSecondsTimeout.setSocketTimeOut(10000);

        twentySecondsTimeout = new HttpCtrl();
        twentySecondsTimeout.setConnectionRequestTimeOut(20000);
        twentySecondsTimeout.setConnectTimeOut(20000);
        twentySecondsTimeout.setSocketTimeOut(20000);

        thirtySecondsTimeout = new HttpCtrl();
        thirtySecondsTimeout.setConnectionRequestTimeOut(30000);
        thirtySecondsTimeout.setConnectTimeOut(30000);
        thirtySecondsTimeout.setSocketTimeOut(30000);

        oneMinuteTimeout = new HttpCtrl();
        oneMinuteTimeout.setConnectionRequestTimeOut(60000);
        oneMinuteTimeout.setConnectTimeOut(60000);
        oneMinuteTimeout.setSocketTimeOut(60000);
    }

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

    private static void setCookie(CookieItem item, HttpRequestBase httpRequestBase) {
        httpRequestBase.setHeader(new BasicHeader("Cookie", item.getName() + "=" + item.getValue()));
    }

    private static void setCookies(List<CookieItem> cookies, HttpRequestBase httpRequestBase) {
        String headCookies = "";
        for (CookieItem cookie : cookies) {
            headCookies = headCookies + cookie.getName() + "=" + cookie.getValue() + ";";
        }
        httpRequestBase.setHeader(new BasicHeader("Cookie", headCookies));
    }

    /**
     * 发起一个HTTP GET的接口请求
     */
    public static <T> T getData(String url, Object request, Type clazz, HttpCtrl ctrl, CookieItem item) {

        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            setCookie(item, httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP GET的接口请求
     */
    public static <T> T getData(String url, Object request, Type clazz, HttpCtrl ctrl, AbstractUser user) {

        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            setCookie(user.getCookieItem(), httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP GET的接口请求(不需要用户信息)
     */
    public static <T> T getData(String url, Object request, Type clazz, HttpCtrl ctrl) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }


    /**
     * 发起一个post请求
     */
    public static <T> T postData(String url, Object request, Type clazz, HttpCtrl ctrl, CookieItem item) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            setCookie(item, httpPost);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个post请求
     */
    public static <T> T postData(String url, Object request, Type clazz, HttpCtrl ctrl, AbstractUser user) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            setCookie(user.getCookieItem(), httpPost);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }


    /**
     * 发起一个post请求，无用户
     */
    public static <T> T postData(String url, Object request, Type clazz, HttpCtrl ctrl) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = JSON.parseObject(responseString, DefaultResponse.class);
            return JSON.parseObject(JSON.toJSONString(baseResponse.getData()), clazz);
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }


    /**
     * 发起一个HTTP GET的接口请求
     */
    public static <T> DefaultResponse<T> get(String url, Object request, Type clazz, HttpCtrl ctrl, CookieItem item) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            setCookie(item, httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }

    public static <T> DefaultResponse<T> get(String url, Object request, Type clazz, HttpCtrl ctrl, List<CookieItem> cookies) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            setCookies(cookies, httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP GET的接口请求
     */
    public static <T> DefaultResponse<T> get(String url, Object request, Type clazz, HttpCtrl ctrl, AbstractUser user) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            setCookie(user.getCookieItem(), httpGet);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }


    /**
     * 发起一个HTTP GET的接口请求（无用户）
     */
    public static <T> DefaultResponse<T> get(String url, Object request, Type clazz, HttpCtrl ctrl) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);
        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }
        HttpGet httpGet = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpGet = new HttpGet(url + (StringUtils.isEmpty(encode) ? "" : "?" + encode));
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP POST的接口请求
     */
    public static <T> DefaultResponse<T> post(String url, Object request, Type clazz, HttpCtrl ctrl, CookieItem item) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }

        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            setCookie(item, httpPost);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP POST的接口请求
     */
    public static <T> DefaultResponse<T> post(String url, Object request, Type clazz, HttpCtrl ctrl, List<CookieItem> cookies) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }

        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            setCookies(cookies, httpPost);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP POST的接口请求
     */
    public static <T> DefaultResponse<T> post(String url, Object request, Type clazz, HttpCtrl ctrl, AbstractUser user) {
        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }

        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            setCookie(user.getCookieItem(), httpPost);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }

    /**
     * 发起一个HTTP POST的接口请求(无用户)
     */
    public static <T> DefaultResponse<T> post(String url, Object request, Type clazz, HttpCtrl ctrl) {

        RequestConfig defaultRequestConfig = getRequestConfig(ctrl);

        String encode = null;
        try {
            encode = encode(request);
        } catch (Exception e) {
            LOGGER.error("encode data filed, url: {} , request: {}.", url, request, e);
            return null;
        }

        HttpPost httpPost = null;
        try {
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
            httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(encode);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            DefaultResponse<T> baseResponse = decode(responseString, clazz);
            return baseResponse;
        } catch (Exception e) {
            LOGGER.error("get data failed, url: {} , request: {}.", url, request, e);
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
        }

        return null;
    }

}
