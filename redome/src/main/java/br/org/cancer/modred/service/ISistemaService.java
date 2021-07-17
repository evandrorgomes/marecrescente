package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.security.Sistema;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface que descreve os métodos de negócio que devem ser implementados pelas classes de serviços que operam sobre a
 * entidade Sistema. Essa entidade atua em função de filtrar os perfis destinados a cada sistema associado (externos ou internos).
 * 
 * @author Pizão
 *
 */
public interface ISistemaService extends IService<Sistema, Long> {
	
	/**
	 * Lista de sistemas disponíveis para cadastro de usuários associados
	 * a perfis/sistemas por parte do Redome e não de terceiros, como Transportadora,
	 * por exemplo.
	 * 
	 * @return lista de sistemas.
	 */
	List<Sistema> listarDisponiveisParaRedome();
	
}
