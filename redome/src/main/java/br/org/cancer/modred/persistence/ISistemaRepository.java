package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.security.Sistema;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre o objeto sistema da plataforma redome. 
 * 
 * @author Pizão
 *
 */
@Repository
public interface ISistemaRepository extends IRepository<Sistema, Long> {
	
	/**
	 * Lista os sistemas que estão disponíveis para o Redome.
	 * 
	 * @return lista de sistemas.
	 */
	@Query("select sist from Sistema sist where sist.disponivelRedome = true")
	List<Sistema> listarDisponiveisParaRedome();
	
}
