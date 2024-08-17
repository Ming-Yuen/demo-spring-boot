package com.demo.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class SqlExecutionTimeInterceptor implements Interceptor {
    private static final long THRESHOLD = 500; // 设置阈值为500毫秒

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = invocation.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();

        if (executionTime > THRESHOLD) {
            log.debug("SQL statement ID：" + sqlId + " execution time：" + executionTime + "ms");

            if (invocation.getMethod().getName().equals("update")) {
                int affectedRows = (int) result;
                log.debug("SQL statement ID：" + sqlId + " affected rows：" + affectedRows);
            }
        }
        return result;
    }
}
