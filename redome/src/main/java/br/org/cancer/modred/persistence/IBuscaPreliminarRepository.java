package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.BuscaPreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade BuscaPreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IBuscaPreliminarRepository extends IRepository<BuscaPreliminar, Long> {}
