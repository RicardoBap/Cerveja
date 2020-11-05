package com.ricbap.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricbap.brewer.model.Cliente;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@GetMapping("/novo")
	public String novo(Cliente cliente) {
		return "cliente/CadastroCliente";
	}
	
	@PostMapping("/novo")
	public String cadastrar(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {			
			return novo(cliente);
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
		System.out.println(">>>> Nome: " + cliente.getNome());
		return "redirect:/clientes/novo";
	}

}
