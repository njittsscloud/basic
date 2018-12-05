package com.tss.basic.site.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author liutong
 * @date 2018/12/05
 */
@Primary
@Component
@ConfigurationProperties(prefix = "user.admin")
public class AdminUserConfig {
    private String infoUrl = "";

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
}
