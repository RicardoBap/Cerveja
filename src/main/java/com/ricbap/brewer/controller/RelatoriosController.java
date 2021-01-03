package com.ricbap.brewer.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ricbap.brewer.dto.PeriodoRelatorio;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@GetMapping("/vendasEmitidas")
	public ModelAndView relatoriosVendasEmitidas() {
		ModelAndView mv = new ModelAndView("relatorio/RelatorioVendasEmitidas");
		mv.addObject(new PeriodoRelatorio());
		
		return mv;
	}
	
	@PostMapping("/vendasEmitidas")
	public ModelAndView gerarRelatoriosVendasEmitidas(PeriodoRelatorio periodoRelatorio) {
		Map<String, Object> parametros = new HashMap<>();
		
		Date dataInicio = Date.from(LocalDateTime.of(periodoRelatorio.getDataInicio(), LocalTime.of(0, 0, 0))
				.atZone(ZoneId.systemDefault()).toInstant());
		Date dataFim = Date.from(LocalDateTime.of(periodoRelatorio.getDataFim(), LocalTime.of(23, 59, 59))
				.atZone(ZoneId.systemDefault()).toInstant());
		
		parametros.put("format", "pdf");
		parametros.put("DATA_INICIO", dataInicio);
		parametros.put("DATA_FIM", dataFim);
		
		return new ModelAndView("relatorio_vendas_emitidas", parametros);
		
	}

}
