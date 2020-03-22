package com.spring.jpa.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler {

	//sobrescribimos el metodo para poder enviarle lo que queramos a la vista donde va
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		//Como no tenemos aqui el redirectAttribute como en los controladores hay que hacerlo de la siguiente manera
				SessionFlashMapManager flashManagerMap = new SessionFlashMapManager();
				//creamos un map del tipo flash donde vamos a meter nuestros atributos para la vista
				FlashMap flashMap = new FlashMap();
				
				flashMap.put("success", "Bienvenido "+authentication.getName()+" que tengas una buena experiencia");
				
				flashManagerMap.saveOutputFlashMap(flashMap, request, response);//guardamos en el manage map nuestro flashmap con los datos que hemos creado
				
				if(authentication != null) {
					this.logger.info("El nombre de usuario es "+authentication.getName());
				}
				
				super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
