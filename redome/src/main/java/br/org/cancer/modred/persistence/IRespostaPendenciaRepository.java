package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.RespostaPendencia;

/**
 * Interface de persistencia de RespostaPendencia.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IRespostaPendenciaRepository extends JpaRepository<RespostaPendencia, Long> {

}
