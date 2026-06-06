package org.cug.geodt.weibo.sos.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;


@Component
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class})
}

)
public class SqlInterceptor implements Interceptor {
    public static final Logger log = LoggerFactory.getLogger(SqlInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long b = System.currentTimeMillis();
        String sql = ((StatementHandler)invocation.getTarget()).getBoundSql().getSql();
        Object obj = invocation.proceed();
        log.info(sql + " cost " + (System.currentTimeMillis() - b) + "ms");
        return obj;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
