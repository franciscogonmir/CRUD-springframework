package com.spring.jpa.controller.login;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error",required=false)String error,@RequestParam(value="logout",required=false)String logout, Model model,Principal principal,RedirectAttributes flash) {
		//si principal es nulo es que el usuario tiene la sesión activa
		if(principal != null) {
			flash.addFlashAttribute("info", "El usuario ya ha iniciado sesion anteriormente");
			return "redirect:/";
		}
		if(error != null) {
			model.addAttribute("error", "El usuario o la password no son correctos");
		}
		if(logout != null) {
			model.addAttribute("success", "Sesión cerrada con éxito");
		}
		return "login";
	}
}
