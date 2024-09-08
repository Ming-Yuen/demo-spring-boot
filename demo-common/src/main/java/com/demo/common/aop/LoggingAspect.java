package com.demo.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.demo..dao.*.*(..))")
    public void daoMethods() {}

    @AfterReturning(pointcut = "daoMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        String interfaceName = (interfaces.length > 0) ? interfaces[0].getName() : joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("{}.{}, args {}, {}", interfaceName, methodName, Arrays.stream(joinPoint.getArgs()).map(clazz->clazz.getClass().getSimpleName()).toArray(), result instanceof List ? "size : " + ((List<?>) result).size():result);
//        log.info("{}.{}, args {}, {}", interfaceName, methodName, joinPoint.getArgs(), result instanceof List ? "size : " + ((List<?>) result).size():result);
    }
}
