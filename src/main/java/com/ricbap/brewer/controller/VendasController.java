package com.ricbap.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.repository.CervejaRepository;
import com.ricbap.brewer.session.TabelaItensVenda;

@Controller
@RequestMapping("/vendas")
public class VendasController {
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private TabelaItensVenda tabelaItensVenda;
	
	@RequestMapping("/nova")
	public String nova() {		
		return "venda/CadastroVenda";
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja) {
		Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItensVenda.adicionarItem(cerveja, 1);
		return mvTabelaItensVenda();
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidadeItem(@PathVariable("codigoCerveja") Cerveja cerveja, Integer novaQuantidade) {
		//Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItensVenda.alterarQuantidadeDeItens(cerveja, novaQuantidade);
		return mvTabelaItensVenda();
	}
	
	@DeleteMapping("/item/{codigoCerveja}") 
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja) {
		//Cerveja cerveja = cervejaRepository.findOne(codigoCerveja);
		tabelaItensVenda.excluirItem(cerveja);
		return mvTabelaItensVenda();
	}

	private ModelAndView mvTabelaItensVenda() {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensVenda.getItens());		
		return mv;
	}
	

}
