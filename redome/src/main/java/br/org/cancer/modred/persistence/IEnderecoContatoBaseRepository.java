package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import br.org.cancer.modred.model.EnderecoContato;

/**
 * Repositório base para a entidade EnderecoContato e as entidades que extendem dela,
 * extendendo da JpaRepository interface disponibilizada pelo Spring Data.
 * 
 * Todos os métodos deste repositório estão presentes no IEnderecoContatoRepository,
 * no IEnderecoContatoDoadorRepository e no IEnderecoContatoPacienteRepository
 * 
 * @author bruno.sousa
 *
 * @param <T>
 */
@NoRepositoryBean
public interface IEnderecoContatoBaseRepository<T extends EnderecoContato> extends IRepository<T, Long>{
	
	/**
	 * realiza a exclusao logica do endereco do doador por id do endereco.
	 * @param id
	 */
	@Modifying
	@Query("update EnderecoContato set excluido = 1 where id = :idendereco")
	int excluirEndereco(@Param("idendereco")Long id);
	
}
