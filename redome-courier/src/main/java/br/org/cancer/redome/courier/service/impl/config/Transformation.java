package br.org.cancer.redome.courier.service.impl.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.org.cancer.redome.courier.exception.BusinessException;

/**
 * Classe que transforma o result sem formatação em uma lista de objetos tipados.
 * 
 * @author Pizão
 *
 */
public class Transformation {

	private static final Logger LOG = LoggerFactory.getLogger(Transformation.class); 
	
	@Autowired
	protected EntityManager entityManager;

	/**
	 * Transforma o resultado da query informada em uma lista de objetos do tipo tClass. Tipifica o resultado da query.
	 * 
	 * @param query query utilizada na transformação.
	 * @param typeClass classe utilizada na tipificação.
	 * @return lista de objetos tipados.
	 */
	public static <T> List<T> get(Query query, Class<T> typeClass) {
		List<T> resultType = getResult(query, typeClass);
		return resultType;
	}

	/**
	 * Formata o resultado para uma lista de objetos tipados.
	 * 
	 * @param query query a ser executada e interpretada.
	 * @param typeClass tipo que deve ser utilizado para lista de retorno.
	 * @return lista tipada.
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> getResult(Query query, Class<T> typeClass) {
		List<Object[]> result = query.getResultList();
		List<T> resultType = new ArrayList<T>();
		List<String> attributeNames = getNamesProjection(query);

		if (CollectionUtils.isNotEmpty(result)) {
			result = normalizeResult(result);
			
			for (Object[] attributeValues : result) {
				if (attributeValues != null) {
					T entity = createInstance(typeClass, attributeNames, attributeValues);
					resultType.add(entity);
				}
			}
		}
		return resultType;
	}

	/**
	 * Cria uma instância da classe informada com os valores e seus atributos, 
	 * com correlação sendo definida pela posição de cada um nas suas listas.
	 * 
	 * @param typeClass tipo da classe a ser instanciada.
	 * @param attributeNames nomes dos atributos contidos na classe.
	 * @param attributeValues valores dos atributos.
	 * 
	 * @return uma nova instância da classe preenchida.
	 */
	private static <T> T createInstance(Class<T> typeClass, List<String> attributeNames, Object[] attributeValues){
		try {
			T entity = typeClass.newInstance();
			
			// Exceção, caso o script não seja identificado na query.
			if(attributeNames.size() != attributeValues.length){
				throw new BusinessException("Quantidade de atributos (" + 
						attributeNames.size() +") diferente dos valores (" + attributeValues.length);
			}
			
			int index = 0;
			if(attributeValues != null){
				for (Object elem : attributeValues) {
					setValue(entity, attributeNames.get(index), elem);
					index++;
				}
			}
			return entity;
		}
		catch (IllegalAccessException | InstantiationException e) {
			throw new BusinessException("Erro ao instanciar um objeto da classe " + typeClass
					+ ", provavelmente, foi a ausência do construtor padrão.");
		}
	}

	/**
	 * Normaliza o resultado da query, para que tenha o mesmo formato independente do resultado.
	 * Isso foi necessário pois, uma query que trás apenas um atributo retorno um array de objetos simples 
	 * enquanto uma query com vários atributos retorna um array de objetos.
	 * Com este método, todo resultado retornará sempre um array de objetos, independente da query.
	 * 
	 * @param result resultado a ser manipulado.
	 * @return resultado normalizado o não, de acordo com o tipo de resultado obtido.
	 */
	private static List<Object[]> normalizeResult(List<Object[]> result) {
		final boolean isSingleAttributes = !(result.get(0) instanceof Object[]);
		
		if (isSingleAttributes) {
			List<Object[]> resultNormalize = new ArrayList<Object[]>();
			for (Object object : result) {
				resultNormalize.add(new Object[]{ object });
			}
			return resultNormalize;
		}
		return result;
	}

