package com.ricbap.brewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.helper.cerveja.CervejaRepositoryQuery;

@Repository
public interface CervejaRepository extends JpaRepository<Cerveja, Long>, CervejaRepositoryQuery {

		
}
