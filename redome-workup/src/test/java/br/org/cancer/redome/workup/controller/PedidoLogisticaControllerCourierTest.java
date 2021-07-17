package br.org.cancer.redome.workup.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import br.org.cancer.redome.workup.model.domain.Recursos;
import br.org.cancer.redome.workup.util.BaseConfigurationTest;
import br.org.cancer.redome.workup.util.CreateMockHttpServletRequest;

public class PedidoLogisticaControllerCourierTest extends BaseConfigurationTest {
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(12L, "COURIER")
			.addPerfil("TRANSPORADORA")
			.addRecurso(Recursos.AGENDAR_TRANSPORTE_MATERIAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}


	@Test
	public void deveAtualizarLogisticaMaterialColetaParaAereoComSucesso() throws Exception {
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePatch("/api/pedidologistica/34/material/nacional/aereo")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void naoDeveAtualizarLogisticaMaterialColetaParaAereoSeStatusDiferenteAberto() throws Exception {
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePatch("/api/pedidologistica/35/material/nacional/aereo")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
		
	}
	
	@Test
	public void naoDeveAtualizarLogisticaMaterialColetaParaAereoSeJaFoiAtualizada() throws Exception {
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePatch("/api/pedidologistica/36/material/nacional/aereo")
			.contentType(BaseConfigurationTest.CONTENT_TYPE))				
			.andExpect(status().is4xxClientError());
		
	}

}
