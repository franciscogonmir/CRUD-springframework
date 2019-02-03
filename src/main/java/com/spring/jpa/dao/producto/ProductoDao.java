package com.spring.jpa.dao.producto;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.spring.jpa.models.entity.Producto.Producto;

public interface ProductoDao extends CrudRepository<Producto, Long> {

	@Query("SELECT P FROM Producto P WHERE NOMBRE LIKE %?1%")
	public List<Producto> findByName(String term);
	public List<Producto> findByNombreLikeIgnoreCase(String term);
}
