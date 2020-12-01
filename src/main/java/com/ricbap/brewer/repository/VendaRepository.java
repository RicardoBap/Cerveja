package com.ricbap.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricbap.brewer.model.Venda;
import com.ricbap.brewer.repository.helper.venda.VendaRepositoryQuery;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long>, VendaRepositoryQuery {

}
