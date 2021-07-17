package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.TipoTransplante;

/**
 * Interface para o repositório de Tipo Transplante.
 * @author Dev Team
 *
 */
public interface ITipoTransplanteRepository extends JpaRepository<TipoTransplante, Long> {
}
