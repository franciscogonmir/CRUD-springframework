package com.spring.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.models.entity.Cliente;

@Repository("ClienteDaoJpa")
public class ClienteDaoImpl implements IClienteDao {
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("FROM Cliente").getResultList();
	}
	
	@Override
	public void save(Cliente cliente) {
		if(cliente.getId() != null && cliente.getId() > 0) {
			update(cliente);
		}else {
			em.persist(cliente);
		}
	}

	@Override
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Cliente.class, id);
	}

	@Override
	public void update(Cliente cliente) {
		em.merge(cliente);
		
	}

	@Override
	@Transactional
	public void remove(Long id) {
		em.remove(findOne(id));		
	}

}
