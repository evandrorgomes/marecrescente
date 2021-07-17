package br.org.cancer.modred.test.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.ConfiguracaoTipoTarefa;
import br.org.cancer.modred.helper.IAtribuirTarefa;
import br.org.cancer.modred.helper.ICriarTarefa;
import br.org.cancer.modred.helper.IFecharTarefa;
import br.org.cancer.modred.helper.IListarTarefa;
import br.org.cancer.modred.helper.IObterTarefa;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.CriacaoAuditavelListener;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.notificacao.ConfiguracaoCategoriaNotificacao;
import br.org.cancer.modred.notificacao.IContarNotificacao;
import br.org.cancer.modred.notificacao.ICriarNotificacao;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IGenotipoPacienteService;
import br.org.cancer.modred.service.IMedicoService;
import br.org.cancer.modred.service.IRascunhoService;
import br.org.cancer.modred.service.IStorageService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.PacienteService;

public class MockService {
	
	public static IUsuarioService criarMockUsuarioService(Long idUsuario, List<Perfil> perfis, List<CentroTransplante> centrosTransplantes) {
		IUsuarioService mockUsuarioService = Mockito.mock(IUsuarioService.class);
		
		Mockito.when(mockUsuarioService.obterUsuarioLogado()).then(new UsuarioAnswer(idUsuario, perfis, centrosTransplantes));
		
		Mockito.when(mockUsuarioService.obterUsuario(Mockito.anyLong())).then(new UsuarioAnswer(idUsuario, perfis, centrosTransplantes));
		
		return mockUsuarioService;
	}
	
	public static IMedicoService criarMockMedicoService(Long idMedico,  Long idUsuario ) {
		IMedicoService mockMedicoService = Mockito.mock(IMedicoService.class);
		
		Mockito.when(mockMedicoService.obterMedicoPorUsuario(Mockito.anyLong())).then(new MedicoAnswer(idMedico, idUsuario));
		
		Mockito.when(mockMedicoService.findById(Mockito.anyLong())).then(new MedicoAnswer(idMedico, idUsuario));
		
		
		return mockMedicoService;
	}
	
	public static IRascunhoService criarMockRascunhoService(Long idUsuario) {
		IRascunhoService mockRascunhoService = Mockito.mock(IRascunhoService.class);
		
		Mockito.doNothing().when(mockRascunhoService).excluirPorIdDeUsuario(idUsuario == null ? Mockito.anyLong(): idUsuario);
		
		return mockRascunhoService;
	}
	
