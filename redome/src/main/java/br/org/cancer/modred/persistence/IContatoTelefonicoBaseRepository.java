package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.model.ContatoTelefonico;

/**
 * Repositório base para a entidade ContatoTelefonico e as entidades que extendem dela,
 * extendendo da JpaRepository interface disponibilizada pelo Spring Data.
 * 
 * Todos os métodos deste repositório estão presentes no IContatoTelefonicoRepository,
 * no IContatoTelefonicoDoadorRepository e no IContatoTelefonicoPacienteRepository
 * 
 * @author bruno.sousa
 *
 * @param <T>
 */
@NoRepositoryBean
public interface IContatoTelefonicoBaseRepository<T extends ContatoTelefonico>  extends IRepository<T, Long> {

	/**
	 * Método para excluir logicamente um contato telefônico.
	 * @param idContato
	 */
	@Modifying
	@Query("update ContatoTelefonico set excluido = 1 where id = :idcontato")
	int excluirLogicamente(@Param("idcontato") Long idContato);
}
