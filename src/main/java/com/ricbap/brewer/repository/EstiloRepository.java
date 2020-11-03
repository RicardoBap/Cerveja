package com.ricbap.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricbap.brewer.model.Estilo;
import com.ricbap.brewer.repository.helper.estilo.EstiloRepositoryQuery;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>, EstiloRepositoryQuery {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

}
