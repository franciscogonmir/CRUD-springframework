package com.spring.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return (List<Cliente>) clienteDao.findAll();
	}
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return  clienteDao.findById(id).orElse(null);
	}
	@Transactional
	public void remove(Long id) {
		clienteDao.deleteById(id); ;
	}
	@Override
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

}
