package br.org.cancer.modred.helper;

import java.util.Comparator;
import java.util.List;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AtributoOrdenacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;

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
	TipoProcesso getTipoProcesso();

	/**
	 * Informa se a tarefa já deve ser criada atribuída ou não.
	 * 
	 * @param tipoProcesso retorna o tipo de processo.
	 */
	void setTipoProcesso(TipoProcesso tipoProcesso);

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
	 * Retorna o JSON utilizado no resultado da consulta.
	 * 
	 * @return
	 */
	Class<? extends ListarPaginacao> getJsonView();

	/**
	 * Informa JSON a ser utilizado na busca.
	 * 
	 * @param jsonView atributos a ser serializados.
	 */
	void setJsonView(Class<? extends ListarPaginacao> jsonView);

	/**
	 * Entidade relacionada a tarefa.
	 * 
	 * @return classe da entidade relacionada.
	 */
	Class<?> getEntidade();

	/**
	 * Informa a entidade associada a tarefa.
	 * 
	 * @param entidade classe da entidade associada.
	 */
	void setEntidade(Class<?> entidade);

	/**
	 * Retorna a tarefa de timeout associa a esta tarefa.
	 * 
	 * @return tipo de tarefa que deverá ser utilizado.
	 */
	TiposTarefa getTimeout();

	/**
	 * Informa o tipo de tarefa de timeout associada a esta tarefa.
	 * 
	 * @param timeout tipo de tarefa de timeout associada.
	 */
	void setTimeout(TiposTarefa timeout);

	/**
	 * Retorna a tarefa de follow up associada a esta tarefa.
	 * 
	 * @return tipos de tarefa de follow up associada.
	 */
	List<TiposTarefa> getFollowUp();

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
	IConfiguracaoProcessServer para(TipoProcesso tipoProcesso);

	/**
	 * Define perfil que deverão ser criados ou consultadas as tarefas geradas.
	 * 
	 * @param perfil que deverá ser responsável pela execução das tarefas desse tipo
	 * @return configuração definida para este tipo de tarefa.
	 */
	IConfiguracaoProcessServer para(Perfis perfil);

	/**
	 * Relaciona uma entidade para ser utilizada na hora de buscar os objetos relacionados.
	 * 
	 * @param entidade que está relacionada as tarefas desse tipo
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer relacionadoCom(Class<?> entidade);

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
	IConfiguracaoProcessServer timeout(TiposTarefa timeout);

	/**
	 * Configura o tipo de tarefa informado como tarefa de followup desse tipo de tarefa.
	 * 
	 * @param followUp tipo de tarefa para folloup a respeito do tipo de tarefa
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer comOFollowUp(TiposTarefa followUp);

	/**
	 * Configura o jsonView que deverá ser utilizado para serialização desse tipo de tarefa.
	 * 
	 * @param jsonView para serialização
	 * @return IConfiguracaoProcessServer configurada
	 */
	IConfiguracaoProcessServer jsonView(Class<? extends ListarPaginacao> jsonView);
	

	/**
	 * Classe responsável pela busca de tarefas.
	 * 
	 * @return interface
	 */
	IListarTarefa listarTarefa();

	/**
	 * Classe responsável para obter a tarefa.
	 * 
	 * @return interface
	 */
	IObterTarefa obterTarefa();

	/**
	 * Classe responsável por fechar tarefa.
	 * 
	 * @return interface
	 */
	IFecharTarefa fecharTarefa();

	/**
	 * Classe responsável por atribuir tarefas.
	 * 
	 * @return interface
	 */
	IAtribuirTarefa atribuirTarefa();

	/**
	 * Classe responsável por criar tarefas.
	 * 
	 * @return interface
	 */
	ICriarTarefa criarTarefa();

	/**
	 * Classe responsável por atualizar tarefas.
	 * 
	 * @return interface
	 */
	IAtualizarTarefa atualizarTarefa();

	/**
	 * Classe responsável por cancelar tarefa.
	 * 
	 * @return interface
	 */
	ICancelarTarefa cancelarTarefa();
	
	/**
	 * Classe responsável por remover a atribuição da tarefa.
	 * 
	 * @return interface
	 */
	IRemoverAtribuirTarefa removerAtribuicaoTarefa();

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
	IConfiguracaoProcessServer agrupando(TiposTarefa... tiposAgrupados);

	/**
	 * Retorna a lista dos tipos a serem agrupados.
	 * 
	 * @return lista dos IDs dos tipos de tarefa.
	 */
	TiposTarefa[] getAgrupados();

	/**
	 * Parceiro responsável da tarefa.
	 * 
	 * @return classe do parceiro relacionado.
	 */
	Class<?> getParceiro();

	/**
	 * Informa o parceiro responsável da tarefa.
	 * 
	 * @param parceiro
	 * @return referência a configuração atualizada.
	 */
	IConfiguracaoProcessServer comParceiro(Class<?> parceiro);

	/**
	 * Possibilidade de criar as classes já com o id da tarefa.
	 * 
	 * @param id
	 */
	void setIdTarefa(Long id);
	
	/**
	 * Indica uma ordenação a ser executada no back-end, anterior a paginação informada.
	 * 
	 * @param classeComparacao classe que possui a implementação da comparação.
	 * @return referência a configuração atualizada.
	 */
	IConfiguracaoProcessServer comOrdenacao(Class<? extends Comparator<TarefaDTO>> classeComparacao);
	
	/**
	 * Indica atributos de ordenação executados no back-end, anterior a paginação.
	 * É uma opção ao @see {{@link #comOrdenacao(Comparator)}
	 * 
	 * @param atributos atributos que devem ser considerados na ordenação, na ordem passada.
	 * @return referência para configuração atualizada.
	 */
	IConfiguracaoProcessServer comOrdenacao(AtributoOrdenacao... atributos);
	
	/**
	 * Retorna a ordenação definida para a lista de tarefas.
	 * 
	 * @return classe com a lógica de comparação informada no tipo de tarefa.
	 */
	Comparator<TarefaDTO> getOrdenacao();
	
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
	
}
