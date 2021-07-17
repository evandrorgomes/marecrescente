package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Metodologia;

/**
 * Interface para metodos de metodologia.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IMetodologiaService {

    /**
     * Lista as metodologias.
     * 
     * @param List<Metodologia>
     */
    List<Metodologia> listarMetodologias();
    
    Metodologia findById(Long id);

}
