package com.ricbap.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricbap.brewer.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
