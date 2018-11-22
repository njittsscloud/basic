package com.tss.basic.site.messageconverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSJsonMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final MediaType SUPPORT_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);

    public TSSJsonMessageConverter() {
        super(SUPPORT_MEDIA_TYPE);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(inputMessage.getBody(), clazz);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        try {
            HttpHeaders headers = outputMessage.getHeaders();

            int len = JSON.writeJSONString(outnew, obj, SerializerFeature.WriteMapNullValue);
            headers.setContentLength(len);

            outnew.writeTo(outputMessage.getBody());
        } catch (JSONException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } finally {
            outnew.close();
        }
    }

}
