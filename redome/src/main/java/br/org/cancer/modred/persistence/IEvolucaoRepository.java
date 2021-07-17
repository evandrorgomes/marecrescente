package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Evolucao;

/**
 * Camada de acesso a base dados de Evolução.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IEvolucaoRepository extends IRepository<Evolucao, Long>{
    
    List<Evolucao> findByPacienteRmr(Long pacienteRmr);
    
    /**
     * Retorna a ultima evolucao por data.
     * @return
     */
    Evolucao findFirstByPacienteRmrOrderByDataCriacaoDesc(Long rmr);   
    
    /**
     * Retorna uma lista ordenada por data decrescente.
     * @param pacienteRmr
     * @return List<Evolucao> lista de evolucoes
     */
    List<Evolucao> findByPacienteRmrOrderByDataCriacaoDesc(Long pacienteRmr);
    
}