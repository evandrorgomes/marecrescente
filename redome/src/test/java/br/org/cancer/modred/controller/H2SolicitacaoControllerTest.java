package br.org.cancer.modred.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.persistence.IMatchRepository;
import br.org.cancer.modred.persistence.ISolicitacaoRepository;
import br.org.cancer.modred.service.impl.MatchService;
import br.org.cancer.modred.service.impl.SolicitacaoService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

/**
 * Testes de contraladores de Busca.
 * @author Filipe Paes
 *
 */
public class H2SolicitacaoControllerTest  extends BaseConfigurationTest{
		
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
		makeAuthotization.paraUsuario("MEDICO_TRANSPLANTADOR")
			.addPerfil("MEDICO", "MEDICO_TRANSPLANTADOR")	
			.addRecurso("CADASTRAR_PRESCRICAO"); 
	}
	
	
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(solicitacaoService, "matchService", matchService);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", solicitacaoRepository);
		ReflectionTestUtils.setField(matchService, "matchRepository", matchRepository);
		
		
	}
	
	@Test
	public void deveCriarSolicitacaoDoadorNacionaoParaPacienteNacional() throws Exception{
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/doadornacionalpacientenacional")
			.content("24728")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))		    
		    .andExpect(jsonPath("$.match.id", is(24728)))
		    .andExpect(jsonPath("$.tipoSolicitacao.id", is(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId().intValue())))
			.andReturn().getResponse().getContentAsString();
						
	}
	
	@Test
	public void naoDeveCriarSolicitacaoDoadorNacionaoParaPacienteNacionalSeMatchNaoEncontrado() throws Exception{
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/doadornacionalpacientenacional")
			.content("24729")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError())
		    .andExpect(content().contentType("text/plain;charset=UTF-8"))
		    .andExpect(content().string("Nenhum match encontrado."))
			.andReturn().getResponse().getContentAsString();
				
	}
	
	@Test
	public void naoDeveCriarSolicitacaoDoadorNacionaoParaPacienteNacionalSeMatchNaoDisponibilizado() throws Exception{
		
		IMatchRepository mockMatchRepository = Mockito.mock(IMatchRepository.class);
		
		ReflectionTestUtils.setField(matchService, "matchRepository", mockMatchRepository);
		
		Mockito.when(mockMatchRepository.findById(24730L)).thenAnswer(new Answer<Optional<Match>>() {
			@Override
			public Optional<Match> answer(InvocationOnMock invocation) throws Throwable {
				final Paciente paciente = new Paciente(18839L); 
				final Busca busca = new Busca(4894L);
				busca.setPaciente(paciente);
				Match match  = new Match(invocation.getArgument(0));
				match.setBusca(busca);
				
				match.setDisponibilizado(false);
				return Optional.of(match);  
			}
		});
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/doadornacionalpacientenacional")
			.content("24730")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError())
		    .andExpect(content().contentType("text/plain;charset=UTF-8"))
		    .andExpect(content().string("Match informado não foi disponibilizado para o paciente 18839."))
			.andReturn().getResponse().getContentAsString();
		
		Mockito.reset(mockMatchRepository);
				
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void naoDeveCriarSolicitacaoDoadorNacionaoParaPacienteNacionalSeJaExistirSolicitacaoEmAbertoDoMesmoTipo() throws Exception{
		
		IMatchRepository mockMatchRepository = Mockito.mock(IMatchRepository.class);
		
		ReflectionTestUtils.setField(matchService, "matchRepository", mockMatchRepository);
		
		Mockito.when(mockMatchRepository.findById(Mockito.anyLong())).thenAnswer(new Answer<Optional<Match>>() {
			@Override
			public Optional<Match> answer(InvocationOnMock invocation) throws Throwable {
				final Paciente paciente = new Paciente(18839L); 
				final Busca busca = new Busca(4894L);
				busca.setPaciente(paciente);
				
				DoadorNacional doador = new DoadorNacional();
				doador.setDmr(3693893L);
				Match match  = new Match(invocation.getArgument(0));
				match.setBusca(busca);				
				match.setDisponibilizado(true);
				match.setDoador(doador);
				
				return Optional.of(match);  
			}
		});
				
		ISolicitacaoRepository mockSolicitacaoRepository = Mockito.mock(ISolicitacaoRepository.class);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", mockSolicitacaoRepository);
		
		Mockito.when(mockSolicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(Mockito.anyLong(), Mockito.anyList())).thenReturn(1L);
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/doadornacionalpacientenacional")
			.content("24731")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().is4xxClientError())
		    .andExpect(content().contentType("text/plain;charset=UTF-8"))
		    .andExpect(content().string("Existe uma solicitação para o paciente 18839 com o doador 3693893 em aberto."));
		
		Mockito.reset(mockMatchRepository, mockSolicitacaoRepository);
		
	}
	
	@Test
	public void deveCriarSolicitacaoDoadorInternacionalParaPacienteNacional() throws Exception{
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/doadorinternacionalpacientenacional")
			.content("24731")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		    //.andExpect(jsonPath("$.id", is(1)))
		    .andExpect(jsonPath("$.match.id", is(24731)))
		    .andExpect(jsonPath("$.tipoSolicitacao.id", is(TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().intValue())))
			.andReturn().getResponse().getContentAsString();
						
	}	
	
	@Test
	public void deveCriarSolicitacaoCordaoNacionalParaPacienteNacional() throws Exception{
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/cordaonacionalpacientenacional")
			.content("24732")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))		    
		    .andExpect(jsonPath("$.match.id", is(24732)))
		    .andExpect(jsonPath("$.tipoSolicitacao.id", is(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().intValue())))
			.andReturn().getResponse().getContentAsString();
						
	}
	
	@Test
	public void deveCriarSolicitacaoCordaoNacionalParaPacienteNacionalSeExistirOutraSolicitacao() throws Exception{
		
		ISolicitacaoRepository mockSolicitacaoRepository = Mockito.mock(ISolicitacaoRepository.class);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", mockSolicitacaoRepository);
		
		Mockito.when(mockSolicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(42503L, Arrays.asList(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId(), TiposSolicitacao.CORDAO_INTERNACIONAL.getId()))).thenReturn(1L);
		Mockito.when(mockSolicitacaoRepository.save(Mockito.any(Solicitacao.class))).thenAnswer(new Answer<Solicitacao>() {
			
			@Override
			public Solicitacao answer(InvocationOnMock invocation) throws Throwable {
				final Solicitacao solicitacao = invocation.getArgument(0);
				solicitacao.setId(1L);
				return solicitacao;
			}
		});
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/cordaonacionalpacientenacional")
			.content("24734")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		    .andExpect(jsonPath("$.id", is(1)))
		    .andExpect(jsonPath("$.match.id", is(24734)))
		    .andExpect(jsonPath("$.tipoSolicitacao.id", is(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId().intValue())))
			.andReturn().getResponse().getContentAsString();
		
		Mockito.reset(mockSolicitacaoRepository);
							
	}
	
	@Test
	public void naoDeveCriarSolicitacaoCordaoNacionalParaPacienteNacionalSeExistirSolicitacaoMedula() throws Exception{
		
		IMatchRepository mockMatchRepository = Mockito.mock(IMatchRepository.class);
		
		ReflectionTestUtils.setField(matchService, "matchRepository", mockMatchRepository);
		
		Mockito.when(mockMatchRepository.findById(Mockito.anyLong())).thenAnswer(new Answer<Optional<Match>>() {
			@Override
			public Optional<Match> answer(InvocationOnMock invocation) throws Throwable {
				final Paciente paciente = new Paciente(18839L); 
				final Busca busca = new Busca(4894L);
				busca.setPaciente(paciente);
				
				CordaoNacional doador = new CordaoNacional();
				doador.setIdBancoSangueCordao("8489498");
				doador.setId(94094L);
				Match match  = new Match(invocation.getArgument(0));
				match.setBusca(busca);				
				match.setDisponibilizado(true);
				match.setDoador(doador);
				
				return Optional.of(match);  
			}
		});
		
		ISolicitacaoRepository mockSolicitacaoRepository = Mockito.mock(ISolicitacaoRepository.class);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", mockSolicitacaoRepository);
		
		Mockito.when(mockSolicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(4894L, Arrays.asList(TiposSolicitacao.WORKUP_DOADOR_NACIONAL_PACIENTE_NACIONAL.getId(), TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId()))).thenReturn(1L);
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/cordaonacionalpacientenacional")
			.content("24738")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().is4xxClientError())
		    .andExpect(content().contentType("text/plain;charset=UTF-8"))
		    .andExpect(content().string("Existe uma solicitação para o paciente 18839 com o doador 8489498 em aberto."))
			.andReturn().getResponse().getContentAsString();
		
		Mockito.reset(mockMatchRepository, mockSolicitacaoRepository);
						
	}
	
	@Test
	public void naoDeveCriarSolicitacaoCordaoNacionalParaPacienteNacionalSeJaExistirDuasSolicitacoes() throws Exception{
		
		IMatchRepository mockMatchRepository = Mockito.mock(IMatchRepository.class);
		
		ReflectionTestUtils.setField(matchService, "matchRepository", mockMatchRepository);
		
		Mockito.when(mockMatchRepository.findById(Mockito.anyLong())).thenAnswer(new Answer<Optional<Match>>() {
			@Override
			public Optional<Match> answer(InvocationOnMock invocation) throws Throwable {
				final Paciente paciente = new Paciente(18839L); 
				final Busca busca = new Busca(4894L);
				busca.setPaciente(paciente);
				
				CordaoNacional doador = new CordaoNacional();
				doador.setIdBancoSangueCordao("8489498");
				doador.setId(94094L);
				Match match  = new Match(invocation.getArgument(0));
				match.setBusca(busca);				
				match.setDisponibilizado(true);
				match.setDoador(doador);
				
				return Optional.of(match);  
			}
		});
		
		ISolicitacaoRepository mockSolicitacaoRepository = Mockito.mock(ISolicitacaoRepository.class);
		ReflectionTestUtils.setField(solicitacaoService, "solicitacaoRepository", mockSolicitacaoRepository);
		
		Mockito.when(mockSolicitacaoRepository.quantidadeSolicitacoesEmAbertoDosMatchsPelaBuscaETipoSolicitacao(4894L, Arrays.asList(TiposSolicitacao.CORDAO_NACIONAL_PACIENTE_NACIONAL.getId(), TiposSolicitacao.CORDAO_INTERNACIONAL.getId()))).thenReturn(2L);
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/cordaonacionalpacientenacional")
			.content("24738")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().is4xxClientError())
		    .andExpect(content().contentType("text/plain;charset=UTF-8"))
		    .andExpect(content().string("Existe uma solicitação para o paciente 18839 com o doador 8489498 em aberto."))
			.andReturn().getResponse().getContentAsString();
		
		Mockito.reset(mockMatchRepository, mockSolicitacaoRepository);
						
	}
		
	@Test
	public void deveCriarSolicitacaoCordaoInternacionalParaPacienteNacional() throws Exception{
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePost("/api/solicitacoes/workup/cordaointernacionalpacientenacional")
			.content("24733")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))
		    .andExpect(status().isOk())
		    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))		    
		    .andExpect(jsonPath("$.match.id", is(24733)))
		    .andExpect(jsonPath("$.tipoSolicitacao.id", is(TiposSolicitacao.CORDAO_INTERNACIONAL.getId().intValue())));			
						
	}
	
}
