package br.org.cancer.redome.tarefa.process.server;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import br.org.cancer.redome.tarefa.dto.ConfiguracaoDTO;
import br.org.cancer.redome.tarefa.feign.client.IConfiguracaoFeign;
import br.org.cancer.redome.tarefa.feign.client.IPedidoContatoSmsFeign;
import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorValidarPedidoContatoSmsFollowUp;
import br.org.cancer.redome.tarefa.service.IProcessoService;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;

/**
 * Processador que verifica o tempo  de vida do pedido de sms. 
 * Caso tenha se cumprido o tempo fecha o pedido de contato sms e inativa o doador.
 * 
 * @author brunosousa
 *
 */
public class ProcessadorValidarPedidoContatoSmsFollowUpTest extends BaseConfigurationTest {

	@Autowired
	private ProcessadorValidarPedidoContatoSmsFollowUp processador;
	
	@Mock
	private IProcessoService processoService;
	
	@Mock
	private IConfiguracaoFeign configuracaoFeign;
	
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
		ReflectionTestUtils.setField(processador, "configuracaoFeign", configuracaoFeign);
		ReflectionTestUtils.setField(processador, "processoService", processoService);
	}
	
	@Test
	public void deveProcessarTarefaFechandoPedidoContatoSmsComPrazoVencido() {
		Tarefa tarefa = this.obterTarefa();
		
		Mockito.when(configuracaoFeign.obterConfiguracaoPorChave(Mockito.anyString())).then(new Answer<ResponseEntity<ConfiguracaoDTO>>() {

			@Override
			public ResponseEntity<ConfiguracaoDTO> answer(InvocationOnMock invocation) throws Throwable {				
				return ResponseEntity.ok(ConfiguracaoDTO.builder().chave(invocation.getArgument(0)).valor("3").build());
			}
			
		});
		Mockito.when(pedidoContatoSmsFeign.finalizarPedidoContatoSms(Mockito.anyLong())).thenReturn(ResponseEntity.ok().build());
		processador.processar(tarefa);
		
	}
	
	@Test
	public void deveProcessarTarefaNaoFechandoPedidoContatoSmsNoPrazo() {
		Tarefa tarefa = this.obterTarefa();
		
		Mockito.when(configuracaoFeign.obterConfiguracaoPorChave(Mockito.anyString())).then(new Answer<ResponseEntity<ConfiguracaoDTO>>() {

			@Override
			public ResponseEntity<ConfiguracaoDTO> answer(InvocationOnMock invocation) throws Throwable {				
				return ResponseEntity.ok(ConfiguracaoDTO.builder().chave(invocation.getArgument(0)).valor("7").build());
			}
			
		});
		Mockito.when(pedidoContatoSmsFeign.finalizarPedidoContatoSms(Mockito.anyLong())).thenReturn(ResponseEntity.ok().build());
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
		tarefaPai.setDataCriacao(LocalDateTime.now().minusDays(5));
		tarefaPai.setRelacaoEntidade(109903L);
		tarefaPai.setStatus(StatusTarefa.ABERTA.getId());
		
		Tarefa tarefa = new Tarefa();
		tarefa.setTarefaPai(tarefaPai);
		
		tarefa.setDataCriacao(tarefaPai.getDataCriacao());
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
