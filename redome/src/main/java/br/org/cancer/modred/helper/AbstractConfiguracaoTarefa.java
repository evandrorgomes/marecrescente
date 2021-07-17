/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.ArrayList;
import java.util.List;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * @author Pizão
 * 
 * Interface que define a estrutura da configuração necessária para a
 * realização de requisições genéricas a lista de tarefas do process
 * server.
 */
public abstract class AbstractConfiguracaoTarefa implements IConfiguracaoProcessServer{

	private TipoProcesso tipoProcesso;
	private Perfis perfil;
	private Class<? extends ListarPaginacao> jsonView;
	private Class<?> entidade;
	private TiposTarefa timeout;
	private List<TiposTarefa> followUp;
	private boolean criarTarefaAtribuida;
	private Long idTipoTarefa;
	private Class<?> parceiro;
	private boolean somenteAgrupado;
	private TiposTarefa[] tiposTarefa;
	private Usuario usuarioParaAgendamento;
	
	
	/**
	 * Coonstrutor para padronizar as extensões de configuração de tarefa, forçando
	 * que seja passado um ID do tipo tarefa para cada instância.
	 * 
	 * @param idTipoTarefa ID do tipo de tarefa associado.
	 */
	protected AbstractConfiguracaoTarefa(Long idTipoTarefa) {
		super();
		this.setCriarTarefaAtribuida(Boolean.TRUE);
		this.idTipoTarefa = idTipoTarefa;
	}
	
	@Override
	public TipoProcesso getTipoProcesso() {
		return tipoProcesso;
	}
	@Override
	public void setTipoProcesso(TipoProcesso tipoProcesso) {
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
	public Class<? extends ListarPaginacao> getJsonView() {
		return jsonView;
	}
	
	@Override
	public void setJsonView(Class<? extends ListarPaginacao> jsonView) {
		this.jsonView = jsonView;
	}


	@Override
	public Class<?> getEntidade() {
		return entidade;
	}
	@Override
	public void setEntidade(Class<?> entidade) {
		this.entidade = entidade;
	}

	@Override
	public TiposTarefa getTimeout() {
		return timeout;
	}
	@Override
	public void setTimeout(TiposTarefa timeout) {
		this.timeout = timeout;
	}

	@Override
	public List<TiposTarefa> getFollowUp() {
		return followUp;
	}
	public void addFollowup(List<TiposTarefa> followUp) {
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
	public IConfiguracaoProcessServer para(TipoProcesso tipoProcesso) {
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
	 * @param entidade que está relacionada as tarefas desse tipo
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer relacionadoCom(Class<?> entidade) {
		this.entidade = entidade;
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
	public IConfiguracaoProcessServer timeout(TiposTarefa timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Configura o tipo de tarefa informado como tarefa de followup desse tipo de tarefa.
	 * 
	 * @param followUp tipo de tarefa para followup a respeito do tipo de tarefa
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer comOFollowUp(TiposTarefa followUp) {
		if(this.followUp == null){
			this.followUp = new ArrayList<TiposTarefa>();
		}
		this.followUp.add(followUp);			
		return this;
	}
	
	/**
	 * Configura o jsonView que deverá ser utilizado para serialização desse tipo de tarefa.
	 * 
	 * @param jsonView para serialização
	 * @return IConfiguracaoProcessServer configurada
	 */
	@Override
	public IConfiguracaoProcessServer jsonView(Class<? extends ListarPaginacao> jsonView) {
		this.jsonView = jsonView;
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
	public IConfiguracaoProcessServer agrupando(TiposTarefa ...tiposTarefa){
		this.tiposTarefa = tiposTarefa;
		return this;
	}

	/**
	 * Retorna a lista dos tipos a serem agrupados.
	 * 
	 * @return lista dos IDs dos tipos de tarefa.
	 */
	@Override
	public TiposTarefa[] getAgrupados() {
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Class<?> getParceiro() {
		return parceiro;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IConfiguracaoProcessServer comParceiro(Class<?> parceiro) {
		this.parceiro = parceiro;
		return this;
	}
	
}
