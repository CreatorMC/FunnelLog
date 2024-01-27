package com.creator.funnellog.service.impl;

import com.alibaba.fastjson.JSON;
import com.creator.funnellog.annotation.DoRateLimiter;
import com.creator.funnellog.constants.StdConstant;
import com.creator.funnellog.service.LimitService;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public class RateLimitServiceImpl implements LimitService {
    @Override
    public Object access(ProceedingJoinPoint joinPoint, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable {
        //判断有没有开启限流，没有则直接执行方法
        if(0 == doRateLimiter.permitsPerSecond()) {
            return joinPoint.proceed();
        }
        String clazzName = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        String key = clazzName + "." + methodName;
        //检查是否已存在，不存在则创建
        if(null == StdConstant.limitMap.get(key)) {
            StdConstant.limitMap.put(key, RateLimiter.create(doRateLimiter.permitsPerSecond()));
        }
        RateLimiter rateLimiter = StdConstant.limitMap.get(key);
        //判断此方法是否允许执行
        if(rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        }
        //不允许执行，返回注解里的信息，并转换成方法的返回对象
        return JSON.parseObject(doRateLimiter.returnJson(), method.getReturnType());
    }
}
