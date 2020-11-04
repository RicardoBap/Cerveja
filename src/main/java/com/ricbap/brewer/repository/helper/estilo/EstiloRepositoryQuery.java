package com.ricbap.brewer.repository.helper.estilo;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Estilo;
import com.ricbap.brewer.repository.filter.EstiloFilter;

public interface EstiloRepositoryQuery {
	
	public List<Estilo> filtrar(EstiloFilter filter, Pageable pageable);

}
