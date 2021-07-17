package br.org.cancer.modred.model.annotations.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;

/**
 * @author Pizão
 * 
 * Anotação que marca o método, dentro dos services, que irá gerar 
 * log de evolução para a entidade associada a este service.
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateLog {
	
	/**
	 * Indica o tipo do evento a ser gerado.
	 * 
	 * @return tipo de log.
	 */
	TipoLogEvolucao value() default TipoLogEvolucao.INDEFINIDO;
	
	/**
	 * Indica os perfis que não poderão ver este item de historico.
	 * @return listagem de perfis que devem ser excluidos da pesquisa.
	 */
	Perfis[] perfisExcluidos() default Perfis.TODOS;
	
}
