package com.spring.jpa;

import java.nio.file.Paths;
import java.util.Locale;

import org.aspectj.weaver.ClassAnnotationValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

/*	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// TODO Auto-generated method stub
		String resourceLocations = Paths.get("uploads").toAbsolutePath().toUri().toString();
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourceLocations);
	}*/
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//creando el bean localresolver para la internalizacion de nuestra aplicacion
	//indica donde se va a guardar el parametro con el idioma de la aplicacion
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(new Locale("es", "ES"));//iniciamos el idioma por defecto
		return localResolver;
	} 
	
	//Creamos el interceptor para cambiar el idioma que se pase con el parametro que seleccionemos
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");//indicamos el nombre del parametro que va a llevar el idioma;
		return localeInterceptor;
	}
	
	//anadimos interceptores
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	//Añadimos un controlador parametrizable,lo que hacemos añadir la Uri de nuestro controlador y el nombre de la vista a la que vamos a ir en caso de un error 403
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	//creamos el objeto que se encargara de serializar clases a Xml
	@Bean
	Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {com.spring.jpa.controller.view.xmlView.ClientesList.class});
		return marshaller;
	}
}

