package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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
import br.org.cancer.redome.workup.dto.PacienteDTO;
import br.org.cancer.redome.workup.dto.ResultadoWorkupNacionalDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.FontesCelulas;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class ResultadoWorkupControllerTest extends BaseConfigurationTest {
	
	@BeforeClass
	public static void setupClass1() {
		makeAuthotization.paraUsuario(1964L, "MEDICO_CENTRO_COLETA")
			.addPerfil("MEDICO_CENTRO_COLETA")
			.addRecurso(Recursos.FINALIZAR_RESULTADO_WORKUP_NACIONAL);
	}

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	@Test
	public void deveCriarResultadoWorkupNacionalcomSucesso() throws Exception {
		
		SolicitacaoDTO solicitacao = criarSolicitacao(5L, 
				TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", 
				criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP);

		ResultadoWorkupNacionalDTO resultadoWorkupNacionalDTO = ResultadoWorkupNacionalDTO.builder()
			.coletaInviavel(false)
			.dataColeta(LocalDate.now())
			.idFonteCelula(FontesCelulas.MEDULA_OSSEA.getFonteCelulaId())
			.sangueAntologoColetado(true)
			.build();
		
		makeStubForPut("/redome/api/solicitacoes/4/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_RESULTADO_WORKUP_NACIONAL.getId()))
				.relacaoEntidade(14L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlszNV0sInN0YXR1c1Rhc" + 
				"mVmYSI6WzJdLCJzdGF0dXNQcm9jZXNzbyI6MSwicHJvY2Vzc29JZCI6bn" + 
				"VsbCwiaW5jbHVzaXZvRXhjbHVzaXZvIjpudWxsLCJwYWdlYWJsZSI6bnV" + 
				"sbCwicmVsYWNhb0VudGlkYWRlSWQiOjEsInJtciI6MzAwMDAwLCJpZERv" + 
				"YWRvciI6bnVsbCwidGlwb1Byb2Nlc3NvIjo1LCJhdHJpYnVpZG9RdWFsc" + 
				"XVlclVzdWFyaW8iOmZhbHNlLCJ0YXJlZmFQYWlJZCI6bnVsbCwidGFyZW" + 
				"ZhU2VtQWdlbmRhbWVudG8iOnRydWUsImlkVXN1YXJpb0xvZ2FkbyI6MzJ" + 
				"9",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/resultadosworkup/nacional")
			.param("idPedidoWorkup", "1")	
			.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(resultadoWorkupNacionalDTO))
			.contentType(MediaType.APPLICATION_JSON_UTF8))
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
