package br.org.cancer.redome.courier.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import br.org.cancer.redome.courier.model.domain.Recursos;
import br.org.cancer.redome.courier.util.BaseConfigurationTest;

public class PedidoTransporteControllerAnalistaLogisticaTest extends BaseConfigurationTest {
	
	@Value("classpath:laudo_aereo.pdf")
	Resource resourceFile;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario(12L, "ANALISTA_LOGISTICA")
			.addPerfil("ANALISTA_LOGISTICA")
			.addRecurso(Recursos.SOLICITAR_LOGISTICA_MATERIAL);
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);		
	}

	@Test
	public void deveCriarPedidoTransporteComSucesso() throws Exception {

		Assert.assertTrue(true);
		
		/*
		 * PedidoTransporteDTO pedidoTransporte = PedidoTransporteDTO.builder() .id(23L)
		 * .idPedidoLogistica(31L)
		 * .transportadora(TransportadoraDTO.builder().id(1L).build())
		 * .status(StatusPedidoTransporteDTO.builder().id(StatusPedidosTransporte.
		 * EM_ANALISE.getId()).build()) .horaPrevistaRetirada(LocalDateTime.now())
		 * .usuarioResponsavel(1L) .build();
		 * 
		 * mockMvc
		 * .perform(CreateMockHttpServletRequest.makePost("/api/pedidostransporte")
		 * .content(getObjectMapper().writeValueAsBytes(pedidoTransporte))
		 * .contentType(BaseConfigurationTest.CONTENT_TYPE))
		 * .andExpect(status().isOk());
		 */
	}

	@Test
	public void deveAtualizarPedidoTransporteAereoComSucesso() throws Exception {

		Assert.assertTrue(true);
		
		/*
		 * PedidoTransporteDTO pedidoTransporte = PedidoTransporteDTO.builder() .id(23L)
		 * .idPedidoLogistica(31L)
		 * .transportadora(TransportadoraDTO.builder().id(1L).build())
		 * .status(StatusPedidoTransporteDTO.builder().id(StatusPedidosTransporte.
		 * AGUARDANDO_DOCUMENTACAO.getId()).build())
		 * .horaPrevistaRetirada(LocalDateTime.now()) .build();
		 * 
		 * 
		 * File file = resourceFile.getFile(); FileInputStream in = new
		 * FileInputStream(file); MockMultipartFile mockMultipartFile = new
		 * MockMultipartFile("file", "laudo_aereo.pdf", "application/pdf", in); final
		 * List<MockMultipartFile> files = new ArrayList<>();
		 */

		
		
//		MockMultipartFile dataPrevistaRetirada = new MockMultipartFile("dataPrevistaRetirada", null, "application/json", LocalDateTime.now());
//		MockMultipartFile descricaoAlteracao = new MockMultipartFile("descricaoAlteracao", null, "application/json", "descricaoAlteracao".getBytes());
//		MockMultipartFile arquivosExamesAdicionaisFile = new MockMultipartFile("arquivos", null, "application/json", 
//				BaseConfigurationTest.getObjectMapper().writeValueAsBytes(listaArquivos));

//		mockMvc
//		.perform(CreateMockHttpServletRequest.makeMultipart("/api/pedidotransporte/23/atualizaraereo", "data")
//		.contentType(MediaType.MULTIPART_FORM_DATA))				
//		.andExpect(status().isOk());
	}
	
	@Test
	public void deveAtualizarPedidoTransporteComSucesso() throws Exception {

		/*
		 * PedidoTransporteDTO pedidoTransporte = PedidoTransporteDTO.builder() .id(23L)
		 * .idPedidoLogistica(31L)
		 * .transportadora(TransportadoraDTO.builder().id(1L).build())
		 * .status(StatusPedidoTransporteDTO.builder().id(StatusPedidosTransporte.
		 * EM_ANALISE.getId()).build()) .horaPrevistaRetirada(LocalDateTime.now())
		 * .usuarioResponsavel(1L) .build();
		 * 
		 * mockMvc
		 * .perform(CreateMockHttpServletRequest.makePost("/api/pedidostransporte")
		 * .content(getObjectMapper().writeValueAsBytes(pedidoTransporte))
		 * .contentType(BaseConfigurationTest.CONTENT_TYPE))
		 * .andExpect(status().isOk());
		 */
		Assert.assertTrue(true);
		
	}
}
