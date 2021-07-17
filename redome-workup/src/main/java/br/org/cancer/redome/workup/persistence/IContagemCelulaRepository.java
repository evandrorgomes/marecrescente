package br.org.cancer.redome.workup.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.ContagemCelula;

/**
 * Repositório para acesso a base de dados ligadas a entidade ContagemCelula.
 * 
 * @author ruan.agra
 *
 */
@Repository
public interface IContagemCelulaRepository extends IRepository<ContagemCelula, Long>{

	/**
	 * Método para consultar solicitação por idDoador e por tipo.
	 * @param idDoador id do doador.
	 * @param tipoSolicitacao
	 * @return solicitação localizada.
	 */
	ContagemCelula findByIdPedidoWorkup(Long idPedidoWorkup);
	
	
}