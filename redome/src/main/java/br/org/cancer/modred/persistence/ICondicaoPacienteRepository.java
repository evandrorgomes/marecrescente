package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.CondicaoPaciente;

/**
 * Interface para persistencia de CondicaoPaciente.
 * @author Filipe Paes
 *
 */
@Repository
public interface ICondicaoPacienteRepository   extends JpaRepository<CondicaoPaciente, Long>{
	
	/**
	 * Método para obter lista de Condições do Paciente.
	 *
	 * Return: List<CondicaoPaciente>
	 */
	@Cacheable(value = "dominio", key = "#root.methodName")
	List<CondicaoPaciente> findByOrderByDescricaoAsc();
	
}
