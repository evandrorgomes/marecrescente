package br.org.cancer.redome.notificacao.persistence;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repositório genérico para acesso ao banco de dados.
 * O acesso as informações são definidas de acordo com a entidade T fornecida.
 * 
 * @param <T> Representa a entidade relacionada ao repositório.
 * @param <ID> Representa o tipo do ID da entidade informada.
 * 
 * @author Pizão.
 *
 */
@NoRepositoryBean
public interface IRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {}
