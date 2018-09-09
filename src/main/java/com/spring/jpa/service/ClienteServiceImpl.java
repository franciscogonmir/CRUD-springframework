package com.spring.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.dao.IClienteDao;
import com.spring.jpa.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private  IClienteDao clienteDao;
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findOne(id);
	}
	@Transactional
	public void remove(Long id) {
		clienteDao.remove(id);
	}

}
