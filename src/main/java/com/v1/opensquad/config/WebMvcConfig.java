/*
package com.v1.opensquad.config;

import com.fasterxml.jackson.core.filter.TokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final MyTokenFilter tokenFilter;

    public WebMvcConfig(MyTokenFilter tokenFilter) {
        this.tokenFilter = tokenFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenFilter)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/**", "/convite/**", "/contador/**", "/squad/**", "/participante/**", "/vaga/**", "/categoriasquad/**", "/historia");

    }
}*/
