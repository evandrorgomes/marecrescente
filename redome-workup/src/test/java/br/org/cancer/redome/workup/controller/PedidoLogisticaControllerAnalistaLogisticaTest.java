package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DetalheLogisticaMaterialDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.PedidoTransporteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.TransportadoraDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoLogisticaControllerAnalistaLogisticaTest extends BaseConfigurationTest {
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(12L, "ANALISTA_LOGISTICA")
			.addPerfil("ANALISTA_LOGISTICA")
			.addRecurso(Recursos.SOLICITAR_LOGISTICA_MATERIAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}

	@Test
	public void deveSalvarLogisticaMaterialColetaComSucesso() throws Exception {

		DetalheLogisticaMaterialDTO detalhe = DetalheLogisticaMaterialDTO.builder()
				.idPedidoLogistica(31L)
				.horaPrevistaRetirada(LocalDateTime.now())
				.transportadora(1L)
				.prosseguirComPedidoLogistica(true)
				.build();
		
//		makeStubForGet("/redome-courier/api/pedidostransporte/emanalise?idPedidoLogistica=31", 
//				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));
//
//		makeStubForPost("/redome-courier/api/pedidostransporte", 
//				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/31/material/nacional/salvar")
		.content(getObjectMapper().writeValueAsBytes(detalhe))				
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
	}

	@Test
	public void deveSalvarLogisticaMaterialColetaEAtualizarPedidoTransporteComSucesso() throws Exception {

		DetalheLogisticaMaterialDTO detalhe = DetalheLogisticaMaterialDTO.builder()
				.idPedidoLogistica(31L)
				.prosseguirComPedidoLogistica(false)
				.justificativa("Justificativa de não presseguir")
				.build();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/31/material/nacional/salvar")
		.content(getObjectMapper().writeValueAsBytes(detalhe))				
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveFinalizarLogisticaMaterialColetaComSucesso() throws Exception {

		DetalheLogisticaMaterialDTO detalhe = DetalheLogisticaMaterialDTO.builder()
				.idPedidoLogistica(31L)
				.horaPrevistaRetirada(LocalDateTime.now())
				.transportadora(1L)
				.prosseguirComPedidoLogistica(true)
				.build();
		
		TransportadoraDTO transportadora = TransportadoraDTO.builder().id(1L).build();
		
		PedidoTransporteDTO pedidoTransporte = PedidoTransporteDTO.builder()
				.transportadora(transportadora)
				.id(23L)
				.build();
		
//		makeStubForGet("/redome-courier/api/pedidostransporte/emanalise?idPedidoLogistica=31", 
//				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));

		makeStubForPost("/redome-courier/api/pedidostransporte", 
				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));
		
		
		SolicitacaoDTO solicitacao = criarSolicitacao(8L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
		UsuarioDTO.builder().id(12L).nome("ANALISTA LOGISTICA").build(),
		criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Logistica Internacional").build(),
		FasesWorkup.AGUARDANDO_ANALISE_PEDIDO_TRANSPORTE);

		makeStubForPut("/redome/api/solicitacoes/8/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
		BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaLogistica = TarefaDTO.builder()
		.id(1L)
		.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
		.relacaoEntidade(6L)
		.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjddLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozMSwicm1yIjozMDAwMDAsImlk" + 
				"RG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1Y" + 
				"WxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YX" + 
				"JlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjo" + 
				"xMn0%3D",
		okForContentType("application/json;charset=UTF-8",
				montarRetornoListarTarfaJson(Arrays.asList(tarefaLogistica))));

		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
		BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaLogistica)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/31/material/nacional/finalizar")
		.content(getObjectMapper().writeValueAsBytes(detalhe))				
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
	}

//	@Test
	public void deveFinalizarLogisticaMaterialColetaEEncerrarPedidoLogisticaEPedidoTransporteComSucesso() throws Exception {

		DetalheLogisticaMaterialDTO detalhe = DetalheLogisticaMaterialDTO.builder()
				.idPedidoLogistica(31L)
				.horaPrevistaRetirada(LocalDateTime.now())
				.transportadora(1L)
				.prosseguirComPedidoLogistica(false)
				.justificativa("Justificativa não prosseguimento")
				.build();
		
		TransportadoraDTO transportadora = TransportadoraDTO.builder().id(1L).build();
		
		PedidoTransporteDTO pedidoTransporte = PedidoTransporteDTO.builder()
				.id(23L)
				.transportadora(transportadora)
				.build();

		makeStubForGet("/redome-courier/api/pedidostransporte/emanalise?idPedidoLogistica=31", 
				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));

		makeStubForPut("/redome-courier/api/pedidostransporte/23", 
				okForContentType("application/json;charset=UTF-8",BaseConfigurationTest.getObjectMapper().writeValueAsString(pedidoTransporte)));

		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/31/material/nacional/finalizar")
		.content(getObjectMapper().writeValueAsBytes(detalhe))				
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
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
