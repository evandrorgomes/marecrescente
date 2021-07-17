package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoPedidoIdm;

/**
 * Interface para acesso ao banco de dados envolvendo a classe ArquivoPedidoIdm.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IArquivoPedidoIdmRepository extends IRepository<ArquivoPedidoIdm, Long> {}
