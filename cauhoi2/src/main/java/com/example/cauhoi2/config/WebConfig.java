package com.example.cauhoi2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // Chỉ cho phép frontend từ localhost:3000
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Cho phép các phương thức HTTP
                .allowedHeaders("*")  // Cho phép tất cả các header
                .allowCredentials(true);  // Cho phép cookie hoặc thông tin xác thực
    }
}
