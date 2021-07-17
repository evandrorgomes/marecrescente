package br.org.cancer.redome.courier.persistence;

import org.springframework.data.repository.NoRepositoryBean;

import br.org.cancer.redome.courier.model.EmailContato;

/**
 * Repositório base para a entidade EmailContato e as entidades que extendem dela,
 * extendendo da JpaRepository interface disponibilizada pelo Spring Data.
 * 
 * Todos os métodos deste repositório estão presentes no IEmailContatoRepository e
 * no IEmailContatoDoadorRepository
 * 
 * @author bruno.sousa
 *
 * @param <T>
 */
@NoRepositoryBean
public interface IEmailContatoBaseRepository<T extends EmailContato>  extends IRepository<T, Long>{
	
}
