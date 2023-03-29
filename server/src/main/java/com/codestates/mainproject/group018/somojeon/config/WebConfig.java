package com.codestates.mainproject.group018.somojeon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("https://dev-somojeon.vercel.app", "http://localhost:3000")
                .allowedMethods("GET","DELETE", "POST", "PATCH", "OPTIONS").allowCredentials(true)
//                .exposedHeaders("Authorization", "Refresh", "location", "member-id", "email", "Request", "Access Token Expired", "Refresh Token Expired");
                .exposedHeaders("*");
    }
}