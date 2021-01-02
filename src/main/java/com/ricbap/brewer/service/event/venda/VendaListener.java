package com.ricbap.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.model.ItemVenda;
import com.ricbap.brewer.repository.CervejaRepository;

@Component
public class VendaListener {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@EventListener
	public void VendaEmitida(VendaEvent vendaEvent) {
		for (ItemVenda item : vendaEvent.getVenda().getItens()) {
			Cerveja cerveja = cervejaRepository.findOne(item.getCerveja().getCodigo());
			cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
			cervejaRepository.save(cerveja);
		}
	}

}
