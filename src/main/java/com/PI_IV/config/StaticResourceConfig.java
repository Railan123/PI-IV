package com.PI_IV.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configura o Spring Boot para servir imagens a partir do diretório local
        registry.addResourceHandler("/imagens_produto/**")
                .addResourceLocations("file:/C:/Users/Administrador/PI-IV/imagens_produto/");
    }
}

