package com.ricbap.brewer.repository.helper.cerveja;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ricbap.brewer.dto.CervejaDTO;
import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.filter.CervejaFilter;

public interface CervejaRepositoryQuery {
	
	public Page<Cerveja> filtrar(CervejaFilter filter, Pageable pageable);
	
	public List<CervejaDTO> porSkuOuNome(String skuOuNome); 

}
