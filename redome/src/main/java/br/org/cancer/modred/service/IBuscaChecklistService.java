package br.org.cancer.modred.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.dto.BuscaChecklistDTO;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.BuscaChecklist;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.domain.TiposBuscaChecklist;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.service.custom.IService;


/**
 * Interface de métodos de negócio de BuscaCheckList.
 * 
 * @author Filipe Paes
 *
 */
public interface IBuscaChecklistService extends IService<BuscaChecklist, Long>{
	
	/**
	 * Cria um checklist inicial para a busca.
	 * @param busca dona do checklist.
	 * @param tiposBuscaChecklist tipo de checklist.
	 */
	void criarItemCheckList(Busca busca, TiposBuscaChecklist tiposBuscaChecklist);
	
	/**
	 * Cria um item de checklist com busca e match.
	 * @param busca do checklist.
	 * @param match match do checklist.
	 * @param tiposBuscaChecklist tipo de checklist a ser inserido.
	 */
	void criarItemCheckList(Busca busca, Match match, TiposBuscaChecklist tiposBuscaChecklist);
	
	/**
	 * Obtém checklist de busca por busca ou tipo de checklist de busca. 
	 * @param idBusca id da busca.
	 * @param idTipoBuscaChecklist id do tipo de checklist de busca.
	 * @return buscachecklist localizado ou null caso não localizado.
	 */
	BuscaChecklist obterBuscaChecklist(Long idBusca, Long idTipoBuscaChecklist);
	
	/**
	 * Lista todos os itens de busca checklist somente da busca informada sem o match.
	 * 
	 * @param idBusca ID da busca.
	 * @return lista de itens de checklist.
	 */
	List<BuscaChecklistDTO> listarChecklist(Long idBusca);
	
	/**
	 * Lista todos os itens de busca checklist para a busca e match informados.
	 * 
	 * @param idBusca ID da busca.
	 * @param idMatch ID do match.
	 * @return lista de itens de checklist.
	 */
	List<BuscaChecklistDTO> listarChecklist(Long idBusca, Long idMatch);
	
	
	/**
	 * Lista checklists por analista e tipo de checklist.
	 * @param loginAnalista - analista responsavel pelo checklist.
	 * @param idTipoChecklist - tipo de checklist a ser listado.
	 * @param pageRequest parametros da paginacao.
	 * @return listagem paginada do filtro passado.
	 */
	Page<BuscaChecklistDTO> listarChecklistPorAnalistaETipo(String loginAnalista, Long idTipoChecklist, PageRequest pageRequest);
	
	/**
	 * Marca o itens de checklist como visto para a busca e match, se informado.
	 * 
	 * @param id ID do checklist.
	 */
	void marcarVisto(Long id);
	
	
	/**
	 * Quantidade de itens de busca checklist para a busca e match inativos (Histórico) com pedidos na busca.
	 * 
	 * @param idBusca ID da Busca
	 * @param fase Fase do doador na busca.
	 * @param tiposDoador - Tipos de doador (Medula ou Cordão)
	 * @return Total de registros encontrados.
	 */
	Long totalChecklistHistoricoPorSituacao(Long idBusca, String fase, List<TiposDoador> tiposDoador);
	
	/**
	 * Marca uma lista itens de checklist como visto para a busca e match, se informado.
	 * 
	 * @param listaIdsChecklists lista de IDS do checklist.
	 */
	void marcarListaDeVistos(List<Long> listaIdsChecklists);
	
	void analisarBuscaViaItemCheckList(Long idBusca);
	
	boolean existeBuscaChecklistEmAberto(Long idBusca, Long idTipoBuscaChecklist);
	
	boolean existeBuscaChecklistEmAberto(Long idBusca, Long idTipoBuscaChecklist, Long idMatch);
	
	void vistarChecklistPorIdBuscaETipo(Long idBusca, Long idTipoBuscaChecklist);
}
