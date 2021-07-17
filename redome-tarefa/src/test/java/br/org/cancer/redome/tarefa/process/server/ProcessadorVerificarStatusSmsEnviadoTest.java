package br.org.cancer.redome.tarefa.process.server;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorVerificarStatusSmsEnviado;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;

/**
 * Precessador para verificar o status do sms enviado.
 * 
 * @author brunosousa
 *
 */
public class ProcessadorVerificarStatusSmsEnviadoTest extends BaseConfigurationTest {

	@Autowired
	private ProcessadorVerificarStatusSmsEnviado processador;
	
	@Mock
	private IProcessoService processoService;
	
	@Mock
	private IPedidoContatoSmsFeign pedidoContatoSmsFeign;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("TESTE");
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(processador, "pedidoContatoSmsFeign", pedidoContatoSmsFeign);
		ReflectionTestUtils.setField(processador, "processoService", processoService);
	}
	
	@Test
	public void deveProcessarTarefaComSucesso() {
		Tarefa tarefa = this.obterTarefa();
		
		Mockito.when(pedidoContatoSmsFeign.atualizaStatusSmsEnviado(Mockito.anyLong())).thenReturn(ResponseEntity.ok().build());
		processador.processar(tarefa);
		
	}
	
	@Test
	public void deveProcessarTarefaFechandoATarefaQuandoTarefaPaiComStatusDiferenteDeAberta() {
		Tarefa tarefa = this.obterTarefa();
		tarefa.getTarefaPai().setStatus(StatusTarefa.FEITA.getId());
		
		Mockito.when(processoService.fecharTarefa(Mockito.any(Tarefa.class))).thenReturn(tarefa);
		processador.processar(tarefa);
		
	}
	
	@Test
	public void deveProcessarTarefaFechandoATarefaQuandoTarefaPaiNaoExistir() {
		Tarefa tarefa = this.obterTarefa();
		tarefa.setTarefaPai(null);
		
		Mockito.when(processoService.fecharTarefa(Mockito.any(Tarefa.class))).thenReturn(tarefa);
		processador.processar(tarefa);
	}
	
	private Tarefa obterTarefa() {
		Tarefa tarefaPai = new Tarefa();
		tarefaPai.setRelacaoEntidade(109903L);
		tarefaPai.setStatus(StatusTarefa.ABERTA.getId());
		
		Tarefa tarefa = new Tarefa();
		tarefa.setTarefaPai(tarefaPai);
		
		tarefa.setDataCriacao(LocalDateTime.now().minusDays(5));
		TipoTarefa tipoTarefa = new TipoTarefa();
		tipoTarefa.setTempoExecucao(432000L);
		tarefa.setTipoTarefa(tipoTarefa );
		Processo processo = new Processo();
		processo.setId(81328L);
		processo.setRmr(1L);
		tarefa.setProcesso(processo );
		tarefa.setId(2L);
		return tarefa;
	}

	
}
