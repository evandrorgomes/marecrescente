/**
 * 
 */
package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.correio.Localidade;


/**
 * @author bruno.sousa
 *
 */
@Repository
public interface ILocalidadeRepository extends JpaRepository<Localidade, Long> {

    /**
     * @param siglaUnidadeFederatica
     * @return List<Localidade>
     */
    List<Localidade> findByUnidadeFederativaSigla(String siglaUnidadeFederatica);
    
    /**
     * @param siglaUnidadeFederatica
     * @param query
     * @return List<Localidade>
     */
    List<Localidade> findByUnidadeFederativaSiglaAndNomeContaining(String siglaUnidadeFederatica, String query);
    
    
    
}
