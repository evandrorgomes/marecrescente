package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.feign.dto.TipoTarefaDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.IExamePacienteService;
import br.org.cancer.modred.service.IPedidoExameService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;

public class H2PedidoExameControllerTest extends BaseConfigurationTest {

	@Autowired
	private IExamePacienteService examePacienteService;
	
	@Autowired
	private IPedidoExameService pedidoExameService;
	
	@Mock
	private ITarefaFeign tarefaFeign;
			
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("ANALISTA_BUSCA")
			.addPerfil("ANALISTA_BUSCA_REDOME")
			.addRecurso(
					"VISUALIZAR_IDENTIFICACAO_COMPLETA",
					"VISUALIZAR_AVALIACAO",
					"CONTACTAR_FASE_3",
					"CONFERIR_EXAME_HLA");
	}
	
	@Before
	public void setupBefore() {
		
		MockitoAnnotations.initMocks(this);
				
		ReflectionTestUtils.setField(pedidoExameService, "tarefaFeign", tarefaFeign);		
		
		ReflectionTestUtils.setField(examePacienteService, "usuarioService", MockService.criarMockUsuarioService(3L, Arrays.asList(new Perfil(Perfis.AVALIADOR_EXAME_HLA.getId())), 
				null));
				
	}
	
	
	@Test
	public void deveCriarCheckListParaExamesSemResultados() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				TarefaDTO tarefa = new TarefaDTO(109904L);
				tarefa.setProcesso(processo);
				tarefa.setTipoTarefa(new TipoTarefaDTO(TiposTarefa.CADASTRAR_RESULTADO_EXAME_IDM_INTERNACIONAL.getId()));
				tarefa.setRelacaoEntidade(521L);
				
				return tarefa;
			}
		});		
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/pedidosexame/criarchecklistexamesemresultado30dias")
		.param("tarefa", "109904")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
}