package br.org.cancer.redome.workup.controller;

public class WorkupControllerTest {//extends BaseConfigurationTest {

////	@BeforeClass
//	public static void setupClass() {
//		makeAuthotization.paraUsuario(9L, "ANALISTA_WORKUP")
//			.addPerfil("ANALISTA_WORKUP")
//			.addRecurso("TRATAR_PEDIDO_WORKUP");
//	}
//
////	@Before
//	public void setup() {
//		MockitoAnnotations.initMocks(this);
//	}
//
////	@Test
//	public void deveListarTarefaWorkupComSucesso() throws Exception {
//
//		SolicitacaoDTO solicitacao = criarSolicitacao(4L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
//				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
//				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
//				FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR);
//
//		makeStubForGet("/redome/api/solicitacoes/4", okForContentType("application/json;charset=UTF-8",
//				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
//
//		TarefaDTO tarefaWorkup = TarefaDTO.builder()
//				.id(1L)
//				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.getId()))
//				.relacaoEntidade(1L)
//				.build();
//
//		makeStubForGet(
//				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6WzldLCJpZFVzdWFyaW9SZXNwb25zYXZlbCI6bnVsbCwicGFyY2Vpcm9zIjpudWxsLCJpZHNUaXBvc1RhcmVmYSI6WzExM10sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6bnVsbCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6bnVsbCwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
//				okForContentType("application/json;charset=UTF-8",
//						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
//
//		MedicoDTO medico = MedicoDTO.builder().id(1L).nome("Medico do centro").build();
//
//		makeStubForGet("/redome/api/medicos/1", okForContentType("application/json;charset=UTF-8",
//				BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
//
//		mockMvc.perform(CreateMockHttpServletRequest.makeGet("/api/workup/tarefas").param("pagina", "0")
//				.param("quantidadeRegistros", "10").contentType(MediaType.APPLICATION_JSON_UTF8))
//				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].idTarefa", is(1)));
//	}
//	
////	@Test
//	public void deveListarSolicitacaoWorkupComSucesso() throws Exception {
//
//		SolicitacaoDTO solicitacao = criarSolicitacao(4L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
//				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
//				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
//				FasesWorkup.AGUARDANDO_PLANO_WORKUP);
//		
//		SolicitacaoDTO solicitacao2 = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
//				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
//				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
//				FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR);
//		
//		makeStubForGet("/redome/api/solicitacoes?tiposSolicitacao=8%2C10&statusSolicitacao=1",
//				okForContentType("application/json;charset=UTF-8",
//						BaseConfigurationTest.getObjectMapper().writeValueAsString(Arrays.asList(solicitacao, solicitacao2))));
//
//		TarefaDTO tarefaWorkup = TarefaDTO.builder()
//				.id(1L)
//				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.CADASTRAR_FORMULARIO_DOADOR.getId()))
//				.relacaoEntidade(2L)
//				.build();
//
//		makeStubForGet(
//				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6WzldLCJpZFVzdWFyaW9SZXNwb25zYXZlbCI6bnVsbCwicGFyY2Vpcm9zIjpudWxsLCJpZHNUaXBvc1RhcmVmYSI6WzExM10sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6bnVsbCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6bnVsbCwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
//				okForContentType("application/json;charset=UTF-8",
//						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
//
//		MedicoDTO medico = MedicoDTO.builder().id(1L).nome("Medico do centro").build();
//
//		makeStubForGet("/redome/api/medicos/1", okForContentType("application/json;charset=UTF-8",
//				BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
//
//		mockMvc.perform(CreateMockHttpServletRequest.makeGet("/api/workup/solicitacoes").param("pagina", "0")
//				.param("quantidadeRegistros", "10").contentType(MediaType.APPLICATION_JSON_UTF8))				
//				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].idPrescricao", is(7)));
//	}
//	
//	
//	
//	
//
//	private SolicitacaoWorkupDTO criarSolicitacao(Long id, TiposSolicitacao tipo, UsuarioDTO usuario, MatchDTO match,
//			UsuarioDTO usuarioResponsavel, FasesWorkup faseWorkup) {
//		return SolicitacaoWorkupDTO.builder().id(id).status(0)
//				.tipoSolicitacao(TipoSolicitacaoDTO.builder().id(tipo.getId()).build()).usuario(usuario).match(match)
//				.usuarioResponsavel(usuarioResponsavel).faseWorkup(faseWorkup.getId()).build();
//	}
//
//	private MatchDTO criarMatch(Long idMatch, Long idDoador, String identificacaoDoador, BuscaDTO busca) {
//		return MatchDTO.builder().id(idMatch)
//				.doador(DoadorDTO.builder().id(idDoador)
//				.Identificacao(identificacaoDoador)
//				.registroOrigem(RegistroDTO.builder().id(1L).nome("REDOME").build())
//				.build())
//				.busca(busca).build();
//	}
//
//	private BuscaDTO criarBusca(Long idCentroTransplante, String nomeCentroTransplante, Long rmr) {
//		return BuscaDTO.builder()
//				.centroTransplante(
//						CentroTransplanteDTO.builder().id(idCentroTransplante).nome(nomeCentroTransplante).build())
//				.paciente(PacienteDTO.builder().rmr(rmr).build()).build();
//	}
}
