package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.MotivoDescarte;

/**
 * Interface para os motivos do descarte.
 * 
 * @author Diogo Paraíso
 *
 */
public interface IMotivoDescarteService {

	/**
	 * Método para retornar os motivos de descate.
	 * 
	 * @return motivos de descarte
	 */
	List<MotivoDescarte> obterMotivosDescarte();

	/**
	 * Método buscar motivo de descarte a partir do ID.
	 * 
	 * @param motivoDescarteId ID do motivo de descarte a ser buscado.
	 * @return o motivo de descarte associado.
	 */
	MotivoDescarte obterMotivoDescarte(Long motivoDescarteId);
}