	public static void criarMockCriarTarefa(TiposTarefa tipoTarefa, TarefaDTO tarefa) {
		
		final ICriarTarefa criarTarefa = Mockito.mock(ICriarTarefa.class);
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setCriarTarefa(criarTarefa);
		
		Mockito.when(criarTarefa.apply()).thenReturn(tarefa);
		
		
		Mockito.when(criarTarefa.agendada()).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comDataFinal(Mockito.any(LocalDateTime.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comDataInicio(Mockito.any(LocalDateTime.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comDescricao(Mockito.anyString())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comDoadorId(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comHoraFim(Mockito.any(LocalDateTime.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comHoraInicio(Mockito.any(LocalDateTime.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comObjetoRelacionado(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comOUsuarioParaAgendamento(Mockito.any(Usuario.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comParceiro(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comProcessoId(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comRmr(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comStatus(Mockito.any(StatusTarefa.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comTarefaPai(Mockito.any(TarefaDTO.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comUltimoUsuarioResponsavel(Mockito.anyLong())).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.comUsuario(Mockito.any(Usuario.class))).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.exclusive()).thenReturn(criarTarefa);
		Mockito.when(criarTarefa.inclusive()).thenReturn(criarTarefa);
		
		
		/*
		 * //final IProcessoFeign processoFeign = Mockito.mock(IProcessoFeign.class);
		 * final ITarefaFeign tarefaFeign = Mockito.mock(ITarefaFeign.class); final
		 * ITipoTarefaFeign tipoTarefaFeign = Mockito.mock(ITipoTarefaFeign.class);
		 * final IDoadorService doadorService = Mockito.mock(IDoadorService.class);
		 * 
		 * CriarTarefa.tarefaFeign = tarefaFeign; CriarTarefa.processoFeign =
		 * Mockito.mock(IProcessoFeign.class, "mockCriarProcessoFeign");;
		 * CriarTarefa.doadorService = doadorService; CriarTarefa.tipoTarefaFeign =
		 * tipoTarefaFeign;
		 * 
		 * Mockito.when(CriarTarefa.processoFeign.obterProcessoemAndamento(Mockito.
		 * anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(iniciarProcsso ?
		 * null : processo);
		 * Mockito.when(CriarTarefa.processoFeign.criarProcesso(Mockito.any(ProcessoDTO.
		 * class))).thenReturn(iniciarProcsso ? processo : null);
		 * Mockito.when(tarefaFeign.criarTarefa(Mockito.any(TarefaDTO.class))).
		 * thenReturn(tarefa);
		 * 
		 * Mockito.when(tipoTarefaFeign.obterTipoTarefa(Mockito.anyLong())).thenAnswer(
		 * new Answer<TipoTarefaDTO>() {
		 * 
		 * @Override public TipoTarefaDTO answer(InvocationOnMock invocation) throws
		 * Throwable { TipoTarefaDTO tipo = new
		 * TipoTarefaDTO(invocation.getArgument(0)); tipo.setTempoExecucao(3600L);
		 * return tipo; } });
		 * Mockito.when(doadorService.findById(Mockito.anyLong())).thenReturn(doador);
		 */
		
	}
	
	@SuppressWarnings("unchecked")
	public static void criarMockListarTarefa(TiposTarefa tipoTarefa, JsonViewPage<TarefaDTO> tarefas) {
		
		final IListarTarefa listarTarefa = Mockito.mock(IListarTarefa.class);
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setListarTarefa(listarTarefa);
		
		Mockito.when(listarTarefa.apply()).thenReturn(tarefas);
		Mockito.when(listarTarefa.comFiltro(Mockito.any(Predicate.class))).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comObjetoRelacionado(Mockito.anyLong())).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comOrdenacao(Mockito.any(Comparator.class))).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comPaginacao(Mockito.any(PageRequest.class))).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comParceiros(Mockito.anyList())).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comProcessoId(Mockito.anyLong())).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comRmr(Mockito.anyLong())).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comIdDoador(Mockito.anyLong())).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comStatus(Mockito.anyList())).thenReturn(listarTarefa);
		//Mockito.when(listarTarefa.comUsuario(Mockito.any(Usuario.class))).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.comUsuario(Mockito.nullable(Usuario.class))).thenReturn(listarTarefa);
		Mockito.when(listarTarefa.paraTodosUsuarios()).thenReturn(listarTarefa);
		
		
		/*
		 * final ITarefaFeign tarefaFeign = Mockito.mock(ITarefaFeign.class);
		 * 
		 * ListarTarefa.usuarioService = usuarioService; ListarTarefa.tarefaFeign =
		 * tarefaFeign; ListarTarefa.objectMapper = objectMapper;
		 * 
		 * Mockito.when(tarefaFeign.listarTarefas(Mockito.anyString())).thenReturn(
		 * tarefas);
		 */
		
	}
	
	public static void criarMockObterTarefa(TiposTarefa tipoTarefa, TarefaDTO tarefa) {
		
		final IObterTarefa obterTarefa = Mockito.mock(IObterTarefa.class);
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setObterTarefa(obterTarefa);
		
		Mockito.when(obterTarefa.apply()).thenReturn(tarefa);		
		Mockito.when(obterTarefa.comObjetoRelacionado(Mockito.anyLong())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comParceiros(Mockito.anyList())).thenReturn(obterTarefa);		
		Mockito.when(obterTarefa.comRmr(Mockito.anyLong())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comIdDoador(Mockito.anyLong())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comStatus(Mockito.anyList())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comTarefa(Mockito.anyLong())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comTarefaPai(Mockito.anyLong())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.comUsuario(Mockito.any(Usuario.class))).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.paraOutroUsuario(Mockito.anyBoolean())).thenReturn(obterTarefa);
		Mockito.when(obterTarefa.semAgendamento(Mockito.anyBoolean())).thenReturn(obterTarefa);
		
		
		/*
		 * final ITarefaFeign tarefaFeign = Mockito.mock(ITarefaFeign.class);
		 * 
		 * ObterTarefa.usuarioService = usuarioService; ObterTarefa.tarefaFeign =
		 * tarefaFeign; ObterTarefa.objectMapper = objectMapper;
		 * 
		 * Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenReturn(tarefa);
		 * Mockito.when(tarefaFeign.listarTarefas(Mockito.anyString())).thenReturn(new
		 * CustomPageImpl<TarefaDTO>(Arrays.asList(tarefa)));
		 */
		
	}
	
	public static void criarMockAtribuirTarefa(TiposTarefa tipoTarefa, TarefaDTO tarefa) {
		
		final IAtribuirTarefa atriburTarefa = Mockito.mock(IAtribuirTarefa.class);
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setAtribuirTarefa(atriburTarefa);
		
		Mockito.when(atriburTarefa.apply()).thenReturn(tarefa);		
		Mockito.when(atriburTarefa.comDoadorId(Mockito.anyLong())).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comObjetoRelacionado(Mockito.anyLong())).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comParceiros(Mockito.anyList())).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comProcessoId(Mockito.anyLong())).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comRmr(Mockito.anyLong())).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comTarefa(Mockito.any(TarefaDTO.class))).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comUsuario(Mockito.any(Usuario.class))).thenReturn(atriburTarefa);
		Mockito.when(atriburTarefa.comUsuarioLogado()).thenReturn(atriburTarefa);

		/*
		 * //final IProcessoFeign processoFeign = Mockito.mock(IProcessoFeign.class);
		 * final ITarefaFeign tarefaFeign = Mockito.mock(ITarefaFeign.class);
		 * 
		 * AtribuirTarefa.processoFeign = Mockito.mock(IProcessoFeign.class,
		 * "mockAtribuirProcessoFeign"); ; AtribuirTarefa.tarefaFeign = tarefaFeign;
		 * AtribuirTarefa.usuarioService = usuarioService;
		 * 
		 * Mockito.when(AtribuirTarefa.processoFeign.obterProcessoemAndamento(Mockito.
		 * anyLong(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(processo);
		 * Mockito.when(tarefaFeign.atribuirTarefaUsuario(Mockito.anyLong(),
		 * Mockito.anyLong())).thenReturn(tarefa);
		 */
				
	}
	
	public static void criarMockFecharTarefa(TiposTarefa tipoTarefa, TarefaDTO tarefa) {
		
		final IFecharTarefa fecharTarefa = Mockito.mock(IFecharTarefa.class);
				
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setFecharTarefa(fecharTarefa);
		
		Mockito.when(fecharTarefa.comStatus(Mockito.anyList())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comDoadorId(Mockito.anyLong())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comObjetoRelacionado(Mockito.nullable(Long.class))).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comParceiros(Mockito.anyList())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comProcessoId(Mockito.anyLong())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comRmr(Mockito.anyLong())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comStatus(Mockito.anyList())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comTarefa(Mockito.anyLong())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comTarefaPai(Mockito.anyLong())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.comUsuario(Mockito.nullable(Usuario.class))).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.finalizarProcesso()).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.semAgendamento(Mockito.anyBoolean())).thenReturn(fecharTarefa);
		Mockito.when(fecharTarefa.apply()).thenReturn(tarefa);
		
	}
	
	public static void criarMockCriarNotificacao(CategoriasNotificacao categoriaNotificacao) {
		
		final ICriarNotificacao criarNotificacao = Mockito.mock(ICriarNotificacao.class);
				
		((ConfiguracaoCategoriaNotificacao) categoriaNotificacao.getConfiguracao()).setCriarNotificacao(criarNotificacao);
		
		Mockito.when(criarNotificacao.comDescricao(Mockito.anyString())).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.comPaciente(Mockito.anyLong()) ).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.comParceiro(Mockito.anyLong())).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.paraPerfil(Mockito.any(Perfis.class))).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.paraUsuario(Mockito.anyLong())).thenReturn(criarNotificacao);		
		Mockito.when(criarNotificacao.apply()).then(new Answer<List<NotificacaoDTO>>() {
			
			@Override
			public List<NotificacaoDTO> answer(InvocationOnMock invocation) throws Throwable {

				return Collections.emptyList();
			}
			
		});

		
	}
	
	public static void criarMockContarNotificacao(CategoriasNotificacao categoriaNotificacao, Long total) {
		
		final IContarNotificacao criarNotificacao = Mockito.mock(IContarNotificacao.class);
				
		if (categoriaNotificacao != null) {
			((ConfiguracaoCategoriaNotificacao) categoriaNotificacao.getConfiguracao()).setContarNotificacao(criarNotificacao);
		}
		
		Mockito.when(criarNotificacao.comRmr(Mockito.anyLong())).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.somenteLidas()).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.somenteNaoLidas()).thenReturn(criarNotificacao);
		Mockito.when(criarNotificacao.apply()).thenReturn(total);
		
		CategoriasNotificacao.setContarNotificacao(criarNotificacao);
		
		
	}
	
	
	
	public static void limparMockCriarTarefa(TiposTarefa tipoTarefa) {
		
		Mockito.reset(tipoTarefa.getConfiguracao().criarTarefa());
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setCriarTarefa(null);
		
	}
	
	public static void limparMockListarTarefa(TiposTarefa tipoTarefa) {
		
		Mockito.reset(tipoTarefa.getConfiguracao().listarTarefa());
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setListarTarefa(null); 
		
	}
	
	public static void limparMockObterTarefa(TiposTarefa tipoTarefa) {
		
		Mockito.reset(tipoTarefa.getConfiguracao().obterTarefa());
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setObterTarefa(null); 
		
	}
	
	public static void limparMockAtribuirTarefa(TiposTarefa tipoTarefa) {
		
		Mockito.reset(tipoTarefa.getConfiguracao().atribuirTarefa());
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setAtribuirTarefa(null);		
	}
	
	public static void limparMockFecharTarefa(TiposTarefa tipoTarefa) {
		
		Mockito.reset(tipoTarefa.getConfiguracao().fecharTarefa());
		
		((ConfiguracaoTipoTarefa) tipoTarefa.getConfiguracao()).setFecharTarefa(null);
				
	}
	
	@SuppressWarnings("rawtypes")
	public static IStorageService criarMockStorageService(Long idUsuario, Long rmr, String caminhoArquivo) {
		IStorageService mockStorageService = Mockito.mock(IStorageService.class);
		
		Mockito.doNothing().when(mockStorageService).moverArquivoExame(
				idUsuario == null ? Mockito.anyLong() : idUsuario, 
				rmr == null ? Mockito.anyLong() : rmr, 
				caminhoArquivo == null ? Mockito.anyString() : caminhoArquivo);
		
		Mockito.doNothing().when(mockStorageService).moverArquivoEvolucao(
				idUsuario == null ? Mockito.anyLong() : idUsuario, 
				rmr == null ? Mockito.anyLong() : rmr, 
				caminhoArquivo == null ? Mockito.anyString() : caminhoArquivo);
		
		return mockStorageService;
	}
	
	public static void mockCriarPacienteComAvaliacaoInicial(PacienteService pacienteService, AvaliacaoService avaliacaoService) {
		
		IUsuarioService mockUsuarioService = MockService.criarMockUsuarioService(1L, null, null);
		
		CriacaoAuditavelListener.usuarioService = mockUsuarioService;
				
		ReflectionTestUtils.setField(pacienteService, "usuarioService",  mockUsuarioService );
		ReflectionTestUtils.setField(pacienteService, "rascunhoService", MockService.criarMockRascunhoService(null));
		ReflectionTestUtils.setField(pacienteService, "medicoService", MockService.criarMockMedicoService(1L, 1L));
	//	ReflectionTestUtils.setField(pacienteService, "storageService", MockService.criarMockStorageService(null, null, null) );
		
		IUsuarioService usuarioServiceParaAvaliacao = MockService.criarMockUsuarioService(2L, Arrays.asList(new Perfil(Perfis.MEDICO_AVALIADOR.getId())), 
				Arrays.asList(new CentroTransplante(2L)));
		
		ReflectionTestUtils.setField(avaliacaoService, "usuarioService", usuarioServiceParaAvaliacao);
		
		criarMockCriarNotificacao(CategoriasNotificacao.EXAME);
		
				
	}
	
	public static void mockCriarPacienteComAvaliacaoInicialAprovada(PacienteService pacienteService, AvaliacaoService avaliacaoService) {
		
		MockService.mockCriarPacienteComAvaliacaoInicial(pacienteService, avaliacaoService);
		
	}
	
	
	public static void mockCriarPacienteComAvaliacaoInicialAprovadaEExameAprovado(IExamePacienteService examePacienteService, IGenotipoPacienteService genotipoService) {
				
		IUsuarioService usuarioServiceParaAvaliacaoExame = MockService.criarMockUsuarioService(3L, Arrays.asList(new Perfil(Perfis.AVALIADOR_EXAME_HLA.getId())), null); 

		ReflectionTestUtils.setField(genotipoService, "usuarioService",  usuarioServiceParaAvaliacaoExame );
		ReflectionTestUtils.setField(examePacienteService, "usuarioService",  usuarioServiceParaAvaliacaoExame );
		ReflectionTestUtils.setField(examePacienteService, "genotipoService",  genotipoService );
		
	}

}
