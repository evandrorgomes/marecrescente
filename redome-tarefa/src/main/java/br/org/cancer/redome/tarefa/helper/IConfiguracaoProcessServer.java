package br.org.cancer.redome.tarefa.helper;

import java.util.List;

import br.org.cancer.redome.tarefa.helper.domain.Perfis;
import br.org.cancer.redome.tarefa.model.domain.ITipoProcesso;
import br.org.cancer.redome.tarefa.model.domain.ITiposTarefa;
import br.org.cancer.redome.tarefa.model.domain.TiposTarefa;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;

/**
 * @author Pizão
 *
 * Define a interface para os eventos que envolvem requisições ao process server.
 */
public interface IConfiguracaoProcessServer {

	/**
	 * Retorna o tipo de processo associado a tarefa.
	 * 
	 * @return tipo de processo.
	 */
	ITipoProcesso getTipoProcesso();

	/**
	 * Informa se a tarefa já deve ser criada atribuída ou não.
	 * 
	 * @param tipoProcesso retorna o tipo de processo.
	 */
	void setTipoProcesso(ITipoProcesso tipoProcesso);

	/**
	 * Retorna o perfil associado a tarefa.
	 * 
	 * @return enum do perfil (contendo o ID da entidade).
	 */
	Perfis getPerfil();

	/**
	 * Informa o perfil associado a tarefa.
	 * 
	 * @param criarTarefaAtribuida
	 */
	void setPerfil(Perfis perfil);

	/**
	 * Retorna a tarefa de timeout associa a esta tarefa.
	 * 
	 * @return tipo de tarefa que deverá ser utilizado.
	 */
	ITiposTarefa getTimeout();

	/**
	 * Informa o tipo de tarefa de timeout associada a esta tarefa.
	 * 
	 * @param timeout tipo de tarefa de timeout associada.
	 */
	void setTimeout(ITiposTarefa timeout);
		

	/**
	 * Retorna a tarefa de follow up associada a esta tarefa.
	 * 
	 * @return tipos de tarefa de follow up associada.
	 */
	List<ITiposTarefa> getFollowUp();

	/**
	 * Informa se a tarefa já deve ser criada atribuída ou não.
	 * 
	 * @return TRUE se deve ser criada atribuída.
	 */
	boolean isCriarTarefaAtribuida();

	/**
	 * Informa o tipo de processo para ser utilizado nas consultas.
	 * 
	 * @param tipoProcesso tipo de processo que o tipo de tarefa é utilizado
	 * @return configuração do process server.
	 */
	IConfiguracaoProcessServer para(ITipoProcesso tipoProcesso);

	/**
	 * Define perfil que deverão ser criados ou consultadas as tarefas geradas.
	 * 
	 * @param perfil que deverá ser responsável pela execução das tarefas desse tipo
	 * @return configuração definida para este tipo de tarefa.
	 */
	IConfiguracaoProcessServer para(Perfis perfil);

	/**
	 * Configurar para que ao criar uma nova tarefa ela mantenha o status aberta mesmo que o usuario responsável tenha sido
	 * informado.
	 * 
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer naoAtribuirAoCriarNovaTarefa();

	/**
	 * Configura o tipo de tarefa {@link TiposTarefa.TIMEOUT} como responsável por desatribuir o usuário da tarefa ao expirar o
	 * seu tempo de execução.
	 * 
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer timeoutDefault();

	/**
	 * Configura o tipo de tarefa informado como responsável por desatribuir o usuário da tarefa ao expirar o seu tempo de
	 * execução.
	 * 
	 * @param timeout tipo de tarefa para desatribuição de usuário responsável
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer timeout(ITiposTarefa timeout);
	
	IConfiguracaoProcessServer comOFollowUp(ITiposTarefa followUp);

	/**
	 * Indica se a configuração é somente um agrupador de outros tipos.
	 * 
	 * @return TRUE, se somente agrupador.
	 */
	boolean isSomenteAgrupador();

	/**
	 * Informa se a configuração é somente um agrupador de tarefas.
	 * 
	 * @param somenteAgrupador boleano indicando se (TRUE) é ou não (FALSE) somente agrupador.
	 */
	IConfiguracaoProcessServer somenteAgrupador(boolean somenteAgrupador);

	/**
	 * Informa os IDs a serem agrupados na consulta.
	 * 
	 * @param tiposAgrupados tipos de tarefa a serem agrupados.
	 * @return referência a configuração atualizada.
	 */
	IConfiguracaoProcessServer agrupando(ITiposTarefa... tiposAgrupados);

	/**
	 * Retorna a lista dos tipos a serem agrupados.
	 * 
	 * @return lista dos IDs dos tipos de tarefa.
	 */
	ITiposTarefa[] getAgrupados();

	/**
	 * Possibilidade de criar as classes já com o id da tarefa.
	 * 
	 * @param id
	 */
	void setIdTarefa(Long id);
			
	/**
	 * Indica que, na criação desta tarefa, se inicia um novo processo 
	 * envolvendo o paciente.
	 * 
	 * @return referência para configuração atualizada.
	 */
	IConfiguracaoProcessServer iniciarProcesso();
	
	/**
	 * Retorna o atributo que indica se deve ou não criar o processo
	 * a partir da criação da tarefa indicada.
	 * 
	 * @return TRUE, se deve iniciar o processo.
	 */
	boolean isIniciarProcesso();
	
	IConfiguracaoProcessServer comProcessadorTarefa(Class<? extends IProcessadorTarefa> processadorTarefa);
	
	Class<? extends IProcessadorTarefa> getProcessadorTarefa();
	
}
