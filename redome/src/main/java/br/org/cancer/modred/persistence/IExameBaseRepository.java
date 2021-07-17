package br.org.cancer.modred.persistence;

import org.springframework.data.repository.NoRepositoryBean;

import br.org.cancer.modred.model.Exame;

/**
 * Camada de acesso a base dados de Exame.
 * 
 * @author Pizão
 * 
 * @param <T> classe que estende de Exame
 *
 */
@NoRepositoryBean
public interface IExameBaseRepository<T extends Exame> extends IRepository<T, Long>{}