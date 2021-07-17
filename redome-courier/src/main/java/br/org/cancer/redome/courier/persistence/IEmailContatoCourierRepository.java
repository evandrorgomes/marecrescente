package br.org.cancer.redome.courier.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.EmailContatoCourier;

/**
 * Interface de persistencia de Email de Courier.
 * @author Filipe Paes
 *
 */
@Repository
public interface IEmailContatoCourierRepository  extends IEmailContatoBaseRepository<EmailContatoCourier>{

}
