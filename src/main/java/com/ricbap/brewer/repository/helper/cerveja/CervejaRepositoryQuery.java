package com.ricbap.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.filter.CervejaFilter;
import com.ricbap.brewer.repository.filter.CervejaSkuOuNomeFilter;
import com.ricbap.brewer.repository.projection.ResumoCerveja;

public interface CervejaRepositoryQuery {
	
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<ResumoCerveja> porSkuOuNome(CervejaSkuOuNomeFilter cervejaSkuOuNomeFilter); 

}
