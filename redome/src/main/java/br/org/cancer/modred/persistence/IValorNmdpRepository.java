package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.ValorNmdp;

/**
 * Repositório para acesso ao banco da entidade ValorNmdp.
 * 
 * @author Pizão
 *
 */
@Repository
public interface IValorNmdpRepository extends JpaRepository<ValorNmdp, String> {
	
	ValorNmdp findByCodigo(String codigo);

}