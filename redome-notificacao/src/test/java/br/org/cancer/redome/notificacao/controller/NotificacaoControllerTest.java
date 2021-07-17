package br.org.cancer.redome.notificacao.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.okForContentType;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;

import br.org.cancer.redome.notificacao.dto.NotificacaoDTO;
import br.org.cancer.redome.notificacao.dto.UsuarioDTO;
import br.org.cancer.redome.notificacao.util.BaseConfigurationTest;
import br.org.cancer.redome.notificacao.util.CreateMockHttpServletRequest;

/**
 * Classe de teste do controller 
 * 
 * @author ergomes
 *
 */
public class NotificacaoControllerTest extends BaseConfigurationTest {

	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("VISUALIZAR_NOTIFICACOES");		
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}
	
	
//	@Test
//	public void deveListarNotificacoes() throws UnsupportedEncodingException, Exception {
//		
//		String retorno = mockMvc
//				.perform(CreateMockHttpServletRequest.makeGet("/api/notificacoes")
//				.param("pagina", "0")
//				.param("quantidadeRegistro", "10"))
//				.andExpect(status().isOk())				
//				.andExpect(content()
//				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$.content[0].id", is(1)))
//				.andExpect(jsonPath("$.content[0].rmr", is(3000000)))
//				.andReturn().getResponse().getContentAsString();
//		
//		System.out.println(retorno);
//		
//		Assert.assertNotNull(retorno);
//		
//	}
	
	@Test
	public void deveCriarNotificacoesComSucesso() throws UnsupportedEncodingException, Exception {

		NotificacaoDTO notificacaoDto = NotificacaoDTO.builder()
				.categoriaId(1L)
				.descricao("Notificação teste.")
				.rmr(3456L)
				.usuarioId(1L)
				.lido(false)
				.build();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/notificacoes/criarnotificacao")
		.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(notificacaoDto))
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveCriarNotificacoesParaPerfilComSucesso() throws UnsupportedEncodingException, Exception {
		
		List<UsuarioDTO> usuarios = Arrays.asList(
				UsuarioDTO.builder().id(9L).build(),
				UsuarioDTO.builder().id(21L).build());
		
		makeStubForGet("/redome-auth/api/perfil/9/usuarios",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarios)));

		NotificacaoDTO notificacaoDto = NotificacaoDTO.builder()
				.categoriaId(1L)
				.descricao("Notificação teste.")
				.rmr(3456L)
				.idPerfil(9L)
				.lido(false)
				.build();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/notificacoes/criarnotificacao")
		.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(notificacaoDto))
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deveCriarNotificacoesParaPerfilEParceiroComSucesso() throws UnsupportedEncodingException, Exception {
		
		List<UsuarioDTO> usuarios = Arrays.asList(
				UsuarioDTO.builder().id(9L).build(),
				UsuarioDTO.builder().id(21L).build());
		
		makeStubForGet("/redome-auth/api/perfil/9/usuarios?parceiro=Laboratorio&identificador=1",
				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(usuarios)));

		NotificacaoDTO notificacaoDto = NotificacaoDTO.builder()
				.categoriaId(1L)
				.descricao("Notificação teste.")
				.rmr(3456L)
				.idPerfil(9L)
				.nomeClasseParceiro("Laboratorio")
				.parceiro(1L)
				.lido(false)
				.build();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/notificacoes/criarnotificacao")
		.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(notificacaoDto))
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}

	@Test
	public void deveMarcarNotificacaoComoLidaComSucesso() throws UnsupportedEncodingException, Exception {

		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/notificacoes/4587/lida")
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	public void deveContarNotificacoesComSucesso() throws UnsupportedEncodingException, Exception {

		mockMvc
		.perform(CreateMockHttpServletRequest.makePost("/api/notificacoes/3456/contar")
		.param("lido", "1")				
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andReturn().getResponse().getContentAsString();
	}
	
	private void makeStubForGet(String url, ResponseDefinitionBuilder response) {
		stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(url)
				.withId(UUID.randomUUID())
				.willReturn(response));
	}

	
	
}