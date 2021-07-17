package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EnderecoContatoLaboratorio;

/**
 * Camada de acesso a base dados de EnderecoContato.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IEnderecoContatoLaboratorioRepository extends IEnderecoContatoBaseRepository<EnderecoContatoLaboratorio>{
	
	@Query("select e from EnderecoContatoLaboratorio e where e.laboratorio.id = :idLaboratorio")
	List<EnderecoContatoLaboratorio> listarEnderecos(@Param("idLaboratorio") Long idLaboratorio);
	
}