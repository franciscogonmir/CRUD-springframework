package com.spring.jpa.dao;
import java.util.List;

import com.spring.jpa.models.entity.*;
public interface IClienteDao {
/**
 * devuelve una lista de todos los clientes
 * @return lista de clientes
 */
	public List<Cliente> findAll();
	/**
	 * inserat un cliente
	 * @param cliente cliente a insertar
	 */
	public void save(Cliente cliente);
	/**
	 * obtiene un cliente
	 * @param id el identificador del cliente
	 * @return un cliente
	 */
	public Cliente findOne(Long id);
	/**
	 * actualiza un cliente
	 * @param cliente cliente a actualizar
	 */
	public void update(Cliente cliente);
	/**
	 * borra un cliente
	 * @param id del cliente ha borrar
	 */
	public void remove(Long id);
}
