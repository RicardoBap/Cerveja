package com.ricbap.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricbap.brewer.controller.page.PageWrapper;
import com.ricbap.brewer.model.Usuario;
import com.ricbap.brewer.repository.GrupoRepository;
import com.ricbap.brewer.repository.UsuarioRepository;
import com.ricbap.brewer.repository.filter.UsuarioFilter;
import com.ricbap.brewer.service.CadastroUsuarioService;
import com.ricbap.brewer.service.StatusUsuario;
import com.ricbap.brewer.service.exception.EmailUsuarioJaCadastradoException;
import com.ricbap.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.ricbap.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos", grupoRepository.findAll());
		return mv;
	}
	
	@PostMapping({"/novo", "{\\d+}"})	
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
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return novo(usuario);
		}
		
		redirectAttributes.addFlashAttribute("mensagem",  "Usuario cadastrado com sucesso!");		
		return new ModelAndView("redirect:/usuarios/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
		mv.addObject("grupos", grupoRepository.findAll());
		//mv.addObject("usuarios", usuarioRepository.filtrar(usuarioFilter));
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarioRepository.filtrar(usuarioFilter, pageable)
				, httpServletRequest);		
		mv.addObject("pagina", paginaWrapper);		
		return mv;
	}
	
	@PutMapping("/status")
	@ResponseStatus(HttpStatus.OK)
	public void atualizarStatus(@RequestParam("codigos[]") Long[] codigos, @RequestParam("status") StatusUsuario statusUsuario) {
		//Arrays.asList(codigos).forEach(System.out::println);
		//System.out.println(">>>> Status " + status);
		cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarioRepository.buscarComGrupos(codigo);
		ModelAndView mv = novo(usuario);
		mv.addObject(usuario);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> excluir(@PathVariable("codigo") Usuario usuario) {
		try {
			cadastroUsuarioService.excluir(usuario);
		} catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	

}
