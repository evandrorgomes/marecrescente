package br.org.cancer.modred.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.authorization.CustomPermissionEvaluator;
import br.org.cancer.modred.controller.dto.ContatoDTO;
import br.org.cancer.modred.controller.dto.ExameDoadorInternacionalDto;
import br.org.cancer.modred.controller.dto.RetornoInclusaoDTO;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.DoadorNacionalDTO;
import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.controller.view.DoadorView;
import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.controller.view.PrescricaoView;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorInternacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.EvolucaoDoador;
import br.org.cancer.modred.model.ExameCordaoInternacional;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.interfaces.IDoadorHeader;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IEnderecoContatoDoadorService;
import br.org.cancer.modred.service.IEvolucaoDoadorService;
import br.org.cancer.modred.service.IPedidoContatoService;
import br.org.cancer.modred.service.ISegurancaService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.integracao.IIntegracaoDoadorNacionalRedomeWebService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.ConsultaDoadorNacionalVo;

/**
 * Registra as chamadas REST envolvendo a entidade Doador.
 * 
 * @author Pizão.
 *
 */
@RestController
@RequestMapping(value = "/api/doadores", produces = "application/json;charset=UTF-8")
public class DoadorController {

	@Autowired
	private IDoadorService doadorService;
	
	@Autowired
	private IDoadorNacionalService doadorNacionalService;

	@Autowired
	private IIntegracaoDoadorNacionalRedomeWebService integracaoDoadorNacionalService;
	
	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;

	@Autowired
	private ICordaoInternacionalService cordaoInternacionalService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IGenericRepository genericRepository;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IEnderecoContatoDoadorService endContatoDoador;

	@Autowired
	private IPedidoContatoService pedidoContatoService;
	
	@Autowired
	private IEvolucaoDoadorService evolucaoDoadorService;
	
	@Autowired
	private ISegurancaService segurancaService;
	
