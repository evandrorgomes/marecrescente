package br.org.cancer.redome.courier.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.EmailContatoTransportadora;

/**
 * Interface de persistencia de Endereco de Contato de Transportadora.
 * @author Filipe Paes
 *
 */
@Repository
public interface IEmailContatoTransportadoraRepository  extends IEmailContatoBaseRepository<EmailContatoTransportadora>{

}
