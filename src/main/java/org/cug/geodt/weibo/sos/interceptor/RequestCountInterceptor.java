package org.cug.geodt.weibo.sos.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.cug.geodt.weibo.sos.common.constants.RedisConstants.KEY_PREFIX;

/**
 * @FileName RequestCountInterceptor
 * @Author WJW
 * @Date 2023/10/9 14:50
 * @Description 拦截器实现接口调用次数统计
 */
@Slf4j
@Configuration
public class RequestCountInterceptor implements HandlerInterceptor {



    private StringRedisTemplate stringRedisTemplate;

    public RequestCountInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String attribute = (String) request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern");
        try {
            stringRedisTemplate.opsForValue().increment(KEY_PREFIX + attribute);
        } catch (Exception e) {
            log.debug("Skip request count statistics, Redis unavailable: {}", e.getMessage());
        }
        return true;
    }


}
