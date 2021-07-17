package br.org.cancer.modred.service.impl.custom;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.service.impl.config.Filter;
import br.org.cancer.modred.service.impl.config.IsNotNull;
import br.org.cancer.modred.service.impl.config.IsNull;
import br.org.cancer.modred.service.impl.config.OrderBy;
import br.org.cancer.modred.service.impl.config.Projection;
import br.org.cancer.modred.service.impl.config.Transformation;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe para implementação dos serviços genéricos para a entidade T informada.
 * 
 * @param <T> Representa a entidade relacionada ao repositório.
 * @param <I> Representa o tipo do ID da entidade informada.
 * 
 * @author Pizão.
 *
 */
@Transactional
public abstract class AbstractService<T, I extends Serializable> implements IService<T, I> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
	
    @Autowired
	protected EntityManager entityManager;

	protected Validator validator;
	
	protected MessageSource messageSource;
	
	@Autowired
	protected TransactionTemplate transactionTemplate;
    
	/**
	 * Classe repositório associada a entidade T,
	 * que será utilizada no contexto desse service.
	 * 
	 * @return instância da classe repositório.
	 */
	public abstract IRepository<T, I> getRepository();
    
    /**
     * Obtém a classe do parametro informado na instância dessa class.
     * 
     * @return Tipo da classe.
     */
    @SuppressWarnings("unchecked")
	protected Class<T> obtainClassType(){
    	Object argument = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    	if(argument instanceof Class){
    		return (Class<T>) argument;
    	}
    	else if(argument instanceof TypeVariable){
    		return (Class<T>) ((TypeVariable<?>) argument).getBounds()[0];
    	}
    	throw new IllegalStateException("Não foi possível encontrar a entidade que esta classe de serviço manipula: " + getClass().getCanonicalName());
    }
    
	protected String obtainClassName(){
    	 return obtainClassType().getName();
    }

	@Override
    public List<T> find(List<Projection> projections, List<Filter<?>> filters){
    	return find(projections, filters, null);
    }
	
	public List<T> find(Projection projection, Filter<?> filter){
		return find(Arrays.asList(projection), Arrays.asList(filter));
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<T> find(List<Projection> projections, List<Filter<?>> filters, List<OrderBy> orders){
    	StringBuilder select = createSqlQuery(projections, filters, orders);
    	Query<?> query = applyFilters(select, filters);
    	
    	List<T> result;
    	
    	if(CollectionUtils.isNotEmpty(projections)){
	    	result = (List<T>) Transformation.get(query, obtainClassType());
    	}
    	else {
    		result = (List<T>) query.getResultList();
    	}
    	return result;
    }

    private StringBuilder createSqlQuery(List<Projection> projections, List<Filter<?>> filters, List<OrderBy> orders) {
		StringBuilder select = createSelect(projections);
    	select.append(" from ").append(obtainClassName()).append(" t ");
    	select.append(createWhere(filters));
    	select.append(createOrderBy(orders));
		return select;
	}

	private Query<?> applyFilters(StringBuilder sql, List<Filter<?>> filters) {
		Query<?> query = (Query<?>) entityManager.createQuery(sql.toString());
    	
    	if(CollectionUtils.isNotEmpty(filters)){
    		filters.stream()
	    		.filter(filter -> !(filter instanceof IsNull || filter instanceof IsNotNull))
	    		.forEach(filter -> {
	    			if (!(filter.getValue() instanceof Collection) && (!filter.getValue().getClass().isArray())) {
	    				query.setParameter(filter.getParameterName(), filter.getValue());
	    			}
	    			else if (filter.getValue() instanceof Collection) {    			
	    				query.setParameterList(filter.getParameterName(), (Collection<?>)filter.getValue());
	    			}
	    			else if (filter.getValue().getClass().isArray()) {    			
	    				query.setParameterList(filter.getParameterName(), (Object[]) filter.getValue());
	    			}
	    		});
    	}
		return query;
	}

	private StringBuilder createWhere(List<Filter<?>> filters) {
		StringBuilder where = new StringBuilder("");
		
		if(CollectionUtils.isNotEmpty(filters)){
			where.append("where ");
			
    		String ands = filters.stream().map(filter -> {
    			StringBuilder and = new StringBuilder("");
    			
    			if (filter.getValue() != null && (filter.getValue() instanceof Collection<?> || filter.getValue().getClass().isArray())) {
    				and.append("t.").append(filter.getAttributeName())
    						.append(" in ( :")
    						.append(filter.getParameterName())
    						.append(" ) ") ;
    			}    			
    			else if (filter instanceof IsNull || filter instanceof IsNotNull) {
    				and.append("t.").append(filter.getAttributeName())
    						.append(" ").append(filter.getComparator());
    			}
    			else {
    				and.append("t.").append(filter.getAttributeName())
    						.append(" ").append(filter.getComparator())
    						.append(" :").append(filter.getParameterName());
    			}
    			
    			return and.toString();
    		}).collect(Collectors.joining(" and "));
    		
    		where.append(ands);
		}
		
		return where;
	}
	
	private StringBuilder createOrderBy(List<OrderBy> orders) {
		StringBuilder orderBy = new StringBuilder("");
		
		if(CollectionUtils.isNotEmpty(orders)){
			orderBy.append(" order by ");
			
			OrderBy lastOrder = orders.get(orders.size() - 1);
			
    		orders.forEach(order -> {
    			orderBy.append("t.").append(order.getAttributeName())
    				.append(" ").append(order.isAsc() ? "asc" : "desc");
    			
    			if(!order.equals(lastOrder)){
    				orderBy.append(", ");
    			}
    		});
    		
		}
		return orderBy;
	}

	private StringBuilder createSelect(List<Projection> projections) {
		StringBuilder select = new StringBuilder("select ");
		boolean hasProjection = CollectionUtils.isNotEmpty(projections);
    	
		if(hasProjection){
    		Projection lastProjection = projections.get(projections.size() - 1);
	    	projections.forEach(projection -> {
	    		select.append("t.").append(projection.getAttributeName());
	    		if(!projection.equals(lastProjection)){
	    			select.append(", ");
	    		}
	    	});
    	}
		else {
			select.append("t");
		}
		return select;
	}
	
	private StringBuilder createUpdate(List<UpdateSet<?>> updateSet) {
		StringBuilder update = new StringBuilder("update ").append(obtainClassName()).append(" t set ");
		
		UpdateSet<?> lastSet = updateSet.get(updateSet.size() - 1);
		updateSet.forEach(itemSet -> {
    		update.append("t.").append(itemSet.getAttributeName()).append(" = :").append(itemSet.getParameterName());
    		if(!itemSet.equals(lastSet)){
    			update.append(", ");
    		}
    	});
		return update;
	}

    @Override
    public List<T> findAll(){
    	return getRepository().findAll();
    }
    
    @Override
    public Page<T> findAll(Pageable pageable){
    	return getRepository().findAll(pageable);
    }
    
	@Override
	public T findById(I id) {
		return getRepository().findById(id).orElse(null);
	}
	
	/**
	 * Método para validar a entity.
	 * 
	 * @param entity Entidade a ser validada
	 * @return List<CampoMensagem> - lista de CampoMensagem 
	 */
	protected List<CampoMensagem> validateEntity(T entity) {
		List<CampoMensagem> validateResult = 
				new ConstraintViolationTransformer(validator.validate(entity)).transform();
		return validateResult;
	}
	
	@Override
	public T save(T entity) {
		List<CampoMensagem> validationResult = validateEntity(entity);
		if (!validationResult.isEmpty()) {
			LOGGER.warn(validationResult.stream().map(msg -> msg.getMensagem()).collect(Collectors.joining("; ")));
			throw new ValidationException("erro.validacao", validationResult);
		}
		T saved = getRepository().save(entity);
		
		notifyObservers(entity);		
		return saved;
	}

	/**
	 * Cria as instâncias dos observers, caso existam, e notifica a 
	 * mudança na entidade.
	 * 
	 * @param entity entidade para referência da criação dos 
	 * observers associados.
	 */
	private void notifyObservers(T entity) {
		if (entity instanceof EntityObservable) {
			EntityObservable entityObservable = (EntityObservable) entity;
			
			entityObservable.getObservers().forEach(observerClass -> {
				entityObservable.addObserver(ApplicationContextProvider.obterBean(observerClass));
			});
			
			entityObservable.notifyChange();
		}
	}

	@Override
	public List<T> find(Filter<?>... filters) {
		return find(null, Arrays.asList(filters));
	}
	
	@Override
	public List<T> find(OrderBy... orders) {
		return find(null, null, Arrays.asList(orders));
	}
	
	@Override
	public List<T> find(Projection... projections) {
		return find(Arrays.asList(projections), null);
	}

	@Override
	public T findOne(Filter<?>... filters) {
    	return findOne(null, Arrays.asList(filters));
	}
	
	@Override
	public T findOne(List<Projection> projections, List<Filter<?>> filters) {
    	List<T> resultList = find(projections, filters);
    	if(CollectionUtils.isNotEmpty(resultList) && resultList.size() > 1){
    		throw new NonUniqueResultException(resultList.size());
    	}
    	if(CollectionUtils.isNotEmpty(resultList)){
    		return resultList.get(0);
    	}
    	return null;
	}
	
	@Override
    public int update(List<UpdateSet<?>> updateSet, List<Filter<?>> filters){
		if(hasJoin(filters)){
			/**
			 * Contorno técnico quando ocorre um update utilizando subquery no where.
			 * Exemplo: update cliente set status = ATIVO where cliente.endereco.id = :idEndereco.
			 * 
			 * TODO: Se possível, estudar forma melhor de resolver isto.
			 */
			List<T> updObjects = find(null, filters);
			if(CollectionUtils.isNotEmpty(updObjects)){
				updObjects = 
						updObjects.stream().map(updObject -> {
							updateSet.forEach(itemSet -> {
								try {
									BeanUtils.setProperty(updObject, itemSet.getAttributeName(), itemSet.getValue());
								}
								catch (ReflectiveOperationException e) {
									LOGGER.error("Erro ao setar o valor " + itemSet.getValue() + 
											" para o atributo " + itemSet.getAttributeName() + " na entidade " + updObject.toString());
									throw new BusinessException("erro.interno");
								}
							});
							return updObject;
						}).collect(Collectors.toList());
				saveAll(updObjects);
				return updObjects.size();
			}
			return 0;
		}
		else {
			return transactionTemplate.execute(transactionStatus -> {
				Query<?> query = createUpdateQuery(updateSet, filters);
				int registrosAtualizados = query.executeUpdate();
				transactionStatus.flush();
				return registrosAtualizados;
			}).intValue();
		}
    }
	
	@Override
    public int update(UpdateSet<?> updateSet, Filter<?> filter){
		return update(Arrays.asList(updateSet), Arrays.asList(filter));
    }
	
	private Query<?> createUpdateQuery(List<UpdateSet<?>> updateSet, List<Filter<?>> filters) {
		StringBuilder update = createUpdate(updateSet);
		update.append(" ").append(createWhere(filters));
    	
    	Query<?> query = applyFiltersAndSetUpdates(update, updateSet, filters);
		return query;
	}
	
	private Query<?> applyFiltersAndSetUpdates(StringBuilder sql, List<UpdateSet<?>> updateSet, List<Filter<?>> filters) {
		Query<?> query = applyFilters(sql, filters);
    	
    	if(CollectionUtils.isNotEmpty(updateSet)){
    		updateSet.forEach(itemSet -> {
    			query.setParameter(itemSet.getParameterName(), itemSet.getValue());
    		});
    	}
		return query;
	}
    
	private boolean hasJoin(List<Filter<?>> filters){
		return filters.stream().anyMatch(filter -> {
			return filter.getAttributeName().split("[.]").length > 1;
		});
	}
	
	@Override
	public List<T> saveAll(List<T> entities) {
		List<CampoMensagem> validationResult = new ArrayList<CampoMensagem>();
		
		entities.forEach(entity -> {
			validationResult.addAll(validateEntity(entity));
		});
		
		if (!validationResult.isEmpty()) {
			throw new ValidationException("erro.validacao", validationResult);
		}
		
		List<T> saved = getRepository().saveAll(entities);
		verifyObservableAfterChange(saved);
		return saved;
	}
	
	@Override
	public void flush() {
		getRepository().flush();
	}

	/**
	 * Verifica se deve e quais as estratégias devem ser disparadas
	 * após identificadas possíveis mudanças no estado dos objetos
	 * que foram alterados.
	 * 
	 * @param entities entidades que, possivelmente, passaram po
	 */
	private void verifyObservableAfterChange(List<T> entities){
		if(CollectionUtils.isNotEmpty(entities)){
			entities.forEach(entity -> {
				if (entity instanceof Observable) {
					((Observable) entity).notifyObservers();
				}
			});
		}
	}
	
	@Override
	public List<T> find(List<Filter<?>> filters, OrderBy... orders) {
		return find(null, filters, Arrays.asList(orders));
	}

	/**
	 * @param validator the validator to set
	 */
	@Autowired
	@Lazy
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	@Lazy
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
