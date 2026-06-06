package org.cug.geodt.weibo.sos.config;

import org.cug.geodt.weibo.sos.interceptor.RequestCountInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @FileName WebConfig
 * @Author WJW
 * @Date 2023/10/9 14:45
 * @Description 拦截器实现接口调用次数统计
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {


    @Autowired
    private Swagger2Configuration swagger2Configuration;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.
                addInterceptor(new RequestCountInterceptor(stringRedisTemplate)).
                addPathPatterns("/**");

    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        /** 配置knife4j 显示文档 */
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        /**
         * 配置swagger-ui显示文档
         */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        /** 公共部分内容 */
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
