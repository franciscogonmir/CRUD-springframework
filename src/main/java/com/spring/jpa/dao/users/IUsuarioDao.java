package com.spring.jpa.dao.users;

import org.springframework.data.repository.CrudRepository;
import com.spring.jpa.models.entity.usuarios.Usuarios;


public interface IUsuarioDao extends CrudRepository<Usuarios, Integer> {

	public Usuarios findByUsername(String username);
}
