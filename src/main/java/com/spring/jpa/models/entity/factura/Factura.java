package com.spring.jpa.models.entity.factura;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.models.entity.lineaFactura.LineaFactura;

@Entity
@Table(name="Facturas")
public class Factura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="observacion")
	private String observacion;
	@Column(name="descripcion")
	private String descripcion;
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name="factura_id")
	private List<LineaFactura> lineaFactura;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
	public Factura() {
		this.lineaFactura = new ArrayList<LineaFactura>();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<LineaFactura> getLineaFactura() {
		return lineaFactura;
	}

	public void setLineaFactura(List<LineaFactura> lineaFactura) {
		this.lineaFactura = lineaFactura;
	}
	
	public void addLineaFactura(LineaFactura item){
		this.lineaFactura.add(item);
	}
	
	public Double getTotal() {
		Double total = 0.0;
		int size = this.lineaFactura.size();
		for (int i = 0; i < size; i++) {
			total += this.lineaFactura.get(i).calcularTotal();
		}
		return total;
	}
}
