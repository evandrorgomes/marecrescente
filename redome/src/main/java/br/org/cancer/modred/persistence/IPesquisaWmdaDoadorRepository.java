package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.PesquisaWmdaDoador;

/**
 * Interface filha de JpaRepository para mapear a entidade Pesquisa Wmda Doador.
 * 
 * @author ergomes
 *
 */
@Repository
public interface IPesquisaWmdaDoadorRepository extends JpaRepository<PesquisaWmdaDoador, Long> {
	
	List<PesquisaWmdaDoador> findByPesquisaWmda(Long pesquisaWmdaId);
}
