package br.org.cancer.modred.controller;

/**
 * Classe controladora responsável por fazer o dispatch das requisições
 * relacionadas às entidades que implementam o motor de processos da plataforma
 * redome.
 * 
 * @author Thiago Moraes
 *
 */
//@RestController
///@RequestMapping(value = "/api/processos", produces = "application/json;charset=UTF-8")
public class ProcessoController {

	/*
	 * @Autowired private IProcessoService processoService;
	 * 
	 * @Autowired private IUsuarioService usuarioService;
	 * 
	 * @Autowired private MessageSource messageSource;
	 * 
	 * private ObjectMapper objectMapper = new ObjectMapper();
	 * 
	 * private static final Logger LOG =
	 * LoggerFactory.getLogger(ProcessoController.class);
	 */
	/**
	 * Método para criar uma nova instância de processo no motor de processos da
	 * plataforma redome.
	 * 
	 * A instância da classe Processo precisa estar com o atributo TipoProcesso
	 * preenchido.
	 * 
	 * @param processo - a instância de processo que será criada no motor de
	 *                 processos da plataforma redome.
	 * 
	 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
	 *         operação.
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST) public
	 * ResponseEntity<CampoMensagem> criarProcesso(@RequestBody(required = true)
	 * Processo processo) {
	 * 
	 * if (processo.getPaciente() != null && ( processo.getPaciente().getRmr() ==
	 * null || processo.getPaciente().getRmr() .longValue() == 0 )) {
	 * processo.setPaciente(null); }
	 * 
	 * if (processo.getTipo() == null) { return new
	 * ResponseEntity<CampoMensagem>(new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "erro.acesso.negado")),
	 * HttpStatus.PRECONDITION_FAILED); }
	 * 
	 * processo.setId(null); processoService.criarProcesso(processo);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "processo.criado.sucesso",
	 * processo.getId()));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 */

