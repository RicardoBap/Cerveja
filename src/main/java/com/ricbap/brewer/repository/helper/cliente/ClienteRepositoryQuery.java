package com.ricbap.brewer.repository.helper.cliente;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {
	
	public List<Cliente> filtrar(ClienteFilter filter, Pageable pageable);

}
