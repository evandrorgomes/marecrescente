package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.controller.dto.GenotipoDTO;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.GenotipoDoador;
import br.org.cancer.modred.model.ValorGenotipoDoador;

/**
 * Interface para disponibilizar funcionalidades para a classe de serviço da entidade de ValorGenótipo.
 * 
 * @author Pizão
 *
 * @param <T> Qualquer classe que extenda de exame.
 */
public interface IValorGenotipoDoadorService<T extends Exame> {

	/**
	 * Gera os valores de genótipo para o doador informado, a partir dos exames já conferidos do mesmo.
	 * 
	 * @param idDoador identificador do doador.
	 * @param exames - exames do doador.
	 * @return lista dos valores genótipos já aprovados nos exames.
	 */
	List<ValorGenotipoDoador> gerarGenotipo(Long idDoador);

	/**
	 * Retorna a lista de genotipo.
	 * 
	 * @param valoresGenotipo lista com valores de genotipo
	 * @return lista de dto preenchido com o genotipo
	 */
	List<GenotipoDTO> obterGenotipoDtoPorValorGenotipo(List<ValorGenotipoDoador> valoresGenotipo);

	/**
	 * Deleta os valores de um determinado genótipo (agrupado de valores).
	 * 
	 * @param idGenotipo ID do genótipo, entidade que agrupa os valores.
	 */
	void deletarValoresPorGenotipo(Long idGenotipo);
	
	/**
	 * Salva a lista de valores genótipo para o doador informado.
	 * 
	 * @param valoresGenotipos valores a serem salvos.
	 * @param genotipo agrupador associado.
	 * @param doador doador que possui os valores informados.
	 * @return lista de valores salvos.
	 */
	List<ValorGenotipoDoador> salvar(List<ValorGenotipoDoador> valoresGenotipos, GenotipoDoador genotipo, Doador doador);
	
	/**
	 * Obtém lista de genótipo por doador.
	 * @param idDoador identificação do doador.
	 * @return lista de genotipo.
	 */
	List<GenotipoDTO> obterGenotipoDoadorDto(Long idDoador);

	List<ValorGenotipoDoador> listarPorGenotipoDoadorId(Long idGenotipoDoador);

	
	/**
	 * Verifica se há valores com divergência para geração de match de doadores.
	 * @param id do doador a ser verificado.
	 * @return verdadeiro caso haja divergencia.
	 */
	Boolean existemValoresComDivergencia(Long idDoador);

}
