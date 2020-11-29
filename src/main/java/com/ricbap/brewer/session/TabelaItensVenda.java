package com.ricbap.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.model.ItemVenda;

@SessionScope
@Component
public class TabelaItensVenda {
	
	private List<ItemVenda> itens = new ArrayList<>();
	
	public BigDecimal getValorTotal() {
		return itens.stream()
				.map(ItemVenda::getValorTotal)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}
	
	public void adicionarItem(Cerveja cerveja, Integer quantidade) {
		Optional<ItemVenda> itemVendaOptional = buscarItemPorCerveja(cerveja);
		
		ItemVenda itemVenda = null;
		if(itemVendaOptional.isPresent()) {
			itemVenda = itemVendaOptional.get();
			itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
		} else {
			itemVenda = new ItemVenda();
			itemVenda.setCerveja(cerveja);
			itemVenda.setQuantidade(quantidade);
			itemVenda.setValorUnitario(cerveja.getValor());			
			itens.add(0, itemVenda);			
		}		
	}
	
	public void alterarQuantidadeDeItens(Cerveja cerveja, Integer novaQuantidade) {
		ItemVenda itemVenda = buscarItemPorCerveja(cerveja).get();
		itemVenda.setQuantidade(novaQuantidade);
	}
	
	public void excluirItem(Cerveja cerveja) {
		int indice = IntStream.range(0, itens.size())
				.filter(i -> itens.get(i).getCerveja().equals(cerveja))
				.findAny().getAsInt();
		itens.remove(indice);
	}

	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		return itens.stream()
			.filter(i -> i.getCerveja().equals(cerveja))
			.findAny();
	}
	/*
	private Optional<ItemVenda> buscarItemPorCerveja(Cerveja cerveja) {
		Optional<ItemVenda> itemVendaOptional = itens.stream()
			.filter(i -> i.getCerveja().equals(cerveja))
			.findAny();
		return itemVendaOptional;
	}
	*/
	
	public int total() {
		return itens.size();
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

}
