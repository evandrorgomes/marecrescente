package br.org.cancer.modred.persistence;

import br.org.cancer.modred.controller.dto.doador.SolicitacaoRedomewebDTO;

/**
 * Interface para métodos customizados de Busca.
 * @author Filipe Paes
 *
 */
public interface ISolicitacaoRedomewebRepositoryCustom {

	/**
	 * Lista o histórico das recusas do CT para a busca informada.
	 * 
	 * @param rmr RMR do paciente associado a busca e, por consequencia, ao histórico da busca.
	 * @return lista de históricos de busca.
	 */
	SolicitacaoRedomewebDTO listarSolicitacaoRedomewebDTOPorId(Long idSolicitacaoRedomeweb);
}
