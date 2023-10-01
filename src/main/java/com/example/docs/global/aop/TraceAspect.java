package com.example.docs.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TraceAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(TraceAspect.class);

    @Before(value = "@annotation(com.example.docs.global.annotation.Trace)")
    public void doTrance(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        LOGGER.info("[trace] {} args={}", joinPoint.getSignature(), args);
    }
}
