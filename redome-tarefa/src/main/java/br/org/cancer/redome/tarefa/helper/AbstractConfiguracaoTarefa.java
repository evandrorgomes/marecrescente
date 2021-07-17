/**
 * 
 */
package br.org.cancer.redome.tarefa.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import br.org.cancer.redome.tarefa.helper.domain.Perfis;
import br.org.cancer.redome.tarefa.model.domain.ITipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.ITiposTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;

/**
 * @author Pizão
 * 
 * Interface que define a estrutura da configuração necessária para a
 * realização de requisições genéricas a lista de tarefas do process
 * server.
 */
public abstract class AbstractConfiguracaoTarefa implements IConfiguracaoProcessServer{

	private ITipoProcesso tipoProcesso;
	private Perfis perfil;
	private ITiposTarefa timeout;
	private List<ITiposTarefa> followUp;
	private boolean criarTarefaAtribuida;
	private Long idTipoTarefa;	
	private boolean somenteAgrupado;
	private ITiposTarefa[] tiposTarefa;
	private Class<? extends IProcessadorTarefa> processadorTarefa;
	
	/**
	 * Coonstrutor para padronizar as extensões de configuração de tarefa, forçando
	 * que seja passado um ID do tipo tarefa para cada instância.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa associado.
	 */
	protected AbstractConfiguracaoTarefa(Long idTipoTarefa) {
		this.setCriarTarefaAtribuida(Boolean.FALSE);
		this.idTipoTarefa = idTipoTarefa;
	}
	
	@Override
	public ITipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}
	@Override
	public void setTipoProcesso(ITipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
	}

	@Override
	public Perfis getPerfil() {
		return perfil;
	}
	@Override
	public void setPerfil(Perfis perfil) {
		this.perfil = perfil;
	}

	@Override
	public ITiposTarefa getTimeout() {
		return timeout;
	}
	
	@Override
	public void setTimeout(ITiposTarefa timeout) {
		this.timeout = timeout;
	}

	@Override
	public List<ITiposTarefa> getFollowUp() {
		return followUp;
	}
	
	public void addFollowup(List<ITiposTarefa> followUp) {
		this.followUp = followUp;
	}

	@Override
	public boolean isCriarTarefaAtribuida() {
		return criarTarefaAtribuida;
	}
	
	public void setCriarTarefaAtribuida(boolean criarTarefaAtribuida) {
		this.criarTarefaAtribuida = criarTarefaAtribuida;
	}

	/**
	 * @param tipoProcesso tipo de processo que o tipo de tarefa é utilizado
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer para(ITipoProcesso tipoProcesso) {
		this.tipoProcesso = tipoProcesso;
		return this;
	}

	/**
	 * @param perfil que deverá ser responsável pela execução das tarefas desse tipo
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer para(Perfis perfil) {
		this.perfil = perfil;
		return this;
	}

	/**
	 * Configurar para que ao criar uma nova tarefa ela mantenha o status aberta mesmo que o usuario responsável tenha sido
	 * informado.
	 * 
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer naoAtribuirAoCriarNovaTarefa() {
		this.criarTarefaAtribuida = false;
		return this;
	}

	/**
	 * Configura o tipo de tarefa {@link TiposTarefa.TIMEOUT} como responsável por desatribuir o usuário da tarefa ao expirar o
	 * seu tempo de execução.
	 * 
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer timeoutDefault() {
		this.timeout = TiposTarefa.TIMEOUT;
		return this;
	}

	/**
	 * Configura o tipo de tarefa informado como responsável por desatribuir o usuário da tarefa ao expirar o seu tempo de
	 * execução.
	 * 
	 * @param timeout tipo de tarefa para desatribuição de usuário responsável
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer timeout(ITiposTarefa timeout) {
		this.timeout = timeout;
		return this;
	}
	
	/**
	 * Indica se a configuração é somente um agrupador de outros tipos.
	 * 
	 * @return TRUE, se somente agrupador.
	 */
	@Override
	public boolean isSomenteAgrupador() {
		return somenteAgrupado;
	}

	/**
	 * @param somenteAgrupado the somenteAgrupado to set
	 */
	@Override
	public IConfiguracaoProcessServer somenteAgrupador(boolean somenteAgrupado) {
		this.somenteAgrupado = somenteAgrupado;
		return this;
	}
	
	/**
	 * Informa os IDs a serem agrupados na consulta.
	 * 
	 * @param ids
	 * @return
	 */
	@Override
	public IConfiguracaoProcessServer agrupando(ITiposTarefa ...tiposTarefa){
		this.tiposTarefa = tiposTarefa;
		return this;
	}

	/**
	 * Retorna a lista dos tipos a serem agrupados.
	 * 
	 * @return lista dos IDs dos tipos de tarefa.
	 */
	@Override
	public ITiposTarefa[] getAgrupados() {
		return tiposTarefa;
	}

	/**
	 * Retorna o ID do tipo de tarefa.
	 * 
	 * @return the idTipoTarefa ID do tipo de tarefa.
	 */
	public Long getIdTipoTarefa() {
		return idTipoTarefa;
	}
	
	@Override
	public IConfiguracaoProcessServer comProcessadorTarefa(Class<? extends IProcessadorTarefa> processadorTarefa) {
		this.processadorTarefa = processadorTarefa;
		return this;
	}
	
	@Override
	public Class<? extends IProcessadorTarefa> getProcessadorTarefa() {
		return this.processadorTarefa;
	}
	
	@Override
	public IConfiguracaoProcessServer comOFollowUp(ITiposTarefa tipoTarefaFollowUp) {
		if (CollectionUtils.isEmpty(this.followUp)) {
			this.followUp = new ArrayList<>();
		}
		this.followUp.add(tipoTarefaFollowUp);
		return this;
	}
		
}