	/**
	 * Método para atualizar status do doador.
	 * 
	 * @param id identificação do doador.
	 * @param motivoStatusId motivo da atualização.
	 * @param timeRetornoInatividade data (em milisegundos) que irá ficar inativo.
	 * @return mensagem de confirmação.
	 */
	@PreAuthorize("hasPermission('INATIVAR_DOADOR_ENRIQUECIMENTO')"
			+ " || hasPermission('INATIVAR_DOADOR_FASE2')"
			+ " || hasPermission('INATIVAR_DOADOR_AVALIACAO_WORKUP')"
			+ " || hasPermission('INATIVAR_DOADOR_FASE3')")
	@RequestMapping(value = "{id}/inativar", method = RequestMethod.PUT)
	public ResponseEntity<DoadorNacional> inativar(@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name = "motivoStatusId") Long motivoStatusId,
			@RequestParam(name = "timeRetornoInatividade", required = false) Long timeRetornoInatividade) {

		DoadorNacional doadorNacional = new DoadorNacional(doadorNacionalService.inativarDoador(id, motivoStatusId,
				timeRetornoInatividade).getId());
		return new ResponseEntity<DoadorNacional>(doadorNacional, HttpStatus.OK);
	}

	/**
	 * Atualiza novo contato para o doador.
	 * 
	 * @param id identificador do doador.
	 * @param contato novo contato a ser salvo.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "{id}/contatostelefonicos", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('ADICIONAR_CONTATO_TELEFONICO_DOADOR')")
	public ResponseEntity<RetornoInclusaoDTO> adicionarContatoTelefonico(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) ContatoTelefonicoDoador contato) {

		return new ResponseEntity<RetornoInclusaoDTO>(doadorNacionalService.adicionarContatoTelefonico(id, contato),
				HttpStatus.OK);
	}

	/**
	 * Adiciona um novo endereco para o doador.
	 * 
	 * @param id - identificador de doador.
	 * @param enderecoContato - endereço de contato do doador.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('ADICIONAR_ENDERECO_DOADOR')")
	@RequestMapping(value = "{id}/enderecocontato", method = RequestMethod.POST)
	public ResponseEntity<RetornoInclusaoDTO> adicionarEnderecoContato(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EnderecoContatoDoador enderecoContato) {
		return new ResponseEntity<RetornoInclusaoDTO>(doadorNacionalService.adicionarEnderecoContato(id, enderecoContato),
				HttpStatus.OK);
	}

	/**
	 * Adiciona um novo e-mail para o doador.
	 * 
	 * @param id - identificador de doador.
	 * @param email - novo e-mail a ser adicionado para o doador.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('ADICIONAR_EMAIL_DOADOR')")
	@RequestMapping(value = "{id}/emailscontato", method = RequestMethod.POST)
	public ResponseEntity<RetornoInclusaoDTO> adicionarEmailContato(
			@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) EmailContatoDoador email) {
		return new ResponseEntity<RetornoInclusaoDTO>(doadorNacionalService.adicionarEmail(id, email), HttpStatus.OK);
	}

	/**
	 * Atualiza a identificação do doador.
	 * 
	 * @param id identificador do doador.
	 * @param doador informações de identificação atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "{id}/identificacao", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('ATUALIZAR_IDENTIFICACAO_DOADOR')")
	public ResponseEntity<String> atualiarIdentificacao(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DoadorNacional doador) {
		doadorNacionalService.atualizarIdentificacao(id, doador);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Atualiza os dados pessoais do doador.
	 * 
	 * @param id identificador do doador.
	 * @param doador informações dos dados pessoais do doador a serem atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "{id}/dadospessoais", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('ATUALIZAR_DADOS_PESSOAIS_DOADOR')")
	public ResponseEntity<String> atualiarDadosPessoais(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DoadorNacional doador) {
		doadorNacionalService.atualizarDadosPessoais(id, doador);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Método para listar a identificação do doador.
	 * 
	 * @param id do doador
	 * @return objecto doador com apenas os dados de identificação.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/identificacao")
	@PreAuthorize("hasPermission('" + Recurso.VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR + "') || "
			+ "hasPermission('" + Recurso.VISUALIZAR_IDENTIFICACAO_PARCIAL + "')")
	public MappingJacksonValue obterDadosIdentificadaoPorDoador(@PathVariable(name = "id", required = true) Long id) {
		final IDoadorHeader doador = doadorService.obterDadosIdentificadaoPorDoador(id);
		final MappingJacksonValue result = new MappingJacksonValue(doador);

		CustomPermissionEvaluator permissionEvaluator = new CustomPermissionEvaluator(usuarioService, genericRepository);

		if (permissionEvaluator.hasPermission(SecurityContextHolder.getContext()
				.getAuthentication(), doador, "VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR")) {
			result.setSerializationView(DoadorView.IdentificacaoCompleta.class);
		}
		else {
			result.setSerializationView(DoadorView.IdentificacaoParcial.class);
		}

		return result;
	}

	/**
	 * Obtém o doador pelo seu identificador (id).
	 * 
	 * @param id - identificador do doador
	 * @param exibirUltimaFase - booleano se exibe dados da ultima fase do doador.
	 * @return Doador - doador com dados para atualizacao na fase 2.
	 */
	@RequestMapping(value = "{id}/contato", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONTATO_PASSIVO') || hasPermission('CONTACTAR_FASE_2') || "
				+ "hasPermission('CONTACTAR_FASE_3') || hasPermission('TRATAR_PEDIDO_WORKUP') || " 
				+ "hasPermission('TRATAR_PEDIDO_WORKUP_INTERNACIONAL') || hasPermission('AVALIAR_PRESCRICAO') ")
	public MappingJacksonValue obterDoador(
			@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name = "exibirUltimaFase", required = false) boolean exibirUltimaFase) {

		ContatoDTO contatoDto = doadorNacionalService.obterDoadorParaContatoPorDmr(id, exibirUltimaFase);
		final MappingJacksonValue result = new MappingJacksonValue(contatoDto);

		if (exibirUltimaFase) {
			result.setSerializationView(DoadorView.ContatoPassivo.class);
		}
		else {
			result.setSerializationView(DoadorView.AtualizacaoFase2.class);
		}

		return result;
	}

	/**
	 * Obtém a lista de doadores por um dos parametros.
	 * 
	 * @param pagina - pagina atual
	 * @param quantidadeRegistros - qtd de dados na paginação
	 * @param dmr - identificador do doador
	 * @param nome - nome do doador
	 * @param cpf - cpf do doador
	 * @return lista com vo doadores nacionais
	 */
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONTATO_PASSIVO') ")
	public ResponseEntity<Page<ConsultaDoadorNacionalVo>> listarDoadores(
			@RequestParam(name = "pagina", required = true) int pagina,
			@RequestParam(name = "quantidadeRegistros", required = true) int quantidadeRegistros,
			@RequestParam(name = "dmr", required = false) Long dmr,
			@RequestParam(name = "nome", required = false) String nome,
			@RequestParam(name = "cpf", required = false) String cpf) {

		return new ResponseEntity<Page<ConsultaDoadorNacionalVo>>(doadorNacionalService.listarDoadoresNacionaisVo(new PageRequest(
				pagina, quantidadeRegistros), dmr, nome, cpf), HttpStatus.OK);
	}

	/**
	 * Obtém a lista de doadores nacionais por um dos parametros.
	 * 
	 * @param pagina - pagina atual
	 * @param quantidadeRegistros - qtd de dados na paginação
	 * @param dmr - identificador do doador
	 * @param nome - nome do doador
	 * @return lista com doadores nacionais
	 */
	@RequestMapping(value = "nacionais", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CONSULTAR_DOADOR') ")
	public ResponseEntity<Page<DoadorNacional>> listarDoadoresNacionais(
			@RequestParam(name = "pagina", required = true) int pagina,
			@RequestParam(name = "quantidadeRegistros", required = true) int quantidadeRegistros,
			@RequestParam(name = "dmr", required = false) Long dmr,
			@RequestParam(name = "nome", required = false) String nome) {

		return new ResponseEntity<Page<DoadorNacional>>(doadorNacionalService.listarDoadoresNacionais(new PageRequest(
				pagina, quantidadeRegistros), dmr, nome), HttpStatus.OK);
	}
	
	/**
	 * Lista os endereços do doador informado.
	 * 
	 * @param id Identificador do doador.
	 * @return lista de endereços.
	 */
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + 
			"') || hasPermission('" + Recurso.SOLICITAR_FASE_3_NACIONAL  + "') ")
	@RequestMapping(value = "{id}/enderecoscontato", method = RequestMethod.GET)
	public ResponseEntity<List<EnderecoContatoDoador>> listarEnderecosContato(@PathVariable(name = "id",
																							required = true) Long id) {
		return new ResponseEntity<List<EnderecoContatoDoador>>(endContatoDoador.listarEnderecos(id), HttpStatus.OK);
	}

	/**
	 * Metodo para atualizar o status do doador.
	 * 
	 * @param id - Identificador do doador.
	 * @param statusId - identificador do status
	 * @param motivoStatusId - identificador do motivo.
	 * @param timeRetornoInatividade tempo em dias de inatividade
	 * @return ContatoDto com o Doador e a solicitação alterados
	 */
	@PreAuthorize("hasPermission('CONTATO_PASSIVO')||hasPermission('CADASTRAR_DOADOR_INTERNACIONAL')")
	@JsonView(DoadorView.ContatoPassivo.class)
	@RequestMapping(value = "{id}/status/alterar", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> atualizarStatus(@PathVariable(name = "id", required = true) Long id,
			@RequestParam(name = "statusId", required = true) Long statusId,
			@RequestParam(name = "motivoStatusId", required = false) Long motivoStatusId,
			@RequestParam(name = "timeRetornoInatividade", required = false) Long timeRetornoInatividade) {

		ContatoDTO contatoDto = doadorNacionalService.atualizarStatusDoador(id, statusId, motivoStatusId, timeRetornoInatividade);
		if (StatusDoador.ATIVO.equals(contatoDto.getDoador().getStatusDoador().getId()) || StatusDoador.ATIVO_RESSALVA.equals(
				contatoDto.getDoador().getStatusDoador().getId())) {
			return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
					"doador.ativo.sucesso"),"", contatoDto), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<CampoMensagem>(new CampoMensagem(AppUtil.getMensagem(messageSource,
					"doador.inativo.sucesso"),"", contatoDto), HttpStatus.OK);
		}

	}

	/**
	 * Obtém o doador pelo seu identificador (id).
	 * 
	 * @param idDoadorInternacional identificador do doador.
	 * @return Doador - doador com dados para atualizacao na fase 2.
	 */
	@RequestMapping(value = "{id}/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<DoadorCordaoInternacionalDTO> obterDoadorInternacional(@PathVariable(	name = "id",
																			required = true) Long idDoadorInternacional) {
		 DoadorCordaoInternacionalDTO doador = doadorInternacionalService.obterDoadorPorId(idDoadorInternacional);
		return new ResponseEntity<DoadorCordaoInternacionalDTO>(doador, HttpStatus.OK);
	}
	
	
	/**
	 * Obtém o doador internacional pelo seu grid.
	 * 
	 * @param grid identificação internacional do doador.
	 * @return Doador localizado.
	 */
	@RequestMapping(value = "internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<DoadorInternacional> obterDoadorInternacionalPorGrid(@RequestParam(name = "grid",required = true) String grid) {
		return new ResponseEntity<DoadorInternacional>(doadorInternacionalService.obterDoadorPorGrid(grid), HttpStatus.OK);
	}

	@RequestMapping(value = "{id}/exame/internacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<List<ExameDoadorInternacionalDto>> listarExames(
			@PathVariable(name = "id",required = true) Long idDoador) {
		return new ResponseEntity<List<ExameDoadorInternacionalDto>>(doadorInternacionalService.listarExamesDeDoador(idDoador), HttpStatus.OK);
	}

	@RequestMapping(value = "{id}/exame/cordaointernacional", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<List<ExameDoadorInternacionalDto>> listarExamesCordaoInternacional(
			@PathVariable(name = "id",required = true) Long idDoador) {
		return new ResponseEntity<List<ExameDoadorInternacionalDto>>(cordaoInternacionalService.listarExamesCordaoInternacional(idDoador), HttpStatus.OK);
	}
	
	
	/**
	 * Atualiza os dados pessoais do doador internacional.
	 * 
	 * @param id identificador do doador.
	 * @param doador informações dos dados pessoais do doador a serem atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "{id}/dadospessoais/internacional", method = RequestMethod.PUT)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> atualiarDadosPessoaisDeDoadorInternacional(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) DoadorInternacional doador) {
		doadorInternacionalService.atualizarDadosPessoais(id, doador);
		CampoMensagem campoMensagem = new CampoMensagem(AppUtil.getMensagem(messageSource, "doador.internacional.atualizado"));
		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}

	
	
	/**
	 * Inclui um novo exame de doador internacional.
	 * 
	 * @param id identificador do doador.
	 * @param exame a ser incluido.
	 * @return mensagem de confirmação.
	 * @throws Exception caso haja erro na gravação do exame
	 */
	@RequestMapping(value = "{id}/exame/internacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> atualiarDadosPessoaisDeDoadorInternacional(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) ExameDoadorInternacional exame) throws Exception {
		doadorInternacionalService.salvarExame(id, exame);
		CampoMensagem campoMensagem = new CampoMensagem(AppUtil.getMensagem(messageSource, "doador.internacional.exame.incluido"));
		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}

	/**
	 * Inclui um novo exame de doador internacional.
	 * 
	 * @param id identificador do doador.
	 * @param exame a ser incluido.
	 * @return mensagem de confirmação.
	 * @throws Exception caso haja erro na gravação do exame
	 */
	@RequestMapping(value = "{id}/exame/cordaointernacional", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('ALTERAR_DOADOR_INTERNACIONAL')")
	public ResponseEntity<CampoMensagem> atualiarDadosPessoaisDeCordaoInternacional(@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) ExameCordaoInternacional exame) throws Exception {
		cordaoInternacionalService.salvarExameCordaoInternacional(id, exame);
		CampoMensagem campoMensagem = new CampoMensagem(AppUtil.getMensagem(messageSource, "doador.internacional.exame.incluido"));
		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}	
	
	/**
	 * Obtém a lista de doadores internacionais de acordo com os parametros fornecidos.
	 * 
	 * @param pagina - pagina atual
	 * @param quantidadeRegistros - qtd de dados na paginação
	 * @param id - identificador do doador
	 * @param idRegistro - id do registro
	 * @param grid - grid
	 * @return lista com doadores
	 */
	@RequestMapping(value="internacionais", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL') ")
	public ResponseEntity<Page<DoadorInternacional>> listarDoadoresInternacional(
			@RequestParam(name = "pagina", required = true) int pagina,
			@RequestParam(name = "quantidadeRegistros", required = true) int quantidadeRegistros,
			@RequestParam(name = "id", required = false) String id,
			@RequestParam(name = "idRegistro", required = false) Long idRegistro,
			@RequestParam(name = "grid", required = false) String grid) {

		return new ResponseEntity<Page<DoadorInternacional>>(doadorInternacionalService.obterDoadoresInternacional(id, idRegistro, grid, new PageRequest(
				pagina, quantidadeRegistros)), HttpStatus.OK);
	}
	
	/**
	 * Obtém o doador pelo seu identificador (id).
	 * 
	 * @param idDoador identificador do doador
	 * @return doador com os dados selecionados.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(DoadorView.Internacional.class)
	@PreAuthorize("hasPermission('CADASTRAR_DOADOR_INTERNACIONAL') ")
	public ResponseEntity<Doador> obterDoador(
			@PathVariable(	name = "id", required = true) Long idDoador) {
		Doador doador = doadorService.obterDoador(idDoador);
		return new ResponseEntity<Doador>(doador, HttpStatus.OK);
	}
	
	/**
	 * Atualiza os dados pessoais do doador nacional via integracao.
	 * 
	 * @param id identificador do doador.
	 * @param doador informações dos dados pessoais do doador a serem atualizadas.
	 * 
	 * @return mensagem de confirmação.
	 */
	@RequestMapping(value = "integracao", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('INTEGRACAO_REDOME_WEB')")	
	public ResponseEntity<List<MensagemErroIntegracao>> salvarDadosIntegracaoDoadorNacional(
			@RequestBody(required = true) List<DoadorNacionalDTO> doadores) {
		List<MensagemErroIntegracao> mensagens = integracaoDoadorNacionalService.atualizarDadosIntegracaoDoadorNacional(doadores);
		return new ResponseEntity<List<MensagemErroIntegracao>>(mensagens,HttpStatus.OK);
	}
	
	/**
	 * Obtém a data do ultimo pedido de contato.
	 * 
	 * @param id - Identificador do pedido de contato.
	 * @return Data do ultimo pedido de contato.
	 */
	@GetMapping(value="{id}/dataultimopedidocontatofechado")
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	public ResponseEntity<LocalDateTime> obterDataUltimoPedidoContatoFechado(
			@PathVariable(	name = "id", required = true) Long id) {
		
		return ResponseEntity.ok().body(pedidoContatoService.obterDataUltimoPedidoContatoFechado(id));
		
	}
	
	/**
	 * Lista as evoluções do doador em ordem decrescente de data de criação. 
	 * 
	 * @param id Identificador do doador.
	 * @return Lista com as evolucões.
	 */
	@GetMapping(value="{id}/evolucoes")
	@PreAuthorize("hasPermission('" + Recurso.CONTACTAR_FASE_2 + "') || hasPermission('" + Recurso.CONTACTAR_FASE_3 + "') || "
			+ "hasPermission('" + Recurso.CADASTRAR_ANALISE_MEDICA_DOADOR +"')")
	@JsonView(DoadorView.Evolucao.class)
	public ResponseEntity<List<EvolucaoDoador>> listarEvolucoes(@PathVariable(	name = "id", required = true) Long id) {
		return ResponseEntity.ok(evolucaoDoadorService.listarEvolucoesPorDoadorOrdernadoPorDataCriacaoDecrescente(id));
	}

	/**
	 * Finaliza o pedido de contato.
	 * 
	 * @param id - identificador do pedido de contato.
	 * @param pedidoContatoFinalizado - VO para facilitar a passagem de parametros.
	 * @return mensagem de sucesso.
	 */
	@PostMapping(value="{id}/doadorContatoPassivo")
	@PreAuthorize("hasPermission('CONTACTAR_FASE_2') || hasPermission('CONTACTAR_FASE_3')")
	public ResponseEntity<ConsultaDoadorNacionalVo> criarPedidoContatoPassivo(
		@PathVariable(name = "id", required = true) Long idDoador) {
		return ResponseEntity.ok(pedidoContatoService.criarPedidoContatoPassivo(idDoador));
	}
	
	
	/**
	 * Obtém o doador pelo seu identificador (id).
	 * 
	 * @param idDoador identificador do doador
	 * @return doador com os dados selecionados.
	 */
	@GetMapping(value = "{id}/detalheprescricao")
	@JsonView(PrescricaoView.DetalheDoador.class)
	@PreAuthorize("hasPermission('" + Recurso.AVALIAR_PRESCRICAO + "') "
			+ " || hasPermission('" + Recurso.CADASTRAR_PRESCRICAO + "') "
			+ " || hasPermission('" + Recurso.CADASTRAR_FORMULARIO + "') "
			+ " || hasPermission('" + Recurso.INFORMAR_PLANO_WORKUP_NACIONAL + "') "
			+ " || hasPermission('" + Recurso.INFORMAR_PLANO_WORKUP_INTERNACIONAL + "') ")
	public ResponseEntity<Doador> obterDetlaheDoadorParaPrescricao(
			@PathVariable(	name = "id", required = true) Long idDoador) {
		return ResponseEntity.ok(doadorService.obterDoador(idDoador));
	}	
	
	/**
	 * Obtém o doador pelo seu identificador (id).
	 * 
	 * @param idDoador identificador do doador
	 * @return doador com os dados selecionados.
	 */
	@GetMapping(value = "{id}/detalhes")
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_FORMULARIO + "')"
			+ "|| hasPermission('" + Recurso.EFETUAR_LOGISTICA + "')"
			+ "|| hasPermission('" + Recurso.EFETUAR_LOGISTICA_DOADOR_COLETA + "')")
	public MappingJacksonValue obterDetalhesDoador(@PathVariable(name = "id", required = true) Long idDoador) {
		
		Doador doador = doadorService.obterDoador(idDoador);
		
		final MappingJacksonValue result = new MappingJacksonValue(doador);
		
		if (segurancaService.usuarioLogadoPossuiRecurso(doador, Arrays.asList(Recurso.EFETUAR_LOGISTICA,
				Recurso.EFETUAR_LOGISTICA_DOADOR_COLETA))) {
			result.setSerializationView(DoadorView.LogisticaDoador.class);
		}
		else {
			result.setSerializationView(FormularioView.DetalheDoador.class);
		}

		return result;
	}	
}
