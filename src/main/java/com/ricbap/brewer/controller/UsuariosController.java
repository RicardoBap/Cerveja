package com.ricbap.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.repository.GrupoRepository;
import com.ricbap.brewer.service.CadastroUsuarioService;
import com.ricbap.brewer.service.exception.EmailUsuarioJaCadastradoException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return novo(usuario);
		}
		
		//salvar no banco
		try {
			cadastroUsuarioService.cadastrar(usuario);	
		} catch(EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		redirectAttributes.addFlashAttribute("mensagem",  "Usuario cadastrado com sucesso!");		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	

}
