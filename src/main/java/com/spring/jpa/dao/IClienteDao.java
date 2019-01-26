package com.spring.jpa.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.spring.jpa.models.entity.*;
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
