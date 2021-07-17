package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ArquivoResultadoWorkup;

/**
 * Interface de persistencia para ArquivoResultadoWorkup.
 * @author Filipe Paes
 *
 */
@Repository
public interface IArquivoResultadoWorkupRepository extends JpaRepository<ArquivoResultadoWorkup, Long> {

}
