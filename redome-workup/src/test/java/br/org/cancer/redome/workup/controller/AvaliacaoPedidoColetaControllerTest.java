package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class AvaliacaoPedidoColetaControllerTest extends BaseConfigurationTest {
		
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(13L, "MEDICO_REDOME")
			.addPerfil("MEDICO_REDOME")
			.addRecurso(Recursos.AVALIAR_PEDIDO_COLETA);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	
	@Test
	public void deveProsseguirPedidoColetaComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(20L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);

		makeStubForPut("/redome/api/solicitacoes/20/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaAvaliarPedidoColeta = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_PEDIDO_COLETA.getId()))
				.relacaoEntidade(3L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlszN10sInN0YXR1c1Rhc" + 
				"mVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bn" + 
				"VsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnV" + 
				"sbCwicmVsYWNhb0VudGlkYWRlSWQiOjMsInJtciI6MzAwMDAwLCJpZERv" + 
				"YWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFsc" + 
				"XVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZW" + 
				"ZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN" + 
				"9",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaAvaliarPedidoColeta))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliarPedidoColeta)));
		
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());		
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
				
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/avaliacoespedidoscoleta/prosseguir")
			.content("3".getBytes())
	        .contentType(MediaType.APPLICATION_JSON_UTF8))				
			.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveProsseguirPedidoColetaSeAvaliacaoResultadoWorkupMarkadaComoNaoProsseguirExistir() throws Exception {
						
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/avaliacoespedidoscoleta/prosseguir")
			.content("4".getBytes())
	        .contentType(MediaType.APPLICATION_JSON_UTF8))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveProsseguirPedidoColetaSeResultadoWorkupMarkadaComoViavelExistir() throws Exception {
						
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/avaliacoespedidoscoleta/prosseguir")
			.content("5".getBytes())
	        .contentType(MediaType.APPLICATION_JSON_UTF8))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveProsseguirPedidoColetaSeAvaliacaoExistir() throws Exception {
						
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/avaliacoespedidoscoleta/prosseguir")
			.content("6".getBytes())
	        .contentType(MediaType.APPLICATION_JSON_UTF8))				
			.andExpect(status().is4xxClientError());
		
	}

	
	@Test
	public void deveNaoProsseguirPedidoColetaComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(22L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AVALIACAO_PEDIDO_COLETA);

		makeStubForPost("/redome/api/solicitacoes/22/cancelar/workup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForGet("/redome/api/solicitacoes/22", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_PEDIDO_COLETA.getId()))
				.relacaoEntidade(7L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlszN10sInN0YXR1c1Rhc" + 
				"mVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bn" + 
				"VsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnV" + 
				"sbCwicmVsYWNhb0VudGlkYWRlSWQiOjcsInJtciI6MzAwMDAwLCJpZERv" + 
				"YWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFsc" + 
				"XVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZW" + 
				"ZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTN" + 
				"9",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
				
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		MockMultipartFile idAvaliacaoResultadoWorkupFile = new MockMultipartFile("idAvaliacaoResultadoWorkup", null, "application/json", "7".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "justificativa".getBytes());
						
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoespedidoscoleta/naoprosseguir", "post")
			.file(idAvaliacaoResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveNaoProsseguirPedidoColetaSeAvaliacaoResultadoWorkupMarkadaComoProsseguirSemJustificativa() throws Exception {

		MockMultipartFile idAvaliacaoResultadoWorkupFile = new MockMultipartFile("idAvaliacaoResultadoWorkup", null, "application/json", "7".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoespedidoscoleta/naoprosseguir", "post")
			.file(idAvaliacaoResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveNaoProsseguirPedidoColetaSeAvaliacaoResultadoWorkupMarkadaComoNaoProsseguirExistir() throws Exception {

		MockMultipartFile idAvaliacaoResultadoWorkupFile = new MockMultipartFile("idAvaliacaoResultadoWorkup", null, "application/json", "4".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "justificativa".getBytes());
		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoespedidoscoleta/naoprosseguir", "post")
			.file(idAvaliacaoResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveNaoProsseguirPedidoColetaSeResultadoWorkupMarkadaComoViavelExistir() throws Exception {
		
		MockMultipartFile idAvaliacaoResultadoWorkupFile = new MockMultipartFile("idAvaliacaoResultadoWorkup", null, "application/json", "5".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "justificativa".getBytes());
								
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoespedidoscoleta/naoprosseguir", "post")
			.file(idAvaliacaoResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveNaoProsseguirPedidoColetaSeAvaliacaoExistir() throws Exception {
		
		MockMultipartFile idAvaliacaoResultadoWorkupFile = new MockMultipartFile("idAvaliacaoResultadoWorkup", null, "application/json", "6".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "justificativa".getBytes());
								
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoespedidoscoleta/naoprosseguir", "post")
			.file(idAvaliacaoResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}	
	
	
		
	private SolicitacaoWorkupDTO criarSolicitacao(Long id, TiposSolicitacao tipo, UsuarioDTO usuario, MatchDTO match,
			UsuarioDTO usuarioResponsavel, FasesWorkup faseWorkup) {
		return SolicitacaoWorkupDTO.builder().id(id).status(0)
				.tipoSolicitacao(TipoSolicitacaoDTO.builder().id(tipo.getId()).build()).usuario(usuario).match(match)
				.usuarioResponsavel(usuarioResponsavel).faseWorkup(faseWorkup.getId()).build();
	}

	private MatchDTO criarMatch(Long idMatch, Long idDoador, String identificacaoDoador, BuscaDTO busca) {
		return MatchDTO.builder().id(idMatch)
				.doador(DoadorDTO.builder().id(idDoador).Identificacao(identificacaoDoador).build()).busca(busca)

				.build();
	}

	private BuscaDTO criarBusca(Long idCentroTransplante, String nomeCentroTransplante, Long rmr) {
		return BuscaDTO.builder()
				.centroTransplante(
						CentroTransplanteDTO.builder().id(idCentroTransplante).nome(nomeCentroTransplante).build())
				.paciente(PacienteDTO.builder().rmr(rmr).build()).build();
	}

}
