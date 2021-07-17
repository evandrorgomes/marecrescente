package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.RecebimentoColeta;

/**
 * Repositório para recebimento e coleta de doador.
 * @author ergomes
 *
 */
@Repository
public interface IRecebimentoColetaRepository  extends IRepository<RecebimentoColeta, Long>{

}
