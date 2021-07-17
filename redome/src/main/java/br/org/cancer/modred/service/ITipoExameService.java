package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.TipoExame;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para métodos de negócio do tipo de exame.
 * 
 * @author bruno.sousa
 *
 */
public interface ITipoExameService extends IService<TipoExame, Long> {

	/**
	 * Lista os lócus associados ao tipo de exame.
	 * 
	 * @param tipoExameId ID do tipo de exame.
	 * @return lista de lócus associados.
	 */
	List<Locus> listarLocusAssociados(Long tipoExameId);

	/**
	 * Lista os tipos de exame para pedido de exame fase 2 nacional.
	 * 
	 * @return Lista de tipos de exame.
	 */
	List<TipoExame> listarTipoExameFase2Nacional();

}