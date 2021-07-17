package br.org.cancer.modred.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.ResultadoPedidoInternacionalDTO;
import br.org.cancer.modred.controller.dto.SolicitacaoInternacionalDTO;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.SolicitacaoView;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Registra as chamadas REST envolvendo a entidade Solicitacao.
 * 
 * @author Pizão.
 *
 */
@RestController
@RequestMapping(value = "/api/solicitacoes", produces = "application/json;charset=UTF-8")
public class SolicitacaoController {

	@Autowired
	private ISolicitacaoService solicitacaoService;

	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;
	
	
	/**
	 * Método para cancelar solicitação de fase 3 de paciente.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @param justificativa - Justificativa do cancelamento
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro no cancelamento da solicitação.
	 */
	@RequestMapping(value = "{id}/cancelar/fase3/paciente", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('" + Recurso.CANCELAR_FASE_3 +"')")
	public ResponseEntity<CampoMensagem> cancelarFase3Paciente(@PathVariable(name = "id") Long idSolicitacao,
			@RequestBody(required=true) String justificativa) {

		solicitacaoService.cancelarSolicitacao(idSolicitacao, justificativa, null, null, null);

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método cancelar solicitacao fase 2 nacional.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro no cancelamento da solicitação.
	 */
	@RequestMapping(value = "{id}/cancelar/fase2/nacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('CANCELAR_FASE_2')")
	public ResponseEntity<CampoMensagem> cancelarFase2Nacional(@PathVariable(name = "id") Long idSolicitacao) {

		solicitacaoService.cancelarSolicitacao(idSolicitacao, null, null, null, null);

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para cancelar solicitação de fase 3 nacional.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro no cancelamento da solicitação.
	 */
	@RequestMapping(value = "{id}/cancelar/fase3/nacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('CANCELAR_FASE_3')")
	public ResponseEntity<CampoMensagem> cancelarPedido(@PathVariable(name = "id") Long idSolicitacao) {

		solicitacaoService.cancelarSolicitacao(idSolicitacao, null, null, null, null);

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para cancelar a solicitação de fase 2 internacional.
	 * 
	 * @param idSolicitacao - identificação da solicitação
	 * @param justificativa - justificativa do cancelamento 
	 * @param motivoStatusId - motivo de inativação de doador
	 * @param tempoInatividade - tempo de retorno do doador
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro no cancelamento da solicitação.
	 */
	@RequestMapping(value = "{id}/cancelar/fase2/internacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.CANCELAR_FASE_2_INTERNACIONAL + "')")
	public ResponseEntity<CampoMensagem> cancelarFase2Internacional(@PathVariable(name = "id") Long idSolicitacao,
			@RequestPart(required = true) String justificativa,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long tempoInatividade) {

		solicitacaoService.cancelarSolicitacao(idSolicitacao, justificativa, null, motivoStatusId, tempoInatividade);

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método para cancelar a solicitação de fase 3 internacional.
	 * 
	 * @param idSolicitacao - identificação da solicitação
	 * @param dataCancelamento - data de cancelamento 
	 * @param motivoStatusId - motivo de inativação de doador
	 * @param tempoInatividade - tempo de retorno do doador
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro na criação do match.
	 */
	@RequestMapping(value = "{id}/cancelar/fase3/internacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CANCELAR_FASE_3_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> cancelarPedidoCT(@PathVariable(name = "id") Long idSolicitacao,
			@RequestPart(required = true) LocalDate dataCancelamento,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long tempoInatividade) {

		solicitacaoService.cancelarSolicitacao(idSolicitacao, null, dataCancelamento, motivoStatusId, tempoInatividade);

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	

	/**
	 * Método para cancelar pedido de idm por solicitacao.
	 * 
	 * @param idSolicitacao - identificação da solicitação
	 * @param dataCancelamento - data de cancelamento 
	 * @param motivoStatusId - motivo de inativação de doador
	 * @param tempoInatividade - tempo de retorno do doador
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro na criação do match.
	 */
	@RequestMapping(value = "{id}/cancelar/idm/internacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CANCELAR_FASE_3_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> cancelarPedidoIdm(@PathVariable(name = "id") Long idSolicitacao,
			@RequestPart(required = true) LocalDate dataCancelamento,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long tempoInatividade) {

		Solicitacao solicitacao = new Solicitacao();
		solicitacao.setId(idSolicitacao);
		pedidoIdmService.cancelarPedido(solicitacao , dataCancelamento);
		
		if (motivoStatusId != null) {
			Long idDoador = solicitacaoService.recuperarIdDoadorPorSolicitacao(idSolicitacao);
			doadorInternacionalService.inativarDoador(idDoador, motivoStatusId, tempoInatividade);
		}

		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "solicitacao.mensagem.sucesso_cancelamento_pedido"));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método que deverá ser utilizado para solicitar fase 2 nacional.
	 * 
	 * @param idMatch - id do match.
	 * @param idTipoExame - identificador do tipo de exame solicitado.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "/fase2/nacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE_2_NACIONAL + "')")	
	public ResponseEntity<CampoMensagem> solicitarFase2Nacional(@RequestPart(required = true) Long idMatch, 
			@RequestPart(required = true) Long idTipoExame) {
		
		solicitacaoService.solicitarFase2Nacional(idMatch, idTipoExame);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"solicitacao.fase.mensagem.sucesso", "2", "nacional")), HttpStatus.OK);		
	}

	/**
	 * Método que deverá ser utilizado para solicitar fase 2 internacional.
	 * 
	 * @param idMatch - id do match.
	 * @param locusSolicitados - Lista de locus solicitados.
	 * @return mensagem de sucesso 
	 */
	@RequestMapping(value = "/fase2/internacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE_2_INTERNACIONAL + "')")
	public ResponseEntity<CampoMensagem> solicitarFase2Internacional(@RequestPart(name = "idMatch", required = true) Long idMatch,
			@RequestPart(name="locusSolicitados", required=true) List<Locus> locusSolicitados){
		
		solicitacaoService.solicitarFase2Internacional(idMatch, locusSolicitados);
				
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource, "pedido.exame.salvo.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método que deverá ser utilizado para solicitar fase 3 nacional.
	 * 
	 * @param idMatch - id do match.
	 * @param idLaboratorio ID do laboratório.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "/fase3/nacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE_3_NACIONAL + "')")
	public ResponseEntity<CampoMensagem> solicitarFase3Nacional(@RequestPart(required = true) Long idMatch,
			@RequestPart(required = true) Long idLaboratorio) {

		solicitacaoService.solicitarFase3Nacional(idMatch, idLaboratorio, false);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(
				AppUtil.getMensagem(messageSource, "pedido.contato.fase3.mensagem.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método rest para solicitar fase 3 internacional.
	 * 
	 * @param idMatch - id do match.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "/fase3/internacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE_3_INTERNACIONAL + "')")
	public ResponseEntity<CampoMensagem> solicitarFase3Internacional(
			@RequestBody(required = true) Long idMatch) {

		solicitacaoService.solicitarFase3Internacional(idMatch);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
				"solicitacao.fase3.mensagem.sucesso", "internacional")), HttpStatus.OK);
	}
	
	/**
	 * Método para obter os pedidos de exame de CT e IDM de um doador internacional.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @return ResponseEntity<SolicitacaoInternacionalDTO> dto preenchido com os pedidos se existirem.
	 */
	@RequestMapping(value = "{idSolicitacao}/pedidoexame/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL') "
			+ "|| hasPermission('CADASTRAR_RESULTADO_PEDIDO_CT')")
	@JsonView(PedidoExameView.ObterParaEditar.class)
	public ResponseEntity<SolicitacaoInternacionalDTO> obterPedidoExameInternacional(@PathVariable(required = true) Long idSolicitacao) {
		return new ResponseEntity<SolicitacaoInternacionalDTO>(solicitacaoService.obterPedidoExamePorSolicitacaoId(idSolicitacao),
				HttpStatus.OK);
	}
	
	/**
	 * Método para obter os pedidos de exame de CT e IDM de um doador internacional.
	 * 
	 * @param idSolicitacao - identificador da solicitação.
	 * @return ResponseEntity<SolicitacaoInternacionalDTO> dto preenchido com os pedidos se existirem.
	 */
	@RequestMapping(value = "{idSolicitacao}/pedidoidm/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_IDM')")
	public ResponseEntity<ResultadoPedidoInternacionalDTO> obterAcompanhamentoPedidoCTPedidoIDM(
			@PathVariable(required = true) Long idSolicitacao) {
		return new ResponseEntity<ResultadoPedidoInternacionalDTO>(solicitacaoService.obterDetalhesPedidoCtPedidoIdmInternacional(idSolicitacao),
				HttpStatus.OK);
	}
	
	/**
	 * Método que deverá ser utilizado para solicitar fase 3 para o paciente.
	 * 
	 * @param idBusca - identificador da busca.
	 * @param idLaboratorio - identificador do laboratório
	 * @param idTipoExame - identificador do tipo de exame
	 * @return CampoMensagem - objeto com a mensagem de retorno
	 */
	@RequestMapping(value = "/fase3/paciente", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE3_PACIENTE + "')")
	public ResponseEntity<CampoMensagem> solicitarFase3Paciente(
			@RequestPart(required=true) Long idBusca, 
			@RequestPart(required=true) Long idLaboratorio,
			@RequestPart(required=true) Long idTipoExame) {
		
		solicitacaoService.solicitarFase3Paciente(idBusca, idLaboratorio, idTipoExame);
		
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "pedido.exame.salvo.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Método que deverá ser utilizado para solicitar fase 3 nacional para resolver divergencia.
	 * 
	 * @param idMatch - id do match.
	 * @param idLaboratorio ID do laboratório.
	 * @return mensagem de sucesso
	 */
	@RequestMapping(value = "/fase3/nacional/resolverdivergencia", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('" + Recurso.SOLICITAR_FASE_3_NACIONAL + "')")
	public ResponseEntity<CampoMensagem> solicitarFase3NacionalResolverDivergencia(@RequestPart(required = true) Long idMatch,
			@RequestPart(required = true) Long idLaboratorio) {

		solicitacaoService.solicitarFase3Nacional(idMatch, idLaboratorio, true);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem(
				AppUtil.getMensagem(messageSource, "pedido.contato.fase3.mensagem.sucesso")), HttpStatus.OK);

	}
	
	@PostMapping("/workup/doadornacionalpacientenacional")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	@JsonView(SolicitacaoView.detalhe.class)
	public ResponseEntity<Solicitacao> criarSolicitacaoWorkupDoadorNacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch) {
		return ResponseEntity.ok(solicitacaoService.criarSolicitacaoWorkupDoadorNacionalPacienteNacional(idMatch));
	}
	
	@PostMapping("/workup/doadorinternacionalpacientenacional")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	@JsonView(SolicitacaoView.detalhe.class)
	public ResponseEntity<Solicitacao> criarSolicitacaoWorkupDoadorInternacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch) {
		return ResponseEntity.ok(solicitacaoService.criarSolicitacaoWorkupDoadorInternacional(idMatch));
	}
	
	@PostMapping("/workup/cordaonacionalpacientenacional")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	@JsonView(SolicitacaoView.detalhe.class)
	public ResponseEntity<Solicitacao> criarSolicitacaoWorkupCordaoNacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch) {
		return ResponseEntity.ok(solicitacaoService.criarSolicitacaoWorkupCordaoNacionalPacienteNacional(idMatch));
	}
	
	@PostMapping("/workup/cordaointernacionalpacientenacional")
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	@JsonView(SolicitacaoView.detalhe.class)
	public ResponseEntity<Solicitacao> criarSolicitacaoWorkupCordaoInternacionalPacienteNacioanl(@RequestBody(required=true) Long idMatch) {
		return ResponseEntity.ok(solicitacaoService.criarSolicitacaoWorkupCordaoInternacional(idMatch));
	}
	
	@GetMapping(value = "{id}")
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_PRESCRICAO + "') || hasPermission('" + Recurso.CADASTRAR_PRESCRICAO + "') "
			+ " || hasPermission('" + Recurso.DISTRIBUIR_WORKUP + "') || hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') "
			+ " || hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "') || hasPermission('" + Recurso.EFETUAR_LOGISTICA + "') " 
			+ " || hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP + "') || hasPermission('" + Recurso.TRATAR_PEDIDO_COLETA_INTERNACIONAL + "') "
			+ " || hasPermission('" + Recurso.AGENDAR_TRANSPORTE_MATERIAL + "') "
			+ " || hasPermission('" + Recurso.FINALIZAR_FORMULARIO_POSCOLETA + "') ")
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> obterSolicitacao(@PathVariable(required = true) Long id) {
		return ResponseEntity.ok(solicitacaoService.obterSolicitacao(id));
	}
	
	@GetMapping("/workup/solicitacoescentrotransplantestatussolicitacao")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_PRESCRICAO + "')")
	@JsonView(SolicitacaoView.listar.class)
	public ResponseEntity<Paginacao<Solicitacao>> listarSolicitacaoPorCentroTransplanteEStatusSolicitacao(
			@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) String[] statusSolicitacao,
 			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros){
		return ResponseEntity.ok(solicitacaoService.listarSolicitacoesPorCentroTransplanteEStatusSolicitacao(idCentroTransplante, statusSolicitacao, PageRequest.of(pagina, quantidadeRegistros)));
	}
	
