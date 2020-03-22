package com.spring.jpa.models.entity.lineaFactura;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.jpa.models.entity.Producto.Producto;

@Entity
@Table(name="linea_factura")
public class LineaFactura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="cantidad")
	private Integer cantidad;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="producto_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Producto producto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double calcularTotal() {
		return cantidad.doubleValue()  * producto.getPrecio();
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
}
