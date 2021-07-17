package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PesquisaWmda;

/**
 * Interface filha de JpaRepository para mapear a entidade Pesquisa Wmda.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPesquisaWmdaRepository extends JpaRepository<PesquisaWmda, Long> {
	
	List<PesquisaWmda> findByBuscaAndStatusIn(Long buscaId, List<Integer> statusId);
	
	List<PesquisaWmda> findByBuscaAndStatus(Long buscaId, Integer statusId);
}
