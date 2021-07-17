package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorGenotipoBuscaPreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade GenotipoPreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IValorGenotipoBuscaPreliminarRepository extends IRepository<ValorGenotipoBuscaPreliminar, Long> {}
