package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.correio.Logradouro;


/**
 * Interface do tipo repository para a comunicação com o banco de dados.
 * @author bruno.sousa
 *
 */
@Repository
public interface ILogradouroCorreioRepository extends JpaRepository<Logradouro, Long> {

    
    /**
     * @param bairro bairro
     * @param localidade loc
     * @param sigla sigla
     * @param query query
     * @return List<Logradouro> logradouros
     */
    List<Logradouro> findByBairroInicialNomeAndBairroInicialLocalidadeNomeAndBairroInicialLocalidadeUnidadeFederativaSiglaAndNomeContaining(String bairro, String localidade, String sigla, String query);
    
    
    /** Método para buscar por cep.
     * @param String
     * @return
     */
    Logradouro findByCep(String cep);
}
