package br.org.cancer.modred.controller.view;

/**
 * Classe necessária para a utilização do anotador @JsonView. Utilizado para filtrar campos dependendo do 
 * contexto de serialização. 
 * 
 * Ao retornar dados para um cliente REST, dependendo de qual serviço REST foi chamado, é necessário limitar
 * quais dados serão serializados ao usar o mesmo modelo de dados.
 * 
 * @author Thiago Moraes
 *
 */
public class UsuarioView {

    /**
     * Filtro mais enxuto para interfaces que retornam coleção de dados.
     */
    public interface Listar{}

    /**
     * Filtro mais detalhado para interfaces que retornam um único objeto.
     */
    public interface Consultar{}
    
    /**
     * Filtro mais detalhado, com info sensível, para interfaces que retornam um único objeto.
     */
    public interface ConsultarSPI{}
    
    /**
     * Listagem de apenas id e nome de usuario.
     * @author Filipe Paes
     *
     */
    public interface ListaBasica{}

}
