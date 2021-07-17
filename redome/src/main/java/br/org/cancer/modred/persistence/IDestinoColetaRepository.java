package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.DestinoColeta;

/**
 * Interfaces de métodos de persistencia de DestinoColeta.
 * @author Filipe Paes
 *
 */
public interface IDestinoColetaRepository  extends JpaRepository<DestinoColeta, Long>{

}
