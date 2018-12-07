package com.tss.basic.site.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author MQG
 * @date 2018/12/07
 */
@Primary
@Component
@ConfigurationProperties(prefix = "user.auth")
public class AccessTokenConfig {
    private String infoUrl = "";

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
}
