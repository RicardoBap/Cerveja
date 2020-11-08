package com.ricbap.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.ClienteRepository;
import com.ricbap.brewer.service.exception.CpfCnpjClienteCadastradoException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao()); // getCodigo()
		if (clienteExistente.isPresent()) {
			throw new CpfCnpjClienteCadastradoException("CPF/CNPJ já cadastrado");
		}
		clienteRepository.save(cliente);
	}

}
