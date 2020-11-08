package com.ricbap.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.helper.cliente.ClienteRepositoryQuery;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery {

	public Optional<Cliente> findByCpfCnpj(String codigo);

}
