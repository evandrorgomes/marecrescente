package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Exame;

/**
 * Camada de acesso a base dados de Exame de doador.
 * 
 * @author bruno.sousa
 *
 */
@Repository("iExameDoadorRepository")
public interface IExameDoadorRepository extends IExameDoadorBaseRepository<Exame>{
    
	
}