package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.SolicitacaoDTO;
import br.org.cancer.modred.controller.view.AvaliacaoPrescricaoView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPrescricaoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade Prescrição.
 * 
 * @author Pizão.
 *
 */
@RestController
@RequestMapping(value = "/api/prescricoes", produces = "application/json;charset=UTF-8")
public class PrescricaoController {

	@Autowired
	private IPrescricaoService prescricaoService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Cria solicitação a partir do tipo passado por parâmtro. São Eles: FASE_2, FASE_3, WORKUP E DISPONIBILIZACAO.
	 * 
	 * @param solicitacaoDTO - dto com idDoador e /rmr e o tipo da solicitacao
	 * @param arquivoJustificativa - arquivo de justificativa para o tipo de solicitacao workup
	 * @param listaArquivos - lista de arquivos para o tipo de solicitacao workup
	 * @return CampoMensagem - mensagem de retorno
	 */
	@RequestMapping(value = "medula", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<CampoMensagem> solicitarPrescricaoMedula(
			@RequestPart(required = true) SolicitacaoDTO solicitacaoDTO,
			@RequestPart(name = "filejustificativa", required = false) MultipartFile arquivoJustificativa,
			@RequestPart(name = "file", required = false) List<MultipartFile> listaArquivos) {

		prescricaoService.solicitarPrescricaoMedula(solicitacaoDTO, arquivoJustificativa, listaArquivos);
		final CampoMensagem mensagemRetorno = new CampoMensagem(AppUtil.getMensagem(messageSource, "solicitacao.prescricao.criado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagemRetorno, HttpStatus.OK);
	}
	
	/**
	 * Solicita a prescrição para cordão, conforme informações contidas no DTO.
	 * 
	 * @param solicitacaoDTO - dto com idDoador e rmr e o tipo da solicitacao
	 * @param arquivoJustificativa - arquivo de justificativa para o tipo de solicitacao workup
	 * @param listaArquivos - lista de arquivos para o tipo de solicitacao workup
	 * @return CampoMensagem - mensagem de retorno
	 */
	@RequestMapping(value = "cordao", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	public ResponseEntity<CampoMensagem> solicitarPrescricaoCordao(
			@RequestPart(required = true) SolicitacaoDTO solicitacaoDTO,
			@RequestPart(name = "filejustificativa", required = false) MultipartFile arquivoJustificativa,
			@RequestPart(name = "file", required = false) List<MultipartFile> listaArquivos) {

		prescricaoService.solicitarPrescricaoCordao(solicitacaoDTO, arquivoJustificativa, listaArquivos);
		final CampoMensagem mensagemRetorno = new CampoMensagem(AppUtil.getMensagem(messageSource, "solicitacao.prescricao.criado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagemRetorno, HttpStatus.OK);
	}
	
	/**
	 * Lista as tarefas de autorização de paciente.
	 * 
	 * @param idCentroTransplante - Identificador do centro de transplante.
	 * @param atribuidoAMin - fag que informa se deve ser retornardo somente as tarefas do usuario logado
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 *
	 */
	@PreAuthorize("hasPermission(#idCentroTransplante, 'CentroTransplante', '" + Recurso.AUTORIZACAO_PACIENTE + "')")
	@RequestMapping(value = "tarefas/autorizacaopaciente", method = RequestMethod.GET)
	public JsonViewPage<TarefaDTO> listarTarefasDeAutorizacaoPaciente(
			@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) Boolean atribuidoAMin,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		
		return prescricaoService.listarTarefasAutorizacaoPaciente(idCentroTransplante, atribuidoAMin, new PageRequest(pagina, quantidadeRegistros));
	}
	
	/**
	 * Recebe o arquivo de autorização de paciente.
	 * 
	 * @param idPrescricao - identificador da prescricao
	 * @param arquivo - Arquivo de autorização do paciente 
	 * @return CampoMensagem - mensagem de retorno
	 */
	@RequestMapping(value = "{id}/autorizacaopaciente", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#idPrescricao, 'Prescricao', '" + Recurso.AUTORIZACAO_PACIENTE + "')")
	public ResponseEntity<CampoMensagem> uploadArquivoAutorizacaoPaciente(
			@PathVariable(name = "id", required = true) Long idPrescricao,			
			@RequestPart(name = "file", required = true) MultipartFile arquivo) {

		prescricaoService.salvarArquivoAutorizacaoPaciente(idPrescricao, arquivo);
		final CampoMensagem mensagemRetorno = new CampoMensagem(AppUtil.getMensagem(messageSource, "prescricao.arquivo.autorizacao.paciente.salvo"));
		return new ResponseEntity<CampoMensagem>(mensagemRetorno, HttpStatus.OK);
	}
	
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_PRESCRICAO + "')")
	@JsonView(AvaliacaoPrescricaoView.Detalhe.class)
	@GetMapping(value = "{id}")	
	public ResponseEntity<Prescricao> obterPrescricaoPorIdentificador(
		@PathVariable(name = "id", required = true) Long idPrescricao) {
			
		return ResponseEntity.ok(prescricaoService.obterPrescricoesPorIdentificador(idPrescricao));
	}
}
