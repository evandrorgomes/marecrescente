package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.MedicoDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class DistribuicaoWorkupControllerTest extends BaseConfigurationTest {

	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(9L, "ANALISTA_WORKUP")
			.addPerfil("ANALISTA_WORKUP", "DISTRIBUIDOR_WORKUP_NACIONAL")
			.addRecurso("DISTRIBUIR_WORKUP");
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveListarDistribuicaoWorkupComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(3L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), null,
				FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);

		makeStubForGet("/redome/api/solicitacoes/3", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaDistribuicao = TarefaDTO.builder().id(1L).relacaoEntidade(1L).build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6WzMwXSwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTBdLCJzdGF0dXNUYXJlZmEiOlsxLDJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bnVsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnVsbCwicmVsYWNhb0VudGlkYWRlSWQiOm51bGwsInJtciI6bnVsbCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6bnVsbCwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaDistribuicao))));

		MedicoDTO medico = MedicoDTO.builder().id(1L).nome("Medico do centro").build();

		makeStubForGet("/redome/api/medicos/1", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));

		mockMvc.perform(CreateMockHttpServletRequest.makeGet("/api/distribuicoesworkup").param("pagina", "0")
				.param("quantidadeRegistros", "10").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].idTarefaDistribuicaoWorkup", is(1)));
	}

	@Test
	public void deveListarDistribuicaoWorkupFeitasComSolicitacaoEmAbertoComSucesso() throws Exception {

		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(4L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR);

		makeStubForGet("/redome/api/solicitacoes?tiposSolicitacao=8%2C10&statusSolicitacao=1",
				okForContentType("application/json;charset=UTF-8",
						BaseConfigurationTest.getObjectMapper().writeValueAsString(Arrays.asList(solicitacao))));

		makeStubForGet("/redome-auth/api/perfil/9/usuarios",
				okForContentType("application/json;charset=UTF-8",
						BaseConfigurationTest.getObjectMapper().writeValueAsString(
								Arrays.asList(UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(),
										UsuarioDTO.builder().id(10L).nome("Outro Analista Wotkup").build()))));

		MedicoDTO medico = MedicoDTO.builder().id(1L).nome("Medico do centro").build();

		makeStubForGet("/redome/api/medicos/1", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(medico)));

		mockMvc.perform(CreateMockHttpServletRequest.makeGet("/api/distribuicoesworkup/usuarios"))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.[\"Analista Wotkup\"]", hasSize(1)))
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup").exists())
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.idDistribuicaoWorkup").value(2))
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.rmr").value(300000))
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.nomeCentroTransplante")
						.value("Centro de Transplante"))
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.medicoResponsavelPrescricao")
						.value("Medico do centro"))
				.andExpect(jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.tipoPrescricao").value("Medula"))
				.andExpect(
						jsonPath("$.[\"Analista Wotkup\"][0].distribuicoesWorkup.identificacaoDoador").value("4353344"))
				.andExpect(jsonPath("$.[\"Outro Analista Wotkup\"]", hasSize(1)))
				.andExpect(jsonPath("$.[\"Outro Analista Wotkup\"][0].distribuicoesWorkup").doesNotExist());
	}

	@Test
	public void deveAtribuirUsuarioDistribuicaoMedulaNacionalComSucesso() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(9L).build();
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));		
		
		makeStubForPut("/redome/api/solicitacoes/5/atribuirusuarioresponsavel",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR);
		
		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		TarefaDTO tarefaDistribuicao = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjksInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTBdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozLCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaDistribuicao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaDistribuicao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/3")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("Workup distribuído com sucesso."));
	}
	
	@Test
	public void deveAtribuirUsuarioDistribuicaoMedulaInternacionalComSucesso() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(9L).build();
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);
		
		makeStubForPut("/redome/api/solicitacoes/5/atribuirusuarioresponsavel",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_PLANO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		TarefaDTO tarefaDistribuicao = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjksInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTFdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo0LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaDistribuicao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaDistribuicao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/4")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("Workup distribuído com sucesso."));
	}
	
	@Test
	public void deveAtribuirUsuarioDistribuicaoCordaoNacionalComSucesso() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(9L).build();
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);
		
		makeStubForPut("/redome/api/solicitacoes/5/atribuirusuarioresponsavel",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		TarefaDTO tarefaDistribuicao = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTBdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo1LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaDistribuicao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaDistribuicao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/5")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Rotina não implementada."));
	}
	
	@Test
	public void deveAtribuirUsuarioDistribuicaoCordaoInternacionalComSucesso() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(9L).build();
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForPut("/redome/api/solicitacoes/5/atribuirusuarioresponsavel",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		TarefaDTO tarefaDistribuicao = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTFdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo2LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjl9",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaDistribuicao))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaDistribuicao)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/6")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Rotina não implementada."));
	}
	
	@Test
	public void naoDeveAtribuirUsuarioDistribuicaoMedulaNacionalSeUsuarioNaoExisitir() throws Exception {
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_PLANO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				null));
				
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/7")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Usuário inválido para atribuição."));
	}
	
	@Test
	public void naoDeveAtribuirUsuarioDistribuicaoMedulaNacionalSeDistribuicaoJaFoiFeita() throws Exception {
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(9L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_PLANO_WORKUP);
		
		makeStubForGet("/redome/api/solicitacoes/5",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));		
		
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(9L).build();
		
		makeStubForGet("/redome-auth/api/usuario/9", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
				
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/distribuicaoworkup/8")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Distribuição do Workup já realizada."));
	}
	
	@Test
	public void deveReatribuirUsuarioDistribuicaoComSucesso() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(10L).build();
		
		makeStubForGet("/redome-auth/api/usuario/10", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
		
		SolicitacaoWorkupDTO solicitacao = criarSolicitacao(5L,
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)),
				UsuarioDTO.builder().id(10L).nome("Analista Wotkup").build(), FasesWorkup.AGUARDANDO_FORMULARIO_DOADOR);
		
		makeStubForPut("/redome/api/solicitacoes/5/atribuirusuarioresponsavel",				
				okForContentType("application/json;charset=UTF-8",	BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));
						
		TarefaDTO tarefaFormulario = TarefaDTO.builder().id(1L).build();
		
		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOjksInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTNdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjpudWxsLCJybXIiOm51bGwsImlkRG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOm51bGwsImF0cmlidWlkb1F1YWxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YXJlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjo5fQ%3D%3D",
				okForContentType("application/json;charset=UTF-8", montarRetornoListarTarfaJson(Arrays.asList(tarefaFormulario))));

		TarefaDTO tarefaCancelada = tarefaFormulario.toBuilder().id(1L).build();
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/cancelar", equalTo("false"), 
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaCancelada)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/distribuicaoworkup/8")
				.content("10".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("Workup redistribuido com sucesso."));
	}
	
	@Test
	public void naoDeveReatribuirUsuarioDistribuicaoSeUsuarioNaoExisitir() throws Exception {
		makeStubForGet("/redome-auth/api/usuario/10", okForContentType("application/json;charset=UTF-8",
				null));
				
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/distribuicaoworkup/8")
				.content("10".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Usuário inválido para atribuição."));
	}
	
	@Test
	public void naoDeveReatribuirUsuarioDistribuicaoSeDistribuicaoAindaNaoFoiFeita() throws Exception {
		UsuarioDTO usuarioAnalista = UsuarioDTO.builder().id(10L).build();
		
		makeStubForGet("/redome-auth/api/usuario/10", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarioAnalista)));
				
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/distribuicaoworkup/7")
				.content("9".getBytes())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Distribuição do Workup ainda não foi realizada."));
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
