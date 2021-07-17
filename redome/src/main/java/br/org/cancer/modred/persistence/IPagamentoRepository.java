package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Pagamento;

/**
 * Interfaze para pagamento.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IPagamentoRepository extends JpaRepository<Pagamento, Long>, IPagamentoRepositoryCustom {

	

}
