package com.ricbap.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ricbap.brewer.repository.CervejaRepository;
import com.ricbap.brewer.repository.ClienteRepository;
import com.ricbap.brewer.repository.VendaRepository;

@Controller
public class DashboardController {
	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping("/")
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		mv.addObject("vendasNoAno", vendaRepository.valorTotalNoAno());
		mv.addObject("vendasNoMes", vendaRepository.valorTotalNoMes());
		mv.addObject("ticketMedio", vendaRepository.valorTicketMedioNoAno());
		
		mv.addObject("valorItensEstoque", cervejaRepository.valorItensEstoque());
		mv.addObject("totalClientes", clienteRepository.count());
		
		return mv;
	}

}
