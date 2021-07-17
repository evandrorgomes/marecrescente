package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CancelamentoBusca;

/**
 * Interface com métodos de persistencia de CancelamentoBusca.
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface ICancelamentoBuscaRepository extends JpaRepository<CancelamentoBusca, Long> {
	
	CancelamentoBusca findFirstByBuscaIdOrderByDataCriacaoDesc(Long buscaId);
	
}