	/**
	 * Método para interromper o processamento das tarefas associadas a este
	 * processo dentro motor de processos da plataforma redome.
	 * 
	 * A instância da classe Processo precisa estar com o atributo id preenchido.
	 * 
	 * @param idProcesso - a chave da instância do processo que será paralizado
	 *                   dentro do motor de processos da plataforma redome.
	 *
	 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
	 *         operação.
	 */
	/*
	 * @RequestMapping(value = "{pid}/parar", method = RequestMethod.PUT) public
	 * ResponseEntity<CampoMensagem> pararProcesso(@PathVariable(name = "pid",
	 * required = true) Long idProcesso) {
	 * 
	 * Processo processo = new Processo(); processo.setId(idProcesso);
	 * processoService.pararProcesso(processo);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "processo.parado.sucesso",
	 * processo.getId()));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 * 
	 *//**
		 * Método para retormar o processamento das tarefas de determinado processo que
		 * foi anteriormente interrompido.
		 * 
		 * A instância da classe Processo precisa estar com o atributo id preenchido.
		 * 
		 * @param idProcesso - a chave da instância de processo que será reiniciado
		 *                   dentro do motor de processos da plataforma redome.
		 *
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "{pid}/reiniciar", method = RequestMethod.PUT) public
	 * ResponseEntity<CampoMensagem> reiniciarProcesso(@PathVariable(name = "pid",
	 * required = true) Long idProcesso) { Processo processo = new Processo();
	 * processo.setId(idProcesso);
	 * 
	 * processoService.reiniciarProcesso(processo);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "processo.reiniciar.sucesso",
	 * processo.getId()));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 * 
	 *//**
		 * Método para encerrar um processo dentro motor de processos da plataforma
		 * redome.
		 * 
		 * Este é um estado final e um processo encerrado não poderá ter seu estado
		 * alterado posteriormente.
		 * 
		 * A instância da classe Processo precisa estar com o atributo id preenchido.
		 * 
		 * @param idProcesso - a chave da instância de processo que será encerrado
		 *                   dentro do motor de processos da plataforma redome.
		 *
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "{pid}/terminar", method = RequestMethod.PUT) public
	 * ResponseEntity<CampoMensagem> terminarProcesso(@PathVariable(name = "pid",
	 * required = true) Long idProcesso) {
	 * 
	 * Processo processo = new Processo(); processo.setId(idProcesso);
	 * 
	 * processoService.terminarProcesso(processo);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "processo.terminar.sucesso",
	 * processo.getId()));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 * 
	 *//**
		 * Método para criar uma nova tarefa vinculada a um processo de negócio da
		 * plataforma redome.
		 * 
		 * A instância da classe tarefa precisa estar com os atributos TipoTarefa,
		 * Processo e Perfil preenchidos.
		 * 
		 * @param tarefa - a tarefa que será criada.
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "criarTarefa", method = RequestMethod.POST) public
	 * ResponseEntity<CampoMensagem> criarTarefa(@RequestBody(required = true)
	 * TarefaDTO tarefa) {
	 * 
	 * if (tarefa.getTipoTarefa() == null || tarefa.getProcesso() == null ||
	 * tarefa.getProcesso().getId() == null ||
	 * tarefa.getProcesso().getId().longValue() == 0 ||
	 * tarefa.getPerfilResponsavel() == null ||
	 * tarefa.getPerfilResponsavel().getId() == null || tarefa
	 * .getPerfilResponsavel().getId().longValue() == 0) {
	 * 
	 * return new ResponseEntity<CampoMensagem>(new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "erro.acesso.negado")),
	 * HttpStatus.PRECONDITION_FAILED);
	 * 
	 * }
	 * 
	 * tarefa.setId(null);
	 * 
	 * if (tarefa.getUsuarioResponsavel() != null && (
	 * tarefa.getUsuarioResponsavel().getId() == null || tarefa
	 * .getUsuarioResponsavel().getId().longValue() == 0 )) {
	 * tarefa.setUsuarioResponsavel(null); }
	 * 
	 * processoService.criarTarefa(tarefa);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "tarefa.criar.sucesso", tarefa.getId(),
	 * tarefa.getProcesso().getId()));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 * 
	 *//**
		 * Método para atribuir um usuário ou um centro de transplante como responsável
		 * por executar a tarefa.
		 * 
		 * As identificações usuarioId e centroTransplanteId são mutuamente exclusivas e
		 * não devem ser passada como parâmetro simultamente.
		 * 
		 * @param idProcesso            - identificação do processo.
		 * @param idTarefa              - identificação da tarefa.
		 * @param usuarioId             - identificação do usuário responsável
		 * @param centroTransplanteId   - identificação do centro de transplante
		 *                              responsável
		 * @param atribuirUsuarioLogado - força a atribuição da tarefa para o usuário
		 *                              logado
		 * 
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "{pid}/tarefas/{tid}/atribuir", method =
	 * RequestMethod.PUT) public ResponseEntity<CampoMensagem>
	 * atribuirTarefa(@PathVariable(name = "pid", required = true) Long idProcesso,
	 * 
	 * @PathVariable(name = "tid", required = true) Long idTarefa,
	 * 
	 * @RequestParam(name = "usuarioId", required = false) Long usuarioId,
	 * 
	 * @RequestParam(name = "centroId", required = false) Long centroTransplanteId,
	 * 
	 * @RequestParam(name = "atribuirUsuarioLogado", required = false) Boolean
	 * atribuirUsuarioLogado) {
	 * 
	 * Processo processo = new Processo(); processo.setId(idProcesso);
	 * 
	 * TarefaDTO tarefa = processoService.obterTarefa(idTarefa); if (usuarioId != null
	 * && usuarioId.longValue() > 0) { Usuario usuarioResponsavel = new Usuario();
	 * usuarioResponsavel.setId(usuarioId); processoService.atribuirTarefa(tarefa,
	 * usuarioResponsavel); final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "tarefa.atribuir.medico.sucesso",
	 * tarefa.getId(), tarefa.getProcesso().getId(), usuarioResponsavel .getId()));
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK); } else {
	 * if (centroTransplanteId != null && centroTransplanteId.longValue() > 0) {
	 * processoService.atribuirTarefa(tarefa, centroTransplanteId); final
	 * CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource,
	 * "tarefa.atribuir.centro.transplante.sucesso", tarefa.getId(),
	 * tarefa.getProcesso().getId(), centroTransplanteId)); return new
	 * ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK); } else { if
	 * (atribuirUsuarioLogado == null || atribuirUsuarioLogado == false) {
	 * processoService.atribuirTarefa(tarefa); } else {
	 * processoService.atribuirTarefaUsuarioLogado(tarefa); } final CampoMensagem
	 * mensagem = new CampoMensagem("", AppUtil.getMensagem(messageSource,
	 * "tarefa.atribuir.medico.sucesso", tarefa.getId(),
	 * tarefa.getProcesso().getId())); return new
	 * ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK); } }
	 * 
	 * }
	 * 
	 *//**
		 * Serviço para desatribuir uma tarefa de um usuario.
		 * 
		 * @param idTarefa - identificação da tarefa.
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "tarefas/{tid}/cancelar", method = RequestMethod.PUT)
	 * public ResponseEntity<CampoMensagem> cancelarTarefa(@PathVariable(name =
	 * "tid", required = true) Long idTarefa) {
	 * 
	 * processoService.removerAtribuicaoTarefa(idTarefa);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "tarefa.cancelar.usuario.sucesso",
	 * idTarefa, "")); return new ResponseEntity<CampoMensagem>(mensagem,
	 * HttpStatus.OK); }
	 * 
	 *//**
		 * Método para sinalizar ao motor de processos da plataforma o encerramento de
		 * uma data tarefa.
		 * 
		 * A instância da classe tarefa precisa estar com o atributo id preenchido.
		 * 
		 * @param idProcesso - identificação do processo.
		 * @param idTarefa   - a tarefa que será encerrada.
		 * 
		 * @return ResponseEntity<CampoMensagem> - mensagem sobre o resultado da
		 *         operação.
		 */
	/*
	 * @RequestMapping(value = "{pid}/tarefa/{tid}/fechar", method =
	 * RequestMethod.PUT) public ResponseEntity<CampoMensagem>
	 * fecharTarefa(@PathVariable(name = "pid", required = true) Long idProcesso,
	 * 
	 * @PathVariable(name = "tid", required = true) Long idTarefa) {
	 * 
	 * Processo processo = new Processo(); processo.setId(idProcesso);
	 * 
	 * TarefaDTO tarefa = new TarefaDTO(); tarefa.setProcesso(processo);
	 * tarefa.setId(idTarefa);
	 * 
	 * processoService.fecharTarefa(tarefa);
	 * 
	 * final CampoMensagem mensagem = new CampoMensagem("",
	 * AppUtil.getMensagem(messageSource, "tarefa.fechar.sucesso"));
	 * 
	 * return new ResponseEntity<CampoMensagem>(mensagem, HttpStatus.OK);
	 * 
	 * }
	 * 
	 *//**
		 * Método para recuperar o conjuto de processos disponíveis no motor de
		 * processos.
		 * 
		 * Caso o tipo do processo seja informado como null, então, todos os processos
		 * serão retornadas independente do seu tipo.
		 * 
		 * Caso o statusProcesso seja informado como null, então, todas os processos
		 * serão retornadas independente do status atual.
		 * 
		 * @param rmr                 - identificação do paciente associado ao processo
		 *                            (informação opcional)
		 * @param tipoProcesso        - identificação do tipo de processos (informação
		 *                            opcional)
		 * @param statusProcesso      - situação atual do processo (informação opcional)
		 * @param pagina              - informações sobre paginação do conjunto que será
		 *                            retornado (informação opcional)
		 * @param quantidadeRegistros - informações sobre quantidade de registros
		 *                            retornados na paginação (informação opcional)
		 * 
		 * @return Page<Processo> - um conjunto de processos conforme os parâmetros
		 *         informados no acionamento deste método.
		 */
	/*
	 * @RequestMapping(method = RequestMethod.GET)
	 * 
	 * @JsonView(ProcessoView.Listar.class) public ResponseEntity<Page<Processo>>
	 * listarProcessos(
	 * 
	 * @RequestParam(required = false) Long rmr,
	 * 
	 * @RequestParam(required = false) Long tipoProcesso,
	 * 
	 * @RequestParam(required = false) Long statusProcesso,
	 * 
	 * @RequestParam(required = true) int pagina,
	 * 
	 * @RequestParam(required = true) int quantidadeRegistros) {
	 * 
	 * Paciente paciente = null; TipoProcesso tipo = null; StatusProcesso status =
	 * null;
	 * 
	 * if (rmr != null) { paciente = new Paciente(); paciente.setRmr(rmr); }
	 * 
	 * if (tipoProcesso != null) { tipo = TipoProcesso.valueOf(tipoProcesso); if
	 * (tipo == null) { return new ResponseEntity<Page<Processo>>(new
	 * ProcessoJsonPage<>(new ArrayList<Processo>(), new PageRequest( pagina,
	 * quantidadeRegistros), 1), HttpStatus.PRECONDITION_FAILED); } } if
	 * (statusProcesso != null) { status = StatusProcesso.valueOf(statusProcesso);
	 * if (status == null) { return new ResponseEntity<Page<Processo>>(new
	 * ProcessoJsonPage<>(new ArrayList<Processo>(), new PageRequest( pagina,
	 * quantidadeRegistros), 1), HttpStatus.PRECONDITION_FAILED); } }
	 * 
	 * return new
	 * ResponseEntity<Page<Processo>>(processoService.listarProcessos(paciente,
	 * tipo, status, new PageRequest( pagina, quantidadeRegistros)), HttpStatus.OK);
	 * }
	 * 
	 *//**
		 * Método para recuperar o conjuto de tarefas disponíveis no motor de processos
		 * e que são de responsabilidade de um determinado perfil, usuário ou centro de
		 * transplante.
		 * 
		 * Caso o tipo da tarefa seja informado como null, então, todas as tarefas serão
		 * retornadas independente do seu tipo.
		 * 
		 * Caso o statusTarefa seja informado como null, então, todas as tarefas serão
		 * retornadas independente do status atual de cada tarefa.
		 * 
		 * Caso o statusProcesso seja informado como null, então, todas as tarefas serão
		 * retornadas independente do status atual do processo.
		 * 
		 * Caso seja enviado o perfilPorUsuarioLogado, será sempre buscado por todos os
		 * perfis do usuário logado, ignorando o parametro (perfilResponsavel) se for
		 * enviado.
		 * 
		 * Caso seja enviado o centroTransplantePorUsuarioLogado, será sempre buscado
		 * por todos os centros do usuário logado, ignorando o parametro
		 * (centroTransplanteId) se for enviado.
		 * 
		 * 
		 * @param perfilResponsavel        - perfil do responsável pelas tarefas
		 *                                 (informação opcional)
		 * @param usuarioId                - identificação do usuário responsável pelas
		 *                                 tarefas
		 * @param parceiroId               - identificação do parceiro responsável pelas
		 *                                 tarefas
		 * @param tipoTarefa               - identificação do tipo da tarefa (informação
		 *                                 opcional)
		 * @param statusTarefa             - situação atual das tarefas (informação
		 *                                 opcional)
		 * @param statusProcesso           - situação atual do processo (informação
		 *                                 opcional)
		 * @param inclusivoExclusivo       - utilizado para visualizar tarefas agendadas
		 *                                 (informação opcional)
		 * @param pagina                   - informações sobre paginação do conjunto que
		 *                                 será retornado (informação opcional)
		 * @param quantidadeRegistros      - informações sobre quantidade de registros
		 *                                 retornados na paginação (informação opcional)
		 * @param buscaPorUsuarioLogado    - informa se deve buscar a tarefa pelo id do
		 *                                 usuario logado (informação opcional).
		 * @param atributoOrdenacaoDTO     - dto com atributos a serem ordenados
		 * @param ordenacao                - tipo de ordenação a ser usada, se a default
		 *                                 genérica ou alguma customizada por entidade.
		 * @param perfilPorUsuarioLogado   - informa que quer buscar por perfis do
		 *                                 usuario logado.
		 * @param parceiroPorUsuarioLogado - informa que quer buscar por parceiros do
		 *                                 usuario logado.
		 * @param rmr                      - identificador do paciente
		 * @return MappingJacksonValue - um conjunto de tarefas conforme os parâmetros
		 *         informados no acionamento deste método.
		 * @throws IOException - Lança exceção qndo ocorre erro no parse do dto
		 */
	/*
	 * @RequestMapping(value = "tarefas", method = RequestMethod.GET) public
	 * MappingJacksonValue listarTarefas(
	 * 
	 * @RequestParam(name = "perfilId", required = false) Long perfilResponsavel,
	 * 
	 * @RequestParam(name = "usuarioId", required = false) Long usuarioId,
	 * 
	 * @RequestParam(name = "parceiroId", required = false) Long parceiroId,
	 * 
	 * @RequestParam(required = true) Long tipoTarefa,
	 * 
	 * @RequestParam(required = false) Long statusTarefa,
	 * 
	 * @RequestParam(required = false) Long statusProcesso,
	 * 
	 * @RequestParam(required = false) Boolean inclusivoExclusivo,
	 * 
	 * @RequestParam(required = true) int pagina,
	 * 
	 * @RequestParam(required = true) int quantidadeRegistros,
	 * 
	 * @RequestParam(required = false) Boolean buscaPorUsuarioLogado,
	 * 
	 * @RequestParam(required = false) String atributoOrdenacaoDTO,
	 * 
	 * @RequestParam(required = false) Integer ordenacao,
	 * 
	 * @RequestParam(required = false) Boolean perfilPorUsuarioLogado,
	 * 
	 * @RequestParam(required = false) Boolean parceiroPorUsuarioLogado,
	 * 
	 * @RequestParam(required = false) Long rmr) throws IOException {
	 * 
	 * Perfil perfil = null; List<Perfil> perfis = null; List<Long> parceiros =
	 * null; TipoTarefa tipo = null; StatusProcesso statusProcessoEnum = null;
	 * List<StatusTarefa> statusTarefas = null; Usuario usuarioResponsavel = null;
	 * //CentroTransplante centroTransplante = null; MappingJacksonValue result =
	 * null; AtributosOrdenacaoDTO atributosOrdenacaoDTO = null; if
	 * (atributoOrdenacaoDTO != null) { atributosOrdenacaoDTO =
	 * getAtributosOrdenacaoDTOFromJson(atributoOrdenacaoDTO); }
	 * 
	 * if (perfilResponsavel != null) { perfil = new Perfil();
	 * perfil.setId(perfilResponsavel); perfis = new ArrayList<Perfil>();
	 * perfis.add(perfil); } if (tipoTarefa != null) { tipo = new
	 * TipoTarefa(tipoTarefa); }
	 * 
	 * if (statusTarefa != null) { StatusTarefa statusTarefaEnum =
	 * StatusTarefa.valueOf(statusTarefa); if (statusTarefaEnum == null) { result =
	 * new MappingJacksonValue(new TarefaJsonPage<>(new ArrayList<TarefaDTO>(), new
	 * PageRequest(pagina, quantidadeRegistros), 1)); } else{ statusTarefas = new
	 * ArrayList<>(); statusTarefas.add(statusTarefaEnum); } }
	 * 
	 * if (statusProcesso != null) { statusProcessoEnum =
	 * StatusProcesso.valueOf(statusProcesso); if (statusProcessoEnum == null) {
	 * result = new MappingJacksonValue(new TarefaJsonPage<>(new
	 * ArrayList<TarefaDTO>(), new PageRequest(pagina, quantidadeRegistros), 1)); } }
	 * 
	 * if (usuarioId != null && usuarioId.longValue() > 0) { usuarioResponsavel =
	 * new Usuario(); usuarioResponsavel.setId(usuarioId); }
	 * 
	 * if (parceiroId != null && parceiroId.longValue() > 0) { parceiros = new
	 * ArrayList<Long>(); parceiros.add(parceiroId); }
	 * 
	 * if (buscaPorUsuarioLogado != null && buscaPorUsuarioLogado) {
	 * //usuarioResponsavel = usuarioService.obterUsuarioLogado(); if (statusTarefas
	 * == null) { statusTarefas = Arrays.asList(StatusTarefa.values()); } else {
	 * Boolean existeStatusAtribuida = statusTarefas.stream().anyMatch(status ->
	 * StatusTarefa.ATRIBUIDA.getId().equals(status.getId()) ); if
	 * (!existeStatusAtribuida) { statusTarefas.add(StatusTarefa.ATRIBUIDA); } }
	 * 
	 * }
	 * 
	 * if (perfilPorUsuarioLogado != null && perfilPorUsuarioLogado) { perfis =
	 * usuarioService.obterUsuarioLogado().getPerfis(); }
	 * 
	 * if (parceiroPorUsuarioLogado != null && parceiroPorUsuarioLogado) { if
	 * (tipo.getConfiguracao().getParceiro() != null) { parceiros =
	 * usuarioService.obterUsuarioLogado().listarIdParceiros(tipo.getConfiguracao().
	 * getParceiro()); } else {
	 * LOG.error("Não foi encontrado nenhum parceiro configurado na tarefa. id: " +
	 * tipo.getId()); throw new BusinessException("erro.requisicao"); } }
	 * 
	 * result = new MappingJacksonValue(processoService.listarTarefas(perfis,
	 * usuarioResponsavel, parceiros, tipo, statusTarefas, statusProcessoEnum, null,
	 * new PageRequest(pagina, quantidadeRegistros), atributosOrdenacaoDTO == null ?
	 * null : atributosOrdenacaoDTO.getAtributos(), ordenacao, rmr));
	 * 
	 * TiposTarefa tipoTarefaObj = TiposTarefa.valueOf(tipo.getId());
	 * 
	 * if (tipoTarefaObj.getJsonView() != null) {
	 * result.setSerializationView(tipoTarefaObj.getJsonView()); } else {
	 * result.setSerializationView(TarefaView.Listar.class); } return result; }
	 * 
	 *//**
		 * Método para recuperar o conjuto de tarefas de um dado processo que estão
		 * disponíveis no motor de processos e que são de responsabilidade de um
		 * determinado perfil, usuário ou centro de transplante.
		 * 
		 * Caso o tipo da tarefa seja informado como null, então, todas as tarefas serão
		 * retornadas independente do seu tipo.
		 * 
		 * Caso o statusTarefa seja informado como null, então, todas as tarefas serão
		 * retornadas independente do status atual de cada tarefa.
		 * 
		 * Caso o statusProcesso seja informado como null, então, todas as tarefas serão
		 * retornadas independente do status atual do processo.
		 * 
		 * @param processoId          - identificação do processo (informação opcional)
		 * @param perfilResponsavel   - perfil do responsável pelas tarefas (informação
		 *                            opcional)
		 * @param usuarioId           - identificação do usuário responsável pelas
		 *                            tarefas
		 * @param centroTransplanteId - identificação do centro de transplante
		 *                            responsável pelas tarefas
		 * @param tipoTarefa          - identificação do tipo da tarefa (informação
		 *                            opcional)
		 * @param statusTarefa        - situação atual das tarefas (informação opcional)
		 * @param inclusivoExclusivo  - utilizado para visualizar tarefas agendadas
		 *                            (informação opcional)
		 * @param pagina              - informações sobre paginação do conjunto que será
		 *                            retornado (informação opcional)
		 * @param quantidadeRegistros - informações sobre quantidade de registros
		 *                            retornados na paginação (informação opcional)
		 * 
		 * @return Page<TarefaDTO> - um conjunto de tarefas conforme os parâmetros
		 *         informados no acionamento deste método.
		 */
	/*
	 * @RequestMapping(value = "{pid}/tarefas", method = RequestMethod.GET)
	 * 
	 * @JsonView(TarefaView.Listar.class) public ResponseEntity<Page<TarefaDTO>>
	 * listarTarefasDoProcesso(
	 * 
	 * @PathVariable(name = "pid", required = false) Long processoId,
	 * 
	 * @RequestParam(name = "perfilId", required = false) Long perfilResponsavel,
	 * 
	 * @RequestParam(name = "usuarioId", required = false) Long usuarioId,
	 * 
	 * @RequestParam(name = "centroId", required = false) Long centroTransplanteId,
	 * 
	 * @RequestParam(required = false) Long tipoTarefa,
	 * 
	 * @RequestParam(required = false) Long statusTarefa,
	 * 
	 * @RequestParam(required = false) Boolean inclusivoExclusivo,
	 * 
	 * @RequestParam(required = true) int pagina,
	 * 
	 * @RequestParam(required = true) int quantidadeRegistros) {
	 * 
	 * Perfil perfil = null; TipoTarefa tipo = null; StatusTarefa statusTarefaEnum =
	 * null;
	 * 
	 * if (perfilResponsavel != null) { perfil = new Perfil();
	 * perfil.setId(perfilResponsavel); }
	 * 
	 * if (tipoTarefa != null) { tipo = new TipoTarefa(tipoTarefa); }
	 * 
	 * if (statusTarefa != null) { statusTarefaEnum =
	 * StatusTarefa.valueOf(statusTarefa); if (statusTarefaEnum == null) { return
	 * new ResponseEntity<Page<TarefaDTO>>(new TarefaJsonPage<>(new
	 * ArrayList<TarefaDTO>(), new PageRequest(pagina, quantidadeRegistros), 1),
	 * HttpStatus.PRECONDITION_FAILED); } }
	 * 
	 * if (usuarioId != null && usuarioId.longValue() > 0) { Usuario
	 * usuarioResponsavel = new Usuario(); usuarioResponsavel.setId(usuarioId);
	 * return new ResponseEntity<Page<TarefaDTO>>(processoService.listarTarefas(
	 * usuarioResponsavel, tipo, statusTarefaEnum, null, processoId,
	 * inclusivoExclusivo, new PageRequest(pagina, quantidadeRegistros)),
	 * HttpStatus.OK); } else if (centroTransplanteId != null &&
	 * centroTransplanteId.longValue() > 0) { CentroTransplante centroTransplante =
	 * new CentroTransplante(); centroTransplante.setId(centroTransplanteId); return
	 * new
	 * ResponseEntity<Page<TarefaDTO>>(processoService.listarTarefas(centroTransplante,
	 * tipo, statusTarefaEnum, null, processoId, inclusivoExclusivo, new
	 * PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK); } else { return
	 * new ResponseEntity<Page<TarefaDTO>>(processoService.listarTarefas(perfil, tipo,
	 * statusTarefaEnum, null, processoId, inclusivoExclusivo, new
	 * PageRequest(pagina, quantidadeRegistros)), HttpStatus.OK); } }
	 * 
	 *//**
		 * Método para atribuir a primeira tarefa que satisfaça os parametros e
		 * recuperar a mesma no motor de processos e que são de responsabilidade de um
		 * determinado perfil, usuário ou centro de transplante. *
		 * 
		 * @param perfilResponsavel - perfil do responsável pelas tarefas (informação
		 *                          obrigatória)
		 * @param tipoTarefaId      - tipo de tarefa
		 * 
		 * @return <? extends TarefaBase> - uma tarefa conforme os parâmetros informados
		 *         no acionamento deste método.
		 */
	/*
	 * @RequestMapping(value = "tarefas/primeira/atribuir", method =
	 * RequestMethod.PUT) public MappingJacksonValue atribuirPrimeiraTarefaFila(
	 * 
	 * @RequestParam(name = "perfilId", required = true) Long perfilResponsavel,
	 * 
	 * @RequestParam(name = "tipoTarefaId", required = false) Long tipoTarefaId) {
	 * 
	 * MappingJacksonValue result = null; Perfil perfil = null;
	 * 
	 * if (perfilResponsavel != null) { perfil = new Perfil();
	 * perfil.setId(perfilResponsavel);
	 * 
	 * TarefaBase tarefa = processoService.atribuirPrimeiraTarefaFila(perfil,
	 * tipoTarefaId); TiposTarefa tipo =
	 * TiposTarefa.valueOf(tarefa.getTipoTarefa().getId());
	 * 
	 * result = new MappingJacksonValue(tarefa);
	 * 
	 * if (tipo.getJsonView() != null) {
	 * result.setSerializationView(tipo.getJsonView()); } else {
	 * result.setSerializationView(TarefaView.Consultar.class); }
	 * 
	 * }
	 * 
	 * return result;
	 * 
	 * }
	 * 
	 * 
	 * private AtributosOrdenacaoDTO getAtributosOrdenacaoDTOFromJson(final String
	 * jsonAtributosOrdenacaoDTO) throws IOException { final SimpleDateFormat
	 * simpleDateFormat = new SimpleDateFormat(( "dd/MM/yyyy" )); return
	 * objectMapper.setDateFormat(simpleDateFormat).readValue(
	 * jsonAtributosOrdenacaoDTO, AtributosOrdenacaoDTO.class); }
	 * 
	 *//**
		 * Atribui para o usuário logado a tarefa informada.
		 * 
		 * @param idTarefa ID da tarefa a ser atribuída.
		 * @return tarefa serializada.
		 *//*
			 * @RequestMapping(value = "tarefas/{tid}/atribuir", method = RequestMethod.PUT)
			 * public MappingJacksonValue atribuirTarefaParaUsuarioLogado(@PathVariable(name
			 * = "tid", required = true) Long idTarefa) { TarefaDTO tarefaAtribuida =
			 * processoService.atribuirTarefaUsuarioLogado(idTarefa); MappingJacksonValue
			 * result = new MappingJacksonValue(tarefaAtribuida); if
			 * (tarefaAtribuida.getTipoTarefa().getConfiguracao().getJsonView() != null) {
			 * result.setSerializationView(tarefaAtribuida.getTipoTarefa().getConfiguracao()
			 * .getJsonView()); } else {
			 * result.setSerializationView(TarefaView.Listar.class); } return result; }
			 */

}
