package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorGenotipoPreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade GenotipoPreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IValorGenotipoPreliminarRepository extends IRepository<ValorGenotipoPreliminar, Long> {

	/**
	 * Lista os valores de genótipo preliminar. 
	 * 
	 * @param idBuscaPreliminar
	 * @return
	 */
	List<ValorGenotipoPreliminar> findByBuscaPreliminarId(Long idBuscaPreliminar);}
