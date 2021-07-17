package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.SmsEnviado;

/**
 * Interface de persistencia para a entidade SmsEnviado.
 * 
 * @author brunosousa
 *
 */
@Repository
public interface ISmsEnviadoRepository extends IRepository<SmsEnviado, Long>, ISmsEnviadoRepositoryCustom {
	
}
