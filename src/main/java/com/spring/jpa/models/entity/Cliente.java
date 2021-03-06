package com.spring.jpa.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.jpa.models.entity.factura.Factura;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="nombre")
	@NotEmpty
	private String nombre;
	@NotEmpty
	@Column(name="apellido")
	private String apellido;
	@Email
	@NotEmpty
	@Column(name="email")
	private String email;
	private String foto;
	@NotNull
	@Column(name="creation_at")
	@DateTimeFormat(pattern="dd-MM-YYYY")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="dd-MM-YYYY HH:mm:ss")
	private Date creationAt;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	List<Factura> facturas;
	
	/*@PrePersist
	public void prePersist() {
		this.creationAt = new Date();
	}*/
	public Cliente() {
		facturas = new ArrayList<Factura>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreationAt() {
		return creationAt;
	}
	public void setCreationAt(Date creationAt) {
		this.creationAt = creationAt;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	//@JsonIgnore ignora la relacion al componer el json
	@JsonManagedReference
	public List<Factura> getFacturas() {
		return facturas;
	}
	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	public void anadirFacturas(Factura f) {
		this.facturas.add(f);
	}
}
