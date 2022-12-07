package com.coderlucifar.middleware.whitelist.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WhiteListProperties.class)      // 当 WhiteListProperties 位于当前类路径上，才会实例化一个类
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigure {

    @Bean("whiteListConfig")
    @ConditionalOnMissingBean // 仅仅在当前上下文中不存在某个对象时，才会实例化一个 Bean
    public String whiteListConfig(WhiteListProperties properties) {
        return properties.getUsers();
    }

}
