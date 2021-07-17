package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EvolucaoDoador;

/**
 * Interface de persistencia para Evolucao de Doador.
 * @author Filipe Paes
 *
 */
@Repository
public interface IEvolucaoDoadorRepository  extends IRepository<EvolucaoDoador, Long>{

}
