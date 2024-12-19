package com.rajith.accordtest.logging;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution( * com.rajith.accordtest..*(..))")
    private void applicationMethods() {
    }

    @Before(value = "applicationMethods()")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        logger.info("Entering method: {}", methodName);
        for(Object arg : args){
            logger.info("-- Request Arguments : {} --",args);
        }
        //logger.debug(">> {}() - {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "applicationMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Response : {} - {}", methodName, result);
    }

    @AfterThrowing(pointcut = "applicationMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("<< {}() - {}", methodName, exception.getMessage());
    }

    @Around(value = "applicationMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
     //   logger.debug(">> {}() - {}", methodName, Arrays.toString(args));
        Object result = joinPoint.proceed();
       // logger.debug("<< {}() - {}", methodName, result);
        return result;
    }
}
