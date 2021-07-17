package br.org.cancer.redome.workup.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.workup.model.security.Usuario;

@Transactional(readOnly = true)
@Repository()
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);
	
	/**
	 * Obtém o usuário a partir do username.
	 * 
	 * @param username login do usuário a ser pesquisado.
	 * @return
	 */
	@Query("select u from Usuario u where upper(u.username) = upper(:username)")
	Usuario obterUsuarioPorUsername(@Param("username") String username);

}
