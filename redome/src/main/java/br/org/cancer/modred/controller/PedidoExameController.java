package br.org.cancer.modred.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.controller.dto.doador.SolicitacaoRedomewebDTO;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoExame;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.service.IPedidoIdmService;
import br.org.cancer.modred.service.integracao.IIntegracaoSolicitacaoRedomeWebService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.PedidoExameDoadorInternacionalVo;
import br.org.cancer.modred.vo.PedidoExameDoadorNacionalVo;

/**
 * Controlador para serviços referentes a pedido de exame.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/pedidosexame", produces = "application/json;charset=UTF-8")
public class PedidoExameController {

	@Autowired
	private IPedidoExameService pedidoExameService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IPedidoIdmService pedidoIdmService;
	
	@Autowired
	private IIntegracaoSolicitacaoRedomeWebService integracaoSolicitacaoRedomeWebService;

	/**
	 * Serviço para recebimento de coleta pelo laboratorio.
	 * 
	 * @param idPedido identificação do pedido.
	 * @param idLaboratorio id do laboratorio ao qual o usuário tem permissão.
	 * @param pedido pedido a ser recebido.
	 * @return Mensagem de OK, caso não ocorram erros.
	 */
	@RequestMapping(value = "{id}/receber", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission(#idLaboratorio, 'Laboratorio', 'RECEBER_COLETA_LABORATORIO')")
	public ResponseEntity<CampoMensagem> receberAmostra(
			@PathVariable(name = "id", required = true) Long idPedido,
			@RequestParam(required = true) Long idLaboratorio, 
			@RequestBody(required = true) PedidoExame pedido) {
		pedidoExameService.receberPedido(pedido);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "pedido.exame.recebimento_coleta_sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método para salvar pedido de exame com o resultado.
	 * 
	 * @param listaArquivosLaudo - lista de arquivos
	 * @param pedidoExame - pedido de exame
	 * @return Lista de campo mensagem.
	 * @throws Exception exceção lançada caso não consiga salvar o pedido.
	 */
	@RequestMapping(method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission(#pedidoExame.laboratorio.id, 'Laboratorio',  'RECEBER_COLETA_LABORATORIO')")
	public ResponseEntity<List<CampoMensagem>> salvarPedidoExame(
			@RequestPart(name = "file") List<MultipartFile> listaArquivosLaudo,
			@RequestPart(required = true) PedidoExame pedidoExame) throws Exception {

		pedidoExameService.salvarResultadoPedidoExamePaciente(pedidoExame, listaArquivosLaudo);
		List<CampoMensagem> listaMensagens = new ArrayList<CampoMensagem>();
		listaMensagens.add(new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"exame.incluido.sucesso")));
		listaMensagens.add(new CampoMensagem("id", pedidoExame.getExame().getId().toString()));

		return new ResponseEntity<List<CampoMensagem>>(listaMensagens, HttpStatus.OK);
	}

	/**
	 * Carrega um pedido de exame por id.
	 * 
	 * @param idPedido - id do pedido de exame.
	 * @return ResponseEntity<PedidoExame> pedido exame carregado.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('SOLICITAR_FASE3_PACIENTE')")
	@JsonView(PedidoExameView.Detalhe.class)
	public ResponseEntity<PedidoExame> carregarPedido(@PathVariable(name = "id", required = true) Long idPedido) {
		return new ResponseEntity<PedidoExame>(pedidoExameService.obterPedidoExame(idPedido), HttpStatus.OK);
	}

	/**
	 * Serviço para alterar o centro transplantador a que o pedido de exame foi destinado.
	 * 
	 * @param pedidoExameId ID do pedido de exame que será cancelado (se possível).
	 * @param idLaboratorio Id do novo laboratorio
	 * 
	 * @return CampoMensagem - objeto com a mensagem de retorno
	 */
	@RequestMapping(value = "{id}/alterar/laboratorio", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('ALTERAR_LABORATORIO_PARA_PEDIDO_EXAME')")
	public ResponseEntity<CampoMensagem> alterarLaboratorio(@PathVariable(name = "id") Long pedidoExameId,
			@RequestBody(required = true) Long idLaboratorio) {
		boolean laboratorioAlterado = pedidoExameService.alterarLaboratorioCT(pedidoExameId, idLaboratorio);

		if (laboratorioAlterado) {
			return new ResponseEntity<CampoMensagem>(
					new CampoMensagem("", AppUtil.getMensagem(messageSource, "pedido.exame.cancelado.sucesso")), HttpStatus.OK);
		}
		final Paciente paciente = pacienteService.obterPacientePorPedidoExame(pedidoExameId);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "pedido.exame.alterado.cancelado.falha", String.valueOf(
						paciente.getRmr()))), HttpStatus.OK);
	}


	/**
	 * Baixar relatório de test confirmatório.
	 * 
	 * @param idBusca identificador da busca
	 * @param response response
	 * @param idTipoExame identificador do tipo de coleta 
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "instrucaocoletasanguectswab/download", method = RequestMethod.GET)
	public void baixarArquivoPedidoExameSangueCTSwab(
			@RequestParam(name = "idBusca", required = true) Long idBusca,
			@RequestParam(name = "idTipoExame", required = true) Long idTipoExame,
			HttpServletResponse response) throws IOException {

		File arquivo = pedidoExameService.obterDocumentoInstrucaoColeta(idBusca, idTipoExame);

		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}
	
	
	/**
	 * Baixar relatório de test confirmatório.
	 * 
	 * @param idBusca identificador da busca
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "testeconfirmatorio/download", method = RequestMethod.GET)
	public void baixarArquivoExameDRB1DQB1(
			@RequestParam(name = "idBusca", required = true) Long idBusca,
			HttpServletResponse response) throws IOException {

		File arquivo = pedidoExameService.obterDocumentoPedidoExame(idBusca);
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Baixar relatório por codigo.
	 * 
	 * @param idPedidoExame identificador do pedido de exame
	 * @param response response
	 * @throws IOException ao ler o arquivo
	 */
	@RequestMapping(value = "{id}/ct/download", method = RequestMethod.GET)
	public void baixarArquivoPedidoExameCT(
			@PathVariable(name = "id", required = true) Long idPedidoExame,
			HttpServletResponse response) throws IOException {

		File arquivo = pedidoExameService.obterArquivoPedidoExameCT(idPedidoExame, Relatorios.PEDIDO_EXAME_CT.getCodigo());
		response.setContentType(Files.probeContentType(arquivo.toPath()));
		response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.addHeader("Content-Disposition",
				String.format("inline; filename=\"" + arquivo.getName() + "\""));
		FileCopyUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
		response.flushBuffer();
	}

	/**
	 * Salva o resultado do pedido de exame fase 2 internacional.
	 * 
	 * @param id ID do pedido do exame.
	 * @param exame exame a ser salvo.
	 * @param motivoStatusId motivo do novo status do doador.
	 * @param timeRetornoInatividade Tempo de retorno da inatividade, caso o doador esteja nesta situação.
	 * @return campos de validação com retorno.
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}/resultado/doadorinternacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> salvarResultadoPedidoExame(
			@PathVariable(name = "id", required = true) Long id,
			@RequestPart(required = true) ExameDoadorInternacional exame,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long timeRetornoInatividade) throws Exception {

		pedidoExameService.salvarResultadoPedidoExameDoadorInternacional(id, exame, motivoStatusId, timeRetornoInatividade);

		return new ResponseEntity<CampoMensagem>(new CampoMensagem("", AppUtil.getMensagem(messageSource,
				"resultado.fase2.incluido.sucesso")), HttpStatus.OK);
	}

	
	/**
	 * Atualiza o pedido de exame quanto aos locus de pedido internacional.
	 * 
	 * @param pedidoExame Pedido de exame a ser persistido.
	 * @return CampoMensagem - objeto com a mensagem de retorno
	 */
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.EDITAR_FASE2_INTERNACIONAL + "')")
	public ResponseEntity<CampoMensagem> atualizarPedidoExame(@RequestBody PedidoExame pedidoExame) {
		pedidoExameService.atualizarPedidoExameInternacional(pedidoExame);
		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "pedido.exame.salvo.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Salva o resultado do pedido de exame fase 2 internacional.
	 * 
	 * @param id ID do pedido do exame.
	 * @param exame exame a ser salvo.
	 * @param motivoStatusId motivo do novo status do doador.
	 * @param timeRetornoInatividade Tempo de retorno da inatividade, caso o doador esteja nesta situação.
	 * @param listaArquivosLaudo - lista com os arquivos de laudo para o exame de pedido internacional
	 * @return campos de validação com retorno.
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}/resultado/ct/doadorinternacional", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_CT')")
	public ResponseEntity<CampoMensagem> salvarResultadoPedidoExameCTInternacional(
			@PathVariable(name = "id", required = true) Long id,
			@RequestPart(required = true) ExameDoadorInternacional exame,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long timeRetornoInatividade,
			@RequestPart(name = "file", required = true) List<MultipartFile> listaArquivosLaudo) throws Exception {

		pedidoExameService.
			salvarResultadoPedidoExameCTDoadorInternacional(id, exame, motivoStatusId, timeRetornoInatividade, listaArquivosLaudo);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(""), HttpStatus.OK);
	}
	
	/**
	 * Registra o resultado do pedido IDM (laudo, basicamente).
	 * 
	 * @param idPedidoIdm ID do pedido de IDM.
	 * @param listaLaudos lista de arquivos a serem salvos. Para este processo, somente um arquivo deve vir.
	 * @return Campo mensagem com a mensagem de sucesso, caso ocorra.
	 */
	@RequestMapping(value = "{id}/resultado/idm/doadorinternacional", method = RequestMethod.POST, consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_IDM')")
	public ResponseEntity<CampoMensagem> salvarResultadoPedidoIdmInternacional(
			@PathVariable(name = "id", required = true) Long idPedidoIdm,
			@RequestPart(name = "file", required = true) List<MultipartFile> listaLaudos) {

		pedidoIdmService.salvarResultadoPedidoIdmDoadorInternacional(idPedidoIdm, listaLaudos);

		return new ResponseEntity<CampoMensagem>(
				new CampoMensagem("", AppUtil.getMensagem(messageSource, "laudo.pedido.exame.idm.sucesso")), HttpStatus.OK);
	}
	
	/**
	 * Salva o resultado do pedido de exame ct cordão internacional.
	 * 
	 * @param id ID do pedido do exame.
	 * @param exame exame a ser salvo.
	 * @param motivoStatusId motivo do novo status do doador.
	 * @param timeRetornoInatividade Tempo de retorno da inatividade, caso o doador esteja nesta situação.
	 * @param listaArquivosLaudo - lista com os arquivos de laudo para o exame de pedido internacional
	 * @return campos de validação com retorno.
	 * @throws Exception 
	 */
	@RequestMapping(value = "{id}/resultado/ct/cordaointernacional", method = RequestMethod.POST,
					consumes = "multipart/form-data")
	@PreAuthorize("hasPermission('CADASTRAR_RESULTADO_PEDIDO_CT')")
	public ResponseEntity<CampoMensagem> salvarResultadoPedidoExameCTCordaoInternacional(
			@PathVariable(name = "id", required = true) Long id,
			@RequestPart(required = true) ExameCordaoInternacional exame,
			@RequestPart(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestPart(name = "timeRetornoInatividade", required = false) Long timeRetornoInatividade,
			@RequestPart(name = "file", required = true) List<MultipartFile> listaArquivosLaudo) throws Exception {

		pedidoExameService.salvarResultadoPedidoExameCTCordaoInternacional(
				id, exame, motivoStatusId, timeRetornoInatividade, listaArquivosLaudo);
		return new ResponseEntity<CampoMensagem>(new CampoMensagem(""), HttpStatus.OK);
	}
	
	/**
	 * Lista tarefas de pedido de exame - coletas Laboratório.
	 * 
	 * @param pagina página a ser exibida.
	 * @param quantidadeRegistros número de registros por página.
	 * @return lista paginada de tarefas de pedido de exame abertas para coleta do laboratório.
	 */
	@RequestMapping(value = "laboratorio/coleta/tarefas", method = RequestMethod.GET)
	@JsonView(LaboratorioView.ListarReceberExame.class)
	@PreAuthorize("hasPermission('RECEBER_COLETA_LABORATORIO')")
	public ResponseEntity<JsonViewPage<TarefaDTO>> listarTarefasDeColetaPedidoExameLaboratorio(
		@RequestParam(required = true) int pagina,
		@RequestParam(required = true) int quantidadeRegistros) {
		return new ResponseEntity<JsonViewPage<TarefaDTO>>(
 				pedidoExameService.listarTarefasDeColetaPedidoExameLaboratorio(
 						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}

	/**
	 * Lista tarefas de pedido de exame - resultados Laboratório.
	 * 
	 * @param pagina página a ser exibida.
	 * @param quantidadeRegistros número de registros por página.
	 * @return lista paginada de tarefas de pedido de exame coletadas resultados do laboratório.
	 */
	@RequestMapping(value = "laboratorio/resultado/tarefas", method = RequestMethod.GET)
	@JsonView(LaboratorioView.ListarReceberExame.class)
	@PreAuthorize("hasPermission('RECEBER_COLETA_LABORATORIO')")
	public ResponseEntity<JsonViewPage<TarefaDTO>> listarTarefasDeResultadoPedidoExameLaboratorio(
		@RequestParam(required = true) int pagina,
		@RequestParam(required = true) int quantidadeRegistros) {
		return new ResponseEntity<JsonViewPage<TarefaDTO>>(
 				pedidoExameService.listarTarefasDeResultadoPedidoExameLaboratorio(
 						new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}

	/**
	 * Atualiza os dados pedido exame do doador nacional via integracao.
	 * 
	 * @param id identificador do pedido exame.
	 * @param doador informações dos dados pessoais do doador a serem atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "integracao", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('INTEGRACAO_REDOME_WEB')")	
	public ResponseEntity<List<MensagemErroIntegracao>> atualizarPedidoExameIntegracaoDoadorNacional(
			@RequestBody(required = true) List<SolicitacaoRedomewebDTO> solicitacoes) {
		List<MensagemErroIntegracao> mensagens = integracaoSolicitacaoRedomeWebService.atualizarPedidoExameIntegracaoDoadorNacional(solicitacoes);
		return new ResponseEntity<List<MensagemErroIntegracao>>(mensagens,HttpStatus.OK);
	}


	/**
	 * Lista doadores exibindo todas as informações vinculadas ao pedido de exame.
	 * 
	 * @param idDoador - identificação do doador
	 * @param idPaciente - identificação do paciente
	 * @param pagina - valor para paginação
	 * @param quantidadeRegistros - quantidade de registro
	 * @return Page<PedidoExameDoadorNacionalVo> - retorna o Vo com informações do pedido de exame.
	 */
	@RequestMapping(value = "/andamento/solicitacao/nacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONSULTAR_DOADOR')")
	public ResponseEntity<Page<PedidoExameDoadorNacionalVo>> listarAndamentoDePedidosExamesPorDoador(
			@RequestParam(required = false) Long idDoador,
			@RequestParam(required = false) Long idPaciente,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros){
		return new ResponseEntity<Page<PedidoExameDoadorNacionalVo>>(
 				pedidoExameService.listarAndamentoDePedidosExamesPorDoador(
 						idDoador, new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK); 
	}
	
	
	/**
	 * Lista os VO's de acompanhamento de pedido de exame para doadores nacionais.
	 * 
	 * @param idDoador ID do doador.
	 * @param idPaciente ID do paciente.
	 * @param idBusca ID da busca.
	 * @param exibirHistorico - exibe ou não o historico.
	 * @param filtroTipoExame - filtro por tipo de exame.
	 * @param pagina página a ser exibida.
	 * @param quantidadeRegistros número de registros por página.
	 * @return lista de ConsultaPedidoExameVo paginada. 
	 */
	@RequestMapping(value = "nacional/pedidos", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('SOLICITAR_FASE3_PACIENTE')  || hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<Page<PedidoExameDoadorNacionalVo>> listarResumoDePedidoExameNacional(
			@RequestParam(required = false) Long idDoador,
			@RequestParam(required = false) Long idPaciente,
			@RequestParam(required = false) Long idBusca,
			@RequestParam(required = false) Boolean exibirHistorico,
			@RequestParam(required = false) Long filtroTipoExame,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros){
		return new ResponseEntity<Page<PedidoExameDoadorNacionalVo>>(
 				pedidoExameService.listarPedidosDeExameNacional(idDoador, idPaciente,
 						idBusca, exibirHistorico, filtroTipoExame, new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK); 
	}
	
	/**
	 * Lista os VO's de acompanhamento de pedido de exame para doadores internacionais.
	 * 
	 * @param idBusca - identificação da busca ao qual os doadores estão vinculados.
	 * @param exibirHistorico - exibe ou não o historico.
	 * @param filtroTipoExame - filtro por tipo de exame. 
	 * @param pagina - pagina referida da paginação
	 * @param quantidadeRegistros - quantidade de registros 
	 * @return lista paginada de tarefas de pedido de exame.
	 */
	@RequestMapping(value = "internacional/pedidos", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('SOLICITAR_FASE3_PACIENTE')  || hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<Page<PedidoExameDoadorInternacionalVo>> listarResumoDePedidoExameInternacional(
			@RequestParam(required = false) Long idBusca,
			@RequestParam(required = false) Boolean exibirHistorico,
			@RequestParam(required = false) Long filtroTipoExame,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros){
		return new ResponseEntity<Page<PedidoExameDoadorInternacionalVo>>(
 				pedidoExameService.listarAndamentoPedidosDeExameInternacional(
 						idBusca, exibirHistorico, filtroTipoExame, new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK); 
	}
	
	@PutMapping("baixapedidosinvoice")
	@PreAuthorize("hasPermission('" + Recurso.CONCILIAR_PEDIDO_PACIENTE_INVOICE + "')")
	public ResponseEntity<CampoMensagem> atualizarPedidosInvoiceConcilidados(List<PedidosPacienteInvoiceDTO> pedidosDto){
		pedidoExameService.atualizarPedidosInvoiceConcilidados(pedidosDto);
		return ResponseEntity.ok(new CampoMensagem("pedido.exame.conciliado.sucesso"));
	}

	/**
	 * Cria 
	 * @param idTarefa
	 * @return
	 */
	@PostMapping("criarchecklistexamesemresultado30dias")
	public ResponseEntity<String> criarCheckListExameSemResultadoMais30Dias(@RequestParam(name = "tarefa", required = true) Long idTarefa){
		pedidoExameService.criarCheckListExameSemResultadoMais30Dias(idTarefa);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
