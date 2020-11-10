package com.ricbap.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Cidade;
import com.ricbap.brewer.repository.filter.CidadeFilter;

public interface CidadeRepositoryQuery {
	
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable);

}
