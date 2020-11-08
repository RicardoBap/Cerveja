package com.ricbap.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {
	
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable);

}
