package com.creator.funnellog.aspect;

import com.creator.funnellog.annotation.DoRateLimiter;
import com.creator.funnellog.service.LimitService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class LimitAspect {
    @Autowired
    private LimitService limitService;

    @Pointcut("@annotation(com.creator.funnellog.annotation.DoRateLimiter)")
    public void pt(){}

    @Around("pt() && @annotation(doRateLimiter)")
    public Object doLimit(ProceedingJoinPoint joinPoint, DoRateLimiter doRateLimiter) throws Throwable {
        return limitService.access(joinPoint, getMethod(joinPoint), doRateLimiter, joinPoint.getArgs());
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return joinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }
}
