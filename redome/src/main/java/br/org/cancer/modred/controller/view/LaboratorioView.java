package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * View para dados referentes a laboratório.
 * 
 * @author Filipe Paes
 *
 */
public class LaboratorioView {
	/**
	 * Interface para lista de exames de CT para serem recebidos.
	 * 
	 * @author Filipe Paes
	 *
	 */
	public interface ListarReceberExame extends ListarPaginacao {
	}

	/**
	 * Interface para lista de exames de CT para cadastrar resultado.
	 * 
	 * @author Filipe Paes
	 *
	 */
	public interface ListarCadastrarResultadoCt extends ListarPaginacao {
	}
    
    /**
     * Filtro para retornar lista de laboratórios de ct.
     */
    public interface ListarCT{}

	/**
	 * Filtro mais enxuto para interfaces que retornam coleção de dados.
	 */
	public interface Listar {
	}
	
	/**
	 * Detalhemento do laboratório.
	 * @author ergomes
	 *
	 */
	public interface Detalhe{}

}
