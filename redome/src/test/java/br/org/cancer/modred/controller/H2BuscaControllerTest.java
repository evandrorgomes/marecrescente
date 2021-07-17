package br.org.cancer.modred.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.modred.controller.dto.PacienteDto;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.ILogEvolucaoService;
import br.org.cancer.modred.service.impl.AvaliacaoService;
import br.org.cancer.modred.service.impl.BuscaService;
import br.org.cancer.modred.service.impl.ExamePacienteService;
import br.org.cancer.modred.service.impl.GenotipoPacienteService;
import br.org.cancer.modred.service.impl.PacienteService;
import br.org.cancer.modred.test.util.BaseConfigurationTest;
import br.org.cancer.modred.test.util.CreateMockHttpServletRequest;
import br.org.cancer.modred.test.util.MockService;
import br.org.cancer.modred.test.util.base.H2BasePaciente;

/**
 * Testes de contraladores de Busca.
 * @author Filipe Paes
 *
 */
public class H2BuscaControllerTest  extends BaseConfigurationTest{

	
	
	
	@Autowired
	private PacienteService pacienteService;
	
	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@Autowired
	private ExamePacienteService examePacienteService;
	
	@Autowired
	private BuscaService buscaService;
	
	@Autowired
	private GenotipoPacienteService genotipoPacienteService; 
	
	@Autowired
	private ILogEvolucaoService logEvolucaoService;
			
	@BeforeClass
	public static void setup() {
		makeAuthotization.paraUsuario("AVALIADOR_REDOME")
			.addPerfil("AVALIADOR_EXAME_HLA")
			.addRecurso("RECUSAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
					"CONSULTAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
					"DETALHE_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
					"VISUALIZAR_FICHA_PACIENTE",
					"CONSULTAR_EXAMES_PACIENTE",
					"CONSULTAR_EVOLUCOES_PACIENTE",
					"AVALIAR_PACIENTE",
					"VISUALIZAR_IDENTIFICACAO_PARCIAL",
					"CONSULTAR_AVALIACOES",
					"ACEITAR_TRANSFERENCIA_PACIENTE_CENTRO_AVALIADOR",
					"VISUALIZAR_PENDENCIAS_AVALIACAO",
					"CONFERIR_EXAME_HLA");
	}
	
	
	@Before
	public void setupBefore() {		
		MockitoAnnotations.initMocks(this);
		
		MockService.mockCriarPacienteComAvaliacaoInicialAprovadaEExameAprovado(examePacienteService, genotipoPacienteService);
						
		ReflectionTestUtils.setField(examePacienteService, "usuarioService", MockService.criarMockUsuarioService(3L, Arrays.asList(new Perfil(Perfis.AVALIADOR_EXAME_HLA.getId())), 
				null));
	}
	
	@Test
	public void deveAlterarStatusDeBuscaParaAlterado() throws Exception{
		H2BasePaciente.criarPacienteComExameAprovadoEAprovacaoInicial(mockMvc, pacienteService, avaliacaoService, examePacienteService, 
				genotipoPacienteService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE PACIENTE PARA BUSCA ALTERADA")
				.comOCpf("25587360008")
				.comOCns("258209557230005"));
		
		
		Busca busca = buscaService.findAll().get(0);
		Long rmr = busca.getPaciente().getRmr();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePut("/api/buscas/" + rmr + "/alterarstatusparaliberado")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk());		
	}
	
	
	@Test
	public void deveRemoverAtribuicaoDeBuscaDeAnalista() throws Exception{
		H2BasePaciente.criarPacienteComExameAprovadoEAprovacaoInicial(mockMvc, pacienteService, avaliacaoService, 
				examePacienteService, genotipoPacienteService, logEvolucaoService, new PacienteDto()
				.comADataNascimento(LocalDate.now().minusYears(25))
				.comONome("TESTE DE PACIENTE PARA DESATRIBUIR BUSCA DO ANALISTA")
				.comOCpf("93828296017")
				.comOCns("192313810520018"));
		
		
		Busca busca = buscaService.findAll().get(0);
		Long rmr = busca.getPaciente().getRmr();
		
		mockMvc
		.perform(CreateMockHttpServletRequest.makePut("/api/buscas/" + rmr + "/removeratribuicaobusca")
		.contentType(BaseConfigurationTest.CONTENT_TYPE))
		.andExpect(status().isOk());		
	}
	
	

}
