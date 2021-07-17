package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.TipoSolicitacao;

/**
 * Interface para acesso a dados de {@link TipoSolicitacao}
 * @author Filipe Paes
 *
 */
@Repository
public interface ITipoSolicitacaoRepository  extends IRepository<TipoSolicitacao, Long> {}