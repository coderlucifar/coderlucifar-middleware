package com.coderlucifar.middleware.whitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 白名单配置获取
 */
@ConfigurationProperties("coderlucifar.whitelist")
public class WhiteListProperties {

    private String users;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

}
