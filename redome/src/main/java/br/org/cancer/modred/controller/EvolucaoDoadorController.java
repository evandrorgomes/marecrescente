package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IEvolucaoDoadorService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.EvolucaoDoadorVo;

/**
 * Classe responsável por controlar as requisições do front-end envolvendo a entidade Evolução e chamadas relacionadas a ela.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(value = "/api/evolucoesdoador", produces = "application/json;charset=UTF-8")
public class EvolucaoDoadorController {

	@Autowired
	private IEvolucaoDoadorService evolucaoDoadorService;

	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Método utilizado para salvar uma nova evolução do doador.
	 * 
	 * @Return Mensagem de sucesso
	 */
	@PostMapping()
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	public ResponseEntity<CampoMensagem> criarEvolucaoDoador(@RequestBody EvolucaoDoadorVo evolucaoDoadorVo) {
		evolucaoDoadorService.criarEvolucaoDoador(evolucaoDoadorVo);
		return ResponseEntity.ok(new CampoMensagem(AppUtil.getMensagem(messageSource, "evolucao.doador.incluido.sucesso")));
	}


}
