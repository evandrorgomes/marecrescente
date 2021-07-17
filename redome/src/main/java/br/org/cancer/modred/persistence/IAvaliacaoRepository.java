package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Avaliacao;

/**
 * Interface de persistencia de Avaliação.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IAvaliacaoRepository extends IRepository<Avaliacao, Long> {

    Avaliacao findLastByPacienteRmrOrderByDataCriacao(Long rmr);

    @Query("select case when (count(1) > 0) then true else false end " +
            "from Avaliacao av join av.medicoResponsavel med join med.usuario usua " +
            "where av.id = ?1 and usua.id = ?2")
    boolean verificarSeUsuarioResponsavelAvaliacao(Long avaliacaoId, Long usuarioId);

    /**
     * Busca e retorna, se encontrar, avaliação associado ao rmr de paciente
     * informado.
     * 
     * @param rmr referencia do paciente para busca.
     * @return avaliação associada.
     */
    Avaliacao findByPacienteRmr(Long rmr);

}
