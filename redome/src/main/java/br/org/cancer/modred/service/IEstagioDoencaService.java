package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.EstagioDoenca;

/**
 * Interface para metodos de negocio de estágio doença.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IEstagioDoencaService {
    
    /**
     * Método para listar todos os estágios da doença de um determinado cid.
     * @author Fillipe Queiroz
     * @param idCid - id do cid com seus determinados estágios
     * @return lista de estagio doenca
     */
    List<EstagioDoenca> listarEstagiosDoencaPorCid(long idCid);
    
    /**
     * Método para obter um estagio doença por id.
     * @param Long id do estágio da doença 
     * @return EstagioDoenca instacia do estagio da doença obtido por id
     */
    EstagioDoenca obterEstagioDoenca(Long id);

    /**
     * método para garantir que o estagio informado tem relação com o CID.
     * @param id
     * @param estagioDoencaId
     * @return
     */
    Boolean isEstagioInvalidoParaCid(Long id, Long estagioDoencaId);
}