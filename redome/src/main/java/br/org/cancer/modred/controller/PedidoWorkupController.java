package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.AgendamentoWorkupDTO;
import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.PedidoWorkupView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.MotivoCancelamentoWorkup;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.domain.MotivosCancelamentoWorkup;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDisponibilidadeService;
import br.org.cancer.modred.service.IMotivoCancelamentoWorkupService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de REST controller para motivo de cancelamento de pedido de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
@RestController
@RequestMapping(value = "/api/pedidosworkup", produces = "application/json;charset=UTF-8")
public class PedidoWorkupController {

	@Autowired
	private IMotivoCancelamentoWorkupService motivoCancelamentoWorkupService;

	@Autowired
	private IPedidoWorkupService pedidoWorkupService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDisponibilidadeService disponibilidadeService;
	

	/**
	 * Método para listar motivos de cancelamento de pedido de workup.
	 * 
	 * @return ResponseEntity<List<MotivoCancelamentoWorkup>> listagem de cancelamento de pedido de workup.
	 */
	@RequestMapping(value = "/motivoscancelamentoworkup", method = RequestMethod.GET)
	public ResponseEntity<List<MotivoCancelamentoWorkup>> listarMotivosCancelamentoWorkup() {
		return new ResponseEntity<List<MotivoCancelamentoWorkup>>(motivoCancelamentoWorkupService
				.listarMotivosCancelamentoWorkupSelecionaveis(),
				HttpStatus.OK);
	}

	/**
	 * Método para cancelar um pedido de workup e as tarefas relacionadas.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @param idMotivoCancelamento - identificador do motivo de cancelamento
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/cancelar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> cancelarPedidoWorkup(
			@PathVariable(required = true) Long idPedido,
			@RequestParam(required = true) Long idMotivoCancelamento) {

		pedidoWorkupService.cancelarPedidoWorkup(idPedido, idMotivoCancelamento);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.cancelado.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método para obter pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return PedidoWorkup - pedido do workup.
	 */
	@RequestMapping(value = "{idPedido}", method = RequestMethod.GET)
	@JsonView(PedidoWorkupView.DetalheWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || "
			+ "hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL') || "
			+ "hasPermission('CADASTRAR_FORMULARIO')")
	public ResponseEntity<PedidoWorkup> obterPedidoWorkup(
			@PathVariable(required = true) Long idPedido) {

		return new ResponseEntity<PedidoWorkup>(pedidoWorkupService.obterPedidoWorkup(idPedido), HttpStatus.OK);
	}
	
