package com.tss.basic.site.autoconfigure;

import com.tss.basic.site.argumentresolver.InternalJsonParamMethodArgumentResolver;
import com.tss.basic.site.argumentresolver.JsonParamMethodArgumentResolver;
import com.tss.basic.site.messageconverter.TSSInternalJsonMessageConverter;
import com.tss.basic.site.messageconverter.TSSJsonMessageConverter;
import com.tss.basic.site.messageconverter.TSSSwagger2MessageConverter;
import com.tss.basic.site.response.BasicResponseErrorController;
import com.tss.basic.site.user.argumentresolver.StudentUserMethodArgumentResolver;
import com.tss.basic.site.user.argumentresolver.TeacherUserMethodArgumentResolver;
import com.tss.basic.site.user.processor.StudentCookieProcessor;
import com.tss.basic.site.user.processor.TeacherCookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * @author MQG
 * @date 2018/11/22
 */
@Configuration
@ComponentScan("com.tss.basic.site")
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class BasicSiteAutoConfiguration {

    @Autowired
    private StudentCookieProcessor studentCookieProcessor;
    @Autowired
    private TeacherCookieProcessor teacherCookieProcessor;

    @Configuration
    @ConditionalOnWebApplication
    protected class MobApiMvcConfiguration extends WebMvcConfigurerAdapter {

        /**
         * 参数解析器
         */
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new JsonParamMethodArgumentResolver());
            argumentResolvers.add(new InternalJsonParamMethodArgumentResolver());
            argumentResolvers.add(new StudentUserMethodArgumentResolver(studentCookieProcessor));
            argumentResolvers.add(new TeacherUserMethodArgumentResolver(teacherCookieProcessor));
        }

    }

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
                        new TSSJsonMessageConverter(),
                        new TSSInternalJsonMessageConverter()));
    }

}
