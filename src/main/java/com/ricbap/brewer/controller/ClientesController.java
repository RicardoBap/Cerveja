package com.ricbap.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String novo() {
		return "cliente/CadastroCliente";
	}
	
	@PostMapping("/novo")
	public String cadastrar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("mensagem", "Erro no formulário");
			System.out.println(">>>> Tem erro sim!");
			return "cliente/CadastroCliente";
		}
		
		redirectAttributes.addFlashAttribute("mensagem", "Nome salvo com sucesso!");
		System.out.println(">>>> Nome: " + cliente.getNome());
		return "redirect:/clientes/novo";
	}

}
