package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do 
 * contexto de serialização. 
 * 
 * Ao retornar dados para um cliente REST, dependendo de qual serviço REST foi chamado, é necessário limitar 
 * quais dados serão serializados ao usar o mesmo modelo de dados.
 * 
 * @author Pizão
 *
 */
public class TarefaLogisticaView {

    /**
     * Listar os itens de transporte.
     */
    public interface Listar extends ListarPaginacao{}

    
}
