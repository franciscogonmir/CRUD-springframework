package com.spring.jpa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.models.entity.Producto.Producto;
import com.spring.jpa.models.entity.factura.Factura;

public interface ClienteService {

	public Iterable<Cliente> findAll();
	Page<Cliente> findAll(Pageable pageable);
	public void save(Cliente cliente);
	public Cliente findOne(Long id);
	public void remove(Long id);
	public List<Producto> findByName(String term);
	public void saveFactura(Factura f);
	public Producto findById(Long id);
	public Factura findFacturaById(Long id);
	public void removeFactura(Long id);
	public Factura fetchByIdWithClienteWhithLineaFacturaWithProducto(Long id);
	public Cliente fetchById(Long id);
}
