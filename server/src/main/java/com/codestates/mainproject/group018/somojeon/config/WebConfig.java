package com.codestates.mainproject.group018.somojeon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://seb42-main-018.vercel.app")
                .allowedOrigins("http://seb42-main-018.vercel.app")
                .allowedMethods("GET","DELETE", "POST", "PATCH", "OPTIONS").allowCredentials(true)
                .exposedHeaders("authorization");
    }
}