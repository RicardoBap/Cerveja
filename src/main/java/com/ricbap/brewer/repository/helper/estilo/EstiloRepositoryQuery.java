package com.ricbap.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Estilo;
import com.ricbap.brewer.repository.filter.EstiloFilter;

public interface EstiloRepositoryQuery {
	
	public Page<Estilo> filtrar(EstiloFilter filter, Pageable pageable);

}
