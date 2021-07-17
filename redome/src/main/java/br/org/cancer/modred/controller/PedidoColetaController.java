package br.org.cancer.modred.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.DadosCentroTransplanteDTO;
import br.org.cancer.modred.controller.view.DisponibilidadeView;
import br.org.cancer.modred.controller.view.PedidoColetaView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IDisponibilidadeService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de REST controller para pedido de coleta.
 * 
 * @author bruno.sousa
 *
 */
@RestController
@RequestMapping(value = "/api/pedidoscoleta", produces = "application/json;charset=UTF-8")
public class PedidoColetaController {

	@Autowired
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDisponibilidadeService disponibilidadeService;
	
	/**
	 * Método para obter pedido de coleta.
	 * 
	 * @param idPedido - identificador do pedido de coleta.
	 * @return PedidoColeta - pedido de coleta.
	 */
	@RequestMapping(value = "{idPedido}", method = RequestMethod.GET)
	@JsonView(PedidoColetaView.DetalheColeta.class)
	@PreAuthorize("hasPermission('TRATAR_COLETA_DOADOR') || hasPermission('TRATAR_PEDIDO_COLETA_INTERNACIONAL')")
	public ResponseEntity<PedidoColeta> obterPedidoColeta(
			@PathVariable(required = true) Long idPedido) {

		return new ResponseEntity<PedidoColeta>(pedidoColetaService.obterPedidoColeta(idPedido), HttpStatus.OK);
	}
	
