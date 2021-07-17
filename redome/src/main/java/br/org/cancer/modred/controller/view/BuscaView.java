package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * View para consulta de Busca.
 * @author Filipe Paes
 *
 */
public class BuscaView {
	/**
	 * Interface para visualização dos dados de busca.
	 * @author Filipe Paes
	 *
	 */
	public interface Busca{}
	
	/**
	 * Interface para dialogos de busca.
	 * @author Filipe Paes
	 *
	 */
	public interface DialogoBusca{}
	
	
    /**
     * Filtro para trazer a tarefa de enriquecimento.
     * 
     * @author bruno.sousa
     *
     */
    public interface Enriquecimento extends ListarPaginacao{}
    
    /**
     * Filtro para trazer tarefas de avaliação de pedido de coleta.
     * @author Filipe Paes
     *
     */
    public interface AvaliacaoPedidoColeta extends ListarPaginacao{}
    
    /**
     * Filtro para trazer tarefas de avaliação de doador.
     * @author Fillipe Queiroz
     *
     */
    public interface AvaliacaoWorkupDoador extends ListarPaginacao{}

	
	/**
	 * Interface para o ultimo pedido de exame.
	 * @author bruno.sousa
	 *
	 */
	public interface UltimoPedidoExame{}
	
	/**
	 * Interface para a lista de arquivos dos relatórios da busca internacional.
	 * @author bruno.sousa
	 *
	 */
	public interface ListarArquivosRelatorioBuscaInternacional{}
	
	
	
}
