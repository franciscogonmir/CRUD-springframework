package com.spring.jpa.dao;
import java.util.List;

import com.spring.jpa.models.entity.*;
public interface IClienteDao {

	public List<Cliente> findAll();
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void update(Cliente cliente);
	public void remove(Long id);
}
