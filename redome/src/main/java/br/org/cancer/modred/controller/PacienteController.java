package br.org.cancer.modred.controller;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.AnaliseMatchDTO;
import br.org.cancer.modred.controller.dto.AvaliacaoDTO;
import br.org.cancer.modred.controller.dto.ContatoPacienteDTO;
import br.org.cancer.modred.controller.dto.DetalheAvaliacaoPacienteDTO;
import br.org.cancer.modred.controller.dto.EvolucaoDto;
import br.org.cancer.modred.controller.dto.ExameDto;
import br.org.cancer.modred.controller.dto.HistoricoBuscaDTO;
import br.org.cancer.modred.controller.dto.LogEvolucaoDTO;
import br.org.cancer.modred.controller.dto.MatchDTO;
import br.org.cancer.modred.controller.dto.MismatchDTO;
import br.org.cancer.modred.controller.dto.PacienteTarefaDTO;
import br.org.cancer.modred.controller.dto.PacienteWmdaDTO;
import br.org.cancer.modred.controller.dto.PedidosPacienteInvoiceDTO;
import br.org.cancer.modred.controller.dto.StatusPacienteDTO;
import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.page.Paginacao;
import br.org.cancer.modred.controller.view.AvaliacaoView;
import br.org.cancer.modred.controller.view.EvolucaoView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.FiltroMatch;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.service.IEvolucaoService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoTransferenciaCentroService;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.service.ISegurancaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Controlador para pacientes.
 * 
 * @author Filipe Paes
 *
 */
@RestController
@RequestMapping(value = "/api/pacientes", produces = "application/json;charset=UTF-8")
public class PacienteController {

	@Autowired
	private IPacienteService pacienteService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IEvolucaoService evolucaoService;

	@Autowired
	private IExamePacienteService exameService;

	@Autowired
	private IAvaliacaoService avaliacaoService;

	@Autowired
	private IPendenciaService pendenciaService;
	
	@Autowired
	private ISegurancaService segurancaService;
	
	@Autowired
	private IBuscaService buscaService;
	
	@Autowired
	private IGenericRepository genericRepository;

	@Autowired
	private IUsuarioService usuarioService;

	
	@Autowired
	private ICordaoInternacionalService cordaoInternacionalService;
	
	@Autowired
	private IPedidoTransferenciaCentroService pedidoTransferenciaCentroService;
	

