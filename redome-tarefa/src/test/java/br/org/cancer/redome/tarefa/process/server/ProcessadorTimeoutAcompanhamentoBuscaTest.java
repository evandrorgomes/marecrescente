package br.org.cancer.redome.tarefa.process.server;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.redome.tarefa.feign.client.IBuscaFeign;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorTimeoutAcompanhamentoBusca;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;

/**
 * Classe de testes para {@link ProcessadorTimeoutAcompanhamentoBuscaTest}
 * @author Filipe Paes
 *
 */
public class ProcessadorTimeoutAcompanhamentoBuscaTest  extends BaseConfigurationTest {
	
	private ProcessadorTimeoutAcompanhamentoBusca processador;
	
	@Autowired
	private IProcessoService processoService; 
	
	@Mock
	private IBuscaFeign buscaFeign;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("TESTE");
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.processador = new ProcessadorTimeoutAcompanhamentoBusca();
		ReflectionTestUtils.setField(processador, "buscaFeign", buscaFeign);
		ReflectionTestUtils.setField(processador, "processoService", processoService);
	}
	
	@Test
	public void deveProcessarTarefaComSucesso() {
		Tarefa tarefa = this.obterTarefa();
		ResponseEntity<String> resultOk = new ResponseEntity<String>(HttpStatus.OK);
		Mockito.when(buscaFeign.alterarStatusParaLiberado(Mockito.anyLong())).thenReturn(resultOk);
		Mockito.when(buscaFeign.removerAtribuicaoDeBusca(Mockito.anyLong())).thenReturn(resultOk);
		processador.processar(tarefa);
	}
	
	private Tarefa obterTarefa() {
		Tarefa tarefa = new Tarefa();
		tarefa.setRelacaoEntidade(109903L);
		tarefa.setDataCriacao(LocalDateTime.now().minusSeconds(60));
		TipoTarefa tipoTarefa = new TipoTarefa();
		tipoTarefa.setTempoExecucao(10L);
		tarefa.setTipoTarefa(tipoTarefa );
		Processo processo = new Processo();
		processo.setId(81328L);
		processo.setRmr(1L);
		tarefa.setProcesso(processo );
		tarefa.setId(7478L);
		return tarefa;
	}
	

}
