package com.tss.basic.site.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author MQG
 * @date 2018/11/29
 */
@Primary
@Component
@ConfigurationProperties(prefix = "user.teacher")
public class TeacherUserConfig {
    private String infoUrl = "";

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }
}
