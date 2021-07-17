package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.model.EmailContato;

/**
 * Camada de acesso a base dados de Email de Contato do doador.
 * @param <T> extendendo de EmailContato.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
@Transactional
public interface IEmailContatoRepository<T extends EmailContato> extends IEmailContatoBaseRepository<T>{}