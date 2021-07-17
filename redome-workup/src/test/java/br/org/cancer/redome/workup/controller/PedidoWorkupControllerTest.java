package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
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

import br.org.cancer.redome.workup.dto.AprovarPlanoWorkupDTO;
import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoWorkupControllerTest extends BaseConfigurationTest {
	
	@Value("classpath:plano_workup.pdf")
	Resource resourceFile;

	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(20L, "MEDICO_CENTRO_COLETA")
			.addPerfil(Perfis.MEDICO_CENTRO_COLETA.name())
			.addRecurso(Recursos.INFORMAR_PLANO_WORKUP_NACIONAL, 
					Recursos.APROVAR_PLANO_WORKUP);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveInformarPlanoWorkupComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_ACEITE_PLANO);

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.getId()))
				.relacaoEntidade(1L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTVdLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozLCJybXIiOjMwMDAwMCwiaWRE" + 
				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjM" + 
				"yfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
		
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		PlanoWorkupNacionalDTO planoWorkup = PlanoWorkupNacionalDTO.builder()
				.dataExame(LocalDate.now().plusDays(1))
				.dataResultado(LocalDate.now().plusDays(1))
				.dataInternacao(LocalDate.now().plusDays(1))
				.dataColeta(LocalDate.now().plusDays(1))
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "plano_workup.pdf", "application/pdf", in);
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/3/planoworkupnacional", "post")
				.file(planoWorkupFile)
				.file(mockMultipartFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk())
				.andExpect(content().string("Plano Workup cadastrado com sucesso."));
	}
	
	@Test
	public void deveInformarPlanoWorkupSemArquivoComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_ACEITE_PLANO);

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_PLANO_WORKUP_NACIONAL.getId()))
				.relacaoEntidade(1L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTVdLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo5LCJybXIiOjMwMDAwMCwiaWRE" + 
				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjM" + 
				"yfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
		
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		PlanoWorkupNacionalDTO planoWorkup = PlanoWorkupNacionalDTO.builder()
				.dataExame(LocalDate.now().plusDays(1))
				.dataResultado(LocalDate.now().plusDays(1))
				.dataInternacao(LocalDate.now().plusDays(1))
				.dataColeta(LocalDate.now().plusDays(1))
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
						
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/9/planoworkupnacional", "post")
				.file(planoWorkupFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk())
				.andExpect(content().string("Plano Workup cadastrado com sucesso."));
	}
	
	@Test
	public void naoDeveInformarPlanoWorkupSePlanoJaTenhaSidoInformado() throws Exception {

		PlanoWorkupNacionalDTO planoWorkup = PlanoWorkupNacionalDTO.builder()
				.dataExame(LocalDate.now())
				.dataResultado(LocalDate.now())
				.dataInternacao(LocalDate.now())
				.dataColeta(LocalDate.now())
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/4/planoworkupnacional", "post")
				.file(planoWorkupFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Erro ao informar plano de workup."));
	}
	
	@Test
	public void naoDeveInformarPlanoWorkupSeDataExameMenorQueDataATual() throws Exception {

		PlanoWorkupNacionalDTO planoWorkup = PlanoWorkupNacionalDTO.builder()
				.dataExame(LocalDate.now().minusDays(5))
				.dataResultado(LocalDate.now())
				.dataInternacao(LocalDate.now())
				.dataColeta(LocalDate.now())
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/5/planoworkupnacional", "post")
				.file(planoWorkupFile)
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.erros[0].campo", is("dataExame")))
				.andExpect(jsonPath("$.erros[0].mensagem", is("dataExame deve ser maior que a Data Atual.")));
	}
	
	@Test
	public void deveAprovarPlanoWorkupComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Centro Transplante").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/5", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.CONFIRMAR_PLANO_WORKUP.getId()))
				.relacaoEntidade(1L)
				.build();

		makeStubForGet(
				"/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMTddLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjozLCJybXIiOjMwMDAwMCwiaWRE" + 
				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjM" + 
				"yfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
				montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		AprovarPlanoWorkupDTO planoWorkup = AprovarPlanoWorkupDTO.builder()
				.dataCondicionamento(LocalDate.now().plusDays(1))
				.dataColeta(LocalDate.now().plusDays(1))
				.dataInfusao(LocalDate.now().plusDays(2))
				.criopreservacao(false)
				.observacaoAprovaPlanoWorkup("Observação")
				.build();
				
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/pedidoworkup/3/aprovacaoplanoworkup")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("Plano Workup aprovado com sucesso."));
	}

	@Test
	public void naoDeveAprovarPlanoWorkupSeDataInfusaoMenorQueDataCondicionamento() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Centro Transplante").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/5", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));		

		AprovarPlanoWorkupDTO planoWorkup = AprovarPlanoWorkupDTO.builder()
				.dataCondicionamento(LocalDate.now().plusDays(2))
				.dataColeta(LocalDate.now().plusDays(1))
				.dataInfusao(LocalDate.now().plusDays(1))
				.criopreservacao(false)
				.observacaoAprovaPlanoWorkup("Observação")
				.build();
				
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/pedidoworkup/5/aprovacaoplanoworkup")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.erros[0].campo", is("dataInfusao")))
				.andExpect(jsonPath("$.erros[0].mensagem", is("dataInfusao deve ser maior que a dataCondicionamento.")));
	}

	@Test
	public void naoDeveAprovarPlanoWorkupSeDataInfusaoMenorQueDataColeta() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(9L).nome("Centro Transplante").build(),
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA);
		
		makeStubForGet("/redome/api/solicitacoes/5", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		AprovarPlanoWorkupDTO planoWorkup = AprovarPlanoWorkupDTO.builder()
				.dataCondicionamento(LocalDate.now().plusDays(1))
				.dataColeta(LocalDate.now().plusDays(5))
				.dataInfusao(LocalDate.now().plusDays(2))
				.criopreservacao(false)
				.observacaoAprovaPlanoWorkup("Observação")
				.build();
				
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/pedidoworkup/5/aprovacaoplanoworkup")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup))
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.erros[0].campo", is("dataInfusao")))
				.andExpect(jsonPath("$.erros[0].mensagem", is("dataInfusao deve ser maior que a dataColeta.")));
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
