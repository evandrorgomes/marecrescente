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
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;

public class H2DoadorInternacionalControllerForClientTest extends BaseConfigurationTest {
	
	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;

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
	public void deveSavarDoadorInternacional() throws Exception {
						
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(null, "DR02458348", "ldw43kjfghgkldsjl");
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/doadoresinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Doador incluído com sucesso."));
		
	}
		
	/**
	 * SUCESSO - Salva o doador associado com o paciente, obtém o id do doador pelo identificador do registro e atualiza o doador internacional.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deveAtualizarDoadorInternacional() throws Exception {
		
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(null, "DH084345348", "ldsds548fdcdfkldsje");
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/doadoresinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Doador incluído com sucesso."));
		
		Long idDoador = doadorInternacionalService.obterIdentifiadorDoadorInternacionalPorIdDoRegistro("DH084345348");
		
		Assert.assertNotNull(idDoador);
		Assert.assertNotEquals(Long.parseLong("0"), idDoador.longValue());
		
		doador = criarDoadorInternacional(null, "DH084345348", "ldsds548fkldsje");
		doador.setPeso(new BigDecimal(74.0));
		doador.setSexo("F");
		
		doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makePut("/api/doadoresinternacionais/" + idDoador)
				.content(BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador))
				.contentType(BaseConfigurationTest.CONTENT_TYPE))
			.andExpect(status().isOk())
			.andExpect(content().json("{\"mensagem\": \"Doador alterado com sucesso.\"}"));
		
	}
	
	private DoadorCordaoInternacionalDTO criarDoadorInternacional(Long rmr, String idRegistro, String grid) {
		
		DoadorCordaoInternacionalDTO doadorCordaoInternacionalDTO = new DoadorCordaoInternacionalDTO();
		doadorCordaoInternacionalDTO.setTipoDoador(TiposDoador.INTERNACIONAL.getId());
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
			
				
		return doadorCordaoInternacionalDTO;
	}
	
	public List<LocusExameDTO> criarExames() {

        LocusExameDTO locusA = new LocusExameDTO(Locus.LOCUS_A, "01:01:02", "01:01:02");
        LocusExamePkDTO locusPK = new LocusExamePkDTO();
        locusPK.setLocus(new LocusDTO(Locus.LOCUS_A));
        locusA.setId(locusPK);

        LocusExameDTO locusB = new LocusExameDTO(Locus.LOCUS_B, "35:251", "35:251");
        LocusExamePkDTO locusPK2 = new LocusExamePkDTO();                
        locusPK2.setLocus(new LocusDTO(Locus.LOCUS_B));
        locusB.setId(locusPK2);

        LocusExameDTO locusDRB1 = new LocusExameDTO(Locus.LOCUS_DRB1, "11:01:19", "11:01:19");
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