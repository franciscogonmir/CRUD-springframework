package com.spring.jpa.controller.locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

	@GetMapping("/locale")
	public String locale(HttpServletRequest request) {
		String urlAnterior = request.getHeader("referer");//aqui se obtiene la url anterior
		return "redirect:".concat(urlAnterior);
	}
}
