package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.correio.UnidadeFederativa;


/**
 * Interface do tipo repository para a comunicação com o banco.
 * @author bruno.sousa
 *
 */
@Repository
public interface IUnidadeFederativaRepository extends JpaRepository<UnidadeFederativa, Long> {
    
    /**Método de busca por unidade federativa através da sigla.
     * @param query
     * @return List<UnidadeFederativaCorreio>
     */
    List<UnidadeFederativa> findBySiglaContaining(String query);
}
