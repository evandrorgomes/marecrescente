package br.org.cancer.modred.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.org.cancer.modred.model.domain.FasesMatch;
import br.org.cancer.modred.model.domain.TiposDoador;

/**
 * Interface para de métodos de banco para match customizadas.
 * 
 * @author bruno.sousa
 *
 */
@Repository
public interface IMatchRepositoryCustom {

	/**
	 * Método para buscar match por rmr.
	 * 
	 * @param rmr - id do paciente.
	 * @param situacao - fase atual do doador.
	 * @param tiposDoador - Lista de Tipos de Doador 
	 * @param status - status do match
	 * @param disponibilizado - se o doador foi disponibilizado para o paciente
	 * @return lista de match de um paciente.
	 */
	List<Object[]> listarMatchPorPacienteSituacaoTipoDoadorStatus(Long rmr, List<FasesMatch> situacoes, List<TiposDoador> tiposDoador, boolean status, boolean disponibilizado);

	/**
	 * Método que busca o match por id.
	 * 
	 * @param id Identificador do match.
	 * @return match de um doador com um paciente.
	 */
	Object[] obterMatchPorId(Long id);
	
	/**
	 * @param rmr - id do paciente.
	 * @param status - status do match
	 * @param listaIdDoador - lista de doadores
	 * @return lista de match de um paciente.
	 */
	List<Object[]> listarMatchsPorPacienteStatusIdDoador(Long rmr, boolean status, List<Long> listaIdDoador);
	
	/**
	 * Método para buscar match inativos por rmr, situacao e tipo do doador com solicitação para o paciente.
	 * 
	 * @param rmr - id do paciente.
	 * @param situacao - fase atual do doador.
	 * @param tiposDoador - Lista de Tipos de Doador 
	 * @return lista de match inativos de um paciente.
	 */
	List<Object[]> listarMatchInativosPorPacienteSituacaoTipoDoadorComSolicitacao(Long rmr, String situacao,
			List<TiposDoador> tiposDoador);
	
}
