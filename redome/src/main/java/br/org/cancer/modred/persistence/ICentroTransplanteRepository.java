package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre o objeto centro de transplante plataforma redome.
 * 
 * Especificamente este repositório serve como uma abstração para pesquisa coleção de instâncias da entidade CentroTransplante.
 * 
 * @author Pizão
 *
 */
@Repository
public interface ICentroTransplanteRepository extends IRepository<CentroTransplante, Long>,
		ICentroTransplanteRepositoryCustom {

	/**
	 * Método para listar os centros de transplante disponíveis na base.
	 *
	 * Return: List<CentroTransplante>
	 */
	List<CentroTransplante> findAll();

	@CacheEvict(value = "dominio", key = "listarCentroTransplantes", beforeInvocation = true)
	@Modifying
	@Query(" update CentroTransplante c SET c.entityStatus = :statusId WHERE c.id=:centroTransplanteId")
	int atualizarStatusEntidade(@Param(value = "centroTransplanteId") Long centroTransplanteId,
			@Param(value = "statusId") Long statusId);

	@CacheEvict(value = "dominio", key = "listarCentroTransplantes", beforeInvocation = true)
	@Modifying
	@Query(" update CentroTransplante c SET c.nome = :nome, c.cnpj=:cnpj, c.cnes=:cnes WHERE c.id=:centroTranplanteId")
	int atualizarCentroTransplante(@Param(value = "centroTranplanteId") Long centroTranplanteId,
			@Param(value = "nome") String nome,
			@Param(value = "cnpj") String cnpj,
			@Param(value = "cnes") String cnes);
	
	
	/**
	 * Método para obter todos os centros do usuário.
	 * @param usuario - usuario ao qual deseja saber os centros.
	 * @return lista de centros.
	 */
	List<CentroTransplante> findByCentroTransplanteUsuariosUsuario(Usuario usuario);
	
}
