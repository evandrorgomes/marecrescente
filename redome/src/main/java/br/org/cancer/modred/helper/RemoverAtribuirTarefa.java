/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.IProcessoFeign;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;

/**
 * Classe para centralizar a atribuição de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public class RemoverAtribuirTarefa extends IRemoverAtribuirTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoverAtribuirTarefa.class);

	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;
	
	private Long idTarefa;
	private Long objetoRelacionado;
	private Perfis perfil;
	private List<Long> parceiros;
	private TarefaDTO tarefa;
	
	public RemoverAtribuirTarefa() {
		super();
	}

	public RemoverAtribuirTarefa(Perfis perfil, Long idTipoTarefa) {
		this.perfil = perfil;
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	@Override
	public IRemoverAtribuirTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}
	@Override
	public IRemoverAtribuirTarefa comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}

	@Override
	public IRemoverAtribuirTarefa comTarefa(TarefaDTO tarefa) {
		this.tarefa = tarefa;
		return this;
	}
	
	@Override
	public IRemoverAtribuirTarefa comTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
		return this;
	}
	
	@Override
	public TarefaDTO apply() {
		
		if (RemoverAtribuirTarefa.processoFeign == null || RemoverAtribuirTarefa.tarefaFeign == null) {
			AutowireHelper.autowire(this);
		}

		LOGGER.info("Iniciando desatribuição de tarefa.");

		if (tarefa == null && idTarefa == null) {

			//final ProcessoDTO processo = obterProcesso();
			tarefa = tipoTarefa.getConfiguracao().obterTarefa()
						.comParceiros(parceiros)
						.comObjetoRelacionado(objetoRelacionado)
						.comRmr(rmr)
						.comIdDoador(idDoador)
						//.comProcessoId(processo.getId())
						.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
						.apply();
		}
		if (tarefa == null && idTarefa != null) {
			tarefa = tarefaFeign.obterTarefa(idTarefa);
		}

		if (tarefa == null) {
			throw new BusinessException("erro.mensagem.tarefa.nulo");
		}

		if (!StatusTarefa.ATRIBUIDA.equals(tarefa.getStatus())) {
			throw new BusinessException("erro.mensagem.tarefa.nao.atribuida");
		}

		//cancelarTarefaAssociadaSeHouver(tarefa);
		tarefa = tarefaFeign.removerAtribuicaoTarefa(tarefa.getId());
		
		return tarefa;

	}

	/**
	 * Cria a tarefa automática, se a tarefa original indicar que necessita.
	 * 
	 * @param tarefa tarefa que origina a tarefa automática, se a configuração indicar que há necessidade.
	 */
	/*
	 * private void cancelarTarefaAssociadaSeHouver(TarefaDTO tarefa) {
	 * 
	 * if (tipoTarefa.getTimeout() == null) { return; }
	 * 
	 * TarefaDTO tarefaRollback = obterTarefaAssociada(tarefa); if (tarefaRollback
	 * != null) { LOGGER.info("Fechando tarefa de rollback");
	 * tarefaRollback.getTipo() .getConfiguracao() .cancelarTarefa()
	 * .comProcessoId(tarefaRollback.getProcesso().getId())
	 * .comTarefa(tarefaRollback.getId()) .apply(); }
	 * 
	 * 
	 * }
	 */

	/**
	 * Obtém tarefa de rollback associada a tarefa informada.
	 * 
	 * @param tarefa ID da tarefa relacionada a tarefa automática.
	 * @param tipoTarefa Tipo de tarefa automática (TIMEOUT, ROLLBACK...)
	 * @return tarefa associada, se existir.
	 */
	/*
	 * private TarefaDTO obterTarefaAssociada(TarefaDTO tarefa) { return
	 * tipoTarefa.getTimeout().getConfiguracao().obterTarefa()
	 * .comTarefaPai(tarefa.getId()) .comProcessoId(tarefa.getProcesso().getId())
	 * .comStatus(Arrays.asList(StatusTarefa.ABERTA)).apply(); }
	 */
	/**
	 * @param tarefaFeign the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		RemoverAtribuirTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired	
	@Lazy(true)
	public void setProcessoFeign(IProcessoFeign processoFeign) {
		RemoverAtribuirTarefa.processoFeign = processoFeign;
	}

	@Override
	protected IProcessoFeign getProcessoFeign() {
		return RemoverAtribuirTarefa.processoFeign;
	}
	

}