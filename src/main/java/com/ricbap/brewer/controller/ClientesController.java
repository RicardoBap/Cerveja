package com.ricbap.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ricbap.brewer.model.TipoPessoa;
import com.ricbap.brewer.repository.EstadoRepository;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private EstadoRepository estadoRepository;

	@RequestMapping("novo") //<-----
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());
		return mv;
	}
	
}