package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.HemoEntidade;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para as funcionalidades envolvidas com a classe HemoEntidade.
 * 
 * @author Pizão.
 *
 */
public interface IHemoEntidadeService extends IService<HemoEntidade, Long>{
	
	/**
	 * Lista as hemoentidades para a UF informada.
	 * 
	 * @param sigla UF informada.
	 * @return List<HemoEntidade>
	 */
	List<HemoEntidade> listarHemoEntidadesPorUf(String sigla);
	
	/**
	 * Lista as hemoentidades para o doador informado.
	 * 
	 * @param idDoador Identificação do doador.
	 * @return List<HemoEntidade>
	 */
	List<HemoEntidade> listarHemoEntidadesPorDoador(Long idDoador);

	
	/**
	 * Método para obter a hemoentidade poor id.
	 * 
	 * @param id - identificador da Hemoentidade.
	 * @return Hemoentidade em caso de sucesso.
	 */
	HemoEntidade obterPorId(Long id);
}
