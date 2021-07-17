package br.org.cancer.redome.notificacao.service.custom;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.redome.notificacao.service.impl.config.Filter;
import br.org.cancer.redome.notificacao.service.impl.config.OrderBy;
import br.org.cancer.redome.notificacao.service.impl.config.Projection;
import br.org.cancer.redome.notificacao.service.impl.config.UpdateSet;

/**
 * Interface base para a classe de serviço genérica. Deve ser utilizada como referência, inclusive em casos de implementação
 * específica da classe service de uma entidade.
 * 
 * @author Pizão.
 *
 * @param <T> entidade associada ao service.
 * @param <TypeId> tipo do ID desta entidade.
 */
public interface IService <T, TypeId extends Serializable> {

	/**
	 * Busca todas as referências para a entidade no banco.
	 * 
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * Busca todas as referências para a entidade no banco com paginação.
	 * 
	 * @return
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * Obtém a entidade a que o ID informado está associada.
	 * 
	 * @param id ID a ser utilizado na pesquisa.
	 * @return a entidade relacionada ao ID.
	 */
	T findById(TypeId id);

	/**
	 * Valida o preenchimento da entidade, segundo o que definido no BeanValidation e salvo o objeto no banco.
	 * 
	 * @param entity entidade a ser salva.
	 * @return a entidade atualizada.
	 */
	T save(T entity);
	
	/**
	 * Valida o preenchimento das entidades, segundo o que definido no BeanValidation, e salva a lista de objetos no banco.
	 * 
	 * @param entities lista de entidades a serem salvas.
	 * @return lista de entidades atualizadas.
	 */
	List<T> saveAll(List<T> entities);

	/**
	 * Busca entidades no banco selecionado apenas os atributos informados, utilizando os filtros, caso também sejam informados.
	 * Se ambos não forem informados, deverá trazer todos os dados com todos os atributos (idêntico ao findAll).
	 * 
	 * @param projections lista de atributos a serem retornados com a entidade.
	 * @param filters lista de filtros a serem aplicados na busca.
	 * @return lista de entidades.
	 */
	List<T> find(List<Projection> projections, List<Filter<?>> filters);
	
	/**
	 * Busca entidades no banco selecionado apenas os atributos informados, utilizando os filtros e ordenação,
	 * caso também sejam informados.
	 * Por convenção, os que não forem informados, deverá ser ignorado chegando a trazer todos os registros,
	 * caso todos sejam passados nulos (idêntico ao findAll).
	 * 
	 * @param projections lista de atributos a serem retornados com a entidade.
	 * @param filters lista de filtros a serem aplicados na busca.
	 * @param orders lista de atributos para ordenação.
	 * @return lista de entidades.
	 */	
	List<T> find(List<Projection> projections, List<Filter<?>> filters, List<OrderBy> orders);
	
	/**
	 * Busca entidades no banco selecionado apenas o único atributo informados, utilizando o único filtro,
	 * caso também sejam informados.
	 * Por convenção, os que não forem informados, deverá ser ignorado chegando a trazer todos os registros,
	 * caso todos sejam passados nulos (idêntico ao findAll).
	 * 
	 * @param projection atributo a ser retornado preenchido na entidade T.
	 * @param filter filtro aplicado a busca.
	 * @return lista de entidades T.
	 */
	List<T> find(Projection projection, Filter<?> filter);

	/**
	 * Busca entidades aplicando os filtros informados. Neste método, todos os atributos são listados no resultado.
	 * 
	 * @param filters lista de filtros aplicados na busca.
	 * @return lista de entidades.
	 */
	List<T> find(Filter<?>... filters);
	

	/**
	 * Busca todas as entidades somente com os atributos informados. Neste método, os filtros não são aplicados.
	 * 
	 * @param projections
	 * @return
	 */
	List<T> find(Projection... projections);

	/**
	 * Realiza uma consulta com resultado único.
	 * 
	 * @param filters
	 * @return resultado único da consulta.
	 */
	T findOne(Filter<?>... filters);
	
	/**
	 * Realiza uma consulta com resultado único.
	 * 
	 * @param filters
	 * @return resultado único da consulta.
	 */
	T findOne(List<Projection> projections, List<Filter<?>> filters);

	/**
	 * Realiza a atualização dos campos informados no updateSet para os registros que atendem os filtros informados.
	 * 
	 * @param updateSet atributos a serem atualizados.
	 * @param filters filtros informados.
	 * @return quantidade de registros modificados.
	 */
	int update(List<UpdateSet<?>> updateSet, List<Filter<?>> filters);

	/**
	 * Realiza a atualização dos campos informados no updateSet para os registros que atendem os filtros informados.
	 * 
	 * @param updateSet
	 * @param filter
	 * @return resultado da consulta
	 */
	int update(UpdateSet<?> updateSet, Filter<?> filter);

	
	/**
	 * Busca todas as entidades ordernando pelos atributos informados. Neste método, os filtros não são aplicados.
	 * 
	 * @param orders
	 * @return 
	 */
	List<T> find(OrderBy...orders); 

	/**
	 * Força a finalização da transação corrente (flush).
	 */
	void flush();
	
	/**
	 * Busca entidades aplicando os filtros e a ordernação informados. Neste método, todos os atributos são listados no resultado.
	 * 
	 * @param filters lista de filtros aplicados na busca.
	 * @param orders
	 * @return lista de entidades.
	 */
	List<T> find(List<Filter<?>> filters, OrderBy...orders);
	
	
}
