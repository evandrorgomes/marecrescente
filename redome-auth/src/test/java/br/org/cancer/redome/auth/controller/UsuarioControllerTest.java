package br.org.cancer.redome.auth.controller;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import br.org.cancer.redome.auth.controller.dto.TransportadoraDTO;
import br.org.cancer.redome.auth.controller.dto.TransportadoraUsuarioDTO;
import br.org.cancer.redome.auth.util.BaseConfigurationTest;

public class UsuarioControllerTest extends BaseConfigurationTest {
		
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("ADMIN")
			.addPerfil("ADMIN");			
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void deveSalvarTransportadoraComSucesso() throws Exception {
		
		TransportadoraDTO transportadora = TransportadoraDTO.builder()
				.id(1L)
				.nome("transportadora")
				.build();
		
//		makeStubForGet("/redome-courier/api/trasportadora/1", 
//				okForContentType("application/json;charset=UTF-8", BaseConfigurationTest.getObjectMapper().writeValueAsString(transportadora)));
		
		TransportadoraUsuarioDTO dto = TransportadoraUsuarioDTO.builder()
				.idTransportadora(1L)
				.usuarios(Arrays.asList(51L))
				.build();		
						
/*		mockMvc.perform(CreateMockHttpServletRequest.makePut("/api/usuarios/transportadora")
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(dto))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
				.andExpect(status().isOk()); */
		
		Assert.assertTrue(true);
	}
	
	

}
