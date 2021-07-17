package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.Solicitacao;

/**
 * Repositório para acesso a base de dados ligadas a entidade Prescrição.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IPrescricaoRepository extends IRepository<Prescricao, Long> {
	
	/**
	 * Obtém a prescrição associada a solicitação passada por parâmetro.
	 * 
	 * @param solicitacao solicitação a ser utilizada no filtro.
	 * @return prescrição, se houver.
	 */
	@Query("select presc from Prescricao presc join presc.solicitacao sol where sol.id = :#{#solicitacao.id}")
	Prescricao obterPrescricao(@Param("solicitacao") Solicitacao solicitacao);
	
	/**
	 * Obtem uma prescrição de acordo com o id da busca. 
	 * @param id da busca do paciente.
	 * @return prescrição requerida.
	 */
	@Query("select p from Prescricao p join p.solicitacao s join s.match m join m.busca b where b.id = :idBusca and s.status <> 3 ")
	Prescricao obterPrescricaoPorBusca(@Param("idBusca") Long idBusca);
	
	
	
}

