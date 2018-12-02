package com.tss.basic.site.messageconverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tss.basic.site.response.DefaultResponse;
import com.tss.basic.site.response.ErrorDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.*;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * 内部系统调用使用的消息转换器
 * obj->json，支持写固定结构，不论是否注释了@JsonParam
 * json->obj，支持读带有固定结构的json，返回data字段的对象
 * 该消息转换器放置在最后
 * 
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSInternalJsonMessageConverter extends AbstractGenericHttpMessageConverter<Object> {
    private static final Logger LOG = LoggerFactory.getLogger(TSSInternalJsonMessageConverter.class);

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    private static final MediaType SUPPORT_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);
    
    public TSSInternalJsonMessageConverter() {
        super(SUPPORT_MEDIA_TYPE);
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        return true;
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
        return true;
    }

    @Override
    protected void writeInternal(Object obj, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
        try {
            HttpHeaders headers = outputMessage.getHeaders();

            if (obj instanceof ErrorDataResponse) {
                int len = JSON.writeJSONString(outnew, obj, SerializerFeature.WriteMapNullValue);
                headers.setContentLength(len);
            } else {
                DefaultResponse<Object> response = new DefaultResponse<>();
                response.setSuccess(true);
                response.setMsg("success");
                response.setErrorCode(DefaultResponse.OK_CODE);
                response.setData(obj);
                int len = JSON.writeJSONString(outnew, response, SerializerFeature.WriteMapNullValue);
                headers.setContentLength(len);
            }
            outnew.writeTo(outputMessage.getBody());
        } catch (JSONException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        } finally {
            outnew.close();
        }
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
