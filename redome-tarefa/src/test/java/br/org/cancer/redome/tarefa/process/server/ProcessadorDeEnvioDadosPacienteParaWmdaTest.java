package br.org.cancer.redome.tarefa.process.server;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.redome.tarefa.dto.LocusExameHlaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PacienteWmdaDTO;
import br.org.cancer.redome.tarefa.dto.PesquisaWmdaDTO;
import br.org.cancer.redome.tarefa.dto.WmdaDto;
import br.org.cancer.redome.tarefa.dto.WmdaSearchDto;
import br.org.cancer.redome.tarefa.feign.client.IPacienteFeign;
import br.org.cancer.redome.tarefa.feign.client.IPesquisaWmdaFeign;
import br.org.cancer.redome.tarefa.integracao.client.impl.RestWmdaClient;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.persistence.ITarefaRepository;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorDeEnvioDadosPacienteParaWmda;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.service.IWmdaService;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;

/**
 * Classe teste para {@link ProcessadorDeEnvioDadosPacienteParaWmda}
 * @author ergomes
 *
 */
public class ProcessadorDeEnvioDadosPacienteParaWmdaTest  extends BaseConfigurationTest{
	
	@Autowired
	private ProcessadorDeEnvioDadosPacienteParaWmda processador;
	
	@Autowired
	private IWmdaService wmdaService;

	@Mock
	private IProcessoService processoService;
	
	@Mock
	private IPacienteFeign pacienteFeign;

	@Mock
	private IPesquisaWmdaFeign pesquisaWmdaFeign;
	
	@Mock
	private RestWmdaClient restWmdaClient;
	
	@Mock
	private ITarefaRepository tarefaRepository;


	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("TESTE");
	}
	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(processador, "pacienteFeign", pacienteFeign);
		ReflectionTestUtils.setField(processador, "pesquisaWmdaFeign", pesquisaWmdaFeign);
		ReflectionTestUtils.setField(processador, "wmdaService", wmdaService);
		ReflectionTestUtils.setField(wmdaService, "restWmdaClient", restWmdaClient);
		ReflectionTestUtils.setField(processador, "processoService", processoService);
	}
	
	@Test
	public void deveProcessarTarefaComSucesso() {
		Tarefa tarefa = this.obterTarefa();
		tarefa.setTipoTarefa(new TipoTarefa(TiposTarefa.ENVIAR_DADOS_PACIENTE_WMDA_FOLLOWUP.getId()));
		
		PacienteWmdaDTO pacienteWmdaDto = construirPacienteWmda();

		PesquisaWmdaDTO pesquisaWmdaDTO = new PesquisaWmdaDTO();
		pesquisaWmdaDTO.setId(2L);
		
		Mockito.when(pacienteFeign.obterPacienteDtoWmdaPorRmr(Mockito.anyLong())).thenReturn(pacienteWmdaDto);
		
		Mockito.when(restWmdaClient.post(Mockito.anyString(), Mockito.any(WmdaDto.class))).thenReturn("{\"wmdaId\" : 526}");

		Mockito.when(restWmdaClient.post(Mockito.anyString(), Mockito.any(WmdaSearchDto.class))).thenReturn("{\"searchId\" : 10}");

		Mockito.when(restWmdaClient.get(Mockito.anyString())).thenReturn("{\"requests\":[{\"searchResultsId\":3435}]}");
		
		Mockito.when(pesquisaWmdaFeign.criarPesquisaWmda(Mockito.any(PesquisaWmdaDTO.class))).thenReturn(pesquisaWmdaDTO);

		Tarefa tarefaNova = this.obterTarefa();
		tarefaNova.setTipoTarefa(TipoTarefa.builder().id(109L).build());
		tarefaNova.setRelacaoEntidade(13L);
		
		Mockito.when(processoService.criarTarefa(Mockito.any(Tarefa.class))).thenReturn(tarefaNova);

		Mockito.when(processoService.fecharTarefa(Mockito.any(Tarefa.class))).thenReturn(tarefa);
		
		processador.processar(tarefa);
	}
	
	private PacienteWmdaDTO construirPacienteWmda() {
		PacienteWmdaDTO pacienteWmda = new PacienteWmdaDTO();
		
		pacienteWmda.setAbo("B+");
		pacienteWmda.setDataNascimento(LocalDate.now());
		pacienteWmda.setPeso(new BigDecimal(80));
		pacienteWmda.setRmr(3059735L);
		pacienteWmda.setSexo("M");
		
		List<LocusExameHlaWmdaDTO> listaLocusExame = new ArrayList<LocusExameHlaWmdaDTO>();
		LocusExameHlaWmdaDTO locusA = new LocusExameHlaWmdaDTO();
		locusA.setLocus("A");
		locusA.setPrimeiroAlelo("01:01");
		locusA.setSegundoAlelo("01:01");
		
		LocusExameHlaWmdaDTO locusB = new LocusExameHlaWmdaDTO();
		locusB.setLocus("B");
		locusB.setPrimeiroAlelo("08:02");
		locusB.setSegundoAlelo("08:02");
		
		
		LocusExameHlaWmdaDTO locusC = new LocusExameHlaWmdaDTO();
		locusC.setLocus("C");
		locusC.setPrimeiroAlelo("01:01");
		locusC.setSegundoAlelo("01:01");
		
		
		listaLocusExame.add(locusA);
		listaLocusExame.add(locusB);
		listaLocusExame.add(locusC);
		pacienteWmda.setLocusExame(listaLocusExame);
		return pacienteWmda;
	}

	private Tarefa obterTarefa() {
		Tarefa tarefa = new Tarefa();
		tarefa.setRelacaoEntidade(12L);
		tarefa.setDataCriacao(LocalDateTime.now().minusDays(5));
		tarefa.setStatus(StatusTarefa.ABERTA.getId() );
		
		TipoTarefa tipoTarefa = new TipoTarefa();
		tipoTarefa.setTempoExecucao(432000L);
		tarefa.setTipoTarefa(tipoTarefa );
		Processo processo = new Processo();
		processo.setId(81328L);
		processo.setRmr(3059735L);
		tarefa.setProcesso(processo);
		tarefa.setId(1L);
		return tarefa;
	}

}