	/**
	 * Método para agendar o pedido de coleta.
	 * 
	 * @param idPedido - identificador do pedido de coleta.
	 * @param pedidoColeta - pedido de coleta.
	 * @return ResponseEntity<String> - mesnagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('TRATAR_COLETA_DOADOR') || hasPermission('TRATAR_PEDIDO_COLETA_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> agendarPedidoColeta(
			@PathVariable(required = true) Long idPedido,
			@RequestBody(required = true) PedidoColeta pedidoColeta) {
		
		pedidoColetaService.agendarPedidoColeta(idPedido, pedidoColeta);
		
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"agendar.pedido.coleta.mensagem.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Inclui uma nova disponibilidade para o centro de coleta.
	 * 
	 * @param id - identificador do pedido de workup.
	 * @param dataSugerida - data em formato string que o usuario ira informar
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{id}/disponibilidade", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('TRATAR_COLETA_DOADOR') || hasPermission('TRATAR_PEDIDO_COLETA_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> incluirDisponibilidade(@PathVariable(required = false) Long id,
			@RequestBody(required = true) String dataSugerida) {

		disponibilidadeService.incluirDisponibilidadeColeta(id, dataSugerida);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"disponibilidade.incluida.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Obtém a disponibilidade informada pelo analista workup para o pedido informado.
	 * 
	 * @param id - identificador do pedido de coleta.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{id}/disponibilidade", method = RequestMethod.GET)
	@JsonView(DisponibilidadeView.VisualizacaoCentroTransplante.class)
	@PreAuthorize("hasPermission('VISUALIZAR_DISPONIBILIDADE')")
	public ResponseEntity<Disponibilidade> obterDisponibilidade(@PathVariable(required = true) Long id) {
		Disponibilidade disponibilidade = pedidoColetaService.obterUltimaDisponibilidade(id);
		return new ResponseEntity<Disponibilidade>(disponibilidade, HttpStatus.OK);
	}
	

	/**
	 * Serviço onde o médico transplantador, representando um centro de transplante, 
	 * responde a solicitação do analista workup com uma nova sugestão de datas para coleta
	 * (disponibilidade).
	 * A disponibilidade, neste ponto, se aplica apenas ao cordão. Para medula este contato
	 * ocorre durante o agendamento do workup.
	 * 
	 * @param idPedido identificador do pedido de workup.
	 * @param disponibilidade nova disponibilidade informada pelo analista redome.
	 * @return CampoMensagem - mensagem de sucesso
	 */
	@RequestMapping(value = "{idPedido}/disponibilidade/ct", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('VISUALIZAR_PENDENCIA_WORKUP')")
	public ResponseEntity<CampoMensagem> responderDisponibilidadeParaColeta(
			@PathVariable(required = true) Long idPedido, @RequestBody(required = true) Disponibilidade disponibilidade) {
		
		pedidoColetaService.responderDisponibilidadeColeta(idPedido, disponibilidade);
		
		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"disponibilidade.incluida.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Cancela o pedido de coleta destinado aquele CT.
	 * Isso ocorre quando, por algum motivo que será informado, o centro não poderá mais realizar
	 * o procedimento para o doador.
	 * 
	 * @param idPedidoColeta ID do pedido de coleta a ser cancelado.
	 * @return mensagem de sucesso da operação.
	 */
	@RequestMapping(value = "{idPedidoColeta}/cancelar/ct", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('CANCELAR_PEDIDO_COLETA_CT')")
	public ResponseEntity<CampoMensagem> cancelarPedidoColeta(@PathVariable(required = true) Long idPedidoColeta) {

		pedidoColetaService.cancelarPedidoColetaPeloCT(idPedidoColeta);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.workup.cancelado.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Método para obter dados do CT .
	 * 
	 * @param idPedido - identificador do pedido de coleta.
	 * @return DadosCentroTransplanteDTO - Dados do centro de transplante.
	 */
	@RequestMapping(value = "{idPedido}/dadosct", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('TRATAR_COLETA_DOADOR') || hasPermission('TRATAR_PEDIDO_COLETA_INTERNACIONAL')")
	public ResponseEntity<DadosCentroTransplanteDTO> listarDadosCT(
			@PathVariable(required = true) Long idPedido) {

		return new ResponseEntity<DadosCentroTransplanteDTO>(pedidoColetaService.listarDadosCT(idPedido), HttpStatus.OK);
	}
	
	/**
	 * Lista as tarefas de SUGERIR_DATA_COLETA para um determinado centro de transplante.
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
		
		return pedidoColetaService.listarDisponibilidadesPorCentroTransplante(idCentroTransplante, 
				new PageRequest(pagina, quantidadeRegistros));
	}
	
	
	/**
	 * Atualiza a data de coleta de acordo com o id de pedido de logistica.
	 * 
	 * @param idPedidoLogistica 
	 * @return mensagem de sucesso da operação.
	 */
	@PutMapping(value = "atualizardatacoleta")
	@PreAuthorize("hasPermission('SOLICITAR_LOGISTICA_MATERIAL') ")
	public ResponseEntity<CampoMensagem> atualizarDataColetaTarefaDeCourier(@RequestParam(required = true, name = "idlogistica") Long idPedidoLogistica
			, @RequestBody(required = true) LocalDate dataColeta) {

		pedidoColetaService.atualizarDataColetaTarefaDeCourier(idPedidoLogistica, dataColeta);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("sucesso", AppUtil.getMensagem(messageSource,
				"pedido.coleta.atualizacao.data.sucesso")), HttpStatus.OK);
	}
	
	
	/**
	 * Lista as tarefas Agendamento pedido de coleta internacional.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas/agendadas/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.TRATAR_PEDIDO_COLETA_INTERNACIONAL + "')")
	public JsonViewPage<TarefaDTO> listarTarefasAgendadasInternacional(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return pedidoColetaService.listarTarefasAgendadasInternacional(
				new PageRequest(pagina, quantidadeRegistros));
	}

	/**
	 * Lista as tarefas Agendamento pedido de coleta.
	 * 
	 * @param pagina página que o resultado deverá separar para o retorno ao front-end.
	 * @param quantidadeRegistros quantidade de registros por página.
	 * @return lista de tarefas paginadas.
	 */
	@RequestMapping(value = "tarefas/agendadas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.TRATAR_COLETA_DOADOR + "')")
	public JsonViewPage<TarefaDTO> listarTarefasAgendadas(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return pedidoColetaService.listarTarefasAgendadas(
				new PageRequest(pagina, quantidadeRegistros));
	}

}
