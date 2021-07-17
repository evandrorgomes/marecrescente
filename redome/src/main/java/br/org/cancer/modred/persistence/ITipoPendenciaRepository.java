package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.TipoPendencia;

/**
 * Interface de persistencia de Tipo de Pendência.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface ITipoPendenciaRepository extends JpaRepository<TipoPendencia, Long> {}
