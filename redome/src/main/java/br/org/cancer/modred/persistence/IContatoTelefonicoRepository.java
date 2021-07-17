package br.org.cancer.modred.persistence;

import br.org.cancer.modred.model.ContatoTelefonico;

/**
 * Camada de persistencia para ContatoTelefonico.
 * @param <T> classe que define a implementação em cima do contato telefonico.
 * 
 * @author Filipe Paes
 *
 */
public interface IContatoTelefonicoRepository<T extends ContatoTelefonico> extends IContatoTelefonicoBaseRepository<T>{}
