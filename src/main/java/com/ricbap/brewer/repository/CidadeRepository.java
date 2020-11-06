package com.ricbap.brewer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ricbap.brewer.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	public List<Cidade> findByEstadoCodigo(Long codigoEstado);
	
}
