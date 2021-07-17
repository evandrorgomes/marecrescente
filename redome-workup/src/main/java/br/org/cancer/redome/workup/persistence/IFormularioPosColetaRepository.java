package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.FormularioPosColeta;

/**
 * Repositório para acesso a base de dados ligadas a entidade PedidoWorkup.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IFormularioPosColetaRepository extends IRepository<FormularioPosColeta, Long> {

	
}
