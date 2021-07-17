package br.org.cancer.modred.service.impl.invocation;

import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;

/**
 * 
 * @author Pizão
 * 
 * Interface que define quais são os métodos necessários para a criação do log
 * de evolução. É utilizada na classe AbstractLoggingService para definir o comportamento
 * necessário sempre que o service extende-la, indicando que algum método irá gerar log.
 *
 * @param <T> define qual o tipo da entidade do service que implementa esta interface.
 */
public interface ILogging<T> {

	/**
	 * Obtém o paciente associado, a partir do ID do objeto informado.
	 * 
	 * @param entity entidade a ser usada como referência 
	 * (ID da entidade que utiliza este service para acesso as funcionalidades).
	 * @return paciente associado a entidade ID informada.
	 */
	Paciente obterPaciente(T entity);

	/**
	 * Obtém o objeto genérico que será gravado no log.
	 * Este objeto pode ser qualquer informação, mas, até agora,
	 * os casos identificados são todos de IDs que precisarão ser
	 * exibidos na mensagem de log.
	 * 
	 * @param entity entidade a ser usada como referência.
	 * @return identificador do objeto que será gravado no log.
	 */
	String[] obterParametros(T entity);
	
	/**
	 * Cria uma instância de log devidamente preenchida para ser incluída.
	 * Este método pode ser no futuro reimplementado, caso o método anotado
	 * tenha características múltiplas, como o método de cria pedido de contato
	 * de fase 2 e 3 e geram logs diferentes dependendo da fase.
	 * 
	 * @param entity
	 *            entidade deste service.
	 * @param tipoLog
	 *            mensagem a ser utilizada para gerar o log (histórico) da
	 *            evolução de busca.
	 *@param perfis que não poderão visualizar o registro de log.
	 * 
	 * @return log de evolução preenchido.
	 */
	LogEvolucao criarLog(T entity, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos);
	
}
