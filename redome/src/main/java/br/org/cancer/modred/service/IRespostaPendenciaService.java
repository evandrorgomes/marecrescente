package br.org.cancer.modred.service;

import br.org.cancer.modred.model.RespostaPendencia;

/**
 * Classe de negócios para pendencia.
 * 
 * @author Filipe Paes
 *
 */
public interface IRespostaPendenciaService {
    
    /**
     * Método para anotar ou reabrir uma ou mais pendências.
     */
    void anotarReabrir(RespostaPendencia resposta);
    
    /**
     * Método para responder uma  ou mais pendências.
     */
    void responder(RespostaPendencia resposta);

}
