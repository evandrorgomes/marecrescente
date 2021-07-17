package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.EmailContatoCentroTransplante;

/**
 * Interface de persistencia de Email de Contato do centro de transplante.
 * @author bruno.sousa
 *
 */
@Repository
public interface IEmailContatoCentroTransplanteRepository extends IEmailContatoBaseRepository<EmailContatoCentroTransplante> {

}
