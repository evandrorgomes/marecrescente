package br.org.cancer.redome.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.redome.auth.model.Perfil;

public interface IPerfilRepository extends JpaRepository<Perfil, Long> {
	
	

}
