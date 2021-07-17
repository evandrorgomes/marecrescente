/**
 * 
 */
package br.org.cancer.modred.helper;

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
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por cancelar tarefas.
 * 
 * @author bruno.sousa
 *
 */
public class CancelarTarefa extends ICancelarTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(CancelarTarefa.class);
	
	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;

	private Usuario usuario;
	private Long objetoRelacionado;
	private List<Long> parceiros;
	private List<StatusTarefa> status;
	private Long idTarefa;
	

	
	public CancelarTarefa() {
		super();
	}

	/**
	 * Construtor com passagem de parametros da configuração do tipo da tarefa.
	 * 
	 * @param perfil - perfis do usuario
	 * @param idTipoTarefa - tipo da tarefa
	 * 
	 */
	public CancelarTarefa(Perfis perfil, Long idTipoTarefa) {
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	@Override
	public ICancelarTarefa comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	@Override
	public ICancelarTarefa comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}

	@Override
	public ICancelarTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public ICancelarTarefa comTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
		return this;
	}
	

	@Override
	public ICancelarTarefa comStatus(List<StatusTarefa> status) {
		this.status = status;
		return this;
	}
	
	
	@Override
	public TarefaDTO apply() {
		//tarefaRepository = ApplicationContextProvider.obterBean(ITarefaRepository.class);
		
		if (CancelarTarefa.processoFeign == null || CancelarTarefa.tarefaFeign == null) {
			AutowireHelper.autowire(this);
		}
		
		TarefaDTO tarefa = null;
		
		if (idTarefa != null) {			
			tarefa = tarefaFeign.obterTarefa(idTarefa);
		}
		else {
			//ProcessoDTO processo = obterProcesso();
			
			tarefa = tipoTarefa.getConfiguracao().obterTarefa()
						.comParceiros(parceiros)
						.comObjetoRelacionado(objetoRelacionado)
						.comRmr(rmr)
						.comIdDoador(idDoador)						
						.comStatus(status)
						.comUsuario(usuario).apply();
	
		}
		
		final boolean operacaoRealizada = cancelarTarefa(tarefa);
		if(!operacaoRealizada){
			throw new BusinessException("erro.mensagem.tarefa.nao.pode.ser.cancelada");
		}
		
		//cancelarTarefasFollowup(tarefa.getId());
		//cancelarTarefaTimeout(tarefa.getId());
		
		//if (super.canncelarProcesso) {			
//			final ProcessoDTO processo = processoFeign.cancelarProcesso(tarefa.getProcesso().getId());
	//		tarefa.setProcesso(processo);
		//}
			
		return tarefa;
	}

	/**
	 * Tenta cancelar a tarefa, porém, valida se houve alguma mudança por conta de concorrência, por exemplo.
	 * Caso tenha ocorrido, o usuário deverá ser alertado sobre a mudança de estado da tarefa.
	 * 
	 * @param tarefa tarefa a ser cancelada, se possível.
	 * @return TRUE se conseguiu cancelar a tarefa, FALSE caso tenha ocorrido alguma mudança inesperada.
	 */
	@SuppressWarnings("rawtypes")
	private boolean cancelarTarefa(TarefaDTO tarefa) {
		if (tarefa == null) {
			LOGGER.error("TarefaDTO que seria finalizada está nula");
			return false;
		}
		else if (StatusTarefa.CANCELADA == tarefa.getStatusTarefa()) {
			LOGGER.error("TarefaDTO que seria cancelada já está com status como CANCELADA no banco. Provavelmente, algum processo concorrente já a realizou.");
			return false;
		}
		else if (StatusTarefa.FEITA == tarefa.getStatusTarefa()) {
			LOGGER.error("TarefaDTO que seria cancelada e está com status como FEITA no banco. Provavelmente, algum processo concorrente fechou a mesma.");
			return false;
		}
		tarefa = tarefaFeign.cancelarTarefa(tarefa.getId(), canncelarProcesso);

		return true;
	}
	
	/*
	 * private void cancelarTarefaTimeout(Long idTarefaPai) { if
	 * (tipoTarefa.getTimeout() != null) { List<TarefaDTO> tarefasTimeout =
	 * listarTarefasFilhasEmAbertoPorTipo(idTarefaPai,
	 * tipoTarefa.getTimeout().getId()); if
	 * (CollectionUtils.isNotEmpty(tarefasTimeout)) {
	 * tarefasTimeout.forEach(tarefaTimeout -> {
	 * tarefaFeign.encerrarTarefa(tarefaTimeout.getId()); }); } } }
	 * 
	 * private void cancelarTarefasFollowup(Long idTarefaPai) { if
	 * (CollectionUtils.isNotEmpty(tipoTarefa.getFollowUp())) {
	 * tipoTarefa.getFollowUp().forEach(tipoTarefaFollowUp -> { List<TarefaDTO>
	 * tarefasFollowUp = listarTarefasFilhasEmAbertoPorTipo(idTarefaPai,
	 * tipoTarefaFollowUp.getId()); if (CollectionUtils.isNotEmpty(tarefasFollowUp))
	 * { tarefasFollowUp.forEach(tarefaFollowUp -> {
	 * tarefaFeign.encerrarTarefa(tarefaFollowUp.getId()); }); } }); } }
	 */
	
	/**
	 * Lista todas as tarefas automáticas, em aberto, associadas a tarefa (coluna "tarefaPai") informada no parâmetro.
	 * 
	 * @param idTarefaPai tarefa associada as tarefas automáticas.
	 * @param idTipoTarefaAutomatica tipo da tarefa automática.
	 * @return lista de tarefas automáticas associadas.
	 */
	private List<TarefaDTO> listarTarefasFilhasEmAbertoPorTipo(Long idTarefaPai, Long idTipoTarefaAutomatica) {
		return tarefaFeign.listarTarefasFilhasEmAbertoPorTipo(idTarefaPai, idTipoTarefaAutomatica);
	}
	

	/**
	 * @param tarefaRepository the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		CancelarTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired
	@Lazy(true)
	public void setProcessoFeign(IProcessoFeign processoFeign) {
		CancelarTarefa.processoFeign = processoFeign;
	}

	@Override
	protected IProcessoFeign getProcessoFeign() {
		return CancelarTarefa.processoFeign;
	}

	
}