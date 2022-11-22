package com.zw.aop;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class AOPLimitAspect {

    private final Map<String, RateLimiter> limitMap = new ConcurrentHashMap<>();

    @Around("@annotation(com.zw.aop.LimitAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if(args[0] instanceof HttpServletRequest) {
            // we can limit per request user now
            HttpServletRequest request = (HttpServletRequest)args[0];
            log.info("Accessed by user : " + request.getUserPrincipal().getName());
        }
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        LimitAnnotation limit = method.getAnnotation(LimitAnnotation.class);
        String key = limit.key();
        RateLimiter  rateLimiter = null;
        if(!limitMap.containsKey(key)) {
            // create token card
            rateLimiter = RateLimiter.create(limit.perSecond());
            limitMap.put(key, rateLimiter);
            log.info("Created new token cooper");
        }
        rateLimiter = limitMap.get(key);
        boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
        if(!acquire) {
            log.error(limit.msg());
            throw new RuntimeException(limit.msg());
        }
        return joinPoint.proceed();
    }
}
