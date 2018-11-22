package com.tss.basic.site.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tss.basic.site.response.DefaultResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSJsonMessageConverter extends AbstractHttpMessageConverter<Object> {
    
    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
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
