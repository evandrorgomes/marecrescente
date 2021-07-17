package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Diagnostico;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para disponibilizar métodos para classe de negócio da entidade Diagnostico.
 * @author Dev Team
 *
 */
public interface IDiagnosticoService extends IService<Diagnostico, Long> {
	
	/**
     * Validar, contra a base, se o Cid informado é válido.
     * @param diagnostico Diagnostico ao qual o Cid está associado.
     * @param campos Lista com os erros encontrados nesta validação.
     */
	void validar(Diagnostico diagnostico, List<CampoMensagem> campos);

	/**
	 * Altera o diagnostico de um paciente somente se o mesmo for diferente do atual.
	 * 
	 * @param rmr - Identificação do diagnostico
	 * @param diagnostico - diagnostico a ser alterado
	 */
	void alterarDiagnostico(Long rmr, Diagnostico diagnostico);
}
