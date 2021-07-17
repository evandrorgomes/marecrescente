package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.LocusExamePreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade LocusExamePreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface ILocusExamePreliminarRepository extends IRepository<LocusExamePreliminar, Long> {}
