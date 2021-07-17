package br.org.cancer.modred.persistence;

import java.time.LocalDate;
import java.util.List;

import br.org.cancer.modred.vo.dashboard.ContatoVo;
import br.org.cancer.modred.vo.dashboard.DetalheContatoVo;

/**
 * Interface de consulta para o dashboard de contato.
 * 
 * @author brunosousa
 *
 */
public interface IDashboardContatoRepository {

	/**
	 * Método que obtem os totais de enriquecimento e contato agrupadas por fase e tipo em um determinado periódo.
	 * 
	 * @param dataInicio - Data inicial 
	 * @param dataFinal - Data final 
	 * @return Quantidade de enriquecimento e contato por periodo
	 */
	ContatoVo obterTotaisContato(LocalDate dataInicio, LocalDate dataFinal);
	
	/**
	 * Método para listar os detalhes de enriquecimento e contato por fase em um determinado período.
	 * 
	 * @param dataInicio
	 * @param dataFinal
	 * @return
	 */
	List<DetalheContatoVo> listaDetalhesContatoPorPeriodo(LocalDate dataInicio, LocalDate dataFinal);
	
}
