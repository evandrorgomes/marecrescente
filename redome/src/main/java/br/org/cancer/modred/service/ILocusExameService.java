package br.org.cancer.modred.service;

import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.model.LocusExamePk;
import br.org.cancer.modred.model.domain.MotivoDivergenciaLocusExame;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de negocios de locus exame.
 * @author Filipe Paes
 *
 */
public interface ILocusExameService extends IService<LocusExame, LocusExamePk>{

	/**
	 * Método para inativar LocusExame de um determinado exame.
	 * 
	 * @param idBusca - Identificador da Busca
	 * @param examsId -Identificador de exame
	 * @param locus - Locus a ser inativado
	 * @param motivo - Motivo da divergencia para inativar
	 */
	void inativar(Long idBusca, Long exameId, String locus, MotivoDivergenciaLocusExame motivo);

	/**
	 * Marca o locus exame como selecionado.
	 * 
	 * @param idBusca - Identificador da Busca
	 * @param idExameSelecionado - Identificador do exame selecionado.
	 * @param locus - Locus selecionado.
	 * @param motivo - Motivo da divergencia para inativar
	 */
	void marcarSelecionado(Long idBusca, Long idExameSelecionado, String locus, MotivoDivergenciaLocusExame motivo);

}
