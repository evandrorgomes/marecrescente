package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ExameCordaoNacional;

/**
 * Camada de acesso a base dados de Exame de cordao internacional.
 * 
 * @author fillipe.queiroz
 *
 */
@Repository
public interface IExameCordaoNacionalRepository extends IExameDoadorBaseRepository<ExameCordaoNacional>{
    
	
}