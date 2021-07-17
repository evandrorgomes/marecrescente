package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ExameDoadorNacional;

/**
 * Interface de persistencia para acesso a dados de exame doador internacional.
 *
 * @author Filipe Paes
 *
 */
@Repository
public interface IExameDoadorNacionalRepository extends IExameDoadorBaseRepository<ExameDoadorNacional> {

}