	/**
	 * Seta o valor no atributo de um determinado objeto.
	 * Caso o atributo não exista, ele será logado e seguirá normalmente, cabendo
	 * ao desenvolvedor identificar que o atributo não foi preenchido e resolver.
	 * A decisão tomada foi esta pois, em alguns casos específicos, como de cláusulas 
	 * mais complexas, principalmente contendo vírgulas, o atributo pode não ser identificado
	 * corretamente. 
	 * (ex.: round(sysdate, sysdate) as...)
	 * 
	 * @param entity entidade (classe) que receberá o valor.
	 * @param property atributo da entidade.
	 * @param value valor a ser setado no atributo da entidade.
	 */
	private static void setValue(Object entity, String property, Object value) {
		try {
			if (value instanceof Timestamp) {
				value = ( (Timestamp) value ).toLocalDateTime();

				Field field = entity.getClass().getDeclaredField(property);
				Class<?> returnType = field.getType();

				if (LocalDate.class.equals(returnType)) {
					value = ( (LocalDateTime) value ).toLocalDate();
				}
			}
			BeanUtils.setProperty(entity, property, value);
		}
		catch (IllegalAccessException | InvocationTargetException | SecurityException | NoSuchFieldException e) {
			throw new BusinessException("Erro ao instanciar um objeto da classe " + entity.getClass()
					+ ", provavelmente, por conta do modificador de acesso em algum propriedade, propriedade com nome incorreto, etc.");
		}
	}

	/**
	 * Extrai os atributos da projeção na ordem da query. Atributos que, após a extração
	 * contenha caracteres especiais deve ser ignorado.
	 * 
	 * @param query query terá seus atributos extraídos.
	 * @return lista de atributos da projeção.
	 */
	private static List<String> getNamesProjection(Query query) {
		String queryString = getQueryString(query);
		String completeSelect = queryString.substring(getSelectIndex(queryString), getFromIndex(queryString));
		completeSelect = completeSelect.replaceAll("'(.*?)'", "");
		
		List<String> selectAttributes = Arrays.asList(completeSelect.split(",")).stream()
				.map(selectItem -> {
					return readProjectionItem(selectItem);
				})
				.filter(selectItem -> {
					boolean accept = selectItem.matches("[a-zA-Z0-9]+$");
					
					if(!accept){
						LOG.error("Não foi possível identificar o atributo (ou alias) para o projeção de nome " + selectItem + 
								" verifique se a linha deve mesmo ser desconsiderada e, se necessário, inclua um AS antes do alias.");
					}
					return accept;
				})
				.collect(Collectors.toList());
		
		return selectAttributes;
	}

	private static int getSelectIndex(String queryString) {
		String queryLCase = new String(queryString).toLowerCase();
		return queryLCase.indexOf("select") + "select".length();
	}

	private static int getFromIndex(String queryString) {
		String queryLCase = new String(queryString).toLowerCase();
		return queryLCase.indexOf("from");
	}

	/**
	 * Realiza a leitura do texto para extração do nome do parâmetro fornecido na projeção. Ex.: item = 'João' as nome | retorno:
	 * nome item = 18 as idade | retorno: idade
	 * 
	 * TODO: Método não está preparado para ler atributos dentro de atributos. Ex.: t.pedido.id (Errado!) t.pedido (Correto!)
	 * TODO: Utilizar regex para solucionar problemas com os possíveis formatos do select e projeções.
	 * 
	 * @param item contém a informação sobre a coluna.
	 * @return o nome da coluna na projeção.
	 */
	private static String readProjectionItem(String item) {
		item = item.trim();
		
		if (item.contains(" as ") || item.split(" ").length >= 2) {
			final String[] itemParts = item.split(" ");
			final String lastPart = itemParts[itemParts.length - 1];
			item = lastPart;
		}
		else
			if (item.contains(".")) {
				item = item.split("[.]")[1];
			}
		return item.trim();
	}

	/**
	 * Recupera a string, de acordo com o formato da query utilizado.
	 * 
	 * @param query query a ser utilizada para busca da query em formato string.
	 * @return SQL em formato texto.
	 */
	private static String getQueryString(Query query) {
		return ( (org.hibernate.query.Query<?>) query ).getQueryString();
	}

}
