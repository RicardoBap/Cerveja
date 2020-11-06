package com.ricbap.brewer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ricbap.brewer.model.Cidade;
import com.ricbap.brewer.repository.CidadeRepository;

@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@RequestMapping("/nova")
	public String nova() {
		return "cidade/CadastroCidade";
	}
	
	// get feito no template ClientesCadastro via AJAX no arquivo cliente.combo-estado-cidade
	//  /brewer/cidades?estado=2
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		return cidadeRepository.findByEstadoCodigo(codigoEstado);
	}
	
	/*
	 * @RequestMapping public String pesquisar() { return "cidade/CadastroCidade"; }
	 */

}
