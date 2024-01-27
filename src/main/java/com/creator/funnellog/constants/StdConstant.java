package com.creator.funnellog.constants;

import com.google.common.util.concurrent.RateLimiter;

import java.util.HashMap;
import java.util.Map;

public class StdConstant {
    /**
     * key 为方法名，value 为对应的限流对象
     */
    public static final Map<String, RateLimiter> limitMap = new HashMap<>();
    /**
     * 默认限流实现类
     */
    public static final String DEFAULT_LIMIT = "com.creator.funnellog.service.impl.RateLimitServiceImpl";
}
