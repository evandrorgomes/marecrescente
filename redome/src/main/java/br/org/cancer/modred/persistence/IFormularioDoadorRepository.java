package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.FormularioDoador;

/**
 * Repositorio para gravação de formulário Doador.
 * @author Filipe Paes
 *
 */
@Repository
public interface IFormularioDoadorRepository extends JpaRepository<FormularioDoador, Long>{

}
