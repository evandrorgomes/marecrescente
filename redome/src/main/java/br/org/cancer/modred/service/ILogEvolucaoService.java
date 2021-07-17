package br.org.cancer.modred.service;

import br.org.cancer.modred.controller.dto.CriarLogEvolucaoDTO;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para definir assinatura das funcionalidades envolvendo a entidade
 * LogEvolucao.
 * 	
 * @author Pizão
 *
 */
public interface ILogEvolucaoService extends IService<LogEvolucao, Long>{

	void criarLogEvolucao(CriarLogEvolucaoDTO criarLogEvolucaoDTO);
	
}
