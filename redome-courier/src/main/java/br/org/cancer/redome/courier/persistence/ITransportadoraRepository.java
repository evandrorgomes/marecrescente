package br.org.cancer.redome.courier.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.Transportadora;

/**
 * Interface de métodos de persistencia de transporte.
 * @author Filipe Paes
 *
 */
@Repository
public interface ITransportadoraRepository  extends IRepository<Transportadora, Long>{

	
	/**
	 * Lista as transportadoras que atendam os filtros, caso este sejam informados.
	 * 
	 * @param page paginação do resultado.
	 * @param nome filtro por nome (opcional).
	 * @return lista de usuários paginada.
	 */
	@Query(	"select t from "
		+ 	"Transportadora t "
		+ 	"where (UPPER(t.nome) like UPPER(:nome) or :nome is null) "
		+ 	"and t.ativo = true "
		+ 	"order by t.nome ")
	Page<Transportadora> listarTransportadoraAtivas(Pageable page, @Param("nome") String nome);
}
