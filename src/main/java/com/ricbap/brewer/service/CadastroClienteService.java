package com.ricbap.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void salvar(Cliente cliente) {
		clienteRepository.save(cliente);
	}

}
