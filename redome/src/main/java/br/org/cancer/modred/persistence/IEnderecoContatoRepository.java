package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EnderecoContato;

/**
 * Repositrório para tratar persistencia de Endereço Contato.
 * @param <T> entidade que extende de endereço de contato.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IEnderecoContatoRepository<T extends EnderecoContato> extends IEnderecoContatoBaseRepository<T>{}
