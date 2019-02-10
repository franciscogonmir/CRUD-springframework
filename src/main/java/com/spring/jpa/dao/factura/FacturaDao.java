package com.spring.jpa.dao.factura;

import org.springframework.data.repository.CrudRepository;

import com.spring.jpa.models.entity.factura.Factura;

public interface FacturaDao extends CrudRepository<Factura, Long>{

}
