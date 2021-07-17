package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

public class H2MatchControllerTest  extends BaseConfigurationTest {

	@Autowired
	private IMatchService matchService;
	
	@Mock
	private ITarefaFeign tarefaFeign;

			
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("AVALIADOR_REDOME")
			.addPerfil("AVALIADOR_EXAME_HLA")
			.addRecurso(
						"RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
						"VISUALIZAR_FICHA_PACIENTE",
						"CONSULTAR_EXAMES_PACIENTE",
						"CONSULTAR_EVOLUCOES_PACIENTE",
						"AVALIAR_PACIENTE",
						"VISUALIZAR_IDENTIFICACAO_PARCIAL",
						"CONSULTAR_AVALIACOES",
						"ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR");
	}
	
	
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(matchService, "tarefaFeign", tarefaFeign);
				
	}
	
	@Test
	public void deveCriarChecklistPorDivergenciaDeDoador() throws Exception {
		
		Mockito.when(tarefaFeign.obterTarefa(Mockito.anyLong())).thenAnswer(new Answer<TarefaDTO>() {
			@Override
			public TarefaDTO answer(InvocationOnMock invocation) throws Throwable {
				ProcessoDTO processo = new ProcessoDTO(1L, TipoProcesso.BUSCA);
				TarefaDTO tarefa = new TarefaDTO(555899L);
				tarefa.setProcesso(processo);
				tarefa.setTipoTarefa(new TipoTarefaDTO(TiposTarefa.CHECKLIST_EXAME_DIVERGENTE_FOLLOWUP.getId()));
				tarefa.setRelacaoEntidade(24721L);
				
				return tarefa;
			}
		});
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/matchs/criarchecklistdivergenciamatch")
		.param("tarefa", "555899")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk())
		.andReturn().getResponse().getContentAsString();
	}
	
}
