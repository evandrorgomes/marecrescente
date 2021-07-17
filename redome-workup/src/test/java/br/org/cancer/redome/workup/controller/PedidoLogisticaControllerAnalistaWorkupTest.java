package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
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
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.LogisticaDoadorColetaDTO;
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

public class PedidoLogisticaControllerAnalistaWorkupTest extends BaseConfigurationTest {
	
	@Value("classpath:voucher.pdf")
	Resource resourceFile;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(9L, "ANALISTA_WORKUP")
			.addPerfil("ANALISTA_WORKUP")
			.addRecurso(Recursos.EFETUAR_LOGISTICA_DOADOR_COLETA);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveSalvarLogisticaDoadorColetaProsseguirComSucesso() throws Exception {

		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação")
				.prosseguirComPedidoLogistica(true)
				.build();
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}	

	@Test
	public void deveSalvarLogisticaDoadorColetaNaoProsseguirComSucesso() throws Exception {

		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação")
				.prosseguirComPedidoLogistica(false)
				.justificativa("Justificativa do não prosseguimento.")
				.build();
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}	
	
	@Test
	public void deveSalvarLogisticaDoadorColetaComTodosOsDadosComSucesso() throws Exception {
		
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
		
		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação 1")
				.aereos(Arrays.asList(passagem))
				.transporteTerrestre(Arrays.asList(transporte))
				.hospedagens(Arrays.asList(hospedagem))
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());		
		
		
	}
	
	
	@Test
	public void deveSalvarLogisticaDoadorColetaExcluindoVoucheETransporteComSucesso() throws Exception {
		
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
				
		
		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação 1")
				.aereos(Arrays.asList(passagem, passagemIncluida))
				.transporteTerrestre(Arrays.asList(transporte, transporteIncluido))
				.hospedagens(Arrays.asList(hospedagem, hospedagemIncluida))
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());		
	}
	
	
	@Test
	public void deveSalvarArquivoVoucherLogisticaComSucesso() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "voucher.pdf", "application/pdf", in);
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidologistica/32/voucher",  "post")
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
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidologistica/32/voucher",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/32/voucher")
				.content(retorno.getBytes())
		        .contentType(BaseConfigurationTest.CONTENT_TYPE))				
				.andExpect(status().isOk());
	}

	
	@Test
	public void naoDeveExcluirArquivoVoucherLogisticaSePedidoLogisticaFinalizadoOuCancelado() throws Exception {		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/32/voucher")
			.content("pedidocoleta/2/pedidoLogistica/32/voucher/teste.pdf".getBytes())				
	        .contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveExcluirArquivoVoucherLogisticaSeCaminhoInvalido() throws Exception {													
		mockMvc
			.perform(CreateMockHttpServletRequest.makeDelete("/api/pedidologistica/32/voucher")
			.content("pedidocoleta/2/pedidoLogistica/32/voucher/teste.pdf".getBytes())				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
	}
	
	
//	@Test
	public void deveEncerrarLogisticaDoadorColetaNaoProsseguirComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(8L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_RESULTADO_DOADOR_COLETA);

		makeStubForPut("/redome/api/solicitacoes/8/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_DOADOR_COLETA.getId()))
				.relacaoEntidade(32L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjhdLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozMiwicm1yIjozMDAwMDAsImlk" + 
				"RG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1Y" + 
				"WxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YX" + 
				"JlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjo" + 
				"5fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação")
				.prosseguirComPedidoLogistica(false)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
	}

	
//	@Test
	public void deveEncerrarLogisticaDoadorColetaProsseguirComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(8L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_RESULTADO_DOADOR_COLETA);

		makeStubForPut("/redome/api/solicitacoes/8/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_DOADOR_COLETA.getId()))
				.relacaoEntidade(32L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjhdLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozMiwicm1yIjozMDAwMDAsImlk" + 
				"RG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1Y" + 
				"WxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YX" + 
				"JlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjo" + 
				"5fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		LogisticaDoadorColetaDTO logisticaDTO = LogisticaDoadorColetaDTO.builder()
				.observacao("texto da observação")
				.prosseguirComPedidoLogistica(true)
				.build();
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/pedidologistica/doador/32")
			.content(getObjectMapper().writeValueAsBytes(logisticaDTO))				
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
