package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EnderecoContatoCentroTransplante;

/**
 * Camada de acesso a base dados de EnderecoContatoCentroTransplante.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IEnderecoContatoCentroTransplanteRepository extends IEnderecoContatoBaseRepository<EnderecoContatoCentroTransplante>{
	
	
}