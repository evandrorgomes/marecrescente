package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.SolicitacaoRedomeweb;

/**
 * Interface para acesso ao banco de dados envolvendo a classe SolicitacaoRedomeweb.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface ISolicitacaoRedomewebRepository extends IRepository<SolicitacaoRedomeweb, Long> {
	
}
