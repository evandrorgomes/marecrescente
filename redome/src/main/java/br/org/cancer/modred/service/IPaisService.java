package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Pais;
import br.org.cancer.modred.vo.CodigoInternacionalVO;

/**
 * Interface para metodos de negocio de país.
 * 
 * @author Filipe Paes
 *
 */
public interface IPaisService {

    /**
     * Lista as raças.
     * 
     * @param List<Pais>
     */
    List<Pais> listarPais();

    /**
     * Método para obter país por id.
     * 
     * @param id
     * @return Pais país por id
     */
    Pais obterPais(Long id);

    /**
     * Método para listar os códigos internacionais.
     * 
     * @return lista de coódigos internacionais
     */
    List<CodigoInternacionalVO> listarCodigoInternacional();
}
