package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.MotivoStatusDoador;

/**
 * Interface de métodos de negócio de motivos de status de doador.
 * @author Filipe Paes
 *
 */
public interface IMotivoStatusDoadorService {

	/**
	 * Método para listar motivos de acordo com o status do doador.
	 * 
	 * @param siglaRecurso - sigla do recurso associado ao status.
	 * @return lista dos motivos associados ao recurso informado.
	 */
	List<MotivoStatusDoador> listarMotivosPorRecurso(String siglaRecurso);
	
	/**
	 * Método para obter um motivo de status por id.
	 * @param idMotivo - identificador do motivo
	 * @return MotivoStatusDoador entidade motivo
	 */
	MotivoStatusDoador obterMotivoPorId(Long idMotivo);
}
