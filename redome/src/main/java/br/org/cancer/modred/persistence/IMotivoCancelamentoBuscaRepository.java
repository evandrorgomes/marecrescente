package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.MotivoCancelamentoBusca;

/**
 * Interface com métodos de persistencia de MotivoCancelamentoBusca.
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface IMotivoCancelamentoBuscaRepository extends JpaRepository<MotivoCancelamentoBusca, Long> {
	
	
}
