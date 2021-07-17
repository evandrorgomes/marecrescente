package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ClassificacaoABO;

/**
 * Interface de persistencia de Classificacao de abo.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IClassificacaoABORepository extends IRepository<ClassificacaoABO, Long> {

}
