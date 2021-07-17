package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.status;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

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
import br.org.cancer.redome.workup.dto.CriarPrescricaoCordaoDTO;
import br.org.cancer.redome.workup.dto.CriarPrescricaoMedulaDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.EvolucaoDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.MedicoDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposDoador;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PrescricaoControllerTest extends BaseConfigurationTest {
	
	
	@Value("classpath:prescricao.pdf")
	Resource resourceFile;
	
	@Value("classpath:justificativa.pdf")
	Resource resourceFileJustificativa;
	
	@Value("classpath:autorizacao_paciente.pdf")
	Resource resourceFileAutorizacaoPaciente;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(15L, "MEDICO_TRANSPLANTADOR")
			.addPerfil("TRANSPLANTADOR")
			.addRecurso("CADASTRAR_PRESCRICAO", Recursos.AUTORIZACAO_PACIENTE);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveCriarPresricaoDoadorNacionalPacienteNacionalComSucesso() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, "",300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/doadornacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));
						
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.quantidadePorKgOpcao1(BigDecimal.valueOf(100, 2))
				.quantidadeTotalOpcao1(BigDecimal.valueOf(100, 2))
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveCriarPresricaoDoadorNacionalPacienteNacionalSemFontedeCelula() throws Exception {
						
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveCriarPresricaoDoadorNacionalPacienteNacionalcomFontedeCelulaIguais() throws Exception {
						
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.fonteCelulaOpcao2(1L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void deveCriarPresricaoDoadorNacionalPacienteNacionalComArquivoDeJustificativa() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/doadornacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
						
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		File fileJustificativa = resourceFileJustificativa.getFile();
		FileInputStream inJustificativa = new FileInputStream(fileJustificativa);
		MockMultipartFile mockMultipartFileJustificativa = new MockMultipartFile("filejustificativa", "justificativa.pdf", "application/pdf", inJustificativa);
		
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.quantidadePorKgOpcao1(BigDecimal.valueOf(100, 2))
				.quantidadeTotalOpcao1(BigDecimal.valueOf(100, 2))
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFileJustificativa)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveCriarPresricaoDoadorNacionalPacienteNacionalSemArquivoJustificativa() throws Exception {
						
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
				
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(25))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.quantidadePorKgOpcao1(BigDecimal.valueOf(100, 2))
				.quantidadeTotalOpcao1(BigDecimal.valueOf(100, 2))				
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
						
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());

	}
	
	@Test
	public void naoDeveCriarPresricaoDoadorNacionalPacienteNacionalSemObterOMedico() throws Exception {
						
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
				
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()
				.rmr(300000L)
				.idTipoDoador(TiposDoador.NACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.quantidadePorKgOpcao1(BigDecimal.valueOf(100, 2))
				.quantidadeTotalOpcao1(BigDecimal.valueOf(100, 2))				
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		makeStubForGet("/redome/api/medicos/logado", 
				status(422).withBody("O usuário logado não é um médico e, portanto, não tem acesso a esta funcionalidade."));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("O usuário logado não é um médico e, portanto, não tem acesso a esta funcionalidade."));
	}
	
	@Test
	public void deveCriarPresricaoDoadorInternacionalPacienteNacionalComSucesso() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/doadorinternacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));
						
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoMedulaDTO prescricaoMedulaDTO = CriarPrescricaoMedulaDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.INTERNACIONAL.getId()) 
				.idMatch(1L)
				.dataColeta1(LocalDate.now().plusDays(31))
				.dataLimiteWorkup1(LocalDate.now().plusDays(40))
				.dataColeta2(LocalDate.now().plusDays(32))
				.dataLimiteWorkup2(LocalDate.now().plusDays(41))
				.fonteCelulaOpcao1(1L)
				.quantidadePorKgOpcao1(BigDecimal.valueOf(100, 2))
				.quantidadeTotalOpcao1(BigDecimal.valueOf(100, 2))
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoMedulaDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoMedulaDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/medula",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}
	
	@Test
	public void deveCriarPresricaoCordaoNacionalPacienteNacionalComSucesso() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/cordaonacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));

		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().plusDays(21))
				.dataReceber2(LocalDate.now().plusDays(22))
				.dataReceber3(LocalDate.now().plusDays(23))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalComDataRecebimentoNulo() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalComDataRecebimentoIguais() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().plusDays(21))
				.dataReceber2(LocalDate.now().plusDays(21))
				.dataReceber3(LocalDate.now().plusDays(21))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalComDataRecebimentoMenorQueDataAtual() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().minusDays(21))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalComDataInfusaoMenorQueDataAtual() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().minusDays(1))
				.dataReceber1(LocalDate.now().plusDays(21))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalComDataInfusaoMenorQueDataRecebimento() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(20))
				.dataReceber1(LocalDate.now().plusDays(21))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveCriarPresricaoCordaoNacionalPacienteNacionalSemArquivoJustificativa() throws Exception {
				
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().plusDays(19))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void deveCriarPresricaoCordaoNacionalPacienteNacionalComArquivoJustificatva() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
														criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/cordaonacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		File fileJustificativa = resourceFileJustificativa.getFile();
		FileInputStream inJustificativa = new FileInputStream(fileJustificativa);
		MockMultipartFile mockMultipartFileJustificativa = new MockMultipartFile("filejustificativa", "justificativa.pdf", "application/pdf", inJustificativa);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_NACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().plusDays(21))
				.dataReceber2(LocalDate.now().plusDays(22))
				.dataReceber3(LocalDate.now().plusDays(23))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFileJustificativa)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}	
	
	@Test
	public void deveCriarPresricaoCordaoInternacionalPacienteNacionalComSucesso() throws Exception {
		
		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
		MedicoDTO medico = MedicoDTO.builder().id(15L).build();
						
		makeStubForGet("/redome/api/evolucoes/ultima/300000",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
		
		makeStubForGet("/redome/api/medicos/logado", 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));
		
		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.CORDAO_INTERNACIONAL, 
														UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
														criarMatch(1L, 1L, "4353344", criarBusca(16L, null, 300000L)), null, FasesWorkup.AGUARDANDO_AVALIACAO_PRESCRICAO);
		
		makeStubForPost("/redome/api/solicitacoes/workup/cordaointernacionalpacientenacional", equalTo("1"), 
			okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaAvaliacao = TarefaDTO.builder().id(2L).build();
		
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.post("/redome-tarefa/api/tarefas")
				.withId(UUID.randomUUID())
				.willReturn(okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaAvaliacao))));

		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDVdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOm51bGx9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/atribuir", equalTo("15"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPut("/redome-tarefa/api/tarefa/1",  
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefa)));
				
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOls1Ml0sInN0YXR1c1RhcmVmYSI6WzFdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6MzAwMDAwLCJpZERvYWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFscXVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZWZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6bnVsbH0%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(null)));
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "prescricao.pdf", "application/pdf", in);
		
		CriarPrescricaoCordaoDTO prescricaoCordaoDTO = CriarPrescricaoCordaoDTO.builder()				
				.rmr(300000L)
				.idTipoDoador(TiposDoador.CORDAO_INTERNACIONAL.getId()) 
				.idMatch(1L)
				.dataInfusao(LocalDate.now().plusDays(50))
				.dataReceber1(LocalDate.now().plusDays(21))
				.dataReceber2(LocalDate.now().plusDays(22))
				.dataReceber3(LocalDate.now().plusDays(23))
				.armazenaCordao(false)
				.nomeContatoParaReceber("Nome de contato para receber")
				.nomeContatoUrgente("Nome contato urgente")
				.codigoAreaUrgente(21)
				.telefoneUrgente(98885454L)
				.build();
		
		MockMultipartFile prescricaoMedulaDTOFile = new MockMultipartFile("prescricaoCordaoDTO", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(prescricaoCordaoDTO));
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricoes/cordao",  "post")
				.file(prescricaoMedulaDTOFile)
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void deveSalvarArquivoAutorizacaoPacienteComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(18L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/18", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.relacaoEntidade(23L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjE1LCJwYXJjZWlyb3MiOlsxXSwiaWRzVGlwb3NUYXJlZmEiOls3OCwyMjNdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		File file = resourceFileAutorizacaoPaciente.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "autorizacao_paciente.pdf", "application/pdf", in);
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricao/23/autorizacaopaciente",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void deveSalvarArquivoAutorizacaoPacienteTipoSolicitacaoNacionalComAvaliacaoResultadoWorkupComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(19L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/19", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/19/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
				
		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.relacaoEntidade(24L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjE1LCJwYXJjZWlyb3MiOlsxXSwiaWRzVGlwb3NUYXJlZmEiOls3OCwyMjNdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		File file = resourceFileAutorizacaoPaciente.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "autorizacao_paciente.pdf", "application/pdf", in);
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricao/24/autorizacaopaciente",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
	}


	@Test
	public void deveSalvarArquivoAutorizacaoPacienteTipoSolicitacaoInternacionalComAvaliacaoResultadoWorkupComSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(19L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_LOGISTICA_COLETA_INTERNACIONAL);
		
		makeStubForGet("/redome/api/solicitacoes/19", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/19/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
				
		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.relacaoEntidade(24L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjE1LCJwYXJjZWlyb3MiOlsxXSwiaWRzVGlwb3NUYXJlZmEiOls3OCwyMjNdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjE1fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		File file = resourceFileAutorizacaoPaciente.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "autorizacao_paciente.pdf", "application/pdf", in);
		
		mockMvc
				.perform(CreateMockHttpServletRequest.makeMultipart("/api/prescricao/24/autorizacaopaciente",  "post")
				.file(mockMultipartFile)				
		        .contentType(MediaType.MULTIPART_FORM_DATA))				
				.andExpect(status().isOk());
		
	}

	
	
