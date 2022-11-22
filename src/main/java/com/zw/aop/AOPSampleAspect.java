package com.zw.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AOPSampleAspect {

    @Pointcut("@annotation(com.zw.aop.SampleAnnotation)")
    private void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("JoinPoint related info");
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            log.info("arg: " + obj);
        }

        Signature signature = joinPoint.getSignature();
        log.info("name: " + signature.getName());
        log.info("info: " + signature);
        log.info("class name: " + signature.getDeclaringTypeName());
        log.info("simple class name: " + signature.getDeclaringType().getSimpleName());
        log.info("Step 1: end of JoinPoint");
    }

    @AfterReturning("pointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        log.info("Step 2 after returning join point " + joinPoint.getSignature().getName());
    }

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("Step 3 after " + joinPoint.getSignature().getName());
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        log.info("Do below at before");
        Object[] args = joinPoint.getArgs();
        for (Object obj : args) {
            log.info("arg: " + obj);
        }
        // change arg
        args[0] = "new hello arg";
        log.info("new arg " + args[0]);
        Object result = null;
        try {
            result = joinPoint.proceed(args);
        } catch (Throwable throwable) {
            log.error("error happened.", throwable);
        }

        log.info("Do below at after");
        log.info("Output of result: " + result);

        return result;
    }
}
