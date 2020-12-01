package com.ricbap.brewer.repository.helper.venda;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Venda;
import com.ricbap.brewer.repository.filter.VendaFilter;

public interface VendaRepositoryQuery {
	
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);

}
