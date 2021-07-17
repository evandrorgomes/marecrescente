package br.org.cancer.modred.controller.view;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do 
 * contexto de serialização. 
 * 
 * Ao retornar dados para um cliente REST, dependendo de qual serviço REST foi chamado, é necessário limitar 
 * quais dados serão serializados ao usar o mesmo modelo de dados.
 * 
 * @author bruno.sousa
 *
 */
public class MedicoView {

    /**
     * Filtro mais enxuto para interfaces que retornam coleção de dados.
     */
    public interface Listar extends ListarPaginacao {}

    /**
     * Filtro utilizado para detalhe do pré cadastro médico.
     */
    public interface Detalhe {} 
    
    /**
     * Filtro utilizado para detalhe do médico associado ao usuário logado.
     */
    public interface Logado {}
}
