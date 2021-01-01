package com.ricbap.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricbap.brewer.controller.page.PageWrapper;
import com.ricbap.brewer.model.Cliente;
import com.ricbap.brewer.model.TipoPessoa;
import com.ricbap.brewer.repository.ClienteRepository;
import com.ricbap.brewer.repository.EstadoRepository;
import com.ricbap.brewer.repository.filter.ClienteFilter;
import com.ricbap.brewer.service.CadastroClienteService;
import com.ricbap.brewer.service.exception.CpfCnpjClienteCadastradoException;
import com.ricbap.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/clientes")
public class ClientesController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@RequestMapping("/novo") //<-----
	public ModelAndView novo(Cliente cliente) {
		ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
		mv.addObject("tiposPessoa", TipoPessoa.values());
		mv.addObject("estados", estadoRepository.findAll());
		return mv;
	}
	
	@PostMapping({"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return novo(cliente);
		}
		
		try {
			cadastroClienteService.salvar(cliente);
		} catch(CpfCnpjClienteCadastradoException e) {
			result.rejectValue("cpfCnpj", e.getMessage(), e.getMessage());
			return novo(cliente);
		}
		redirectAttributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso");
		return new ModelAndView("redirect:/clientes/novo");		
	}
	
	@GetMapping
	public ModelAndView pesquisar(ClienteFilter clienteFilter,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
		
		//System.out.println(">>>>>pageNumber " + pageable.getPageNumber());
		//System.out.println(">>>>>pageSize " + pageable.getPageSize());
		
		//mv.addObject("clientes", clienteRepository.findAll());
		PageWrapper<Cliente> paginaWrapper = 
				new PageWrapper<>(clienteRepository.filtrar(clienteFilter, pageable), httpServletRequest);
		
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody List<Cliente> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return clienteRepository.findByNomeStartingWithIgnoreCase(nome);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cliente cliente) {
		ModelAndView mv = novo(cliente);
		mv.addObject(cliente);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cliente cliente) {
		try {
			cadastroClienteService.excluir(cliente);
		} catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build(); // 200
	}
	
	

	private void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 3) {
			throw new IllegalArgumentException();
		}
	}
		
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
	
}