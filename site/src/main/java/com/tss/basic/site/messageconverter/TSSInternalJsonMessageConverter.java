package com.tss.basic.site.messageconverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tss.basic.site.argumentresolver.JsonParam;
import com.tss.basic.site.response.DefaultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 内部系统调用使用的消息转换器
 * 
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSInternalJsonMessageConverter extends AbstractHttpMessageConverter<Object> {
    private static final Logger LOG = LoggerFactory.getLogger(TSSInternalJsonMessageConverter.class);

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    private static final MediaType SUPPORT_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);
    
    public TSSInternalJsonMessageConverter() {
        super(SUPPORT_MEDIA_TYPE);
    }
    
    @Override
    protected boolean supports(Class<?> clazz) {
        JsonParam annotation = clazz.getAnnotation(JsonParam.class);
        return annotation == null;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        DefaultResponse response = JSON.parseObject(inputMessage.getBody(), DefaultResponse.class);
        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new IOException(response == null ? "未知错误" : response.getMsg());
        }
        return JSON.parseObject(JSON.toJSONString(response.getData()), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        try {
            HttpHeaders headers = outputMessage.getHeaders();

            DefaultResponse response = new DefaultResponse();
            response.setData(obj);
            response.setErrorCode(DefaultResponse.OK_CODE);
            response.setMsg("OK");
            response.setSuccess(true);

            int len = JSON.writeJSONString(outnew, response, SerializerFeature.WriteMapNullValue);
            headers.setContentLength(len);

            outnew.writeTo(outputMessage.getBody());
        } catch (JSONException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } finally {
            outnew.close();
        }

    }
}
