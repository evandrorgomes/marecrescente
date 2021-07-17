package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.Formulario;

/**
 * Interface filha de JpaRepository para mapear a entidade Cid.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IFormularioRepository extends JpaRepository<Formulario, Long> {

	Formulario findByTipoFormularioIdAndAtivo(Long idTipoformulario, Boolean ativo);
	
}