//	@Test
//	public void deveListarPrescricoesDisponiveisComSucesso() throws Exception {
//		
//		TarefaDTO tarefa = TarefaDTO.builder().id(1L).build();
//		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6WzE2XSwiaWRzVGlwb3NUYXJlZmEiOlsxMDZdLCJzdGF0dXNUYXJlZmEiOlsxXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOnsic29ydCI6eyJzb3J0ZWQiOmZhbHNlLCJ1bnNvcnRlZCI6dHJ1ZSwiZW1wdHkiOnRydWV9LCJwYWdlTnVtYmVyIjoxLCJwYWdlU2l6ZSI6MTAsIm9mZnNldCI6MTAsInBhZ2VkIjp0cnVlLCJ1bnBhZ2VkIjpmYWxzZX0sInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOm51bGwsImlkRG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOm51bGwsImF0cmlidWlkb1F1YWxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YXJlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjpudWxsfQ%3D%3D",
//				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefa))));
//		
//		mockMvc
//		.perform(CreateMockHttpServletRequest.makeGet("/api/prescricoes/disponiveis")
//		.param("idCentroTransplante", "16")
//		.param("pagina", "1")
//		.param("quantidadeRegistros", "10")
//		.contentType(BaseConfigurationTest.CONTENT_TYPE))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//		.andReturn().getResponse().getContentAsString();
//	}

