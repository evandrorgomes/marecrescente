package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.MotivoCancelamentoWorkup;

/**
 * Interface de persistencia de motivo de cancelamento de workup.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IMotivoCancelamentoWorkupRepository extends JpaRepository<MotivoCancelamentoWorkup, Long> {

	List<MotivoCancelamentoWorkup> findBySelecionavelOrderByDescricao(boolean selecionavel);
}
