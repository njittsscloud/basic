package com.tss.basic.site.messageconverter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tss.basic.site.response.DefaultResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author: MQG
 * @date: 2018/11/22
 */
public class TSSJsonMessageConverter extends AbstractHttpMessageConverter<Object> {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static final MediaType SUPPORT_MEDIA_TYPE = new MediaType("application", "json", DEFAULT_CHARSET);

    //http头字段名 返回的数据组织类型
    public static final String HTTP_HEAD_RESPONSE_DATA_TYPE = "TSS-RES-DATA-TYPE";
    //返回的数据为json字符串
    private static final String TSS_RESPONSE_DATA_TYPE_JSON = "json/text";
    //返回的数据位json格式并base64加密后字符串
    private static final String TSS_RESPONSE_DATA_TYPE_JSON_BASE64 = "json/base64";
    public static final String TSS_ORGIN_RESPONSE_DATA_TYPE_JSON = "orgin/text";

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
        boolean handled = false;
        if (obj instanceof springfox.documentation.spring.web.json.Json ||
                obj instanceof springfox.documentation.swagger.web.UiConfiguration) {
            // 排除swagger相关返回
            handled = true;
            outputMessage.getBody().write(JSON.toJSONString(obj).getBytes());
        } else if (obj instanceof ArrayList) {
            //排除swagger相关返回
            ArrayList<Object> arrayList = (ArrayList<Object>) obj;
            if (arrayList != null && arrayList.size() > 0) {
                if (arrayList.get(0) instanceof springfox.documentation.swagger.web.SwaggerResource) {
                    handled = true;
                    outputMessage.getBody().write(JSON.toJSONString(obj).getBytes());
                }
            }
        } 
        
        if (!handled) {
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

}
