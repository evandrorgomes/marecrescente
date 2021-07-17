package br.org.cancer.redome.courier.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.ContatoTelefonicoCourier;


/**
 * Interface de persistencia de dados de contato telefonico de courier.
 * @author Filipe Paes
 *
 */
@Repository
public interface IContatoTelefonicoCourierRepository  extends IContatoTelefonicoBaseRepository<ContatoTelefonicoCourier> {

}
