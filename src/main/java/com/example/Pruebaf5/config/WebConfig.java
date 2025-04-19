package com.example.Pruebaf5.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Manejador de recursos para las imágenes en el directorio uploads
        registry.addResourceHandler("/uploads/**") // Configura la URL para acceder a las imágenes
                .addResourceLocations("file:/C:/Users/DIEGO ALFREDO/PruebaF5/uploads/"); // La ruta absoluta en tu sistema
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuración de CORS
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Ajusta esto si tu frontend está en otro puerto
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
