package com.ricbap.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.CervejaRepository;
import com.ricbap.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.ricbap.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private FotoStorage fotoStorage;
	
	@Transactional
	public void salvar(Cerveja cerveja) {
		cervejaRepository.save(cerveja);
	}
	
	@Transactional
	public void excluir(Cerveja cerveja) {
		try {
			String foto = cerveja.getFoto();
			cervejaRepository.delete(cerveja);
			cervejaRepository.flush();
			fotoStorage.excluir(foto);
		} catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível apagar cerveja. Já foi usada em alguma venda");
		}
	}

}
