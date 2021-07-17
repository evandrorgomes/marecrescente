package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.LocusDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExameDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExamePkDTO;
import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.controller.dto.doador.StatusDoadorDTO;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.service.ICordaoInternacionalService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

public class H2CordaoInternacionalControllerForClientTest extends BaseConfigurationTest {
	
	@Autowired
	private ICordaoInternacionalService cordaoInternacionalService;

	@BeforeClass
	public static void setup() {
		makeAuthotization.paraClient("redome-fila")			
			.addResource("modred_rest_api", "modred-validation", "modred-rest-fila")
			.addScope("read_redome_fila", "write_redome_fila");
		
	}
	
	@Before
	public void setupBefore() {
		
		MockitoAnnotations.initMocks(this);
						
	}
	
	
	@Test
	public void deveSavarCordaoInternacional() throws Exception {
						
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(null, "jkdfjke893", null);
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/cordoesinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Cordão incluído com sucesso."));
		
	}
	
	
	/**
	 * SUCESSO - Salva o doador associado com o paciente, obtém o id do doador pelo identificador do registro e atualiza o doador internacional.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deveAtualizarDoadorInternacional() throws Exception {
		
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(null, "fjkf3489389", null);
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/cordoesinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Cordão incluído com sucesso."));
		
		Long idDoador = cordaoInternacionalService.obterIdentifiadorCordaoInternacionalPorIdDoRegistro("fjkf3489389");
		
		Assert.assertNotNull(idDoador);
		Assert.assertNotEquals(Long.parseLong("0"), idDoador.longValue());
		
		doador = criarDoadorInternacional(null, "fjkf3489389", null);
		doador.setPeso(new BigDecimal(74.0));
		doador.setSexo("F");
		
		doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/cordoesinternacionais/" + idDoador)
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"mensagem\": \"Cordão alterado com sucesso.\"}"));
		
	}
	
	
	private DoadorCordaoInternacionalDTO criarDoadorInternacional(Long rmr, String idRegistro, String grid) {
		
		DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO = new DoadorCordaoInternacionalDTO();
		doadorCordaoInternacionalDTO.setTipoDoador(TiposDoador.CORDAO_INTERNACIONAL.getId());
		doadorCordaoInternacionalDTO.setRmrAssociado(rmr);
		doadorCordaoInternacionalDTO.setIdRegistro(idRegistro);
		doadorCordaoInternacionalDTO.setRegistroOrigem(new RegistroDTO(2L, null, null));
		doadorCordaoInternacionalDTO.setRegistroPagamento(new RegistroDTO(2L, null, null));
		doadorCordaoInternacionalDTO.setGrid(grid);
		doadorCordaoInternacionalDTO.setSexo("M");
		doadorCordaoInternacionalDTO.setAbo("AB+");
		doadorCordaoInternacionalDTO.setPeso(new BigDecimal(85));
		doadorCordaoInternacionalDTO.setDataNascimento(LocalDate.now().minusYears(25));
		doadorCordaoInternacionalDTO.setLocusExames(criarExames());
		doadorCordaoInternacionalDTO.setStatusDoador(new StatusDoadorDTO(StatusDoador.ATIVO));
		doadorCordaoInternacionalDTO.setDataRetornoInatividade(null);
		doadorCordaoInternacionalDTO.setCadastradoEmdis(false);
		doadorCordaoInternacionalDTO.setQuantidadeTotalCD34(new BigDecimal("15"));
		doadorCordaoInternacionalDTO.setQuantidadeTotalTCN(new BigDecimal("20"));
		doadorCordaoInternacionalDTO.setVolume(new BigDecimal("52"));
			
				
		return doadorCordaoInternacionalDTO;
	}
	
	public List<LocusExameDTO> criarExames() {

        LocusExameDTO locusA = new LocusExameDTO("01:01:02", "01:01:02");
        LocusExamePkDTO locusPK = new LocusExamePkDTO();
        locusPK.setLocus(new LocusDTO(Locus.LOCUS_A));
        locusA.setId(locusPK);

        LocusExameDTO locusB = new LocusExameDTO("35:251", "35:251");
        LocusExamePkDTO locusPK2 = new LocusExamePkDTO();                
        locusPK2.setLocus(new LocusDTO(Locus.LOCUS_B));
        locusB.setId(locusPK2);

        LocusExameDTO locusDRB1 = new LocusExameDTO("11:01:19", "11:01:19");
        LocusExamePkDTO locusPK3 = new LocusExamePkDTO();
        locusPK3.setLocus(new LocusDTO(Locus.LOCUS_DRB1));
        locusDRB1.setId(locusPK3);

        List<LocusExameDTO> listaLocus = new ArrayList<>();
        listaLocus.add(locusA);
        listaLocus.add(locusB);
        listaLocus.add(locusDRB1);

        return listaLocus;
    }
	
	
}