//	@Test
//	public void deveListarPrescricoesPorStatusECentroTransplanteComSucesso() throws Exception {
//		
//		mockMvc
//		.perform(CreateMockHttpServletRequest.makeGet("/api/prescricoes")
//		.param("status", "1,2")
//		.param("idCentroTransplante", "16")
//		.param("pagina", "1")
//		.param("quantidadeRegistros", "10")
//		.contentType(BaseConfigurationTest.CONTENT_TYPE))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//		.andReturn().getResponse().getContentAsString();
//	}
	
//	@Test
//	public void deveObterPrescricacaoComEvolucaoComSucesso() throws Exception {
//		
//		PrescricaoDTO prescricao = PrescricaoDTO.builder().id(1L).build();
//		EvolucaoDTO evolucao = EvolucaoDTO.builder().id(1L).build();
//
//		SolicitacaoDTO solicitacao = criarSolicitacao(1L, TiposSolicitacao.CORDAO_INTERNACIONAL, 
//							UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(), 
//							criarMatch(1L, 1L, "4353344", criarBusca(16L, 300000L)));
//		
//		makeStubForGet("/redome/api/solicitacoes/"+solicitacao.getId(), 
//				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
//
//		makeStubForGet("/redome/api/evolucoes/ultima/300000",
//				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(evolucao)));
//
//		mockMvc
//		.perform(CreateMockHttpServletRequest.makeGet("/api/prescricao/" + prescricao.getId())
//		.contentType(BaseConfigurationTest.CONTENT_TYPE))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//		.andReturn().getResponse().getContentAsString();
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
