package br.org.cancer.modred.service;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.HistoricoBusca;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para definir assinatura das funcionalidades envolvendo a entidade
 * HistoricoBusca.
 * 
 * @author Pizão
 *
 */
public interface IHistoricoBuscaService extends IService<HistoricoBusca, Long>{
	
	/**
	 * Registra o histórico da busca para a busca informada.
	 * 
	 * @param buscaAtiva busca que irá gerar o histórico.
	 * @param justificativa justificativa para recusa do CT.
	 */
	void registrar(Busca buscaAtiva, String justificativa);
}
