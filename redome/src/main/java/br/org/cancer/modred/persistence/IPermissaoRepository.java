package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.security.Permissao;
import br.org.cancer.modred.model.security.PermissaoId;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Repositório da entidade Permissao.
 * 
 * @author Cintia Oliveira
 *
 */
@Repository
public interface IPermissaoRepository extends IRepository<Permissao, PermissaoId> {

	/**
	 * Método para obter o usuário pelo username.
	 * 
	 * @param usuario usuario
	 * @return usuario usuario
	 */
	@Query("from Permissao p join fetch p.id.recurso join fetch p.id.perfil where :param member of p.id.perfil.usuarios")
	@Cacheable(	value = "autorizacao", key = " #root.args[0].username.toUpperCase()",
				unless = "#result == null || (#result != null && #result.isEmpty())")
	List<Permissao> findAllByUsuario(@Param("param") Usuario usuario);
	
}
