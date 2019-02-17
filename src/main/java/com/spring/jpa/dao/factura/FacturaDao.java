package com.spring.jpa.dao.factura;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.spring.jpa.models.entity.factura.Factura;

public interface FacturaDao extends CrudRepository<Factura, Long>{

	@Query("select f from Factura f join fetch f.cliente c join fetch f.lineaFactura l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClienteWhithLineaFacturaWithProducto(Long id);
}
