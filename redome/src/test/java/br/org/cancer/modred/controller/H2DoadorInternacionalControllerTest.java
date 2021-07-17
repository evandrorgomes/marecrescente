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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.controller.dto.PedidoDto;
import br.org.cancer.modred.controller.dto.doador.DoadorCordaoInternacionalDTO;
import br.org.cancer.modred.controller.dto.doador.LocusDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExameDTO;
import br.org.cancer.modred.controller.dto.doador.LocusExamePkDTO;
import br.org.cancer.modred.controller.dto.doador.RegistroDTO;
import br.org.cancer.modred.controller.dto.doador.StatusDoadorDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.service.IDoadorInternacionalService;
import br.org.cancer.modred.service.IExecutarProcedureMatchService;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.ExamePacienteService;
import br.org.cancer.modred.service.impl.GenotipoPacienteService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

public class H2DoadorInternacionalControllerTest extends BaseConfigurationTest {

	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Autowired	
	private ExamePacienteService examePacienteService;
	
	@Autowired
	private GenotipoPacienteService genotipoService;
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
	
	@Autowired
	private IDoadorInternacionalService doadorInternacionalService;
		
	@Spy
	private IExecutarProcedureMatchService executarProcedureMatchService;
		
		
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("ANALISTA_BUSCA")
			.addPerfil("ANALISTA_BUSCA_REDOME")
			.addRecurso("VISUALIZAR_IDENTIFICACAO_COMPLETA_DOADOR")
			.addRecurso("VISUALIZAR_LOG_EVOLUCAO")
			.addRecurso("VISUALIZAR_FAVORITO_MATCH")
			.addRecurso("VISUALIZAR_RESSALVA_MATCH")
			.addRecurso("VISUALIZAR_COMENTARIO_MATCH")
			.addRecurso("SOLICITAR_FASE_2_NACIONAL")
			.addRecurso("SOLICITAR_FASE_3_NACIONAL")
			.addRecurso("DISPONIBILIZAR_DOADOR")
			.addRecurso("CANCELAR_FASE_2")
			.addRecurso("CANCELAR_FASE_3")
			.addRecurso("VISUALIZAR_OUTROS_PROCESSOS")
			.addRecurso("ENCONTRAR_CENTRO_TRANSPLANTADOR")
			.addRecurso("SOLICITAR_FASE3_PACIENTE")
			.addRecurso("ALTERAR_DOADOR_INTERNACIONAL")
			.addRecurso("CADASTRAR_RESULTADO_PEDIDO_CT")
			.addRecurso("VISUALIZAR_IDENTIFICACAO_COMPLETA")
			.addRecurso("CONCLUIR_BUSCA")
			.addRecurso("DIALOGO_BUSCA")
			.addRecurso("CADASTRAR_RESULTADO_PEDIDO_FASE_2_INTERNACIONAL")
			.addRecurso("ANALISE_MATCH")
			.addRecurso("CRIAR_PRESCRICAO")
			.addRecurso("CONFIRMAR_CENTRO_TRANSPLANTADOR")
			.addRecurso("SOLICITAR_FASE_3_INTERNACIONAL")
			.addRecurso("CADASTRAR_RESULTADO_PEDIDO_IDM")
			.addRecurso("PACIENTES_PARA_PROCESSO_BUSCA")
			.addRecurso("ALTERAR_LABORATORIO_PARA_PEDIDO_EXAME")
			.addRecurso("SOLICITAR_FASE_2_INTERNACIONAL")
			.addRecurso("CANCELAR_FASE_3_INTERNACIONAL")
			.addRecurso("CONSULTAR_DOADOR")
			.addRecurso("ADICIONAR_COMENTARIO_MATCH")
			.addRecurso("CANCELAR_FASE_2_INTERNACIONAL")
			.addRecurso("CADASTRAR_DOADOR_INTERNACIONAL")
			.addRecurso("INCLUIR_RESSALVA")
			.addRecurso("VISTAR_CHECKLIST")
			.addRecurso("EDITAR_FASE2_INTERNACIONAL")
			.addRecurso("EXIBIR_HISTORICO_MATCH")
			.addRecurso("CRIAR_PEDIDO_ENVIO_PACIENTE_EMDIS")
			.addRecurso("CADASTRAR_ARQUIVO_RELATORIO_INTERNACIONAL")
			.addRecurso("LISTAR_ARQUIVOS_RELATORIO_BUSCA_INTERNACIONAL")
			.addRecurso("EXCLUIR_ARQUIVO_RELATORIO_BUSCA_INTERNACIONAL")
			.addRecurso("VISUALIZAR_GENOTIPO_DIVERGENTE")
			.addRecurso("DESCARTAR_LOCUS_EXAME")
			.addRecurso("CONSULTA_PACIENTES_PARA_PROCESSO_BUSCA");			
		
	}
	
	@Before
	public void setupBefore() {
		
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(doadorInternacionalService, "executarProcedureMatchService", executarProcedureMatchService);
						
	}
	
	
	/**
	 * SUCESSO - Deve salvar o doador internacional associado ao paciente. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void deveSavarDoadorInternacionalAssociadoAoPaciente() throws Exception {
		
		Long rmr = H2BasePaciente.criarPacienteComExameAprovadoEAprovacaoInicial(mockMvc, pacienteService, avaliacaoService, 
				examePacienteService, genotipoService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE DOADOR")
				.comOCpf("87725634002")
				.comOCns("765401219830009"));
				
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(rmr, "DS0298348", "ldskjfkldsjl");
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/doadoresinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Doador incluído com sucesso."));
		
		Mockito.verify(executarProcedureMatchService, Mockito.timeout(100).times(1)).gerarMatchDoador(Mockito.any(Doador.class), Mockito.nullable(PedidoDto.class), Mockito.nullable(Long.class));
		
	}
		
	/**
	 * SUCESSO - Salva o doador associado com o paciente, obtém o id do doador pelo identificador do registro e atualiza o doador internacional.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deveAtualizarDadosPessoaisDoadorInternacional() throws Exception {
				
		Long rmr = H2BasePaciente.criarPacienteComExameAprovadoEAprovacaoInicial(mockMvc, pacienteService, avaliacaoService, 
				examePacienteService, genotipoService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE PACENITE COM DOADOR internacional")
				.comOCpf("13156651079")
				.comOCns("237176203510018"));
				
		DoadorCordaoInternacionalDTO doador = criarDoadorInternacional(rmr, "DE084345348", "ldsds548fkldsjl");
		MockMultipartFile doadorJson = new MockMultipartFile("doadorCordaoInternacional", "", "application/json",BaseConfigurationTest.getObjectMapper().writeValueAsBytes(doador));		
		
		mockMvc
			.perform(CreateMockHttpServletRequest.makeMultipart("/api/doadoresinternacionais", "POST")
				.file(doadorJson)
                .contentType(MediaType.MULTIPART_FORM_DATA))
		.andExpect(status().isOk())
		.andExpect(content().string("Doador incluído com sucesso."));
		
		Long idDoador = doadorInternacionalService.obterIdentifiadorDoadorInternacionalPorIdDoRegistro("DE084345348");
				
		Assert.assertNotNull(idDoador);
		Assert.assertNotEquals(Long.parseLong("0"), idDoador.longValue());
		
		doador = criarDoadorInternacional(null, "DE084345348", "ldsds548fkldsjl");
		doador.setPeso(new BigDecimal(74.0));
		doador.setSexo("F");
		doador.setRegistroOrigem(null);
		doador.setRegistroPagamento(null);
		
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