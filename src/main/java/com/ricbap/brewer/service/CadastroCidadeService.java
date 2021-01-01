package com.ricbap.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.Cidade;
import com.ricbap.brewer.repository.CidadeRepository;
import com.ricbap.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public void salvar(Cidade cidade) {
		Optional<Cidade> cidadeExistente = cidadeRepository.findByNomeAndEstado(cidade.getNome(), cidade.getEstado());
		if (cidadeExistente.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome de cidade já cadastrado");
		}
		
		cidadeRepository.save(cidade);
	}
	
}