	/**
	 * Método para cancelar pedido de idm por solicitacao.
	 * 
	 * @param idSolicitacao - identificação da solicitação
	 * @param dataCancelamento - data de cancelamento 
	 * @param motivoStatusId - motivo de inativação de doador
	 * @param tempoInatividade - tempo de retorno do doador
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso ou erro na criação do match.
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "{id}/cancelar/workup")
	@PreAuthorize("hasPermission('CANCELAR_WORKUP')")
	@JsonView(SolicitacaoView.detalhe.class)
	public ResponseEntity<Solicitacao> cancelarSoliiacaoWorkup(@PathVariable(name = "id") Long id) {		
		return ResponseEntity.ok(solicitacaoService.cancelarSolicitacao(id, null, null, null, null));
	}
	
	@GetMapping
	@PreAuthorize("hasPermission('" + Recurso.DISTRIBUIR_WORKUP + "') "
			+ "|| hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') "
			+ "|| hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "')"
			+ "|| hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP + "')"
			+ "|| hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_INTERNACIONAL + "')")
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<List<Solicitacao>> listarSolicitacoes(@RequestParam(required = true) String[] tiposSolicitacao,
			@RequestParam(required = true) String[] statusSolicitacao) {
		return ResponseEntity.ok(solicitacaoService.listarSolicitacoesPorTiposSolicitacaoEStatus(tiposSolicitacao, statusSolicitacao));
	}

	@GetMapping(value = "solicitacaofasesworkup")
	@PreAuthorize("hasPermission('" + Recurso.DISTRIBUIR_WORKUP + "') || "
			+ "hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') || "
			+ "hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP_CENTRO_COLETA + "')")
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<List<Solicitacao>> listarSolicitacoesPorTiposSolicitacaoPorStatusEFasesWorkup(
			@RequestParam(required = true) String[] tiposSolicitacao,
			@RequestParam(required = true) String[] statusSolicitacao,
			@RequestParam(required = true) String[] fasesWorkup) {
		return ResponseEntity.ok(solicitacaoService.listarSolicitacoesPorTiposSolicitacaoPorStatusEFasesWorkup(tiposSolicitacao, statusSolicitacao, fasesWorkup));
	}
	
	
	@PutMapping(value = "{id}/atribuirusuarioresponsavel")
	@PreAuthorize("hasPermission('" + Recurso.DISTRIBUIR_WORKUP + "') || hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') ")
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> atribuirUsuarioResponsavel(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idUsuario) {
		return ResponseEntity.ok(solicitacaoService.atribuirUsuarioResponsavel(id, idUsuario));
	}
	
	@PutMapping(value = "{id}/atualizarfaseworkup")
	@PreAuthorize("hasPermission('" + Recurso.DISTRIBUIR_WORKUP+ "') "
			+ "|| hasPermission('" + Recurso.DISTRIBUIR_WORKUP_INTERNACIONAL + "') "
		    + "|| hasPermission('" + Recurso.AVALIAR_PRESCRICAO + "') "
		    + "|| hasPermission('" + Recurso.APROVAR_PLANO_WORKUP + "') "
		    + "|| hasPermission('" + Recurso.EFETUAR_LOGISTICA + "') "
		    + "|| hasPermission('" + Recurso.AGENDAR_TRANSPORTE_MATERIAL + "') "
		    + "|| hasPermission('" + Recurso.FINALIZAR_RESULTADO_WORKUP_NACIONAL + "') ")
	
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> atualizarFaseWorkup(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idFaseWorkup) {
		return ResponseEntity.ok(solicitacaoService.atualizarFaseWorkup(id, idFaseWorkup));
	}
	
	@PutMapping(value = "{id}/atribuircentrocoleta")
	@PreAuthorize("hasPermission('" + Recurso.TRATAR_PEDIDO_WORKUP + "') ")	
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> atribuirCentroColeta(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long idCentroColeta) {
		return ResponseEntity.ok(solicitacaoService.atribuirCentroColeta(id, idCentroColeta));
	}
	
	@PutMapping(value = "{id}/atualizarsolicitacaoposcoleta")
	@PreAuthorize("hasPermission('" + Recurso.FINALIZAR_FORMULARIO_POSCOLETA+ "') ")		
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> atualizarSolicitacaoPosColeta(@PathVariable(required = true) Long id,
			@RequestBody(required = true) Long posColeta) {
		return ResponseEntity.ok(solicitacaoService.atualizarSolicitacaoPosColeta(id, posColeta));
	}	
	
	@PutMapping(value = "{id}/atualizarsolicitacaocontagemcelula")
	@PreAuthorize("hasPermission('" + Recurso.TRATAR_CONTAGEM_CELULA+ "') ")	
	@JsonView(SolicitacaoView.detalheWorkup.class)
	public ResponseEntity<Solicitacao> atualizarSolicitacaoContagemCelula(@PathVariable(required = true) Long id) {
		return ResponseEntity.ok(solicitacaoService.atualizarSolicitacaoContagemCelula(id));
	}
	
	
}
