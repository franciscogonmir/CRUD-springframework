package com.spring.jpa.controller.view.xmlView;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.spring.jpa.models.entity.Cliente;

@Component("lista.xml")
public class ClienteViewXml extends MarshallingView{

	@Autowired//inyectamos el bean creado en la clase de configuracion
	public ClienteViewXml(Jaxb2Marshaller marshaller) {
		super(marshaller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//limpiamo el modelo para posteriormente introducir el envoltorio de XMl que sera lo que nos devuelva transformado en xml
		model.remove("titulo");
		model.remove("page");
		Page<Cliente> clientes = (Page<Cliente>) model.get("Clientes");
		model.remove("Clientes");
		model.put("clientes", new ClientesList(clientes.getContent()));
		
		super.renderMergedOutputModel(model, request, response);
	}
	
	

	
}
