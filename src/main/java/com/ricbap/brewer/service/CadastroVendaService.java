package com.ricbap.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricbap.brewer.model.StatusVenda;
import com.ricbap.brewer.model.Venda;
import com.ricbap.brewer.repository.VendaRepository;


@Service
public class CadastroVendaService {
	
	@Autowired
	private VendaRepository vendaRepository;

	@Transactional
	public void salvar(Venda venda) {
		if(venda.isSalvarProibido()) {
			throw new RuntimeException("Usuário tentando salvar uma venda proibida");
		}
		
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		} else {
			Venda vendaExistente = vendaRepository.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		}
		
		if(venda.getDataEntrega() != null) {
			venda.setDataHoraEntrega(LocalDateTime.of(venda.getDataEntrega(),
					venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
		}
				
		vendaRepository.save(venda);
	}

	
	@Transactional
	public void emitir(Venda venda) {
		venda.setStatus(StatusVenda.EMITIDA);
		salvar(venda);
	}


	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendaRepository.findOne(venda.getCodigo());
		
		vendaExistente.setStatus(StatusVenda.CANCELADA);
		vendaRepository.save(vendaExistente);
	}

	
}


