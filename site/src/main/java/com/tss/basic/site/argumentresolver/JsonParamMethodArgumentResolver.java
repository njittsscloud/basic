package com.tss.basic.site.argumentresolver;

import com.alibaba.fastjson.JSON;
import com.tss.basic.site.exception.ValidationInvalidParamException;
import com.tss.basic.site.support.RequestUtil;
import com.tss.basic.site.validation.ValidationResult;
import com.tss.basic.site.validation.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;

/**
 * 对@JsonParam注解参数解析
 *
 * @author MQG
 * @date 2018/11/22
 */
public class JsonParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String json = this.getInput(nativeWebRequest, methodParameter);
        
        JsonParam jsonParam = methodParameter.getParameterAnnotation(JsonParam.class);
        this.validateRequired(jsonParam, json);
        if (StringUtils.isEmpty(json)) {
                return null;
        }
        
        // 打个补丁支持key=v方式
        if (json.charAt(json.length() - 1) == '=') {
            json = json.substring(0, json.length() - 1);
        }
        Object o = JSON.parseObject(json, methodParameter.getGenericParameterType());//JsonParser.readJavaType(JsonParser.getJavaType(methodParameter.getGenericParameterType(), null), json);

        // 校验
        if (jsonParam.validation()) {
            ValidationUtils.validate(o);
        }
        return o;
    }

    private String getInput(NativeWebRequest nativeWebRequest, MethodParameter methodParameter) throws IOException {
        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String method = httpServletRequest.getMethod();
        if ("GET".equals(method) || "DELETE".equals(method)) {
            if (StringUtils.isEmpty(httpServletRequest.getQueryString())) {
                return null;
            }
            return URLDecoder.decode(httpServletRequest.getQueryString(), "UTF-8");
        }

        InputStream inputStream = RequestUtil.getBody(httpServletRequest);
        if (inputStream == null) {
            return this.handleEmptyBody(methodParameter);
        }

        StringBuilder buffer = new StringBuilder();
        String line;
        try (Reader reader = new InputStreamReader(inputStream, "UTF-8");
             BufferedReader br = new BufferedReader(reader)
        ) {
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            //处理URL中含有%的情况
            return URLDecoder.decode(buffer.toString().replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
        }
    }

    private String handleEmptyBody(MethodParameter param) {
        if (null == param) {
            return null;
        }
        if (((JsonParam) param.getParameterAnnotation(JsonParam.class)).required()) {
            throw new HttpMessageNotReadableException("Required request body content is missing: " + param);
        } else {
            return null;
        }
    }
    
    private void validateRequired(JsonParam jsonParam, String json) {
        if (jsonParam.required() && StringUtils.isEmpty(json)) {
            throw new ValidationInvalidParamException("参数不能为空", "参数不正确");
        }
            
    }
}
