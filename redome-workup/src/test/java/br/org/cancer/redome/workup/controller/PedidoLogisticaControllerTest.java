package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DetalheLogisticaInternacionalColetaDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.LogisticaDoadorWorkupDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.ArquivoVoucherLogistica;
import br.org.cancer.redome.workup.model.TransporteTerrestre;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.model.domain.TiposVoucher;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoLogisticaControllerTest extends BaseConfigurationTest {
	
	@Value("classpath:voucher.pdf")
	Resource resourceFile;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(20L, "ANALISTA_WORKUP_INTERNACIONAL")
			.addPerfil("ANALISTA_WORKUP_INTERNACIONAL")
			.addRecurso(Recursos.EFETUAR_LOGISTICA)
			.addRecurso(Recursos.EFETUAR_LOGISTICA_INTERNACIONAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveSalvarArquivoVoucherLogisticaDoadorWorkupComSucesso() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "voucher.pdf", "application/pdf", in);
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidologistica/1/voucher",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveSalvarArquivoVoucherLogisticaSePedidoLogisticaFinalizadoOuCancelado() throws Exception {
		
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "voucher.pdf", "application/pdf", in);
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidologistica/2/voucher",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	
	@Test
	public void deveExcluirArquivoVoucherLogisicaComSucesso() throws Exception {		
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "voucher.pdf", "application/pdf", in);
				
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidologistica/3/voucher",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/3/voucher")
				.content(retorno.getBytes())
		        .contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().isOk());
		
		
	}
	
	@Test
	public void naoDeveExcluirArquivoVoucherLogisticaSePedidoLogisticaFinalizadoOuCancelado() throws Exception {		
												
		mockMvc
				.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/2/voucher")
				.content("pedidoworkup/7/pedidoLogistica/2/voucher/teste.pdf".getBytes())				
		        .contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().is4xxClientError());
		
		
	}
	
	@Test
	public void naoDeveExcluirArquivoVoucherLogisticaSeCaminhoInvalido() throws Exception {													
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/3/voucher")
				.content("pedidoworkup/7/pedidoLogistica/3/voucher/teste.pdf".getBytes())				
				.contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void deveSalvarLogisticaDoadorWorkupComSucesso() throws Exception {
		
		LogisticaDoadorWorkupDTO logisticaDTO = LogisticaDoadorWorkupDTO.builder()
				.observacao("texto da observação")
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/4")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
		
	}	
	
	@Test
	public void deveSalvarLogisticaDoadorWorkupComTodosOsDadosComSucesso() throws Exception {
		
		ArquivoVoucherLogistica passagem = ArquivoVoucherLogistica.builder()
				.caminho("teste/teste/teste.pdf")
				.comentario("passagem de ida")
				.tipo(TiposVoucher.TRANSPORTE_AEREO.getCodigo())
				.build();
		
		ArquivoVoucherLogistica hospedagem = ArquivoVoucherLogistica.builder()
				.caminho("teste/teste/teste.pdf")
				.comentario("Hospedagem de 2 dia")
				.tipo(TiposVoucher.HOSPEDAGEM.getCodigo())
				.build();
		
		TransporteTerrestre transporte = TransporteTerrestre.builder()
				.origem("")
				.destino("")
				.dataRealizacao(LocalDateTime.now())
				.build();
				
		
		LogisticaDoadorWorkupDTO logisticaDTO = LogisticaDoadorWorkupDTO.builder()
				.observacao("texto da observação 1")
				.aereos(Arrays.asList(passagem))
				.transporteTerrestre(Arrays.asList(transporte))
				.hospedagens(Arrays.asList(hospedagem))
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/4")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());		
		
		
	}
	
	
	@Test
	public void deveSalvarLogisticaDoadorWorkupExcluindoVoucheETransporteComSucesso() throws Exception {
		
		ArquivoVoucherLogistica passagem = ArquivoVoucherLogistica.builder()
				.id(1L)
				.caminho("passagem.pdf")
				.comentario("passagem de ida")
				.tipo(TiposVoucher.TRANSPORTE_AEREO.getCodigo())
				.excluido(true)
				.build();
		
		ArquivoVoucherLogistica passagemIncluida = ArquivoVoucherLogistica.builder()
				.caminho("teste/teste/teste.pdf")
				.comentario("passagem de ida")
				.tipo(TiposVoucher.TRANSPORTE_AEREO.getCodigo())
				.build();
		
		
		ArquivoVoucherLogistica hospedagem = ArquivoVoucherLogistica.builder()
				.id(2L)
				.caminho("hospedagem.pdf")
				.comentario("Hospedagem de 2 dia")
				.tipo(TiposVoucher.HOSPEDAGEM.getCodigo())
				.excluido(true)
				.build();
		
		ArquivoVoucherLogistica hospedagemIncluida = ArquivoVoucherLogistica.builder()
				.caminho("teste/teste/teste.pdf")
				.comentario("Hospedagem de 2 dia")
				.tipo(TiposVoucher.HOSPEDAGEM.getCodigo())
				.build();
		
		TransporteTerrestre transporte = TransporteTerrestre.builder()
				.id(1L)
				.origem("")
				.destino("")
				.dataRealizacao(LocalDateTime.now())
				.excluido(true)
				.build();
		
		TransporteTerrestre transporteIncluido = TransporteTerrestre.builder()
				.origem("")
				.destino("")
				.dataRealizacao(LocalDateTime.now())
				.build();
				
		
		LogisticaDoadorWorkupDTO logisticaDTO = LogisticaDoadorWorkupDTO.builder()
				.observacao("texto da observação 1")
				.aereos(Arrays.asList(passagem, passagemIncluida))
				.transporteTerrestre(Arrays.asList(transporte, transporteIncluido))
				.hospedagens(Arrays.asList(hospedagem, hospedagemIncluida))
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/5")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());		
		
		
	}
	
	
	@Test
	public void deveEncerrarLogisticaDoadorWorkupComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_RESULTADO_WORKUP);

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getId()))
				.relacaoEntidade(6L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlszNl0sInN0YXR1c1RhcmVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOjYsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MjB9",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		LogisticaDoadorWorkupDTO logisticaDTO = LogisticaDoadorWorkupDTO.builder()
				.observacao("texto da observação")
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/6")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}

	
	/* ################################################################################################# */
	
	@Test
	public void deveFinalizarLogisticaColetaDoadorInternacionalComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(8L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Logistica Internacional").build(),
				FasesWorkup.AGUARDANDO_RECEBIMENTO_COLETA);
		
		makeStubForPut("/redome/api/solicitacoes/8/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RECEBIMENTO_COLETA.getId()))
				.relacaoEntidade(6L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOls4NF0sInN0YXR1c1Rhc" + 
				"mVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bn" + 
				"VsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnV" + 
				"sbCwicmVsYWNhb0VudGlkYWRlSWQiOjMzLCJybXIiOjMwMDAwMCwiaWRE" + 
				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjI" + 
				"wfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/33/coleta/internacional/finalizar")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
	}

	@Test
	public void deveSalvarLogisticaColetaDoadorInternacionalComSucesso() throws Exception {

		DetalheLogisticaInternacionalColetaDTO detalhe = DetalheLogisticaInternacionalColetaDTO.builder()
				.retiradaIdDoador("1234")
				.retiradaHawb("12343")
				.nomeCourier("João")
				.passaporteCourier("Passaporte")
				.dataEmbarque(LocalDate.now())
				.dataChegada(LocalDate.now())
				.retiradaLocal("Local da Retirada")
				.build();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/33/coleta/internacional")
		.content(getObjectMapper().writeValueAsBytes(detalhe))				
		.contentType(BaseConfigurationTest.CONTENT_TYPE))				
		.andExpect(status().isOk());
	}

	@Test
	public void deveObterPedidoLogisticaDoadorInternacionalColetaComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(8L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", 
				criarBusca(16L, "Nome do Centro de Transplante", 300000L)), 
				UsuarioDTO.builder().id(20L).nome("Analista Logistica Internacional").build(),
				FasesWorkup.AGUARDANDO_RECEBIMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/8", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makeGet("/api/pedidologistica/33/material/internacional")
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
