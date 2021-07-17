package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.org.cancer.modred.controller.dto.BuscaPaginacaoDTO;
import br.org.cancer.modred.controller.dto.HistoricoBuscaDTO;

/**
 * Interface para métodos customizados de Busca.
 * @author Filipe Paes
 *
 */
public interface IBuscaRepositoryCustom {

	/**
	 * Lista paginada das buscas ativas, associadas ao um determinado analista de busca.
	 * Existem outras opções de filtro como por RMR e nome do paciente ou tipo de checklist de busca
	 * associado ao paciente (ou processos de match a que está envolvido), sendo esses dois últimos 
	 * filtros opcionais.
	 * 
	 * @param loginAnalistaBusca login do analista de busca associado.
	 * @param itemCheckList ID do tipo de item de checklist de busca a ser utilizado como filtro (opcional). 
	 * @param rmr RMR do paciente a ser utilizado como filtro (opcional).
	 * @param nome Nome do paciente a ser utilizado como filtro (opcional).
	 * @param campoOrdenacao - campo para parametro de ordenacao. Se não for preenchido, pelo nome é o padrão.
	 * @param pageable - paginação para o resultado.
	 * 
	 * @return lista paginada de buscas.
	 */

	Page<BuscaPaginacaoDTO> listarBuscas(String loginAnalistaBusca, Long idTipoBuscaCheckList, 
			Long rmr, String nome, List<Long> status, Pageable pageable);
	
	/**
	 * Lista o histórico das recusas do CT para a busca informada.
	 * 
	 * @param rmr RMR do paciente associado a busca e, por consequencia, ao histórico da busca.
	 * @return lista de históricos de busca.
	 */
	List<HistoricoBuscaDTO> listarHistoricoBusca(Long rmr);
}
