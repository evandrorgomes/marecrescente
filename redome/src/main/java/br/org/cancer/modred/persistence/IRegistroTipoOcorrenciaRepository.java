package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.RegistroTipoOcorrencia;

/**
 * Interface de banco de dados de tipo de ocorrencia de contato.
 * @author Filipe Paes
 *
 */
@Repository
public interface IRegistroTipoOcorrenciaRepository  extends IRepository<RegistroTipoOcorrencia, Long> {

}
