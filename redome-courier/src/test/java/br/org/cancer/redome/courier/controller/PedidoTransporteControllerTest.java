package br.org.cancer.redome.courier.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.courier.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.model.domain.TiposTarefa;
import br.org.cancer.redome.courier.util.BaseConfigurationTest;
import br.org.cancer.redome.courier.util.CreateMockHttpServletRequest;
import br.org.cancer.redome.courier.util.TestHelper;
import br.org.cancer.redome.feign.client.domain.FasesWorkup;
import br.org.cancer.redome.feign.client.domain.TiposSolicitacao;
import br.org.cancer.redome.feign.client.dto.SolicitacaoDTO;
import br.org.cancer.redome.feign.client.dto.TarefaDTO;
import br.org.cancer.redome.feign.client.dto.TipoTarefaDTO;
import br.org.cancer.redome.feign.client.dto.UsuarioDTO;

public class PedidoTransporteControllerTest extends BaseConfigurationTest {
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(12L, "COURIER")
			.addPerfil("TRANSPORTADORA")
			.addRecurso(Recursos.AGENDAR_TRANSPORTE_MATERIAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}

	@Test
	public void deveConfirmarEAgendarTransporteMaterialComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = TestHelper.criarSolicitacao(2L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				TestHelper.criarMatch(1L, 1L, "4353344", TestHelper.criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_RETIRADA_MATERIAL);

		makeStubForPut("/redome/api/solicitacoes/2/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		TarefaDTO tarefaAgendar = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()))
				.relacaoEntidade(2L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls1NV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjIsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTJ9",
				okForContentType("application/json;charset=UTF-8",
						TestHelper.montarRetornoListarTarfaJson(Arrays.asList(tarefaAgendar))));

		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAgendar)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(false)
				.idCourier(1L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/2/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}
	
	
	@Test
	public void deveConfirmarEAgendarTransporteMaterialAereoComSucesso() throws Exception {
		
		makeStubForPatch("/redome-workup/api/pedidologistica/2/material/nacional/aereo", WireMock.ok());
		
		SolicitacaoDTO solicitacao = TestHelper.criarSolicitacao(2L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				TestHelper.criarMatch(1L, 1L, "4353344", TestHelper.criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_DOCUMENTACAO_PEDIDO_TRANSPORTE_AEREO);

		makeStubForPut("/redome/api/solicitacoes/2/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		TarefaDTO tarefaAgendar = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()))
				.relacaoEntidade(3L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls1NV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjMsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTJ9",
				okForContentType("application/json;charset=UTF-8",
						TestHelper.montarRetornoListarTarfaJson(Arrays.asList(tarefaAgendar))));

		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAgendar)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(true)
				.dadosVoo("teste dos dados")
				.idCourier(1L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/3/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}
	
	@Test
	public void deveConfirmarEAgendarTransporteMaterialAereoAguardandoconfirmacaoComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = TestHelper.criarSolicitacao(2L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				TestHelper.criarMatch(1L, 1L, "4353344", TestHelper.criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_RETIRADA_MATERIAL);

		makeStubForPut("/redome/api/solicitacoes/2/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		TarefaDTO tarefaAgendar = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.ANALISAR_PEDIDO_TRANSPORTE.getId()))
				.relacaoEntidade(6L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls1NV0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjYsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MTJ9",
				okForContentType("application/json;charset=UTF-8",
						TestHelper.montarRetornoListarTarfaJson(Arrays.asList(tarefaAgendar))));

		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAgendar)));
		
		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()				
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/6/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}
	
	
	@Test
	public void naoDeveConfirmarEAgendarTransporteMaterialSePedidoNaoExistir() throws Exception {
		

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(false)
				.dadosVoo("teste dos dados")
				.idCourier(1L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/0/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveConfirmarEAgendarTransporteMaterialSeStatusDiferenteAberto() throws Exception {
		

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(false)
				.dadosVoo("teste dos dados")
				.idCourier(1L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/4/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}
	
	
	@Test
	public void naoDeveConfirmarEAgendarTransporteMaterialSeCourierNaoExistir() throws Exception {
		

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(true)
				.dadosVoo("teste dos dados")
				.idCourier(2L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/5/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveConfirmarEAgendarTransporteMaterialAereoSeDadosDoVooNaoInformados() throws Exception {
		

		ConfirmacaoTransporteDTO dados = ConfirmacaoTransporteDTO.builder()
				.voo(true)
				.idCourier(1L)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidotransporte/5/confirmaragendamento")
			.content(getObjectMapper().writeValueAsBytes(dados))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}


}