	/**
	 * Método rest para gravar paciente.
	 *
	 * @param paciente paciente a ser persistido.
	 * @return ResponseEntity<List<CampoMensagem>> Lista de erros
	 */
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<CampoMensagem> salvar(@RequestBody(required = true) Paciente paciente) {
		pacienteService.salvar(paciente);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "paciente.incluido.sucesso", paciente.getRmr() + ""));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Método que verifica se existe paciente em duplicidade com o parametro informado.
	 * 
	 * @param paciente pac
	 * @return TRUE se existir, FALSE se não existir.
	 */
	@RequestMapping(value = "verificarduplicidade", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('CADASTRAR_PACIENTE')")
	public ResponseEntity<String> verificarDuplicidadePaciente(
			@RequestBody(required = true) Paciente paciente) {

		pacienteService.verificarPacienteJaCadastradoRetorno(paciente);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Método que verifica se existe paciente em duplicidade com o parametro informado.
	 * 
	 * @param paciente
	 * @return TRUE se existir, FALSE se não existir.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(PacienteView.Detalhe.class)
	@PreAuthorize("(hasPermission(#id, 'Paciente', 'VISUALIZAR_FICHA_PACIENTE') ||"
			+ " hasPermission('PACIENTES_PARA_PROCESSO_BUSCA'))")
	public ResponseEntity<Paciente> obterFichaPaciente(@PathVariable(required = true) Long id) {
		return new ResponseEntity<Paciente>(pacienteService.obterPaciente(id), HttpStatus.OK);
	}
	/**
	 * Método que lista pacientes por rmr ou parte do nome. Caso seja enviado os dois parametros preenchidos, será escolhido o rmr
	 * pois é o de maior coesão Caso não seja enviado os parametros rmr e nome será retornado um BusinessException com a mensagem
	 * 'Parâmetros mínimos não fornecidos'.
	 * 
	 * @param rmr identificador do paciente
	 * @param nome parte do nome do paciente
	 * @param meusPacientes Somente os pacientes do médico logoado
	 * @param idCentroAvaliador identificador do centro avaliador
	 * @param pagina número da página
	 * @param quantidadeRegistros que deverão ser retornados.
	 * @return lista de pacientes paginada.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONSULTAR_PACIENTE')")
	public ResponseEntity<JsonPage> listarPacientesPorRmrOuNome(
			@RequestParam(required = true) Long rmr, 
			@RequestParam(required = true) String nome,
			@RequestParam(required = false) Boolean meusPacientes, 
			@RequestParam(required = false) Long idFuncaoTransplante,
			@RequestParam(required = false) Long idCentroAvaliador,
 			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		
		if (meusPacientes == null) {
			meusPacientes = false;
		}
		
		return new ResponseEntity<JsonPage>(new JsonPage(PacienteView.Consulta.class, pacienteService
				.listarPacientePorRmrOuNome(rmr, nome, meusPacientes, idFuncaoTransplante, idCentroAvaliador, 
						new PageRequest(pagina, quantidadeRegistros))), HttpStatus.OK);
	}

	/**
	 * Serviço para buscar os dados da última evolução preenchida.
	 * 
	 * @param rmr rmr
	 * @return ResponseEntity<Evolucao> evolucao com o retorno apenas dos dados necessários.
	 */
	@RequestMapping(value = "{id}/ultimaEvolucao", method = RequestMethod.GET)
	@JsonView(EvolucaoView.NovaEvolucao.class)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'CONSULTAR_EVOLUCOES_PACIENTE') || "
			+ "hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")
	public ResponseEntity<Evolucao> obterUltimaEvolucao(
			@PathVariable(name = "id", required = true) Long rmr) {

		Evolucao evolucao = pacienteService.obterUltimaEvolucao(rmr);
		return new ResponseEntity<Evolucao>(evolucao, HttpStatus.OK);
	}

	/**
	 * Método para se obter uma listagem de evoluções por RMR do paciente.
	 * 
	 * @param rmr rmr
	 * @return EvolucaoVo listagem de evoluoes de acordo com o RMR
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/evolucoes")
	@JsonView(EvolucaoView.ListaEvolucao.class)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'CONSULTAR_EVOLUCOES_PACIENTE')"
			+ "|| hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')"
			+ "|| hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")
	public ResponseEntity<EvolucaoDto> buscarEvolucoesPorRMR(
			@PathVariable(name = "id", required = true) Long rmr) {
		return new ResponseEntity<EvolucaoDto>(evolucaoService.carregarEvolucaoPorRMR(rmr),
				HttpStatus.OK);
	}

	/**
	 * Método para listar todos os exames do paciente.
	 * 
	 * @return listagem de exames encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/exames")
	@JsonView(ExameView.ListaExame.class)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'CONSULTAR_EXAMES_PACIENTE')"
			+ "|| hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')")
	public ResponseEntity<ExameDto> listarExamesPorRmr(@PathVariable(name = "id", required = true) Long rmr) {
		return new ResponseEntity<ExameDto>(exameService.listarExamesPorRmr(rmr), HttpStatus.OK);
	}

	/**
	 * Método para listar a identificação do paciente.
	 * 
	 * @param rmr do paciente
	 * @return objecto paciente com apenas os dados de identificação.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/identificacao")
	@PreAuthorize("(hasPermission(#rmr, 'Paciente', 'VISUALIZAR_IDENTIFICACAO_COMPLETA')"
			+ " || hasPermission(#rmr, 'Paciente', 'VISUALIZAR_IDENTIFICACAO_PARCIAL')"
			+ " || hasPermission('PACIENTES_PARA_PROCESSO_BUSCA')"
			+ " || hasPermission('RECEBER_COLETA_LABORATORIO'))"
			+ " || hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")			
	public MappingJacksonValue obterDadosIdentificadaoPorPaciente(
			@PathVariable(name = "id", required = true) Long rmr) {
		final Paciente paciente = pacienteService.obterDadosIdentificadaoPorPaciente(rmr);
		final MappingJacksonValue result = new MappingJacksonValue(paciente);
		
		if (segurancaService.usuarioLogadoPossuiRecurso(
				paciente, Arrays.asList(Recurso.VISUALIZAR_IDENTIFICACAO_COMPLETA
				, Recurso.PACIENTES_PARA_PROCESSO_BUSCA, Recurso.AVALIAR_NOVA_BUSCA_PACIENTE))) {
			result.setSerializationView(PacienteView.IdentificacaoCompleta.class);
		}
		else {
			result.setSerializationView(PacienteView.IdentificacaoParcial.class);
		}

		return result;
	}

	/**
	 * Método para obter a avaliacao atual do paciente.
	 * 
	 * @return AvaliacaoDto
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/avaliacoes/atual")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'AVALIAR_PACIENTE') "
			+ " || hasPermission(#rmr, 'Paciente', 'VISUALIZAR_AVALIACAO')")
	@JsonView(AvaliacaoView.Avaliacao.class)
	public ResponseEntity<AvaliacaoDTO> obterAvaliacaoAtual(@PathVariable(name = "id", required = true) Long rmr) {
		return new ResponseEntity<AvaliacaoDTO>(
				avaliacaoService.obterAvaliacaoAtual(rmr), HttpStatus.OK);
	}

	/**
	 * Método para listar as pendências em aberto por tipo da avaliacao atual do paciente.
	 * 
	 * @return AvaliacaoDto
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/avaliacoes/atual/pendencias")
	@PreAuthorize("(hasPermission(#rmr, 'Paciente', 'AVALIAR_PACIENTE')"
			+ " || hasPermission(#rmr, 'Paciente', 'VISUALIZAR_AVALIACAO'))")
	@JsonView(EvolucaoView.NovaEvolucao.class)
	public ResponseEntity<List<Pendencia>> listarPendenciasEmAbertoPorTipoDaAvaliacaoAtual(@PathVariable(name = "id", required = true) Long rmr,
			@QueryParam(value = "idTipoPendencia") Long idTipoPendencia) {
		return new ResponseEntity<List<Pendencia>>(pendenciaService.listarPendenciasPorTipoEEmAbertoDaAvaliacaoAtualDeUmPaciente(
				rmr, idTipoPendencia), HttpStatus.OK);
	}

	/**
	 * Listar os pacientes, sob responsabilidade do médico, que estão sob avaliação. Os pacientes trazidos na lista estão com
	 * pendências não solucionadas e que necessitam de ação do médico.
	 * 
	 * @param idCentroAvalaidor - identificador do centro de transplante
	 * @param pagina representa a "fatia" da paginação dos registros.
	 * @param quantidadeRegistros representa o tamanho da "fatia" que contida na página.
	 * @return lista paginada de paciente com sumário de tarefas em que está associado.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/pendencias")
	@PreAuthorize("hasPermission('VISUALIZAR_PENDENCIAS_AVALIACAO')")
	public ResponseEntity<Page<PacienteTarefaDTO>> listarPendencias(
			@RequestParam(required = false) Long idCentroAvalaidor,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {

		Page<PacienteTarefaDTO> pacientesSobAvaliacao = pacienteService.listarPacientesEmAvaliacaoPorMedicoLogado(idCentroAvalaidor, 
				new PageRequest(pagina, quantidadeRegistros));
		return new ResponseEntity<>(pacientesSobAvaliacao, HttpStatus.OK);
	}

	/**
	 * Obtém o contato do paciente informado, através do RMR.
	 * 
	 * @param rmr
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{rmr}/contatos")
	@PreAuthorize("hasPermission(#rmr, 'Paciente','EDITAR_CONTATO_PACIENTE')")
	public ResponseEntity<ContatoPacienteDTO> obterContatoPaciente(@PathVariable(required = true) Long rmr) {
		ContatoPacienteDTO contatoDTO = pacienteService.obterContatoPaciente(rmr);
		return new ResponseEntity<ContatoPacienteDTO>(contatoDTO, HttpStatus.OK);
	}

	/**
	 * Método rest para gravar paciente.
	 *
	 * @param rmr referência do paciente passada via URL. Foi necessário para verificação de permissão.
	 * @param paciente paciente a ser persistido.
	 * @return ResponseEntity<List<CampoMensagem>> Lista de erros
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "{rmr}/contatos")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'EDITAR_CONTATO_PACIENTE')")
	public ResponseEntity<CampoMensagem> atualizarContatoPaciente(@PathVariable(name = "rmr") Long rmr, 
			@RequestBody(required = true) Paciente paciente) {
		pacienteService.atualizarContatoPaciente(paciente);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "paciente.atualizado.sucesso", paciente.getRmr()));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}

	/**
	 * Serviço para obter somente os dados pessoais de um paciente.
	 * 
	 * @param rmr referência para busca dos dados pessoais do paciente.
	 * @return HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/dadospessoais")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'EDITAR_DADOS_PESSOAIS')")
	@JsonView(PacienteView.DadosPessoais.class)
	public ResponseEntity<Paciente> obterDadosPessoais(@PathVariable(name = "id", required = true) Long rmr) {

		return new ResponseEntity<Paciente>(pacienteService.obterDadosPessoais(rmr), HttpStatus.OK);
	}

	/**
	 * Serviço para atualizar os dados pessoais de um paciente.
	 * 
	 * @param rmr referência do paciente passada via URL. Foi necessário para verificação de permissão.
	 * @param paciente paciente que sofrerá a atualização.
	 * @return HttpStatus.OK
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/dadospessoais")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'EDITAR_DADOS_PESSOAIS')")
	public ResponseEntity<CampoMensagem> editarDadosPessoais(@PathVariable(name = "id", required = true) Long rmr,
			@RequestBody(required = true) Paciente paciente) {

		pacienteService.atualizarDadosPessoais(paciente);
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "paciente.atualizado.sucesso", paciente.getRmr()));

		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para listar todos os exames do paciente.
	 * 
	 * @return listagem de exames encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{rmr}/matchs")
	@PreAuthorize("hasPermission(#rmr, 'Paciente', 'ANALISE_MATCH')")
	public ResponseEntity<AnaliseMatchDTO> listarMatchs(@PathVariable(name = "rmr", required = true) Long rmr,
			@RequestParam(name="filtro", required=true) Long filtro ) {
		
		return new ResponseEntity<AnaliseMatchDTO>(pacienteService.listarMatchs(rmr, FiltroMatch.valueOf(filtro)), HttpStatus.OK);
	}
	
	/**
	 * Serviço para buscar se a última evolução está atualizada.
	 * 
	 * @param rmr rmr
	 * @return ResponseEntity<Boolean> True se a ultima evolução estiver atualizada.
	 */
	@GetMapping(value = "{id}/ultimaevolucaoatualizada")	
	@PreAuthorize("hasPermission('CADASTRAR_PRESCRICAO')")
	@JsonView(EvolucaoView.ListaEvolucao.class)
	public ResponseEntity<Evolucao> verificarUltimaEvolucaoAtualizada(
			@PathVariable(name = "id", required = true) Long rmr) {
		return new ResponseEntity<>(evolucaoService.obterUltimaEvolucaoAtualizadaComVerificacaoDePeriodoMaximoSemAtualizacao(rmr), HttpStatus.OK);
	}

	/**
	 * Serviço para buscar a lista de histórico da busca.
	 * 
	 * @param rmr rmr
	 * @param pagina pagina
	 * @param quantidadeRegistros quantidadeRegistros
	 * @return ResponseEntity<Paginacao<LogEvolucaoDTO>>.
	 */
	@PreAuthorize("hasPermission('VISUALIZAR_LOG_EVOLUCAO')")
	@RequestMapping(value = "{rmr}/historico", method = RequestMethod.GET)
	public ResponseEntity<Paginacao<LogEvolucaoDTO>> listarHistorico(
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros,
			@PathVariable(name="rmr", required = true) Long rmr){
		
		return new ResponseEntity<Paginacao<LogEvolucaoDTO>>(
				pacienteService.listarLogEvolucao(rmr, new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Obtem o detalhe da avaliação do paciente que irá preencher a tela de confirmação de
	 * centro de transplante.
	 * 
	 * @param rmr paciente que se deseja listar.
	 * @return detalhes da avaliação através de DTO.
	 */
	@RequestMapping(value="{rmr}/detalhesAvaliacao", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.DISPONIBILIZAR_DOADOR + "') || hasPermission('" + Recurso.ENCONTRAR_CENTRO_TRANSPLANTADOR + "')")
	public ResponseEntity<DetalheAvaliacaoPacienteDTO> obterDetalheParaConfirmacaoCentroTransplantador(
			@PathVariable(value = "rmr", required = true) Long rmr){
		return new ResponseEntity<DetalheAvaliacaoPacienteDTO>(
				pacienteService.obterDetalheAvaliacaoPaciente(rmr), HttpStatus.OK);
	}
	
	/**
	 * Obtem o detalhe da avaliação do paciente que irá preencher a tela de confirmação de
	 * centro de transplante.
	 * 
	 * @param rmr paciente que se deseja listar.
	 * @param centroTransplantadorId ID do centro transplantador.
	 * @return detalhes da avaliação através de DTO.
	 */
	@RequestMapping(value="{rmr}/centrosTransplante/{centroTransplantadorId}/confirmar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('CONFIRMAR_CENTRO_TRANSPLANTADOR') || hasPermission('ENCONTRAR_CENTRO_TRANSPLANTADOR')")
	public ResponseEntity<CampoMensagem> confirmarCentroTransplantador(
			@PathVariable(value = "rmr", required=true) Long rmr,
			@PathVariable(value = "centroTransplantadorId", required=true) Long centroTransplantadorId){
		
		pacienteService.confirmarCentroTransplantador(rmr, centroTransplantadorId);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "centro.transplantador.confirmado.sucesso", rmr));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Marca o centro transplantador como indefinido para a busca.
	 * Gera tarefa para o controlador de lista selecionar um novo centro, posteriormente.
	 * 
	 * @param rmr Identificação do paciente.
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value="{rmr}/centrosTransplante/indefinir", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('CONFIRMAR_CENTRO_TRANSPLANTADOR')")
	public ResponseEntity<CampoMensagem> marcarCentroTransplantadorComoIndefinido(@PathVariable(value = "rmr", required=true) Long rmr){
		
		pacienteService.indefinirCentroTransplantador(rmr);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "centro.transplantador.indefinido.sucesso", rmr));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Marca o centro transplantador como indefinido para a busca.
	 * Gera tarefa para o controlador de lista selecionar um novo centro, posteriormente.
	 * 
	 * @param rmr Identificação do paciente.
	 * @param justificativa justificativa para a recusa do CT.
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value="{rmr}/centrosTransplante/recusar", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('RECUSAR_CT_BUSCA')")
	public ResponseEntity<CampoMensagem> recusarCTParaBusca(
			@PathVariable(value = "rmr", required=true) Long rmr,
			@RequestBody String justificativa){
		
		pacienteService.recusarPacientePeloCentroTransplante(rmr, justificativa);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "centro.transplantador.recusado.sucesso"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Lista as recusas que o centro de transplante realizou para o paciente, após
	 * ele ser definido como responsável pelo mesmo.
	 * 
	 * @param rmr identificador do paciente a ter o histório consultado.
	 * @return lista de recusas do centro.
	 */
	@RequestMapping(value="{rmr}/centrosTransplante/recusas", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ENCONTRAR_CENTRO_TRANSPLANTADOR')")
	public ResponseEntity<List<HistoricoBuscaDTO>> listarHistoricoRecusasParaCT(
			@PathVariable(value = "rmr", required = true) Long rmr) {
		return new ResponseEntity<List<HistoricoBuscaDTO>>(buscaService.listarHistoricoBusca(rmr), HttpStatus.OK);
	}
	
	/**
	 * Método rest para transfererir um paciente.
	 *
	 * @param rmr - identificador do paciente.
	 * @param idCentroAvaliadorDestino - identificador do centro avaliador de destino.
	 * @return ResponseEntity<CampoMensagem> mensagem de sucesso.
	 */
	@RequestMapping(value = "{rmr}/transferircentroavaliador", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR + "')")
	public ResponseEntity<CampoMensagem> transferirCentroAvaliador(
			@PathVariable(value = "rmr", required = true) Long rmr,
			@RequestBody(required = true) Long idCentroAvaliadorDestino) {
		
		pedidoTransferenciaCentroService.solicitarTransferenciaCentroAvaliador(rmr, idCentroAvaliadorDestino);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "pedido.transferencia.centroavaliador.solicitado"));
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método rest para obter o diagnostico do paciente.
	 *
	 * @param rmr - identificador do paciente.
	 * @return ResponseEntity<Diagnostico> Diagnostico do paciente.
	 */
	@RequestMapping(value = "{rmr}/diagnostico", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.EDITAR_DIAGNOSTICO_PACIENTE + "') || "
			+ "hasPermission(#rmr, 'Paciente', '" + Recurso.SOLICITAR_NOVA_BUSCA_PACIENTE + "') || "
					+ "hasPermission('" + Recurso.AVALIAR_NOVA_BUSCA_PACIENTE + "')")
	@JsonView(PacienteView.Diagnostico.class)
	public ResponseEntity<Diagnostico> obterDiagnostico(
			@PathVariable(value = "rmr", required = true) Long rmr) {
		
		return new ResponseEntity<Diagnostico>(pacienteService.obterDiagnostico(rmr), HttpStatus.OK);
	}
	
	/**
	 * Método rest para obter os dados de mismatch do paciente.
	 *
	 * @param rmr - identificador do paciente.
	 * @return ResponseEntity<MismatchDTO> Dados do mismatch do paciente.
	 */
	@RequestMapping(value = "{rmr}/dadosmismatch", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.EDITAR_MISMATCH_PACIENTE + "')")	
	public ResponseEntity<MismatchDTO> obterDadosMismatch(
			@PathVariable(value = "rmr", required = true) Long rmr) {
		
		return new ResponseEntity<MismatchDTO>(pacienteService.obterDadosMismatch(rmr), HttpStatus.OK);
	}
	
	/**
	 * Método rest para alterar os dados de mismatch do paciente.
	 *
	 * @param rmr - identificador do paciente.
	 * @param mismatchDTO - DTO com os dados de mismatch.
	 * @return ResponseEntity<CampoMensagem> Mensagem de sucesso.
	 */
	@RequestMapping(value = "{rmr}/dadosmismatch", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('" + Recurso.EDITAR_MISMATCH_PACIENTE + "')")	
	public ResponseEntity<CampoMensagem> alterarDadosMismatch(
			@PathVariable(value = "rmr", required = true) Long rmr,
			@RequestBody(required = true) MismatchDTO mismatchDTO) {
		
		pacienteService.alterarDadosMismatch(rmr, mismatchDTO);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "paciente.atualizado.sucesso", rmr));
		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	
	/**
	 * Serviço que solicita uma nova busca para o paciente (RMR) 
	 * informado. A solicitação ainda passará por avaliação, antes
	 * de ser aprovada e virar, de fato, uma busca ativa.
	 *
	 * @param rmr - identificador do paciente.
	 * 
	 * @return ResponseEntity<CampoMensagem> Mensagem de sucesso.
	 */
	@RequestMapping(value = "{rmr}/novabusca", method = RequestMethod.POST)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', '" + Recurso.SOLICITAR_NOVA_BUSCA_PACIENTE + "')")	
	public ResponseEntity<CampoMensagem> solicitarNovaBusca(@PathVariable(value = "rmr", required = true) Long rmr) {
		
		pacienteService.solicitarNovaBusca(rmr);
		
		final CampoMensagem mensagem = new CampoMensagem("",
				AppUtil.getMensagem(messageSource, "nova.busca.solicitada.sucesso", rmr));
		
		return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	}
	
	/**
	 * Método para listar od matchs inativos de um paciente.
	 * 
	 * @return listagem de matchs inativos encontrados para o paciente.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{rmr}/matchs/inativos")
	@PreAuthorize("hasPermission('EXIBIR_HISTORICO_MATCH')")
	public ResponseEntity<List<MatchDTO>> listarMatchsInativos(@PathVariable(name = "rmr", required = true) Long rmr,
			@RequestParam(name="filtro", required=true) Long filtro,
			@RequestParam(name="fase", required=true) String fase) {
		
		return new ResponseEntity<List<MatchDTO>>(pacienteService.listarFaseInativos(rmr, FiltroMatch.valueOf(filtro), FasesMatch.valueById(fase)), HttpStatus.OK);
	}

	
	/**
	 * Serviço que solicita uma nova busca para o paciente (RMR) 
	 * informado. A solicitação ainda passará por avaliação, antes
	 * de ser aprovada e virar, de fato, uma busca ativa.
	 *
	 * @param rmr - identificador do paciente.
	 * 
	 * @return ResponseEntity<CampoMensagem> Mensagem de sucesso.
	 */
	@RequestMapping(value = "{rmr}/statuspaciente", method = RequestMethod.GET)
	@PreAuthorize("hasPermission(#rmr, 'Paciente', '" + Recurso.VISUALIZAR_IDENTIFICACAO_COMPLETA + "') ||"
			+ "hasPermission(#rmr, 'Paciente', '" + Recurso.VISUALIZAR_IDENTIFICACAO_PARCIAL + "')")
	public ResponseEntity<StatusPacienteDTO> obterStatusPacientePorRmr(
			@PathVariable(value = "rmr", required = true) Long rmr) {
		
		return new ResponseEntity<StatusPacienteDTO>(pacienteService.obterStatusPacientePorRmr(rmr), HttpStatus.OK);
	}

	/**
	 * Serviço que lista os pedidos de exame, IDM e coleta pedentes de pagamento. 
	 * 
	 * @param rmr - rmr do paciente.
	 * @param nomePaciente - nome do paciente.
	 * @param pagina pagina
	 * @param quantidadeRegistros quantidadeRegistros
	 * @return ResponseEntity<Paginacao<PedidosPacienteInvoiceDTO>>.
	 */
	@PreAuthorize("hasPermission('" + Recurso.CONSULTAR_PEDIDO_PACIENTE_INVOICE+ "')")
	@RequestMapping(value = "invoice/pedidospaginado", method = RequestMethod.GET)
	public ResponseEntity<Paginacao<PedidosPacienteInvoiceDTO>> listarPedidosPacienteInvoice(
			@RequestParam(name="rmr", required=false) Long rmr,
			@RequestParam(name="nomePaciente", required=false) String nomePaciente,
			@RequestParam(required = true) int pagina,
			@RequestParam(required = true) int quantidadeRegistros) {
		return new ResponseEntity<Paginacao<PedidosPacienteInvoiceDTO>>(pacienteService.listarPedidosPacienteInvoicePorRmrENomePaciente(rmr, nomePaciente, 
				new PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	
	/** 
	 * Serviço que lista os pedidos de exame, IDM e coleta pedentes de pagamento. 
	 * 
	 * @param rmr - rmr do paciente.
	 * @return ResponseEntity<Paginacao<PedidosPacienteInvoiceDTO>>.
	 */
	@GetMapping(value = "invoice/pedidos")
	public ResponseEntity<List<PedidosPacienteInvoiceDTO>> listarPedidosPacienteInvoice(
			@RequestParam(name="rmr", required=false) Long rmr
			, @RequestParam(name="nome", required=false) String nome) {
		return ResponseEntity.ok().body(pacienteService.listarPedidosPacienteInvoicePorRmrENomePaciente(rmr, nome));
	}
	
	/**
	 * Método para obter paciente formatado wmda por rmr.
	 * @param rmr do paciente.
	 * @return PacienteWmdaDTO - DTO do paciente localizado. 
	 */
	@GetMapping("{rmr}/obterpacientewmda")
	public ResponseEntity<PacienteWmdaDTO> obterPacienteDtoWmdaPorRmr(@PathVariable(name="rmr", required = true) Long rmr) {
		
		return new ResponseEntity<PacienteWmdaDTO>(pacienteService.obterPacienteDtoWmdaPorRmr(rmr), HttpStatus.OK);
	}

	/**
	 * Método para atualizar wmdaid do paciente utilizando rmr do paciente.
	 * @param rmr do paciente
	 * @return mensagem do sistema. 
	 */
	@PutMapping(value = "{rmr}/wmda")
	public ResponseEntity<String> atualizarWmdaIdPorRmrDoPaciente(@PathVariable(name="rmr", required = true) Long rmr, 
			@RequestBody(required=true) String idWmda) {
			pacienteService.atualizarWmdaIdPorRmrDoPaciente(rmr, idWmda);
			
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}