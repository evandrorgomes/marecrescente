package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.WorkupView;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para avaliação de resultado de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/avaliacoesresultadoworkup", produces = "application/json;charset=UTF-8")
public class AvaliacaoResultadoWorkupController {

	@Autowired
	private IAvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Efetua o pedido de coleta após a avaliação ser feita pelo médico.
	 * 
	 * @param idAvaliacao - identificação
	 * @param justificativa - caso inviável e medico queira prosseguir com coleta
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "{idAvaliacao}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('AVALIAR_RESULTADO_WORKUP')")
	public ResponseEntity<CampoMensagem> efetuarPedidoColeta(@PathVariable(required = true) Long idAvaliacao,
			@RequestBody(required = false) String justificativa) {
		AvaliacaoResultadoWorkup avaliacaoResultadoWorkup = avaliacaoResultadoWorkupService.efetuarPedidoColeta(idAvaliacao,
				justificativa);
		if (avaliacaoResultadoWorkup.getSolicitaColeta()) {

			return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
					"pedido.coleta.criado")),
					HttpStatus.OK);
		}
		else {
			return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
					"pedido.coleta.sera.avaliado")),
					HttpStatus.OK);
		}
	}

	/**
	 * Obtém um resultado por id.
	 * 
	 * @param idResultadoWorkup - identificador do resultado
	 * @return entidade resultado
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('AVALIAR_RESULTADO_WORKUP')")
	@JsonView(WorkupView.ResultadoParaAvaliacao.class)
	public ResponseEntity<AvaliacaoResultadoWorkup> obterResultadoParaAvaliacao(
			@PathVariable(name = "id", required = true) Long idAvaliacaoResultadoWorkup) {
		return new ResponseEntity<AvaliacaoResultadoWorkup>(avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorId(
				idAvaliacaoResultadoWorkup), HttpStatus.OK);
	}

	/**
	 * Efetua o pedido de coleta após a avaliação ser feita pelo médico.
	 * 
	 * @param idAvaliacao - identificação
	 * @param justificativa - caso inviável e medico queira prosseguir com coleta
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "{idAvaliacao}/descartar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('AVALIAR_RESULTADO_WORKUP')")
	public ResponseEntity<CampoMensagem> negarAvaliacaoResultadoWorkup(@PathVariable(required = true) Long idAvaliacao,
			@RequestBody(required = false) String justificativa) {
		avaliacaoResultadoWorkupService.negarAvaliacaoResultadoWorkup(idAvaliacao, justificativa);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"avaliacao.resultado.doador.descartado")),
				HttpStatus.OK);
	}

	/**
	 * Método para baixar o arquivo de laudo de acordo com o caminho passado.
	 * 
	 * @param arquivoId - id do arquivo resultado workup a ser baixado.
	 * @param response - resposta do servidor, arquivo anexado.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/arquivo")
	@PreAuthorize("(hasPermission('AVALIAR_RESULTADO_WORKUP') || hasPermission('AVALIAR_PEDIDO_COLETA') || hasPermission('AVALIAR_WORKUP_DOADOR'))")
	public void baixarArquivoDeLaudo(@PathVariable(name = "id") Long arquivoId, HttpServletResponse response) {
		File arquivoLaudo = avaliacaoResultadoWorkupService.obterArquivoLaudo(arquivoId);
		response.setContentType("application/pdf; application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + arquivoLaudo
				.getName() + "\""));
		try {
			FileCopyUtils.copy(new FileInputStream(arquivoLaudo), response.getOutputStream());
			response.flushBuffer();
		}
		catch (FileNotFoundException e) {
			new BusinessException("erro.avaliacao.arquivo.arquivo_nao_encontrado");
		}
		catch (IOException e) {
			new BusinessException("erro.avaliacao.arquivo.erro_ao_tentar_baixar_arquivo");
		}
	}
	
	/**
	 * Lista as tarefas AVALIAR_RESULTADO_WORKUP para um determinado centro de transplante.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante
	 * @param pagina - Página desejada 
	 * @param quantidadeRegistro - Quantidade de registros por página
	 * @return lista de tarefas.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "tarefas")
	@PreAuthorize("hasPermission(#idCentroTransplante, 'CentroTransplante', '" + Recurso.CONSULTAR_AVALIAR_RESULTADO_WORKUP + "')")
	public JsonViewPage<TarefaDTO> listarTarefas(@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistro) {
		
		return avaliacaoResultadoWorkupService.listarTarefasPorCentroTransplante(idCentroTransplante, 
				new PageRequest(pagina, quantidadeRegistro));
	}
	
	
	
	
	
	
	
}
