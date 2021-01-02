package com.ricbap.brewer.repository.helper.venda;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.dto.VendaMes;
import com.ricbap.brewer.model.Venda;
import com.ricbap.brewer.repository.filter.VendaFilter;

public interface VendaRepositoryQuery {
	
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);
	
	public Venda buscarComItens(Long codigo);
	
	
	// DASHBOARD
	public BigDecimal valorTotalNoAno();	
	public BigDecimal valorTotalNoMes();
	public BigDecimal valorTicketMedioNoAno();
	
	//GRAFICO VENDAS POR MES - DTO(VendasMes) - SQL(Nativo-Aeq	uivo XML)
	public List<VendaMes> totalPorMes();
	
	
}
