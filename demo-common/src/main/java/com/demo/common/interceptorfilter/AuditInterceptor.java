package com.demo.common.interceptorfilter;

import com.demo.common.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.naming.AuthenticationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class AuditInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameter = args[1];

        SqlCommandType sqlCommandType = ms.getSqlCommandType();
        if (sqlCommandType == SqlCommandType.INSERT || sqlCommandType == SqlCommandType.UPDATE) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new AuthenticationException("user has not register");
            }
            String updateBy = authentication.getName();

            if (parameter instanceof Map paramMap) {
                for(Object key : paramMap.keySet()){
                    if(String.valueOf(key).startsWith("param")){
                        Object param = paramMap.get(key);
                        if(param instanceof List list){
                            for(Object entity : list){
                                setAuditFields(entity, sqlCommandType, updateBy);
                            }
                        }
                    }
                }
                if (paramMap.containsKey("list")) {
                    List<?> paramList = (List<?>) paramMap.get("list");
                    paramList.forEach(entity->setAuditFields(entity, sqlCommandType, updateBy));
                }
            } else {
                setAuditFields(parameter, sqlCommandType, updateBy);
            }
        }

        return invocation.proceed();
    }

    private void setAuditFields(Object entity, SqlCommandType sqlCommandType, String updateBy) {
        if (entity instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) entity;
            OffsetDateTime now = OffsetDateTime.now();
            if (sqlCommandType == SqlCommandType.INSERT) {
                if (baseEntity.getCreatedAt() == null) {
                    baseEntity.setCreatedAt(now);
                }
                if (baseEntity.getCreatedBy() == null) {
                    baseEntity.setCreatedBy(updateBy);
                }
            }
            if (baseEntity.getUpdatedAt() == null) {
                baseEntity.setUpdatedAt(now);
            }
            if (baseEntity.getUpdatedBy() == null) {
                baseEntity.setUpdatedBy(updateBy);
            }
        }
    }
}