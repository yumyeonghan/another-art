package com.imagine.another_arts.config;

import com.imagine.another_arts.web.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(new RequestInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/user", "/api/login", "/api/find/id", "/api/find/password", "/api/user/duplicate-check",
                        "/api/main/arts", "/api/keyword/arts", "/api/hashtag/arts", "/api/session-check");
    }
}
