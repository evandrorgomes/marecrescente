package br.org.cancer.redome.workup.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.workup.model.ArquivoPrescricao;

/**
 * Interface de persistencia para ArquivoPrescricao.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IArquivoPrescricaoRepository extends IRepository<ArquivoPrescricao, Long> {

	/**
	 * Obtém o arquivo referente a autorização do paciente.
	 * 
	 * @param idPrescricao ID da prescrição.
	 * @return entidade de arquivo prescrição que guarda a autorização.
	 */
	@Query("select ap from ArquivoPrescricao ap join ap.prescricao p where p.id = :idPrescricao and ap.autorizacaoPaciente = true")
	ArquivoPrescricao obterAutorizacaoPaciente(@Param("idPrescricao") Long idPrescricao);
	
}
