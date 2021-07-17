package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.service.IAvaliacaoWorkupDoadorService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade de avaliação de Doador.
 * 
 * @author Fillipe Queiroz.
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoesdoador", produces = "application/json;charset=UTF-8")
public class AvaliacaoWorkupDoadorController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IAvaliacaoWorkupDoadorService avaliacaoWorkupDoadorService;

	/**
	 * Finaliza a avaliação do doador.
	 * 
	 * @param idAvaliacao - identificador da avaliação
	 * @return CampoMensagem com mensagem de sucesso
	 */
	@PreAuthorize("hasPermission('AVALIAR_WORKUP_DOADOR')")
	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> finalizarAvaliacao(@PathVariable(name = "id", required = true) Long idAvaliacao) {
		avaliacaoWorkupDoadorService.finalizarAvaliacao(idAvaliacao);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource, "avaliacao.finalizada")),
				HttpStatus.OK);
	}

}
