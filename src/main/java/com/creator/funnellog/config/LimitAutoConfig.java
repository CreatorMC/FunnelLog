package com.creator.funnellog.config;

import com.creator.funnellog.properties.LimitProperties;
import com.creator.funnellog.service.LimitService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@AutoConfiguration
@EnableConfigurationProperties(LimitProperties.class)
public class LimitAutoConfig {

    @Resource
    private LimitProperties limitProperties;

    @Bean
    @ConditionalOnMissingBean
    public LimitService getLimitService() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String clazz = limitProperties.getClazz();
        return (LimitService) Class.forName(clazz).newInstance();
    }
}
