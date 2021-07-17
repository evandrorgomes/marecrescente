package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EnderecoContatoDoador;

/**
 * Camada de acesso a base dados de EnderecoContato.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IEnderecoContatoDoadorRepository extends IEnderecoContatoBaseRepository<EnderecoContatoDoador>{
	
	@Query("select e from EnderecoContatoDoador e where e.doador.id = :idDoador")
	List<EnderecoContatoDoador> listarEnderecos(@Param("idDoador") Long idDoador);
	
}