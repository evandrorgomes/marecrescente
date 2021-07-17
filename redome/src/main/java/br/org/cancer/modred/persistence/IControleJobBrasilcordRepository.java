package br.org.cancer.modred.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.ControleJobBrasilcord;

/**
 * Interface para métodos de persistencia de Controle de Job Brasilcord.
 * @author Filipe Paes
 *
 */
public interface IControleJobBrasilcordRepository extends JpaRepository<ControleJobBrasilcord, Long> {
	
	
}
