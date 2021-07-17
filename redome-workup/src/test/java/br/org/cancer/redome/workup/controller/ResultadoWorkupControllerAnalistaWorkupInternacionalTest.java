package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
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
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.RetornoInclusaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.ArquivoResultadoWorkup;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class ResultadoWorkupControllerAnalistaWorkupInternacionalTest extends BaseConfigurationTest {
	
	@Value("classpath:resultado_workup.pdf")
	Resource resourceFile;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(20L, "ANALISTA_WORKUP_INTERNACIONAL")
			.addPerfil("ANALISTA_WORKUP_INTERNACIONAL")
			.addRecurso(Recursos.CADASTRAR_RESULTADO_WORKUP_INTERNACIONAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveSalvarArquivoResultadoWorkupComSucesso() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "10".getBytes());
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveSalvarArquivoResultadoWorkupSePedidoWorkupFinalizadoOuCancelado() throws Exception {
		
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "11".getBytes());
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveSalvarArquivoResultadoWorkupSeResultadoWorkupExistir() throws Exception {		
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "12".getBytes());
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	
	@Test
	public void deveExcluirArquivoResultadoWorkupComSucesso() throws Exception {		
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "13".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);
		
		MockMultipartFile caminhoDTO = new MockMultipartFile("caminho", null, "application/json", retornoInclusao.getStringRetorno().getBytes());		
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "delete")
				.file(idPedidoWorkupDTOFile)
				.file(caminhoDTO)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
		
		
	}
	
	@Test
	public void naoDeveExcluirArquivoResultadoWorkupSePedidoWorkupFinalizadoOuCancelado() throws Exception {		
			
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "11".getBytes());
								
		MockMultipartFile caminhoDTO = new MockMultipartFile("caminho", null, "application/json", "pedidoworkup/11/resultadoworkup/teste.pdf".getBytes());		
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "delete")
				.file(idPedidoWorkupDTOFile)
				.file(caminhoDTO)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
		
	}
	
	@Test
	public void naoDeveExcluirArquivoResultadoWorkupSeResultadoWorkupExisitir() throws Exception {		
			
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "12".getBytes());
								
		MockMultipartFile caminhoDTO = new MockMultipartFile("caminho", null, "application/json", "pedidoworkup/11/resultadoworkup/teste.pdf".getBytes());		
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "delete")
				.file(idPedidoWorkupDTOFile)
				.file(caminhoDTO)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveExcluirArquivoResultadoWorkupSeCaminhoInvalido() throws Exception {		
			
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "13".getBytes());
								
		MockMultipartFile caminhoDTO = new MockMultipartFile("caminho", null, "application/json", "pedidoworkup/11/resultadoworkup/teste.pdf".getBytes());		
				
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "delete")
				.file(idPedidoWorkupDTOFile)
				.file(caminhoDTO)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void deveGravarResultadoWorkupcomSucesso() throws Exception {
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "14".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP);

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(14L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjBdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjoxNCwicm1yIjozMDAwMDAsImlkRG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1YWxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YXJlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjoyMH0%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho(retornoInclusao.getStringRetorno())
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.coletaInviavel(false)
				.doadorIndisponivel(false)
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveGravarResultadoWorkupSeColetaInviavelNaoforInformada() throws Exception {
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "14".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);		
				
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho(retornoInclusao.getStringRetorno())
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.coletaInviavel(null)
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.mensagem", is("Ocorreu um erro de validação.")))
			.andExpect(jsonPath("$.erros[0].campo", is("coletaInviavel")));
		
	}
	
	@Test
	public void naoDeveGravarResultadoWorkupSeColetaInviavelInformadaEDoadorIndisponivelNaoInformado() throws Exception {
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "14".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);		
				
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho(retornoInclusao.getStringRetorno())
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.coletaInviavel(true)
				.doadorIndisponivel(null)
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.mensagem", is("Ocorreu um erro de validação.")))
			.andExpect(jsonPath("$.erros[0].campo", is("doadorIndisponivel")));
		
	}
	
	@Test
	public void deveGravarResultadoWorkupComColetaInviavelEDoadorIndisponivelComSucesso() throws Exception {
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "25".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP);

		makeStubForPost("/redome/api/solicitacoes/5/cancelar/workup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForGet("/redome/api/solicitacoes/5", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(25L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjBdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjoyNSwicm1yIjozMDAwMDAsImlkRG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1YWxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YXJlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjoyMH0%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
				
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho(retornoInclusao.getStringRetorno())
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.coletaInviavel(true)
				.doadorIndisponivel(true)
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveSalvarResultadoWorkupSePedidoWorkupFinalizadoOuCancelado() throws Exception {
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "11".getBytes());
		
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho("pedidoworkup/11/resultadoworkup")
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.coletaInviavel(false)
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveSalvarResultadoWorkupSeResultadoWorkupExistir() throws Exception {
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "12".getBytes());
		
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho("pedidoworkup/11/resultadoworkup")
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());		
	}
	
//	@Test
//	public void naoDeveSalvarResultadoWorkupSeListaArquivosEmBranco() throws Exception {
//		
//		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "15".getBytes());
//				
//		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
//				.build();
//		
//		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
//		
//		mockMvc
//			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
//			.file(idPedidoWorkupDTOFile)
//			.file(resultadoWorkupDTOFile)				
//	        .contentType(MediaType.MULTIPART_FORM_DATA))				
//			.andExpect(status().is4xxClientError());		
//	}
	
	@Test
	public void naoDeveSalvarResultadoWorkupSeStorageNaoPosuirOsArquivos() throws Exception {
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "15".getBytes());
		
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho("pedidoworkup/15/resultadoworkup/teste.pdf")
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
	        .contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().is4xxClientError());		
	}
	
	@Test
	public void naoDeveGravarResultadoWorkupSeArquivoNaoExistirNoStorage() throws Exception {
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "resultado_workup.pdf", "application/pdf", in);
		
		MockMultipartFile idPedidoWorkupDTOFile = new MockMultipartFile("idPedidoWorkup", null, "application/json", "16".getBytes());
		
		String retorno = mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup/arquivo",  "post")
				.file(idPedidoWorkupDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		RetornoInclusaoDTO retornoInclusao = getObjectMapper().readValue(retorno, RetornoInclusaoDTO.class);
		
	
		ArquivoResultadoWorkup arquivoResultadoWorkup = ArquivoResultadoWorkup.builder()
				.caminho(retornoInclusao.getStringRetorno())
				.build();
		
		ArquivoResultadoWorkup arquivoResultadoWorkupInexistente = ArquivoResultadoWorkup.builder()
				.caminho("pedidoworkup/16/resultadoworkup/teste.pdf")
				.build();
		
		ResultadoWorkup resultadoWorkup = ResultadoWorkup.builder()
				.arquivosResultadoWorkup(Arrays.asList(arquivoResultadoWorkup, arquivoResultadoWorkupInexistente))
				.build();
		
		MockMultipartFile resultadoWorkupDTOFile = new MockMultipartFile("resultadoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/resultadosworkup",  "post")
			.file(idPedidoWorkupDTOFile)
			.file(resultadoWorkupDTOFile)				
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
