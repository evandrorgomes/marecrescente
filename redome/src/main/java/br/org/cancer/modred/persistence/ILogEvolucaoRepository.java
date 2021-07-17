package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.LogEvolucao;

/**
 * Camada de acesso a base dados de LogEvolucao.
 * 
 * @author Pizão
 *
 */
@Repository
public interface ILogEvolucaoRepository extends IRepository<LogEvolucao, Long>{
	
	List<LogEvolucao> findByPacienteRmr(Long rmr);
}