package br.org.cancer.modred.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.persistence.IMatchRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRepository;
import br.org.cancer.modred.service.impl.MatchService;
import br.org.cancer.modred.service.impl.SolicitacaoService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

/**
 * Testes de contraladores de solicitação.
 * @author Bruno Sousa
 *
 */
public class H2SolicitacaoControllerParaAnalistaWorkupTest  extends BaseConfigurationTest{
		
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	@Spy
	private MatchService matchService;
	
	@Autowired
	private IMatchRepository matchRepository;
	
	@Autowired
	private ISolicitacaoRepository solicitacaoRepository;
			
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("ANALISTA_WORKUP")
			.addPerfil("ANALISTA_WORKUP", "DISTRIBUIDOR_WORKUP_NACIONAL")	
			.addRecurso("DISTRIBUIR_WORKUP");
	}
		
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(solicitacaoService, "matchService", matchService);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", solicitacaoRepository);
		ReflectionTestUtils.setField(matchService, "matchRepository", matchRepository);
		
	}
	
	@Test
	public void deveAtribuirUsuarioResponsavelComSucesso() throws Exception {
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/solicitacoes/389389/atribuirusuarioresponsavel")
					.content("9")
					.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))		    
			.andExpect(jsonPath("$.id", is(389389)));
		
	}
		
	@Test
	public void naoDeveAtribuirUsuarioResponsavelSeUsuarioNaoExistir() throws Exception {
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/solicitacoes/389389/atribuirusuarioresponsavel")
					.content("300")
					.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().is4xxClientError())
			.andExpect(content().string("Não foi encontrado nenhum usuário."));
		
	}
	
	@Test
	public void deveAlterarFaseWorkupComSucesso() throws Exception {
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/solicitacoes/389389/atualizarfaseworkup")
					.content("2")
					.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))		    
			.andExpect(jsonPath("$.id", is(389389)));
		
	}
	
	@Test
	public void naoDeveAlterarFaseWorkupSeFaseWorkupNaoExistir() throws Exception {
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/solicitacoes/389389/atualizarfaseworkup")
					.content("300")
					.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().is4xxClientError())
			.andExpect(content().string("Nenhuma fase workup encontrada."));
		
	}
	

}
