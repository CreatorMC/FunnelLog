package com.creator.funnellog.service;

import com.creator.funnellog.annotation.DoRateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

public interface LimitService {
    public Object access(ProceedingJoinPoint joinPoint, Method method, DoRateLimiter doRateLimiter, Object[] args) throws Throwable;
}
