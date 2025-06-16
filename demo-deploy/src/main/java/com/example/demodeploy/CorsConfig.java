package com.example.demodeploy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")    // Allow CORS for all endpoints
                        .allowedOrigins("https://3.77.73.3:3000")
                        .allowedOrigins("http://localhost:5173") // Explicitly set the frontend origin for security
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // Add all methods you use, including OPTIONS for preflight
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(false) // Set to true if you need cookies/auth headers, otherwise false. False is often safer for simple cases.
                        .maxAge(3600);
            }
        };
    }
}
