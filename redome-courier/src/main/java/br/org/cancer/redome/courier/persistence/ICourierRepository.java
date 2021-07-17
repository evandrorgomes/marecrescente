package br.org.cancer.redome.courier.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.courier.model.Courier;

/**
 * Interface com métodos de persistencia de Courier.
 * 
 * @author Fillipe Queiroz
 *
 */
@Repository
public interface ICourierRepository extends IRepository<Courier, Long>{

	/**
	 * Lista todos os Courier's associados a transportadora logada.
	 * @param idTransportadora - identificação da transportadora.
	 * @return List<Courier> lista de courier's
	 */
	List<Courier> findByTransportadoraId(Long idTransportadora);
	
	
	/**
	 * Lista os Couries que atendam os filtros, caso este sejam informados.
	 * 
	 * @param page paginação do resultado.
	 * @param idTransportadora id da transportadora ao qual pertence o Courier.
	 * @param nome filtro por nome (opcional).
	 * @param cpf filtro por cpf (opcional).
	 * @return lista de usuários paginada.
	 */
	@Query(	"select c from "
		+ " Courier c"
		+ "	join c.transportadora t "
		+ " where t.id = :transportadora "
		+ "	and (UPPER(c.nome) like UPPER(:nome) or :nome is null) "
		+ "	and (c.cpf =:cpf or :cpf is null)  "
		+ " and (c.id <> :id or :id is null )"
		+ " and c.ativo = true "
		+ " order by c.nome ")
	Page<Courier> listarCouriesAtivosEPor(Pageable page, @Param("transportadora") Long idTransportadora, @Param("nome") String nome, @Param("cpf") String cpf, @Param("id") Long id);
}
