package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.correio.TipoLogradouro;

/**
 * Interface do tipo repository para a comunicação com o banco.
 * @author bruno.sousa
 *
 */
@Repository
public interface ITipoLogradouroCorreioRepository extends JpaRepository<TipoLogradouro, Long> {

    /**
     * Método de busca de tipo de logradouro.
     * @param String query
     * @return List<TipoLogradouro>
     */
    List<TipoLogradouro> findByNomeContaining(String query);
}
