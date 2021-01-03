package com.ricbap.brewer.service.event.venda;

import com.ricbap.brewer.model.Venda;

public class VendaEvent {
	
	private Venda venda;

	public VendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}	

}