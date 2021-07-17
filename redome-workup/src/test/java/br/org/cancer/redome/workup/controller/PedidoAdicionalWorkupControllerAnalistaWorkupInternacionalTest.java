package br.org.cancer.redome.workup.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

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
import br.org.cancer.redome.workup.model.ArquivoPedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoAdicionalWorkupControllerAnalistaWorkupInternacionalTest extends BaseConfigurationTest {
		
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(20L, "ANALISTA_WORKUP_INTERNACIONAL")
			.addPerfil("ANALISTA_WORKUP_INTERNACIONAL")
			.addRecurso(Recursos.CADASTRAR_ARQUIVO_PEDIDO_ADICIONAL_WORKUP)
			.addRecurso(Recursos.FINALIZAR_PEDIDO_ADICIONAL_WORKUP);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
		
	@Test
	public void deveFinalizarPedidoAdicionalWorkupComSucesso() throws Exception {

		SolicitacaoDTO solicitacao = criarSolicitacao(10L, TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL,
				UsuarioDTO.builder().id(15L).nome("MEDICO TRANSPLANTADOR").build(),
				criarMatch(1L, 1L, "4353344", criarBusca(16L, "Centro de Transplante", 300000L)), UsuarioDTO.builder().id(20L).nome("Analista Workup Internacional").build(),
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP);
		
		ArquivoPedidoAdicionalWorkup arquivoAdicional = ArquivoPedidoAdicionalWorkup.builder()
				.id(1L)
				.caminho("PEDIDO_WORKUP/1/PEDIDO_EXAME_ADICIONAL/1595812027099_Exame Adicional 1.txt")
				.descricao("Descricao Arquivo 1")
				.pedidoAdicional(1L)
				.build();

		List<ArquivoPedidoAdicionalWorkup> listaArquivosAdicionais = Arrays.asList(arquivoAdicional); 
		
		PedidoAdicionalWorkup pedidoAdicionalWorkup = PedidoAdicionalWorkup.builder()
				.id(1L)
				.descricao("Descricao Pedido Adicional")
				.dataCriacao(LocalDateTime.now())
				.pedidoWorkup(21L)
				.arquivosPedidoAdicionalWorkup(listaArquivosAdicionais)
				.build();
		
		makeStubForPut("/redome/api/solicitacoes/10/atualizarfaseworkup", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(solicitacao)));

		TarefaDTO tarefaWorkup = TarefaDTO.builder()
				.id(2L)
				.tipoTarefa( new TipoTarefaDTO(TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL.getId()))
				.relacaoEntidade(8L)
				.build();

		makeStubForGet("/redome-tarefa/api/tarefas?filter=eyJwZXJmaWxSZXNwb25zYXZ" + 
				"lbCI6bnVsbCwiaWRVc3VhcmlvUmVzcG9uc2F2ZWwiOm51bGwsInBhcmNl" + 
				"aXJvcyI6bnVsbCwiaWRzVGlwb3NUYXJlZmEiOlsyMjVdLCJzdGF0dXNUY" + 
				"XJlZmEiOlsyXSwic3RhdHVzUHJvY2Vzc28iOjEsInByb2Nlc3NvSWQiOm" + 
				"51bGwsImluY2x1c2l2b0V4Y2x1c2l2byI6bnVsbCwicGFnZWFibGUiOm5" + 
				"1bGwsInJlbGFjYW9FbnRpZGFkZUlkIjoxLCJybXIiOjMwMDAwMCwiaWRE" + 
				"b2Fkb3IiOm51bGwsInRpcG9Qcm9jZXNzbyI6NSwiYXRyaWJ1aWRvUXVhb" + 
				"HF1ZXJVc3VhcmlvIjpmYWxzZSwidGFyZWZhUGFpSWQiOm51bGwsInRhcm" + 
				"VmYVNlbUFnZW5kYW1lbnRvIjp0cnVlLCJpZFVzdWFyaW9Mb2dhZG8iOjI" + 
				"wfQ%3D%3D",
				okForContentType("application/json;charset=UTF-8",
				montarRetornoListarTarfaJson(Arrays.asList(tarefaWorkup))));
				
		makeStubForPost("/redome-tarefa/api/tarefa/2/feita", okForContentType("application/json;charset=UTF-8",
				BaseConfigurationTest.getObjectMapper().writeValueAsString(tarefaWorkup)));
		
		makeStubForPost("/redome/api/logsevolucao", WireMock.ok());
		
		makeStubForPost("/redome-tarefa/api/tarefas", WireMock.ok());
		
		MockMultipartFile pedidoAdicionalWorkupFile = new MockMultipartFile("pedidoAdicionalWorkup", null, "application/json", 
				BaseConfigurationTest.getObjectMapper().writeValueAsBytes(pedidoAdicionalWorkup));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoadicionalworkup/finalizarpedidoadicionalworkup",  "post")
			.file(pedidoAdicionalWorkupFile)			
			.contentType(MediaType.MULTIPART_FORM_DATA))				
			.andExpect(status().isOk());
	}

	
	@Test
	public void deveSalvarArquivosExamesAdicionaisWorkupComSucesso() throws Exception {

		ArquivoPedidoAdicionalWorkup arquivoAdicional = ArquivoPedidoAdicionalWorkup.builder()
				.id(1L)
				.caminho("PEDIDO_WORKUP/1/PEDIDO_EXAME_ADICIONAL/1595812027099_Exame Adicional 1.txt")
				.descricao("Descricao Arquivo 1")
				.pedidoAdicional(1L)
				.build();
		
		List<ArquivoPedidoAdicionalWorkup> listaArquivosAdicionais = Arrays.asList(arquivoAdicional); 
		
		MockMultipartFile idPedidoAdicionalFile = new MockMultipartFile("idPedidoAdicional", null, "application/json", "1".getBytes());
		MockMultipartFile arquivosExamesAdicionaisFile = new MockMultipartFile("arquivosExamesAdicionais", null, "application/json", 
				BaseConfigurationTest.getObjectMapper().writeValueAsBytes(listaArquivosAdicionais));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidoadicionalworkup",  "post")
			.file(idPedidoAdicionalFile)			
			.file(arquivosExamesAdicionaisFile)			
			.contentType(MediaType.MULTIPART_FORM_DATA))				
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
