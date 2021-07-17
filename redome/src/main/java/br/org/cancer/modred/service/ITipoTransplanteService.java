package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.TipoTransplante;

/**
 * Interface para métodos de negócio do tipo de transplante.
 * 
 * @author bruno.sousa
 *
 */
public interface ITipoTransplanteService {

    /**
     * Lista os tipos de transplantes.
     * 
     * @param List<TipoTransplante>
     */
    List<TipoTransplante> listarTipoTransplante();

    /**
     * Método para carregar tipo transplante por id.
     * 
     * @param id
     * @return TipoTransplante buscado por id
     */
    TipoTransplante obterTipoTransplante(Long id);

}