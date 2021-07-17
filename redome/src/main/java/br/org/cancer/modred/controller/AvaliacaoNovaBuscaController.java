package br.org.cancer.modred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.impl.AvaliacaoNovaBuscaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;


/**
 * Contralador para requisições envolvendo a avaliação de nova busca para o paciente.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoesnovabusca", produces = "application/json;charset=UTF-8")
public class AvaliacaoNovaBuscaController {

	@Autowired
	private AvaliacaoNovaBuscaService avaliacaoNovaBuscaService;
	
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Aprova uma avaliação de nova busca.
	 * 
	 * @param id da avaliação a ser aprovada.
	 * @return mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}/aprovar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")	
	public ResponseEntity<CampoMensagem> aprovarAvaliacao(@PathVariable(name="id", required = true) Long id) {
		avaliacaoNovaBuscaService.aprovarAvaliacaoNovaBusca(id);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"nova.busca.aprovada.sucesso")),
				HttpStatus.OK);
	}
	
	/**
	 * Reprova uma avaliação de nova busca.
	 * 
	 * @param id da avaliação a ser reprovada
	 * @param justificativa da reprovação.
	 * @return mensagem de sucesso.
	 */
	@RequestMapping(value = "{id}/reprovar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")	
	public ResponseEntity<CampoMensagem> reprovarAvaliacao(@PathVariable(name="id", required = true) Long id
			, @RequestParam(required = true) String justificativa) {
		avaliacaoNovaBuscaService.reprovarAvaliacaoNovaBusca(id, justificativa);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"nova.busca.reprovada.sucesso")),
				HttpStatus.OK);
	}
	
	/**
	 * Lista as tarefas pendentes de avaliação da nova busca
	 * do paciente solicitada pelo médico. 
	 * 
	 * @param pagina número da página exibida.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas.
	 */
	@RequestMapping(value = "tarefas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")	
	public JsonViewPage<TarefaDTO> listarTarefas(@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		JsonViewPage<TarefaDTO> tarefasAvaliacao = 
				avaliacaoNovaBuscaService.listarTarefas(new PageRequest(pagina, quantidadeRegistros));
		return tarefasAvaliacao;
	}

}
