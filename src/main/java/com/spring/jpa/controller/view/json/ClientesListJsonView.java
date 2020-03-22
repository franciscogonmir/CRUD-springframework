package com.spring.jpa.controller.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.spring.jpa.models.entity.Cliente;

@Component("lista.json")
public class ClientesListJsonView extends MappingJackson2JsonView {

	@SuppressWarnings("unchecked")
	@Override
	protected Object filterModel(Map<String, Object> model) {
		model.remove("titulo");
		model.remove("page");
		Page<Cliente> clientes = (Page<Cliente>) model.get("Clientes");
		model.remove("Clientes");
		model.put("clientes", clientes.getContent());
		return super.filterModel(model);
	}
	
	

}
