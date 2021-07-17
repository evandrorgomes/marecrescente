package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorGenotipoExpandidoPreliminar;

/**
 * Classe que faz acesso aos dados relativos a entidade GenotipoExpandidoPreliminar
 * no banco de dados.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IValorGenotipoExpandidoPreliminarRepository extends IRepository<ValorGenotipoExpandidoPreliminar, Long> {}
