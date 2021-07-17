package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.controller.dto.CriarLogEvolucaoDTO;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.ILogEvolucaoService;

/**
 * Constrolador para diagnostico.
 * 
 * @author brunosousa
 *
 */
@RestController
@RequestMapping(value = "/api/logsevolucao", produces = "application/json;charset=UTF-8")
public class LogEvolucaoController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;

	/**
	 * Método rest para criar log evolucao
	 *
	 * @param logEvolucao log evolução a ser criado.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso.
	 */
	@PostMapping()
	@PreAuthorize( "hasPermission('" + Recurso.EDITAR_DIAGNOSTICO_PACIENTE + "') "
			+ " || hasPermission('" + Recurso.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "') "
			+ " || hasPermission('" + Recurso.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL + "') ")
	public ResponseEntity<String> criarLogEvolucao(@RequestBody(required = true) CriarLogEvolucaoDTO criarLogEvolucaoDTO) {
		
		logEvolucaoService.criarLogEvolucao(criarLogEvolucaoDTO);
				
		return ResponseEntity.ok().build();
	}
	
}
