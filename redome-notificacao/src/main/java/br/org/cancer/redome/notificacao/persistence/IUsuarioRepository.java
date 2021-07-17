package br.org.cancer.redome.notificacao.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.notificacao.model.Usuario;

@Transactional(readOnly = true)
@Repository()
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);
	

}
