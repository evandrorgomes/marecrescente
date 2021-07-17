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

import br.org.cancer.redome.workup.dto.BuscaDTO;
import br.org.cancer.redome.workup.dto.CentroTransplanteDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.MatchDTO;
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.PlanoWorkupInternacionalDTO;
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

public class PedidoWorkupControllerAnalistaWorkupInternacionalTest extends BaseConfigurationTest {
	
	@Value("classpath:plano_workup.pdf")
	Resource resourceFile;
	

	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(20L, "ANALISTA_WORKUP_INTERNACIONAL")
			.addPerfil(Perfis.ANALISTA_WORKUP_INTERNACIONAL.name())
			.addRecurso(Recursos.INFORMAR_PLANO_WORKUP_INTERNACIONAL);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveInformarPlanoWorkupInternacionalComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(5L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_ACEITE_PLANO);

		makeStubForPut("/redome/api/solicitacoes/5/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_PLANO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(1L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZlbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNlaXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsxMThdLCJzdGF0dXNUYXJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm51bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo2LCJybXIiOjMwMDAwMCwiaWREb2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhbHF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcmVmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjIwfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		PlanoWorkupInternacionalDTO planoWorkup = PlanoWorkupInternacionalDTO.builder()
				.dataExame(LocalDate.now().plusDays(1))
				.dataResultado(LocalDate.now().plusDays(2))
				.dataColeta(LocalDate.now().plusDays(3))
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "plano_workup.pdf", "application/pdf", in);
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/6/planoworkupinternacional", "post")
				.file(planoWorkupFile)
				.file(mockMultipartFile)				
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk())
				.andExpect(content().string("Plano Workup cadastrado com sucesso."));
	}
	
	@Test
	public void naoDeveInformarPlanoWorkupInternacionalSePlanoJaTenhaSidoInformado() throws Exception {

		PlanoWorkupInternacionalDTO planoWorkup = PlanoWorkupInternacionalDTO.builder()
				.dataExame(LocalDate.now().plusDays(1))
				.dataResultado(LocalDate.now().plusDays(2))
				.dataColeta(LocalDate.now().plusDays(3))
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "plano_workup.pdf", "application/pdf", in);
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/7/planoworkupinternacional", "post")
				.file(planoWorkupFile)
				.file(mockMultipartFile)				
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().is4xxClientError())
				.andExpect(content().string("Erro ao informar plano de workup."));
	}
	
	@Test
	public void naoDeveInformarPlanoWorkupInternacionalSeDataExameMenorQueDataATual() throws Exception {

		PlanoWorkupInternacionalDTO planoWorkup = PlanoWorkupInternacionalDTO.builder()
				.dataExame(LocalDate.now().minusDays(3))
				.dataResultado(LocalDate.now().plusDays(2))
				.dataColeta(LocalDate.now().plusDays(3))
				.observacaoPlanoWorkup("Observação")
				.build();
		
		MockMultipartFile planoWorkupFile = new MockMultipartFile("planoWorkup", null, "application/json", BaseConfigurationTest.getObjectMapper().writeValueAsBytes(planoWorkup));
		
		File file = resourceFile.getFile();
		FileInputStream in = new FileInputStream(file);
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "plano_workup.pdf", "application/pdf", in);
				
		mockMvc.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoworkup/8/planoworkupinternacional", "post")
				.file(planoWorkupFile)
				.file(mockMultipartFile)				
				.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.erros[0].campo", is("dataExame")))
				.andExpect(jsonPath("$.erros[0].mensagem", is("dataExame deve ser maior que a Data Atual.")));
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
