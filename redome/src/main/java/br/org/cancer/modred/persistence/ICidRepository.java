package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Cid;

/**
 * Interface filha de JpaRepository para mapear a entidade Cid.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface ICidRepository extends JpaRepository<Cid, Long> {

	/**
	 * Método para fazer consulta de CID's por codigo ou descricao.
	 * 
	 * @param String parametro para pesquisa
	 * @return List<Cid> listagem de cids
	 */
	@Query("select c from Cid c join c.internacionalizacao i where "+
	                                        "upper(c.codigo) like upper(:param) or "+
	                                        "(upper(i.descricao) like upper(:param) and "+
	                                        "i.id.idioma.sigla = :idioma)")
	List<Cid> findByCodigoOrDescricao(@Param("param") String textoPesquisa, @Param("idioma") String idioma);


	/**
	 * Busca Cid por código.
	 * 
	 * @param codigo da cid
	 * @return cid com o código informado
	 */
	Cid findByCodigo(String codigo);
}
