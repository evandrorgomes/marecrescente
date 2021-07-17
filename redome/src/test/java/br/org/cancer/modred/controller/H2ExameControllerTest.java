package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.ArquivoExame;
import br.org.cancer.modred.model.ExameDoadorInternacional;
import br.org.cancer.modred.model.ExamePaciente;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.Metodologia;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.IExameDoadorService;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.ExecutarProcedureMatchService;
import br.org.cancer.modred.service.impl.GenotipoPacienteService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

public class H2ExameControllerTest extends BaseConfigurationTest {

	private static final String EXAME_ATUALIZADO_COM_SUCESSO = "Exame atualizado com sucesso.";
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private AvaliacaoService avaliacaoService;

	@Autowired
	private IExamePacienteService examePacienteService;
	
	@Autowired
	private GenotipoPacienteService genotipoService;
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
	
	@Spy
	private ExecutarProcedureMatchService executarProcedureMatchService;
	
	@Autowired
	private IExameDoadorService<ExameDoadorInternacional> exameDoadorInternacionalService;
	
	@Mock
	private ITarefaFeign tarefaFeign;
	
		
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("AVALIADOR_REDOME")
			.addPerfil("AVALIADOR_EXAME_HLA")
			.addRecurso(
						"VISUALIZAR_IDENTIFICACAO_COMPLETA",
						"VISUALIZAR_AVALIACAO",
						"CONTACTAR_FASE_3",
						"CONFERIR_EXAME_HLA");
	}
	
	@Before
	public void setupBefore() {
		
		MockitoAnnotations.initMocks(this);
		
		MockService.mockCriarPacienteComAvaliacaoInicialAprovada(pacienteService, avaliacaoService);
		
		ReflectionTestUtils.setField(genotipoService, "executarProcedureMatchService", executarProcedureMatchService);
		ReflectionTestUtils.setField(examePacienteService, "genotipoService", genotipoService);
		
		ReflectionTestUtils.setField(exameDoadorInternacionalService, "tarefaFeign", tarefaFeign);		
		
		ReflectionTestUtils.setField(examePacienteService, "usuarioService", MockService.criarMockUsuarioService(3L, Arrays.asList(new Perfil(Perfis.AVALIADOR_EXAME_HLA.getId())), 
				null));
				
	}
	
	
	@Test
	public void deveAceitarExame() throws Exception{
		Long rmr = H2BasePaciente.criarPacienteAprovadoAvaliacaoInicial(mockMvc, pacienteService, avaliacaoService, 
				logEvolucaoService, new PacienteDto()
			.comADataNascimento(LocalDate.now().minusYears(25))
			.comONome("TESTE DE DOADOR PARA ACEITAR EXAME")
			.comOCpf("74524622039")
			.comOCns("918918170400009"));
		
		List<ExamePaciente> examesPaciente = examePacienteService.listarExamesPorPaciente(rmr);
		Assert.assertEquals(1,examesPaciente.size());
		
		ExamePaciente examePaciente = criarExamePaciente(examesPaciente.get(0));
		
		ProcessoDTO processo = new ProcessoDTO(4L, TiposTarefa.AVALIAR_EXAME_HLA.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_EXAME_HLA, new Perfil(TiposTarefa.AVALIAR_EXAME_HLA.getPerfil().getId()));
		tarefa.setStatus(StatusTarefa.ABERTA.getId());
		tarefa.setId(4L);
		
		MockService.criarMockObterTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		MockService.criarMockAtribuirTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		
		ProcessoDTO processoBusca = new ProcessoDTO(5L, TiposTarefa.RECEBER_PACIENTE.getTipoProcesso());
		TarefaDTO tarefaReceberPaciente = new TarefaDTO(processoBusca, TiposTarefa.RECEBER_PACIENTE, new Perfil(TiposTarefa.RECEBER_PACIENTE.getPerfil().getId()));
		
		MockService.criarMockCriarTarefa(TiposTarefa.RECEBER_PACIENTE, tarefaReceberPaciente);

		TarefaDTO tarefaEnviarWmda = new TarefaDTO(processoBusca, TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP, null);
		
		MockService.criarMockCriarTarefa(TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP, tarefaEnviarWmda);
		
		Mockito.doNothing().when(executarProcedureMatchService).gerarMatchPaciente(Mockito.anyLong());		
		
		TarefaDTO tarefaEmdis = new TarefaDTO(processoBusca, TiposTarefa.ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP, null);
		
		MockService.criarMockCriarTarefa(TiposTarefa.ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP, tarefaEmdis);
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());		
						
		String retornoJson = mockMvc
				.perform(CreateMockHttpServletRequest.makePut("/api/exames/" + examePaciente.getId() + "/aceito")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(examePaciente))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType("text/plain;charset=UTF-8"))
				.andReturn().getResponse().getContentAsString();
		
		Assert.assertEquals(EXAME_ATUALIZADO_COM_SUCESSO, retornoJson);
	}
	
	@Test
	public void deveNotificarSobrePedidoExameDoadorInternacional() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				processo.setPaciente(new Paciente(3059735L));
				TarefaDTO tarefa = new TarefaDTO(109903L);
				tarefa.setProcesso(processo);
				tarefa.setRelacaoEntidade(109902L);
				tarefa.setDataCriacao(LocalDateTime.of(2019, 4, 17, 11, 0, 5));
				return tarefa;
			}
		});
		
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/exames/notificarresultadoexameinternacional")
		.param("tarefa", "109903")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void deveNotificarSobreCTInternacionalDe15Dias() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				processo.setPaciente(new Paciente(3059735L));
				TarefaDTO tarefa = new TarefaDTO(109903L);
				tarefa.setProcesso(processo);
				tarefa.setRelacaoEntidade(109902L);
				tarefa.setDataCriacao(LocalDateTime.of(2019, 4, 17, 11, 0, 5));
				return tarefa;
			}
		});
		
		ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
		TarefaDTO tarefa = new TarefaDTO(1L);		
		
		MockService.criarMockCriarTarefa(TiposTarefa.RESULTADO_EXAME_CT_INTERNACIONAL_15DIAS_FOLLOW_UP, tarefa);
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/exames/notificarctinternacional15dias")
		.param("tarefa", "109903")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void deveNotificarSobreCTInternacionalDe7Dias() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				processo.setPaciente(new Paciente(3059735L));
				TarefaDTO tarefa = new TarefaDTO(109903L);				
				tarefa.setProcesso(processo);
				tarefa.setRelacaoEntidade(109902L);
				tarefa.setDataCriacao(LocalDateTime.of(2019, 4, 17, 11, 0, 5));
				return tarefa;
			}
		});
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/exames/notificarctinternacional7dias")
		.param("tarefa", "109903")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}

	
	@Test
	public void deveNotificarSobreIdmInternacionalDe15Dias() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				processo.setPaciente(new Paciente(3059735L));
				TarefaDTO tarefa = new TarefaDTO(109904L);
				tarefa.setProcesso(processo);
				tarefa.setRelacaoEntidade(521L);
				tarefa.setDataCriacao(LocalDateTime.of(2019, 4, 17, 11, 0, 5));
				return tarefa;
			}
		});
		
		ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
		TarefaDTO tarefa = new TarefaDTO(1L);
		
		MockService.criarMockCriarTarefa(TiposTarefa.RESULTADO_IDM_INTERNACIONAL_7DIAS_FOLLOW_UP, tarefa);
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/exames/notificaridminternacional15dias")
		.param("tarefa", "109904")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void deveNotificarSobreIdmInternacionalDe7Dias() throws Exception {
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				processo.setPaciente(new Paciente(3059735L));
				TarefaDTO tarefa = new TarefaDTO(109904L);
				tarefa.setProcesso(processo);
				tarefa.setRelacaoEntidade(521L);
				tarefa.setDataCriacao(LocalDateTime.of(2019, 4, 17, 11, 0, 5));
				return tarefa;
			}
		});
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/exames/notificaridminternacional7dias")
		.param("tarefa", "109904")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}

	

	private ExamePaciente criarExamePaciente(ExamePaciente examePaciente) {
		ExamePaciente exame = new ExamePaciente();
		ArquivoExame arquivoExame = new ArquivoExame();
		ArquivoExame arquivosExamePaciente = examePaciente.getArquivosExame().get(0);
		arquivoExame.setCaminhoArquivo(arquivosExamePaciente.getCaminhoArquivo());
		arquivoExame.setExame(exame);
		arquivoExame.setId(arquivoExame.getId());
		arquivoExame.setRmr(examePaciente.getPaciente().getRmr());
		exame.setArquivosExame(Arrays.asList(arquivoExame));
		exame.setDataColetaAmostra(examePaciente.getDataColetaAmostra());
		exame.setEditadoPorAvaliador(false);
		exame.setDataExame(examePaciente.getDataExame());
		exame.setLaboratorioParticular(false);
		exame.setId(examePaciente.getId());
		List<LocusExame> locusExames = new ArrayList<>();
		examePaciente.getLocusExames().forEach(e -> {
			LocusExame locus = new LocusExame();
			locus.setDataMarcacao(e.getDataMarcacao());
			locus.setId(e.getId());
			locus.setIdBusca(e.getIdBusca());
			locus.setMotivoDivergencia(e.getMotivoDivergencia());
			locus.setPrimeiroAlelo(e.getPrimeiroAlelo());
			locus.setPrimeiroAleloComposicao(e.getPrimeiroAleloComposicao());
			locus.setPrimeiroAleloDivergente(e.getPrimeiroAleloDivergente());
			locus.setSegundoAlelo(e.getSegundoAlelo());
			locus.setSegundoAleloComposicao(e.getSegundoAleloComposicao());
			locus.setSegundoAleloDivergente(e.getSegundoAleloDivergente());
			locus.setSelecionado(e.getSelecionado());
			locusExames.add(locus);
		});
		exame.setLocusExames(locusExames );
		List<Metodologia> metodologias = new ArrayList<>();
		examePaciente.getMetodologias().forEach(m ->{
			Metodologia metodologia = new Metodologia();
			metodologia.setDescricao(m.getDescricao());
			metodologia.setId(m.getId());
			metodologia.setPesoGenotipo(m.getPesoGenotipo());
			metodologias.add(metodologia);
		});
		exame.setMetodologias(metodologias );
		Paciente paciente = new Paciente();
		paciente.setRmr(examePaciente.getPaciente().getRmr());
		paciente.setExames(Arrays.asList(exame));
		exame.setPaciente(paciente );
		exame.setStatusExame(examePaciente.getStatusExame());
		exame.setTipoAmostra(examePaciente.getTipoAmostra());
		exame.setLaboratorioParticular(examePaciente.getLaboratorioParticular());
		if (!exame.getLaboratorioParticular()) {
			Laboratorio laboratorio = new Laboratorio();
			laboratorio.setId(examePaciente.getLaboratorio().getId());
			exame.setLaboratorio(laboratorio );
		}
		return exame;
	}
}
