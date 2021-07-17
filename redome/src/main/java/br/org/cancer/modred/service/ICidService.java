package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Cid;

/**
 * Interface para disponibilizar métodos de Cid.
 * 
 * @author Filipe Paes
 *
 */
public interface ICidService {

	/**
	 * Método de consulta de Cids por código ou descrição com valores totais ou parciais.
	 * 
	 * @param String parametro para pesquisa
	 * @return List<Cid> listagem de cids consultadas de acordo com o código ou a descrição
	 */
	List<Cid> listarCidPorCodigoOuDescricao(String textoPesquisa);

	/**
	 * Método para consultar CID por Id.
	 * 
	 * @param Long Id do Cid a ser pesquisado
	 * @return Cid CID encontrado na base
	 */
	Cid findById(Long id);

	/**
	 * Consulta a Cid pelo código.
	 * 
	 * @param codigo da cid a ser consultado.
	 * @return Cid com o código informado.
	 */
	Cid obterCidPeloCodigo(String codigo);
}
