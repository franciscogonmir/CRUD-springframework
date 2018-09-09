package com.spring.jpa.service;

import java.util.List;

import com.spring.jpa.models.entity.Cliente;

public interface ClienteService {

	public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void remove(Long id);
}
