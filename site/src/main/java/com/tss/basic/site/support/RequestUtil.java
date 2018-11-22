package com.tss.basic.site.support;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MQG
 * @date 2018/11/22
 */
public class RequestUtil {
    public static InputStream getBody(HttpServletRequest servletRequest) throws IOException {
        return (InputStream)(isFormPost(servletRequest)?getBodyFromServletRequestParameters(servletRequest):servletRequest.getInputStream());
    }

    public static boolean isFormPost(HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().contains("application/x-www-form-urlencoded") && "POST".equalsIgnoreCase(request.getMethod());
    }
    private static InputStream getBodyFromServletRequestParameters(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        OutputStreamWriter writer = new OutputStreamWriter(bos, "UTF-8");
        Map<String, String[]> form = request.getParameterMap();

        Iterator<Map.Entry<String, String[]>> it = form.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, String[]> entry = it.next();
            String name = entry.getKey();
            List<String> values = Arrays.asList(entry.getValue());
            Iterator<String> valueIterator = values.iterator();

            while(valueIterator.hasNext()) {
                String value = valueIterator.next();
                writer.write(URLEncoder.encode(name, "UTF-8"));
                if(value != null) {
                    writer.write(61);
                    writer.write(URLEncoder.encode(value, "UTF-8"));
                    if(valueIterator.hasNext()) {
                        writer.write(38);
                    }
                }
            }

            if(it.hasNext()) {
                writer.append('&');
            }
        }

        writer.flush();
        return new ByteArrayInputStream(bos.toByteArray());
    }
}
