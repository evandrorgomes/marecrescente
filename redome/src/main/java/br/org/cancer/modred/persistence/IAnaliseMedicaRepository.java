package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.AnaliseMedica;

/**
 * Interface para acesso ao banco de dados envolvendo a classe AnaliseMedica.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IAnaliseMedicaRepository extends IRepository<AnaliseMedica, Long> {



}
