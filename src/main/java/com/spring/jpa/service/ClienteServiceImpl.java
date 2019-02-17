package com.spring.jpa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.jpa.dao.IClienteDao;
import com.spring.jpa.dao.factura.FacturaDao;
import com.spring.jpa.dao.producto.ProductoDao;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.models.entity.Producto.Producto;
import com.spring.jpa.models.entity.factura.Factura;

@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private  IClienteDao clienteDao;
	@Autowired
	private ProductoDao productoDao;
	@Autowired
	private FacturaDao facturaDao;
	
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
	@Transactional(readOnly = true)
	public List<Producto> findByName(String term){
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}
	@Override
	@Transactional
	public void saveFactura(Factura f) {
		facturaDao.save(f);
	}
	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}
	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return this.facturaDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public void removeFactura(Long id) {
		this.facturaDao.deleteById(id);		
	}


}
