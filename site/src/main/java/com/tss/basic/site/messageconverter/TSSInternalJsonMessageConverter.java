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
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 内部系统调用使用的消息转换器（只支持读取无@JsonParam注释的参数）
 * 
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSInternalJsonMessageConverter extends AbstractHttpMessageConverter<Object> implements GenericHttpMessageConverter<Object> {
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
    }
    
    // GenericHttpMessageConverter实现

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        Class<?> clazz = this.getClass(type);
        return clazz == null ? false : (clazz.getAnnotation(JsonParam.class) == null);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Class<?> clazz = this.getClass(type);
        
        DefaultResponse response = JSON.parseObject(inputMessage.getBody(), DefaultResponse.class);
        if (response == null || !response.isSuccess() || response.getData() == null) {
            throw new IOException(response == null ? "未知错误" : response.getMsg());
        }
        return JSON.parseObject(JSON.toJSONString(response.getData()), clazz);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public void write(Object obj, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    }
    
    private Class<?> getClass(Type type) {
        Class<?> clazz = null;
        if (type instanceof Class) {
            clazz = (Class) type;
        } else if (type instanceof ParameterizedTypeImpl) {
            ParameterizedTypeImpl pType = (ParameterizedTypeImpl) type;
            clazz = pType.getRawType();
        }
        return clazz;
    }
}
