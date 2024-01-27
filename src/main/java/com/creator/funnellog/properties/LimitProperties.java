package com.creator.funnellog.properties;

import com.creator.funnellog.constants.StdConstant;
import com.creator.funnellog.service.LimitService;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "funnellog.limit")
public class LimitProperties {
    /**
     * 实现 LimitService 的类的全类名
     */
    private String clazz;

    public LimitProperties() {
        this.clazz = StdConstant.DEFAULT_LIMIT;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        try {
            Object o = Class.forName(clazz).newInstance();
            if(!(o instanceof LimitService)) {
                throw new RuntimeException("不是 LimitService 的实现类");
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.clazz = clazz;
    }
}
