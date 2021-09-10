package com.tujia.myssm.common.db.interceptor;

import java.util.Properties;
import java.util.concurrent.Executors;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tujia.myssm.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2021/09/10 19:30
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
@Slf4j
public class ErrorSqlInterceptor implements Interceptor {
    public boolean printSql = true;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sql = null;
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        try {
            sql = mappedStatement.getBoundSql(parameter).getSql();
            return invocation.proceed();
        } catch (Exception e) {
            log.error("SQL Error : method => {}, sql => {} , parameter => {}", mappedStatement.getId(), sql,
                    JsonUtils.tryToJson(parameter), e);
            throw e;
        } finally {
            if (printSql) {
                log.info("SQL INFO : method => {}, sql => {} , parameter => {}", mappedStatement.getId(), sql,
                        JsonUtils.tryToJson(parameter));
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
