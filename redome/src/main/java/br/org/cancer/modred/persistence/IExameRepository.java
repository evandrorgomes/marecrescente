package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Exame;

/**
 * Camada de acesso a base dados de Exame.
 * 
 * @author Pizão
 * 
 *
 */
@Repository
public interface IExameRepository extends IExameBaseRepository<Exame>, IExameRepositoryCustom {
		
}
