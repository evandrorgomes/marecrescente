package br.org.cancer.modred.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.controller.dto.CriarLogEvolucaoDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

/**
 * Testes de contraladores de Busca.
 * @author Filipe Paes
 *
 */
public class H2LogEvolucaoControllerTest  extends BaseConfigurationTest{
		
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
	
	@Autowired
	private PacienteService pacienteService;
		
	@Mock
	private IAvaliacaoService avaliacaoService;
	
				
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("MEDICO_TRANSPLANTADOR")
			.addPerfil("MEDICO", "MEDICO_TRANSPLANTADOR", "ANALISTA_WORKUP_INTERNACIONAL")	
			.addRecurso("CADASTRAR_PRESCRICAO");
	}
	
	
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(logEvolucaoService, "pacienteService", pacienteService);
		ReflectionTestUtils.setField(pacienteService, "avaliacaoService", avaliacaoService);		
		
		
	}
	
	@Test
	public void deveCriarLogEvolucaoComSucesso() throws Exception{
		
		Mockito.when(avaliacaoService.verificarSeAvaliacaoEmAndamento(Mockito.anyLong())).thenReturn(true);
		Mockito.when(avaliacaoService.verificarSeAvaliacaoAprovada(Mockito.anyLong())).thenReturn(false);
		
		makeStubForGet("/redome-notificacao/api/notificacoes/18839/contar",
				okForContentType("application/json;charset=UTF-8", "0"));
		
		String[] parametros = {"8489489"};
		
		CriarLogEvolucaoDTO log = new CriarLogEvolucaoDTO();
		log.setRmr(18839L);
		log.setTipo(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA.name());
		log.setParametros(parametros);
		log.setPerfisExcluidos(Arrays.asList(Perfis.MEDICO.getId()));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/logsevolucao")
			.content(getObjectMapper().writeValueAsString(log))
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk());
						
	}
	
	@Test
	public void naoDeveCriarLogEvolucaoSeTipoIgualIndefinido() throws Exception{
				
		String[] parametros = {"8489489"};
		
		CriarLogEvolucaoDTO log = new CriarLogEvolucaoDTO();
		log.setRmr(18839L);
		log.setTipo(TipoLogEvolucao.INDEFINIDO.name());
		log.setParametros(parametros);
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/logsevolucao")
			.content(getObjectMapper().writeValueAsString(log))
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError());
						
	}
	
	
	@Test
	public void naoDeveCriarLogEvolucaoSeTipoNaoExistir() throws Exception{
				
		String[] parametros = {"8489489"};
		
		CriarLogEvolucaoDTO log = new CriarLogEvolucaoDTO();
		log.setRmr(18839L);
		log.setTipo("NAO_EXISTE");
		log.setParametros(parametros);
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/logsevolucao")
			.content(getObjectMapper().writeValueAsString(log))
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError());
						
	}
	
	@Test
	public void naoDeveCriarLogEvolucaoSePerfilNaoExistir() throws Exception{
				
		String[] parametros = {"8489489"};
		
		CriarLogEvolucaoDTO log = new CriarLogEvolucaoDTO();
		log.setRmr(18839L);
		log.setTipo(TipoLogEvolucao.PRESCRICAO_CRIADA_PARA_MEDULA.name());
		log.setParametros(parametros);
		log.setPerfisExcluidos(Arrays.asList(1000L));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/logsevolucao")
			.content(getObjectMapper().writeValueAsString(log))
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError());
						
	}
	
	
}
