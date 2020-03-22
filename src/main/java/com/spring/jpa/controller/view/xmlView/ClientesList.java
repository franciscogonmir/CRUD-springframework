package com.spring.jpa.controller.view.xmlView;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.spring.jpa.models.entity.Cliente;

@XmlRootElement(name="clientes")//indica el elemento raiz del fichero XML
public class ClientesList {
	@XmlElement(name="cliente")//indica que es un elemento XML
	private List<Cliente> clienteList;

	public ClientesList() {
	}

	public ClientesList(List<Cliente> clienteList) {
		super();
		this.clienteList = clienteList;
	}

	public List<Cliente> getClienteList() {
		return clienteList;
	}

	
	

}
