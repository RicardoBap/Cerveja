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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ricbap.brewer.controller.page.PageWrapper;
import com.ricbap.brewer.model.Cerveja;
import com.ricbap.brewer.model.Origem;
import com.ricbap.brewer.model.Sabor;
import com.ricbap.brewer.repository.CervejaRepository;
import com.ricbap.brewer.repository.EstiloRepository;
import com.ricbap.brewer.repository.filter.CervejaFilter;
import com.ricbap.brewer.repository.filter.CervejaSkuOuNomeFilter;
import com.ricbap.brewer.repository.projection.ResumoCerveja;
import com.ricbap.brewer.service.CadastroCervejaService;
import com.ricbap.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@Autowired
	private CervejaRepository cervejaRepository;
	
	@RequestMapping("/nova")
	public ModelAndView nova(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values()); // <-----	 enum
		mv.addObject("estilos", estiloRepository.findAll()); // <------ classe
		mv.addObject("origens", Origem.values()); // <---- enum		
		return mv;
	}
	
	@RequestMapping(value = { "/nova", "{\\d+}" }, method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Cerveja cerveja, BindingResult result, RedirectAttributes redirectAttributes) {		
		if (result.hasErrors()) {
			//throw new RuntimeException(); Simulando erro para pagina 500 erro no servidor
			return nova(cerveja);
		}		 
		
		// Salvar no banco de dados
		cadastroCervejaService.salvar(cerveja);
		redirectAttributes.addFlashAttribute("mensagem", "Cerveja salva com sucesso");		
		return new ModelAndView("redirect:/cervejas/nova");
	}
	
	@GetMapping
	public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result,
			@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");		
		mv.addObject("estilos", estiloRepository.findAll()); 
		mv.addObject("sabores", Sabor.values()); 
		mv.addObject("origens", Origem.values());	
		mv.addObject("cervejas", cervejaRepository.filtrar(cervejaFilter, pageable));
		//mv.addObject("cervejas", cervejaRepository.findAll(pageable));		
		
		/*
		 * System.out.println(">>> Page number " + pageable.getPageNumber());
		 * System.out.println(">>> Page size " + pageable.getPageSize());
		 * System.out.println(">>> Offset " + pageable.getOffset());
		 * System.out.println(">>> Next " + pageable.next());
		 * System.out.println(">>> Total de paginas " + pagina.getTotalPages());
		 * System.out.println(">>> Page number " + pageable.getPageNumber());
		 * System.out.println(">>> primeira pagina " + pagina.isFirst());
		 * System.out.println(">>> ultima pagina " + pagina.isLast());
		 */	
		
		PageWrapper<Cerveja> paginaWrapper = new PageWrapper<>(cervejaRepository.filtrar(cervejaFilter, pageable)
				, httpServletRequest);		
		mv.addObject("pagina", paginaWrapper);
		
		return mv;
	}
	
	
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ResumoCerveja> pesquisar(CervejaSkuOuNomeFilter cervejaSkuOuNomeFilter) {
		return cervejaRepository.porSkuOuNome(cervejaSkuOuNomeFilter); 

	} 
	
	/*
	 * @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	 * public @ResponseBody List<CervejaDTO> pesquisar(String skuOuNome) { return
	 * cervejaRepository.porSkuOuNome(skuOuNome); }
	 */
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cerveja cerveja) {
		try {
			cadastroCervejaService.excluir(cerveja);
		} catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build(); // 200
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Cerveja cerveja) {
		ModelAndView mv = nova(cerveja);
		mv.addObject(cerveja);
		return mv;
	}
	
	/*
	 * @RequestMapping("/clientes/novo") public String cadastro() { return
	 * "cliente/CadastroCliente"; }
	 */
	 
	
	/*
	 * @RequestMapping("/usuarios/novo") public String cadastro() { return
	 * "usuario/CadastroUsuario"; }
	 */
	 
	/*
	 * @RequestMapping("/cidade/nova") public String cadastro() { return
	 * "cidade/CadastroCidade"; }
	 */
	
	/*
	 * @RequestMapping("/estilo/novo") public String cadastro() { return
	 * "estilo/CadastroEstilo"; }
	 */

}
