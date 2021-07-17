package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EvolucaoDoador;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.vo.EvolucaoDoadorVo;

/**
 * Interface de negócios para Evolução Doador.
 * @author Filipe Paes
 *
 */
public interface IEvolucaoDoadorService extends IService<EvolucaoDoador, Long> {

	/**
	 * Lista as evoluções do doador em ordem decrescente de data de criação. 
	 * 
	 * @param idDoador Identificador do doador.
	 * @return Lista de evoluções do doador.
	 */
	List<EvolucaoDoador> listarEvolucoesPorDoadorOrdernadoPorDataCriacaoDecrescente(Long idDoador);
	
	/**
	 * Método para criar a evolução do doador. 
	 * 
	 * @param evolucaoDoadorVo - A Evolucao a ser criada.
	 */
	void criarEvolucaoDoador(EvolucaoDoadorVo evolucaoDoadorVo);

}
