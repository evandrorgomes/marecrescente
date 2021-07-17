package br.org.cancer.redome.auth.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.cancer.redome.auth.model.Usuario;

/**
 * Inteface que concentra e abstrai regras de pesquisa sobre o objeto usuario de acesso da plataforma redome. Especificamente este
 * repositório serve como uma abstração para pesquisa coleção de instâncias da entidade Usuario.
 * 
 * @author Bruno Sousa
 *
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Obtém o usuário de mesmo login, mas sem considerar o próprio usuário informado.
	 * 
	 * @param usuario usuario usuário a ser pesquisado por username.
	 * @return usuario usuario
	 */
	@Query("select u from Usuario u where upper(u.username) = upper(:#{#usuario.username}) and u.id <> :#{#usuario.id}")
	Usuario obterUsuarioPorUsername(@Param("usuario") Usuario usuario);
	
	/**
	 * Obtém o usuário a partir do username.
	 * 
	 * @param username login do usuário a ser pesquisado.
	 * @return
	 */
	@Query("select u from Usuario u where upper(u.username) = upper(:username)")
	Usuario obterUsuarioPorUsername(@Param("username") String username);
	
	//@Query("select u from Usuario u inner join u.centroTransplanteUsuarios c inner join u.perfis p where c.centroTransplante.id = :centro and p.id = :perfil ")
	//List<Usuario> listarUsuariosPorCentroTransplanteEPerfil(@Param("centro") Long idCentro, @Param("perfil") Long idPerfil);
	
	@Query("select u from Usuario u inner join u.perfis p where p.id = :perfil and u.ativo = true order by u.nome ")
	List<Usuario> listarUsuariosAtivosPorPerfil(@Param("perfil") Long idPerfil);
	
	
	//@Query("select u from Usuario u inner join u.perfis p where p.id = :perfil and u.transportadora is null order by u.nome ")
	//List<Usuario> listarUsuariosPorPerfilEQueNaoTemTransportadora(@Param("perfil") Long idPerfil);

	//@Query("select u from Usuario u inner join u.perfis p where p.id = :perfil and u.laboratorio is null order by u.nome ")
	//List<Usuario> listarUsuariosPorPerfilEQueNaoTemLaboratorio(@Param("perfil") Long idPerfil);
	
	/**
	 * Lista os usuários que atendam os filtros, caso este sejam informados.
	 * 
	 * @param page paginação do resultado.
	 * @param nome filtro por nome (opcional).
	 * @param email filtro por e-mail (opcional).
	 * @param login filtro por login (opcional).
	 * @return lista de usuários paginada.
	 */
	@Query(	"select usua from "
		+ 	"Usuario usua "
		+ 	"where (UPPER(usua.nome) like UPPER(:nome) or :nome is null) "
		+ 	"and (UPPER(usua.email) like UPPER(:email) or :email is null) "
		+ 	"and (UPPER(usua.username) like UPPER(:login) or :login is null) "
		+ 	"and usua.ativo = true "
		+ 	"order by usua.nome ")
	Page<Usuario> listarUsuariosAtivos(Pageable page, @Param("nome") String nome, @Param("email") String email, @Param("login") String login);

}
