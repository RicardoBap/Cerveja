package com.ricbap.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.repository.ClienteRepository;
import com.ricbap.brewer.service.exception.CpfCnpjClienteCadastradoException;
import com.ricbap.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	/*
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao()); // getCodigo()
		if (clienteExistente.isPresent()) {
			throw new CpfCnpjClienteCadastradoException("CPF/CNPJ já cadastrado");
		}
		clienteRepository.save(cliente);
	} */
	
	@Transactional
	public void salvar(Cliente cliente) {
		if (cliente.isNovo()) {
			this.clienteRepository.findByCpfCnpj(cliente.getCpfCnpjSemFormatacao())
						 .ifPresent(c -> {
							 throw new CpfCnpjClienteCadastradoException("CPF/CNPJ já cadastrado"); 
						 });
		}
		this.clienteRepository.save(cliente);
	}
	
	
	@Transactional
	public void excluir(Cliente cliente) {
		try {
			clienteRepository.delete(cliente);
			clienteRepository.flush();
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cliente. Já foi usada em alguma venda");
		}
	}

}
