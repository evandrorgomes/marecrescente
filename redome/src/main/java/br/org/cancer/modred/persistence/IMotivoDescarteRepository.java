package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.MotivoDescarte;

/**
 * @author Diogo Paraíso
 *
 */
@Repository
public interface IMotivoDescarteRepository extends JpaRepository<MotivoDescarte, Long> {
}
