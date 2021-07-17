package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.StatusPendencia;

/**
 * Interface de persistencia de Status Pendência.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IStatusPendenciaRepository extends JpaRepository<StatusPendencia, Long> {}
