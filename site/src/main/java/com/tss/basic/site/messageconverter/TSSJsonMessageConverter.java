package com.tss.basic.site.messageconverter;

import com.alibaba.fastjson.JSON;
import com.tss.basic.site.argumentresolver.JsonParam;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSJsonMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final MediaType SUPPORT_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);

    public TSSJsonMessageConverter() {
        super(SUPPORT_MEDIA_TYPE);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.getAnnotation(JsonParam.class) != null;
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return (type instanceof Class) && ((Class) type).getAnnotation(JsonParam.class) != null;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(inputMessage.getBody(), clazz);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return JSON.parseObject(inputMessage.getBody(), (Class) type);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    protected void writeInternal(Object obj, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    }


}
