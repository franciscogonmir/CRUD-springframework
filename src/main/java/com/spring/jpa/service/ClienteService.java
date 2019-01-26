package com.spring.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.jpa.models.entity.Cliente;

public interface ClienteService {

	public Iterable<Cliente> findAll();
	Page<Cliente> findAll(Pageable pageable);
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void remove(Long id);
}
