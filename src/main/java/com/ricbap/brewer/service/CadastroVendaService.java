package com.ricbap.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
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
		if(venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		}
		
		/*
		 * BigDecimal valorTotalItens = venda.getItens().stream()
		 * .map(ItemVenda::getValorTotal) .reduce(BigDecimal::add) .get();
		 * 
		 * BigDecimal valorTotal = calcularValorTotal(valorTotalItens,
		 * venda.getValorFrete(), venda.getValorDesconto());
		 * venda.setValorTotal(valorTotal);
		 */
		
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

	/*
	 * private BigDecimal calcularValorTotal(BigDecimal valorTotalItens, BigDecimal
	 * valorFrete, BigDecimal valorDesconto) { BigDecimal valorTotal =
	 * valorTotalItens .add(Optional.ofNullable(valorFrete).orElse(BigDecimal.ZERO))
	 * .subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO)); return
	 * valorTotal; }
	 */
}


