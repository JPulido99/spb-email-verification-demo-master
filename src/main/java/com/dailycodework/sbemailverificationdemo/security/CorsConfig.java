package com.dailycodework.sbemailverificationdemo.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("https://victorious-rock-009afba10.3.azurestaticapps.net") // Reemplaza "http://localhost:3000" con el origen de tu aplicación frontend
      .allowedMethods("*")
      .allowedHeaders("*");
  }
}

