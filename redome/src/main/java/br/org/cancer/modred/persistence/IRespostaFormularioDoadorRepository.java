package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.RespostaFormularioDoador;

/**
 * Interface filha de JpaRepository para mapear a entidade Cid.
 * 
 * @author Filipe Paes
 *
 */
@Repository
public interface IRespostaFormularioDoadorRepository extends JpaRepository<RespostaFormularioDoador, Long>, IRespostaFormularioDoadorRepositoryCustom {

	
}
