package br.org.cancer.redome.courier.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.tomakehurst.wiremock.client.WireMock;

import br.org.cancer.redome.courier.model.ContatoTelefonicoTransportadora;
import br.org.cancer.redome.courier.model.EmailContatoTransportadora;
import br.org.cancer.redome.courier.model.EnderecoContatoTransportadora;
import br.org.cancer.redome.courier.model.Municipio;
import br.org.cancer.redome.courier.model.Pais;
import br.org.cancer.redome.courier.model.Transportadora;
import br.org.cancer.redome.courier.model.domain.Perfis;
import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.model.domain.TipoTelefone;
import br.org.cancer.redome.courier.persistence.IEnderecoContatoTransportadoraRepository;
import br.org.cancer.redome.courier.persistence.ITransportadoraRepository;
import br.org.cancer.redome.courier.util.BaseConfigurationTest;
import br.org.cancer.redome.courier.util.CreateMockHttpServletRequest;

public class TransportadoraControllerTest extends BaseConfigurationTest {
		
	@Autowired
	private IEnderecoContatoTransportadoraRepository enderecoRepository;
	
	@Autowired
	private ITransportadoraRepository transportadoraRepository; 

	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(19L, "ADMIN")
		    .transportadora(1L)
			.addPerfil(Perfis.TRANSPORTADORA.name())
			.addRecurso(Recursos.SALVAR_TRANSPORTADORA,
					Recursos.EXCLUSAO_TRANSPORTADORA);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveSalvarTransportadoraComSucesso() throws Exception {

		
		makeStubForPut("/redome-auth/api/usuarios/transportadora", WireMock.ok());
		
		Transportadora transportadora = Transportadora.builder()
				.nome("Teste inclusão")
				.endereco(EnderecoContatoTransportadora.builder()
						.nomeLogradouro("teste")
						.bairro("teste")
						.cep("20231-048")
						.correspondencia(true)
						.numero("50")
						.principal(true)
						.tipoLogradouro("RUA")
						.municipio(Municipio.builder().id(1L).build())
						.pais(Pais.builder().id(1L).build())
						.build())
				.emails(Arrays.asList(EmailContatoTransportadora.builder().email("teste@teste.com").build()))
				.telefones(Arrays.asList(ContatoTelefonicoTransportadora.builder()
						.codInter(55)
						.codArea(21)
						.nome("teste")
						.numero(21574600L)
						.principal(true)
						.tipo(TipoTelefone.FIXO.getCodigo())
						.build()))
				.build();
						
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadoras")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(transportadora))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void naoDeveSalvarTransportadoraSemEndereco() throws Exception {

		Transportadora transportadora = Transportadora.builder()
				.nome("Teste inclusão")				
				.emails(Arrays.asList(EmailContatoTransportadora.builder().email("teste@teste.com").build()))
				.telefones(Arrays.asList(ContatoTelefonicoTransportadora.builder()
						.codInter(55)
						.codArea(21)
						.nome("teste")
						.numero(21574600L)
						.principal(true)
						.tipo(TipoTelefone.FIXO.getCodigo())
						.build()))
				.build();
						
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadoras")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(transportadora))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveSalvarTransportadoraSemEaEmail() throws Exception {

		Transportadora transportadora = Transportadora.builder()
				.nome("Teste inclusão")
				.endereco(EnderecoContatoTransportadora.builder()
						.nomeLogradouro("teste")
						.bairro("teste")
						.cep("20231-048")
						.correspondencia(true)
						.numero("50")
						.principal(true)
						.tipoLogradouro("RUA")
						.municipio(Municipio.builder().id(1L).build())
						.pais(Pais.builder().id(1L).build())
						.build())
				.telefones(Arrays.asList(ContatoTelefonicoTransportadora.builder()
						.codInter(55)
						.codArea(21)
						.nome("teste")
						.numero(21574600L)
						.principal(true)
						.tipo(TipoTelefone.FIXO.getCodigo())
						.build()))
				.build();
						
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadoras")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(transportadora))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void naoDeveSalvarTransportadoraSemTelefone() throws Exception {

		Transportadora transportadora = Transportadora.builder()
				.nome("Teste inclusão")
				.endereco(EnderecoContatoTransportadora.builder()
						.nomeLogradouro("teste")
						.bairro("teste")
						.cep("20231-048")
						.correspondencia(true)
						.numero("50")
						.principal(true)
						.tipoLogradouro("RUA")
						.municipio(Municipio.builder().id(1L).build())
						.pais(Pais.builder().id(1L).build())
						.build())
				.emails(Arrays.asList(EmailContatoTransportadora.builder().email("teste@teste.com").build()))
				.build();
						
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadoras")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(transportadora))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void deveInativarTransportadoraComSucesso() throws Exception {
		
		mockMvc.perform(CreateMockHttpServletRequest.makeDelete("/api/transportadora/1")				
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void deveAtualizarEnderecoTransportadoraComSucesso() throws Exception {
		
		EnderecoContatoTransportadora enderecoContato = enderecoRepository.findById(2L).get();
		
		EnderecoContatoTransportadora enderecoContatoAtualizado = enderecoContato.toBuilder()
				.nomeLogradouro("Logradouro Atualizado").build();
		
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/transportadora/2/atualizarendereco")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(enderecoContatoAtualizado))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void deveInserirNovoEmailTransportadoraComSucesso() throws Exception {
		
		EmailContatoTransportadora novoEmail = EmailContatoTransportadora.builder()
				.email("novo-email@teste.com")
				.build();
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadora/2/inseriremail")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(novoEmail))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
		
	}
	
	@Test
	public void deveInserirNovoTelefoneTransportadoraComSucesso() throws Exception {
		
		ContatoTelefonicoTransportadora telefone = ContatoTelefonicoTransportadora.builder()
				.codInter(55)
				.codArea(21)
				.numero(21574611L)
				.tipo(1)
				.nome("Outro telefone")
				.principal(false)
				.build();
		
		mockMvc.perform(CreateMockHttpServletRequest.makePost("/api/transportadora/2/inserirtelefone")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(telefone))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
				
		
	}
	
	@Test
	public void deveAtualizarTransportadoraComSucesso() throws Exception {
		
		Transportadora transportadora = transportadoraRepository.findById(2L).get().toBuilder()
				.nome("Nome Atualizado")
				.build();
		
		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/transportadora/2")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(transportadora))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk());
		
		
		
		
		
		
	}

	
	
	

}
