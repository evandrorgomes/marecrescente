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

public class AvaliacaoResultadoWorkupInternacionalControllerTest extends BaseConfigurationTest {
		
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(15L, "MEDICO_TRANSPLANTADOR")
			.addPerfil("MEDICO_TRANSPLANADOR")
			.addRecurso(Recursos.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	
	@Test
	public void deveProsseguirResultadoWorkupcomSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(6L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);

		makeStubForGet("/redome/api/solicitacoes/6", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/6/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(2L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjJdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjoyLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "2".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	
	@Test
	public void deveProsseguirResultadoWorkupParaAutorizacaoPacienteComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(7L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AUTORIZACAO_PACIENTE);

		makeStubForPut("/redome/api/solicitacoes/7/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(3L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjJdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
				
		TarefaDTO tarefaAutorizacao = TarefaDTO.builder()
				.id(2L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.relacaoEntidade(10L)
				.build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzFdLCJpZHNUaXBvc1RhcmVmYSI6Wzc4XSwic3RhdHVzVGFyZWZhIjpbMV0sInN0YXR1c1Byb2Nlc3NvIjoxLCJwcm9jZXNzb0lkIjpudWxsLCJpbmNsdXNpdm9FeGNsdXNpdm8iOm51bGwsInBhZ2VhYmxlIjpudWxsLCJyZWxhY2FvRW50aWRhZGVJZCI6MTAsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaAutorizacao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/2/cancelar", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
				
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "3".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveProsseguirResultadoWorkupSeAvaliacaoExistir() throws Exception {
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "4".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveProsseguirResultadoWorkupColetaInviavelSeJustificativaNaoInformada() throws Exception {
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "5".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void deveProsseguirResultadoWorkupColetaInviavelcomSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(10L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);

		makeStubForGet("/redome/api/solicitacoes/10", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/10/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(6L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjJdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo2LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "6".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "Texto da Justificativa".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveProsseguirPedidoWorkupJaFinalizado() throws Exception {
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "7".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/prosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void deveNaoPProsseguirResultadoWorkupComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(12L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);

		makeStubForPost("/redome/api/solicitacoes/12/cancelar/workup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForGet("/redome/api/solicitacoes/12", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(8L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjJdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo4LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		TarefaDTO tarefaAutorizacao = TarefaDTO.builder()
				.id(2L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.relacaoEntidade(15L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzFdLCJpZHNUaXBvc1RhcmVmYSI6Wzc4XSwic3RhdHVzVGFyZWZhIjpbMV0sInN0YXR1c1Byb2Nlc3NvIjoxLCJwcm9jZXNzb0lkIjpudWxsLCJpbmNsdXNpdm9FeGNsdXNpdm8iOm51bGwsInBhZ2VhYmxlIjpudWxsLCJyZWxhY2FvRW50aWRhZGVJZCI6MTUsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaAutorizacao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/2/cancelar", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());		
				
		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "8".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "Texto da Justificativa".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/naoprosseguir",  "post")
			.file(idResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naDeveNaoPProsseguirResultadoWorkupsePedidoWorkupDiferenteDeIniciado() throws Exception {

		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "9".getBytes());
		MockMultipartFile justificativaFile = new MockMultipartFile("justificativa", null, "application/json", "Texto da Justificativa".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/naoprosseguir",  "post")
			.file(idResultadoWorkupFile)
			.file(justificativaFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naDeveNaoPProsseguirResultadoWorkupsePedidoWorkupSeJustificativaEmBranco() throws Exception {

		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "9".getBytes());
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/naoprosseguir",  "post")
			.file(idResultadoWorkupFile)
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}	

//	@Test
//	public void deveSolicitarExameAdicionalDoadorInternacionalComSucesso() throws Exception {
//
//		SolicitacaoDTO solicitacao = criarSolicitacao(17L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
//				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
//				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
//				FasesWorkup.AGUARDANDO_EXAME_ADICIONAL_DOADOR);
//
//		makeStubForPut("/redome/api/solicitacoes/17/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
//				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
//
//		TarefaDTO tarefaWorkup = TarefaDTO.builder()
//				.id(2L)
//				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL.getId()))
//				.relacaoEntidade(8L)
//				.build();
//
//		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
//				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
//				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlszM10sInN0YXR1c1Rhc" + 
//				"mVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bn" + 
//				"VsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnV" + 
//				"sbCwicmVsYWNhb0VudGlkYWRlSWQiOjEyLCJybXIiOjMwMDAwMCwiaWRE" + 
//				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
//				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
//				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE" + 
//				"1fQ%3D%3D",
//				okForContentType("application/json;charset=UTF-8",
//				montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
//				
//		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
//				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
//		
//		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
//		
//		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
//				
//		MockMultipartFile idResultadoWorkupFile = new MockMultipartFile("idResultadoWorkup", null, "application/json", "10".getBytes());
//		MockMultipartFile descricaoFile = new MockMultipartFile("descricao", null, "application/json", "Texto da descricao".getBytes());
//		
//		mockMvc
//			.perform(CreateMockHttpServletRequest.makeMultipart("/api/avaliacoesresultadoworkup/internacional/exameadicional",  "post")
//			.file(idResultadoWorkupFile)			
//			.file(descricaoFile)			
//			.contentType(MediaType.MULTIPART_FORM_DATA))				
//			.andExpect(status().isOk());
//	}
	
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
