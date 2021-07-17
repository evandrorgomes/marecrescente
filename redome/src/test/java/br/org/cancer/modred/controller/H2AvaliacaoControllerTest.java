package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.RespostaPendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.TipoPendencia;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.StatusPendencias;
import br.org.cancer.modred.model.domain.TipoPendencias;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.RetornoControladorErro;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

public class H2AvaliacaoControllerTest extends BaseConfigurationTest {
	
	
	private static final String PENDENCIA_CRIADA_COM_SUCESSO = "Pendência criada com sucesso.";
	private static final String AVALIACAO_APROVADA_COM_SUCESSO = "Avaliação aprovada com sucesso.";
	private static final String AVALIACAO_REPROVADA_COM_SUCESSO = "Avaliação reprovada com sucesso.";
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
		
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("AVALIADOR")
			.addPerfil("MEDICO_AVALIADOR")
			.addRecurso(
				"RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
				"CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
				"DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
				"VISUALIZAR_FICHA_PACIENTE",
				"CONSULTAR_EXAMES_PACIENTE",
				"CONSULTAR_EVOLUCOES_PACIENTE",
				"AVALIAR_PACIENTE",
				"VISUALIZAR_IDENTIFICACAO_PARCIAL",
				"CONSULTAR_AVALIACOES",
				"ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
				"VISUALIZAR_PENDENCIAS_AVALIACAO");
	}
	
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(pacienteService, "avaliacaoService", avaliacaoService);
		MockService.mockCriarPacienteComAvaliacaoInicial(pacienteService, avaliacaoService);
		
	}


	@Test
	public void gerarTarefaInicialDeAvaliacao() throws Exception {
//		H2BasePaciente.criarPacienteComAvaliacaoInicial(mockMvc, pacienteService, new PacienteDto()
//				.comADataNascimento(LocalDate.now().minusYears(25))
//				.comONome("TESTE DE DOADOR")
//				.comOCpf("06742987057")
//				.comOCns("116980737390004"));
	}
	
	@Test
	public void aprovarAvaliacao() throws Exception {
		
		Long idAvaliacao = H2BasePaciente.criarPacienteComAvaliacaoInicial(mockMvc, pacienteService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE DOADOR")
				.comOCpf("35265640002")
				.comOCns("250517169680003"));
		Avaliacao avaliacao = criarAvaliacao();
		avaliacao.setAprovado(true);
		
		ProcessoDTO processo = new ProcessoDTO(2L, TiposTarefa.AVALIAR_EXAME_HLA.getTipoProcesso());
		TarefaDTO tarefa = new TarefaDTO(processo, TiposTarefa.AVALIAR_EXAME_HLA,  new Perfil(TiposTarefa.AVALIAR_EXAME_HLA.getPerfil().getId()));
		tarefa.setId(2L);		
		MockService.criarMockCriarTarefa(TiposTarefa.AVALIAR_EXAME_HLA, tarefa);
		
		ProcessoDTO processoAvaliacao = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());		
		TarefaDTO tarefaAvalaicao = new TarefaDTO(processoAvaliacao, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefaAvalaicao.setId(1L);
				
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefaAvalaicao);
		
		String retornoJson = mockMvc
				.perform(CreateMockHttpServletRequest.makePut("/api/avaliacoes/" + idAvaliacao)				
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(avaliacao))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		RetornoControladorErro retorno = getObjectMapper().readValue(retornoJson, RetornoControladorErro.class);
		Assert.assertEquals(AVALIACAO_APROVADA_COM_SUCESSO, retorno.getMensagem());
		
		MockService.limparMockCriarTarefa(TiposTarefa.AVALIAR_EXAME_HLA);
		MockService.limparMockFecharTarefa(TiposTarefa.AVALIAR_PACIENTE);
		
		
	}
	
	@Test
	public void adicionarPendencia() throws Exception{
		Long idAvaliacao = H2BasePaciente.criarPacienteComAvaliacaoInicial(mockMvc, pacienteService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE DOADOR")
				.comOCpf("25217993006")
				.comOCns("843292142120000"));
		
		Avaliacao avaliacao = criarAvaliacao();
		avaliacao.setId(idAvaliacao);
		
		PendenciaDto pendencia = new PendenciaDto();
		pendencia.setAvaliacao(avaliacao);
		pendencia.setDescricao("TESTE PENDENCIA");
		pendencia.setDataCriacao(LocalDateTime.now());
		pendencia.setStatusPendencia(new StatusPendencia(StatusPendencias.ABERTA.getId()));
		pendencia.setTipoPendencia(new TipoPendencia(TipoPendencias.QUESTIONAMENTO.getId()));
		
		ProcessoDTO processo = new ProcessoDTO(3L, TiposTarefa.RESPONDER_PENDENCIA.getTipoProcesso());
		TarefaDTO tarefaPendencia = new TarefaDTO(processo, TiposTarefa.RESPONDER_PENDENCIA, null);
		
		MockService.criarMockCriarTarefa(TiposTarefa.RESPONDER_PENDENCIA, tarefaPendencia);
				
		String retornoJson = mockMvc
				.perform(CreateMockHttpServletRequest.makePost("/api/avaliacoes/" + idAvaliacao + "/pendencias")				
				.content(getObjectMapper().writeValueAsBytes(pendencia))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		RetornoControladorErro retorno = getObjectMapper().readValue(retornoJson, RetornoControladorErro.class);
		Assert.assertEquals(PENDENCIA_CRIADA_COM_SUCESSO, retorno.getMensagem());
	}
	
	
	@Test
	public void reprovarAvaliacao() throws Exception {
		Long idAvaliacao = H2BasePaciente.criarPacienteComAvaliacaoInicial(mockMvc, pacienteService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE DOADOR")
				.comOCpf("41663611041")
				.comOCns("130616297080006"));
		Avaliacao avaliacao = criarAvaliacao();
		avaliacao.setAprovado(false);
		avaliacao.setObservacao("TESTE DE REPROVACAO");
		
		ProcessoDTO processoAvaliacao = new ProcessoDTO(1L, TiposTarefa.AVALIAR_PACIENTE.getTipoProcesso());		
		TarefaDTO tarefaAvalaicao = new TarefaDTO(processoAvaliacao, TiposTarefa.AVALIAR_PACIENTE, new Perfil(TiposTarefa.AVALIAR_PACIENTE.getPerfil().getId()));
		tarefaAvalaicao.setId(1L);
				
		MockService.criarMockFecharTarefa(TiposTarefa.AVALIAR_PACIENTE, tarefaAvalaicao);
		
		MockService.criarMockCriarNotificacao(CategoriasNotificacao.AVALIACAO_PACIENTE);
		
		String retornoJson = mockMvc
				.perform(CreateMockHttpServletRequest.makePut("/api/avaliacoes/" + idAvaliacao)
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(avaliacao))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk())
				.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		RetornoControladorErro retorno = getObjectMapper().readValue(retornoJson, RetornoControladorErro.class);
		Assert.assertEquals(AVALIACAO_REPROVADA_COM_SUCESSO, retorno.getMensagem());
	}

	private Avaliacao criarAvaliacao() {
		Avaliacao avaliacao = new Avaliacao();
		Paciente paciente = new Paciente();
		avaliacao.setPaciente(paciente);
		avaliacao.getPaciente().setTempoParaTransplante(30);
		return avaliacao;
	}
	
	private class PendenciaDto {
		
		private Long id;
		private LocalDateTime dataCriacao;
		private String descricao;
		private Avaliacao avaliacao;
		private StatusPendencia statusPendencia;
		private TipoPendencia tipoPendencia;
		private List<RespostaPendencia> respostas;
		
		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(Long id) {
			this.id = id;
		}
		/**
		 * @return the dataCriacao
		 */
		public LocalDateTime getDataCriacao() {
			return dataCriacao;
		}
		/**
		 * @param dataCriacao the dataCriacao to set
		 */
		public void setDataCriacao(LocalDateTime dataCriacao) {
			this.dataCriacao = dataCriacao;
		}
		/**
		 * @return the descricao
		 */
		public String getDescricao() {
			return descricao;
		}
		/**
		 * @param descricao the descricao to set
		 */
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		/**
		 * @return the avaliacao
		 */
		public Avaliacao getAvaliacao() {
			return avaliacao;
		}
		/**
		 * @param avaliacao the avaliacao to set
		 */
		public void setAvaliacao(Avaliacao avaliacao) {
			this.avaliacao = avaliacao;
		}
		/**
		 * @return the statusPendencia
		 */
		public StatusPendencia getStatusPendencia() {
			return statusPendencia;
		}
		/**
		 * @param statusPendencia the statusPendencia to set
		 */
		public void setStatusPendencia(StatusPendencia statusPendencia) {
			this.statusPendencia = statusPendencia;
		}
		/**
		 * @return the tipoPendencia
		 */
		public TipoPendencia getTipoPendencia() {
			return tipoPendencia;
		}
		/**
		 * @param tipoPendencia the tipoPendencia to set
		 */
		public void setTipoPendencia(TipoPendencia tipoPendencia) {
			this.tipoPendencia = tipoPendencia;
		}
		/**
		 * @return the respostas
		 */
		public List<RespostaPendencia> getRespostas() {
			return respostas;
		}
		/**
		 * @param respostas the respostas to set
		 */
		public void setRespostas(List<RespostaPendencia> respostas) {
			this.respostas = respostas;
		}
		
	}
	
	/*@MockBean(value=CriacaoAuditavelListener.class, reset= MockReset.NONE)
	public class MockCriacaoAuditavelListener {
		
		@PrePersist
		public void preencherDadosAuditoria(CriacaoAuditavel auditavel) {
			AutowireHelper.autowire(this);
			
			//IUsuarioService usuarioService = (IUsuarioService) ApplicationContextProvider.obterBean("customUserDetailsService");
			
			auditavel.setDataCriacao(LocalDateTime.now());
			auditavel.setUsuario(new Usuario(1L));
		}
		
	}*/
	
}
