package com.imagine.another_arts.config;

import com.imagine.another_arts.web.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor())
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/find/**", "/api/reset/password", "/api/login" // LoginController
                        , "/api/session-check", "/api/user/duplicate-check" // UserController
                        , "/api/main/arts", "/api/hashtag/arts", "/api/keyword/arts" // ArtController
                );
    }
}
