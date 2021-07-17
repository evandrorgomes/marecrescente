package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
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
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoSolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.dto.UsuarioDTO;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.StatusPedidosColeta;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoColetaControllerTest extends BaseConfigurationTest {
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(1964L, "MEDICO_CENTRO_COLETA")
			.addPerfil("MEDICO_CENTRO_COLETA")
			.addRecurso(Recursos.AGENDAR_PEDIDO_COLETA);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}

	@Test
	public void deveAgendarPedidoColetaComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(45L, TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO CENTRO COLETA").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Coleta", 300000L)), UsuarioDTO.builder().id(9L).nome("Analista Workup").build(),
				FasesWorkup.AGUARDANDO_DEFINICAO_LOGISTICA);

		makeStubForPut("/redome/api/solicitacoes/45/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(1L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
				.relacaoEntidade(1L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ"
				+ "lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl"
				+ "aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjRdLCJzdGF0dXNUY"
				+ "XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm"
				+ "51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5"
				+ "1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjo0MCwicm1yIjozMDAwMDAsImlk"
				+ "RG9hZG9yIjpudWxsLCJ0aXBvUHJvY2Vzc28iOjUsImF0cmlidWlkb1F1Y"
				+ "WxxdWVyVXN1YXJpbyI6ZmFsc2UsInRhcmVmYVBhaUlkIjpudWxsLCJ0YX"
				+ "JlZmFTZW1BZ2VuZGFtZW50byI6dHJ1ZSwiaWRVc3VhcmlvTG9nYWRvIjo"
				+ "zMn0%3D",
				okForContentType("application/json;charset=UTF-8",
						montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
		
		
		makeStubForPost("/redome-tarefa/api/tarefa/1/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-notificacao/api/notificacoes/criarnotificacao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		PedidoColeta pedidoColeta = PedidoColeta.builder()
				.id(40L)
				.status(StatusPedidosColeta.AGENDADO.getId())
				.tipoProdudo(0L)
				.dataInicioGcsf(null)
				.dataInternacao(LocalDateTime.now().plusDays(1))
				.gcsfSetorAndar("Setor gcsf 01")
				.gcsfProcurarPor("Nome gcsf 1")
				.internacaoSetorAndar("Setor internacao 2")
				.internacaoProcurarPor("nome internacao 2")
				.estaEmJejum(true)
				.quantasHorasEmJejum(2L)
				.estaTotalmenteEmJejum(true)
				.informacoesAdicionais("Texto informações adicionais")
				.solicitacao(45L)
				.build();
		
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/pedidocoleta/40")
				.param("idPedidoColeta","40")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(pedidoColeta))
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
