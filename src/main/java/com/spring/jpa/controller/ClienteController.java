package com.spring.jpa.controller;

import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.jpa.dao.IClienteDao;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.service.ClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteSvc;
	@RequestMapping(value="listar",method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo","Bienvenido a mi vista");
		model.addAttribute("Clientes",clienteSvc.findAll());
		return "lista";
	}
	
	@GetMapping(value="form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de spring");
		model.put("cliente", cliente);
		return "form";
	}
	
	@RequestMapping(value="form/{id}")
	public String editar(@PathVariable(value="id") Long id,Model model) {
		Cliente cliente = null;
		if(id != null && id > 0) {
			cliente = clienteSvc.findOne(id);
		}else {
			return  "redirect:listar";
		}
		model.addAttribute("cliente",cliente);
		return "form";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result,SessionStatus status) {
		if(result.hasErrors()) {
			return "form";
		}
		clienteSvc.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}

	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id) {
		clienteSvc.remove(id);
		return "redirect:/listar";
	}
}
