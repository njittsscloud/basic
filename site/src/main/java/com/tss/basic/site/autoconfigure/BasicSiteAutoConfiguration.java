package com.tss.basic.site.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration}
 * APP/M站接口请求参数数据格式的封装，自动完成JSON的编码解码
 * Date: 2015-05-13
 *
 * @author MQG
 */
@Configuration
@ComponentScan("com.tss.basic.site")
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class BasicSiteAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(
            value = {ErrorController.class},
            search = SearchStrategy.CURRENT
    )
    public BasicResponseErrorController basicResponseErrorController(ErrorAttributes errorAttributes) {
        return new BasicResponseErrorController(errorAttributes);
    }

}
