package com.demo.common.aop;

import com.demo.common.annotation.BatchProcess;
import com.demo.common.config.AppConfig;
import com.demo.common.processor.BatchProcessor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class BatchProcessingAspect {
    private final BatchProcessor batchProcessor;
    @Pointcut("@annotation(com.demo.common.annotation.BatchProcess)")
    public void batchProcessPointcut() {
        // 方法为空，因为这是一个切点
    }
    @Around("batchProcessPointcut()")
    public Object processInBatch(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取注解
        BatchProcess batchProcess = signature.getMethod().getAnnotation(BatchProcess.class);
        int size = batchProcess.value();

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg instanceof List) {
                List<?> list = (List<?>) arg;
                batchProcessor.processInBatch(list, size, batch -> {
                    try {
                        joinPoint.proceed(new Object[]{batch});
                    } catch (Throwable throwable) {
                        throw new RuntimeException(throwable);
                    }
                });
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
