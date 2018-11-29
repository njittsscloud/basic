package com.tss.basic.site.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author MQG
 * @date 2018/11/29
 */
@Component
@ConfigurationProperties(prefix = "user.student")
public class UserStudentConfig {
    private String infoUrl = "";

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
}
