package com.spring.jpa.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.controller.view.xmlView.ClientesList;
import com.spring.jpa.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {
	
	@Autowired
	private ClienteService clienteSvc;
	
	@GetMapping("/listar")//para poder devolver xml tanto json devemos de retornar un objeto del tipo clientelist que es nuestra clase wrapper
	public  ClientesList listar() {
		return new ClientesList(clienteSvc.findAll());
		}

}
