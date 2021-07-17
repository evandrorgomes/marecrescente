package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.StatusDoador;

/**
 * Interface de persistencia de status de doador.
 * 
 * @author Fillipe Queiroz
 *
 */
public interface IStatusDoadorRepository extends JpaRepository<StatusDoador, Long> {

}
