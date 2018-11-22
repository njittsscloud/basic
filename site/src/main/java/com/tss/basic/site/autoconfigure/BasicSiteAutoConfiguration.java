package com.tss.basic.site.autoconfigure;

import com.tss.basic.site.messageconverter.TSSInternalJsonMessageConverter;
import com.tss.basic.site.messageconverter.TSSJsonMessageConverter;
import com.tss.basic.site.messageconverter.TSSSwagger2MessageConverter;
import com.tss.basic.site.response.BasicResponseErrorController;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration}
 * APP/M站接口请求参数数据格式的封装，自动完成JSON的编码解码
 *
 * @author MQG
 * @date 2018/11/22
 */
@Configuration
@ComponentScan("com.tss.basic.site")
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class BasicSiteAutoConfiguration {

   /* @Configuration
    @ConditionalOnWebApplication
    protected class MobApiMvcConfiguration extends WebMvcConfigurerAdapter {
        *//**
     * 参数解析器
     * *//*
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new JsonParamMethodArgumentResolver());
        }

    }*/

    /**
     * 统一异常处理
     */
    @Bean
    @ConditionalOnMissingBean(
            value = {ErrorController.class},
            search = SearchStrategy.CURRENT
    )
    public BasicResponseErrorController basicResponseErrorController(ErrorAttributes errorAttributes) {
        return new BasicResponseErrorController(errorAttributes);
    }

    /**
     * 消息转换器
     */
    @Bean
    public HttpMessageConverters customConverters() {
        return new HttpMessageConverters(false,
                Arrays.asList(new TSSSwagger2MessageConverter(),
                        new TSSInternalJsonMessageConverter(),
                        new TSSJsonMessageConverter()));
    }

}
