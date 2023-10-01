package com.example.docs.global.aop;

import com.example.docs.global.annotation.Retry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RetryAspect {
    static final Logger LOGGER = LoggerFactory.getLogger(RetryAspect.class);
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        LOGGER.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for(int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                LOGGER.info("[retry] max={} now={}", maxRetry, retryCount);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        return exceptionHolder;
    }

}
