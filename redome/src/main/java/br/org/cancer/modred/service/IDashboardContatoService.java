package br.org.cancer.modred.service;

import java.time.LocalDate;

import br.org.cancer.modred.vo.dashboard.ContatoVo;

/**
 * Interface de serviço para o dashboard de contato.  
 * 
 * @author brunosousa
 *
 */
public interface IDashboardContatoService {
	
	/**
	 * Método que obtem os totais de enriquecimento e contato agrupadas por fase e tipo em um determinado periódo.
	 * 
	 * @param dataInicio - Data inicial 
	 * @param dataFinal - Data final 
	 * @return Quantidade de enriquecimento e contato por periodo
	 */
	ContatoVo obterTotaisPorPeriodo(LocalDate dataInicio, LocalDate dataFinal);

}