	/**
	 * Método para obter pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return PedidoWorkup - pedido do workup.
	 */
	@RequestMapping(value = "{idPedido}/dadosct", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<DadosCentroTransplanteDTO> listarDadosCT(
			@PathVariable(required = true) Long idPedido) {

		return new ResponseEntity<DadosCentroTransplanteDTO>(pedidoWorkupService.listarDadosCT(idPedido), HttpStatus.OK);
	}
	
	/**
	 * Método para atualizar pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return PedidoWorkup - pedido do workup.
	 */
	@RequestMapping(value = "{idPedido}/atribuir", method = RequestMethod.PUT)
	@JsonView(PedidoWorkupView.DetalheWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> atualizarPedidoWorkup(
			@PathVariable(required = true) Long idPedido) {
		pedidoWorkupService.atribuirPedidoWorkup(idPedido);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.atribuido.sucesso")), HttpStatus.OK);
	}

	/**
	 * Método para agendar pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @param agendamentoWorkupDTO - dto com informações necessárias para o agendamento.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/agendar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> agendarPedidoWorkup(
			@PathVariable(required = true) Long idPedido,
			@RequestBody(required = true) AgendamentoWorkupDTO agendamentoWorkupDTO) {

		pedidoWorkupService.agendarPedidoWorkup(idPedido, agendamentoWorkupDTO);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.agendado.sucesso")), HttpStatus.OK);
	}

	
	/**
	 * Método para cancelar um pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/cancelar/ct", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('CANCELAR_PEDIDO_WORKUP_CT')")
	public ResponseEntity<CampoMensagem> cancelarPedidoWorkup(@PathVariable(required = true) Long idPedido) {

		pedidoWorkupService.cancelarPedidoWorkup(idPedido, MotivosCancelamentoWorkup.PRESCRICAO_CANCELADA.getCodigo());

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.cancelado.sucesso")), HttpStatus.OK);
	}

	/**
	 * Obtém a disponibilidade informada pelo analista workup para o pedido informado.
	 * 
	 * @param idPedido - identificador do pedido de workup.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/disponibilidade", method = RequestMethod.GET)
	@JsonView(DisponibilidadeView.VisualizacaoCentroTransplante.class)
	@PreAuthorize("hasPermission('VISUALIZAR_DISPONIBILIDADE')")
	public ResponseEntity<Disponibilidade> obterDisponibilidade(@PathVariable(required = true) Long idPedido) {
		Disponibilidade disponibilidade = pedidoWorkupService.obtemUltimaDisponibilidade(idPedido);
		return new ResponseEntity<Disponibilidade>(disponibilidade, HttpStatus.OK);
	}
	
	/**
	 * Responde a solicitação do analista redome com uma nova sugestão de datas (disponibilidade).
	 * 
	 * @param idPedido identificador do pedido de workup.
	 * @param disponibilidade nova disponibilidade informada pelo analista redome.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/disponibilidade/ct", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('VISUALIZAR_PENDENCIA_WORKUP')")
	public ResponseEntity<CampoMensagem> incluirDisponibilidadeCt(
			@PathVariable(required = true) Long idPedido, @RequestBody(required = true) Disponibilidade disponibilidade) {
		
		pedidoWorkupService.responderDisponibilidadeWorkup(idPedido, disponibilidade);
		
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"programacao.confirmada.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Método para finalizar pedido de workup.
	 * 
	 * @param idPedido - identificador do pedido
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/finalizar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> finalizarPedidoWorkup(
			@PathVariable(required = true) Long idPedido) {
		
		pedidoWorkupService.finalizarPedidoWorkup(idPedido);
		
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.finalizado.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Lista todos os pedidos de workups internacional disponíveis para atribuição.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "disponiveis", method = RequestMethod.GET)
	@JsonView(PedidoWorkupView.AgendamentoWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP')")
	public ResponseEntity<Page<TarefaDTO>> listarWorkupsDisponiveis(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		return new ResponseEntity<Page<TarefaDTO>>(
				pedidoWorkupService.listarPedidosWorkupDisponiveis(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Lista todos os pedidos de workups internacional já em andamento (atribuídos a algum analista).
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "atribuidos", method = RequestMethod.GET)
	@JsonView(PedidoWorkupView.AgendamentoWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP')")
	public ResponseEntity<Page<TarefaDTO>> listarWorkupsAtribuidos(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		return new ResponseEntity<Page<TarefaDTO>>(
				pedidoWorkupService.listarPedidosWorkupAtribuidos(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Lista todos os pedidos de workups internacional disponíveis para atribuição.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "disponiveis/internacional", method = RequestMethod.GET)
	@JsonView(PedidoWorkupView.AgendamentoWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<Page<TarefaDTO>> listarWorkupsInternacionaisDisponiveis(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		return new ResponseEntity<Page<TarefaDTO>>(
				pedidoWorkupService.listarPedidosWorkupInternacionaisDisponiveis(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Lista todos os pedidos de workups internacional já em andamento.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "atribuidos/internacional", method = RequestMethod.GET)
	@JsonView(PedidoWorkupView.AgendamentoWorkup.class)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<Page<TarefaDTO>> listarWorkupsInternacionais(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		return new ResponseEntity<Page<TarefaDTO>>(
				pedidoWorkupService.listarPedidosWorkupInternacionais(
						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Inclui uma nova disponibilidade para o centro de transtlante.
	 * 
	 * @param id - identificador do pedido de workup.
	 * @param dataSugerida - data em formato string que o usuario ira informar
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{id}/disponibilidade", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('TRATAR_PEDIDO_WORKUP') || hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> incluirDisponibilidade(@PathVariable(required = false) Long id,
			@RequestBody(required = true) String dataSugerida) {

		disponibilidadeService.incluirDisponibilidadeWorkup(id, dataSugerida);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"programacao.incluida.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Lista as tarefas de SUGERIR_DATA_WORKUP para um determinado centro de transplante.
	 * 
	 * @param idCentroTransplante - identificador do centro de transplante.
	 * @param pagina - Número da página
	 * @param quantidadeRegistros - Quantidade de registros por página
	 * @return Lista de tarefas para um determinado centro de transplante
	 */
	@RequestMapping(value = "disponibilidades", method = RequestMethod.GET)	
	@PreAuthorize("hasPermission(#idCentroTransplante, 'CentroTransplante', '" + Recurso.LISTAR_DISPONIBILIDADES + "')")
	public JsonViewPage<TarefaDTO> listarDisponibilidades(@RequestParam(required = true) Long idCentroTransplante,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		
		return pedidoWorkupService.listarDisponibilidadesPorCentroTransplante(idCentroTransplante, 
				new PageRequest(pagina, quantidadeRegistros));
	}

}
