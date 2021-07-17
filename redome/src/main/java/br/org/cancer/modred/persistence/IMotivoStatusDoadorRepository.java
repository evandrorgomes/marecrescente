package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cancer.modred.model.MotivoStatusDoador;

/**
 * Interface de persistencia de motivo status de doador.
 * @author Filipe Paes
 *
 */
public interface IMotivoStatusDoadorRepository extends JpaRepository<MotivoStatusDoador, Long> {

	
	/**
	 * Retorna uma listagem de motivos de status de acordo com o status de doador.
	 * 
	 * @param siglaRecurso sigla do recurso que será utilizado para filtrar os status associados.
	 * @return listagem de motivo status de doador.
	 */
	List<MotivoStatusDoador> findByRecursosSigla(String siglaRecurso);
}
