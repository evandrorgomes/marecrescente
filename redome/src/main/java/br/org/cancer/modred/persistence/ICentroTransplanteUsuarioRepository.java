package br.org.cancer.modred.persistence;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CentroTransplanteUsuario;

/**
 * Interface de persistencia de CentroTransplanteUsuario.
 * @author Filipe Paes
 *
 */
@Repository
public interface ICentroTransplanteUsuarioRepository  extends IRepository<CentroTransplanteUsuario, Long>{

}
