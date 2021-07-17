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
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe responsável por fechar tarefas.
 * 
 * @author fillipe.queiroz
 *
 */
public class FecharTarefa extends IFecharTarefa {

	private static final Logger LOGGER = LoggerFactory.getLogger(FecharTarefa.class);
	
	public static IProcessoFeign processoFeign;
	public static ITarefaFeign tarefaFeign;

	private Usuario usuario;
	private Long objetoRelacionado;
	private List<Long> parceiros;
	private List<StatusTarefa> status;
	private Long idTarefa;
	private Long tarefaPai;
	private Boolean semAgendamento = false;

	public FecharTarefa() {
		super();
	}

	/**
	 * Construtor com passagem de parametros da configuração do tipo da tarefa.
	 * 
	 * @param perfil - perfis do usuario
	 * @param idTipoTarefa - tipo da tarefa
	 * @param classeObterTarefa - classe a ser utilizada para obter a tarefa
	 */
	public FecharTarefa(Perfis perfil, Long idTipoTarefa) {
		this.tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
	}

	@Override
	public FecharTarefa comUsuario(Usuario usuario) {
		this.usuario = usuario;
		return this;
	}

	@Override
	public FecharTarefa comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}

	@Override
	public FecharTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public FecharTarefa comTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
		return this;
	}

	@Override
	public IFecharTarefa comStatus(List<StatusTarefa> status) {
		this.status = status;
		return this;
	}
	
	@Override
	public IFecharTarefa comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}
	
	@Override
	public IFecharTarefa comTarefaPai(Long tarefaPai) {
		this.tarefaPai = tarefaPai;
		return this;
	}

	@Override
	public IFecharTarefa semAgendamento(Boolean semAgendamento) {
		this.semAgendamento = semAgendamento;
		return this;
	}
	
	
	@Override
	public TarefaDTO apply() {
		if (FecharTarefa.processoFeign == null || FecharTarefa.tarefaFeign == null) {
			AutowireHelper.autowire(this);
		}

		TarefaDTO tarefa = null;
		
		if (idTarefa != null) {			
			tarefa = tarefaFeign.obterTarefa(idTarefa);
		}
		else {
			//ProcessoDTO processo = obterProcesso();
			
			// TODO: habilitar este código para garantir que será fechada apenas uma tarefa. Este
			// código ainda não foi habilitado porque poderá gerar exceções em vários pontos do sistema.
			if (usuario == null && status == null) {
				status = Arrays.asList(StatusTarefa.ABERTA);
			}
			else {
				if (usuario != null && status == null) {
					throw new BusinessException("erro.mensagem.status.id.obrigatorio");
				}
			}
			
			tarefa = tipoTarefa.getConfiguracao().obterTarefa()
					.comParceiros(parceiros)
					.comObjetoRelacionado(objetoRelacionado)
					.comRmr(rmr)
					.comIdDoador(idDoador)
					//.comProcessoId(processo.getId())
					.comStatus(status)
					.comUsuario(usuario)
					.semAgendamento(semAgendamento)
					.comTarefaPai(tarefaPai).apply();
		}

		final boolean operacaoRealizada = fecharTarefa(tarefa);
		if(!operacaoRealizada){
			throw new BusinessException("erro.mensagem.tarefa.nao.pode.ser.finalizada");
		}
		
		//encerrarTarefasFollowup(tarefa.getId());
		//encerrarTarefaTimeout(tarefa.getId());
		
		//if(super.finalizarProcesso){			
			//processoFeign.terminarProcesso(tarefa.getProcesso().getId());
		//}

		return tarefa;
	}

	/*
	 * private void encerrarTarefaTimeout(Long idTarefaPai) { if
	 * (tipoTarefa.getTimeout() != null) { List<TarefaDTO> tarefasTimeout =
	 * listarTarefasFilhasEmAbertoPorTipo(idTarefaPai,
	 * tipoTarefa.getTimeout().getId()); if
	 * (CollectionUtils.isNotEmpty(tarefasTimeout)) {
	 * tarefasTimeout.forEach(tarefaTimeout -> {
	 * tarefaFeign.encerrarTarefa(tarefaTimeout.getId()); }); } } }
	 * 
	 * private void encerrarTarefasFollowup(Long idTarefaPai) { if
	 * (CollectionUtils.isNotEmpty(tipoTarefa.getFollowUp())) {
	 * tipoTarefa.getFollowUp().forEach(tipoTarefaFollowUp -> { List<TarefaDTO>
	 * tarefasFollowUp = listarTarefasFilhasEmAbertoPorTipo(idTarefaPai,
	 * tipoTarefaFollowUp.getId()); if (CollectionUtils.isNotEmpty(tarefasFollowUp))
	 * { tarefasFollowUp.forEach(tarefaFollowUp -> {
	 s* tarefaFeign.encerrarTarefa(tarefaFollowUp.getId()); }); } }); } }
	 */

	/**
	 * Tenta fechar a tarefa, porém, valida se houve alguma mudança por conta de concorrência, por exemplo.
	 * Caso tenha ocorrido, o usuário deverá ser alertado sobre a mudança de estado da tarefa.
	 * 
	 * @param tarefa tarefa a ser fechada, se possível.
	 * @return TRUE se conseguiu fechar a tarefa, FALSE caso tenha ocorrido alguma mudança inesperada.
	 */
	@SuppressWarnings("rawtypes")
	private boolean fecharTarefa(TarefaDTO tarefa) {
		if (tarefa == null) {
			LOGGER.error("TarefaDTO que seria finalizada está nula");
			return false;
		}
		else if (StatusTarefa.FEITA == tarefa.getStatusTarefa()) {
			LOGGER.error("TarefaDTO que seria finalizada já está com status como FEITA no banco. Provavelmente, algum processo concorrente já a realizou.");
			return false;
		}
		tarefa = tarefaFeign.encerrarTarefa(tarefa.getId(), finalizarProcesso);
		return true;
	}

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
	 * @param tarefaFeign the tarefaRepository to set
	 */
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		FecharTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired
	@Lazy(true)
	public void setProcessoService(IProcessoFeign processoFeign) {
		FecharTarefa.processoFeign = processoFeign;
	}
	
	public IProcessoFeign getProcessoFeign() {
		return FecharTarefa.processoFeign;
	}

}