package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.MotivoCancelamentoWorkup;

/**
 * Interface de métodos de negócio de motivos de cancelamento de workup.
 * @author Fillipe Queiroz
 *
 */
public interface IMotivoCancelamentoWorkupService {

	/**
	 * Método para listar motivos de cancelamento de workup.
	 * 
	 * @return lista dos motivos.
	 */
	List<MotivoCancelamentoWorkup> listarMotivosCancelamentoWorkupSelecionaveis();
}
