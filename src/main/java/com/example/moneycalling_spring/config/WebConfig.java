package com.example.moneycalling_spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite toate rutele
                .allowedOrigins("http://localhost:5176") // Frontend-ul tău
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Metodele permise
                .allowedHeaders("*") // Permite toate capetele (headers)
                .allowCredentials(true); // Permite trimite cookies/credite
    }
}
