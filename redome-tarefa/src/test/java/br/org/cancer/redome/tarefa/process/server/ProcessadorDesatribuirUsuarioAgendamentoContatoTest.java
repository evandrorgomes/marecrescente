package br.org.cancer.redome.tarefa.process.server;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.redome.tarefa.model.Processo;
import br.org.cancer.redome.tarefa.model.Tarefa;
import br.org.cancer.redome.tarefa.model.TipoTarefa;
import br.org.cancer.redome.tarefa.model.domain.StatusTarefa;
import br.org.cancer.redome.tarefa.process.server.impl.ProcessadorDesatribuirUsuarioAgendamentoContato;
import br.org.cancer.redome.tarefa.util.BaseConfigurationTest;


/**
 * Classe para testar {@link ProcessadorDesatribuirUsuarioAgendamentoContato}
 * @author Filipe Paes
 *
 */
public class ProcessadorDesatribuirUsuarioAgendamentoContatoTest extends BaseConfigurationTest{

	
	@Autowired
	private ProcessadorDesatribuirUsuarioAgendamentoContato processador;
	
	@BeforeClass
	public static void setupClass() {
		makeAuthotization.paraUsuario("MEDICO")
			.addPerfil("MEDICO")
			.addRecurso("TESTE");
	}
	
	@Before
	public void setup() {
	}
	
	@Test
	public void deveDesatribuirAgendamentoDoUsuarioCasoDataDaTarefaSejaMaiorQueAtualETarefaEstejaAberta() {
		processador.processar(obterTarefa());
	}
	
	private Tarefa obterTarefa() {
		Tarefa tarefa = new Tarefa();		
		tarefa.setDataCriacao(LocalDateTime.now().minusDays(5));
		tarefa.setStatus(StatusTarefa.ABERTA.getId() );
		Tarefa tarefaPai = new Tarefa();
		tarefaPai.setId(300L);
		tarefa.setTarefaPai(tarefaPai );
		
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
