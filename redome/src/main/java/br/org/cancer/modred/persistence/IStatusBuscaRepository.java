package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.StatusBusca;

/**
 * Interface de persistencia de Status busca.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IStatusBuscaRepository extends JpaRepository<StatusBusca, Long> {}
