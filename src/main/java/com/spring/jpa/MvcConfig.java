package com.spring.jpa;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

/*	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// TODO Auto-generated method stub
		String resourceLocations = Paths.get("uploads").toAbsolutePath().toUri().toString();
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourceLocations);
	}*/
}

