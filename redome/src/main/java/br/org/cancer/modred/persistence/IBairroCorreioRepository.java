package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.correio.Bairro;


/**
 * @author bruno.sousa
 *
 */
@Repository
public interface IBairroCorreioRepository extends JpaRepository<Bairro, Long> {

    /**
     * @param String - sigla
     * @param String - localidade
     * @return List<Bairro>
     */
    List<Bairro> findByLocalidadeUnidadeFederativaSiglaAndLocalidadeNome(String sigla, String localidade);
    
    /**
     * @param sigla
     * @param localidade
     * @param query
     * @return List<Bairro>
     */
    List<Bairro> findByLocalidadeUnidadeFederativaSiglaAndLocalidadeNomeAndNomeContaining(String sigla, 
                                                                                         String localidade, 
                                                                                         String query);
    
}
