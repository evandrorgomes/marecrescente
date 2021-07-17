package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Exame;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para o serviço que trata do acesso a entidade Exame e relacionadas.
 * 
 * @author Pizão
 *
 * @param <T> classe que estende de Exame
 * 
 */
public interface IExameService<T extends Exame> extends IService<T, Long> {

	/**
	 * Verificar se o HLA informado é válido.
	 * 
	 * @param codigo do locus a ser validado.
	 * @param valor a ser validado.
	 * esta flag deverá passar true senão false.
	 */
	List<CampoMensagem> validarHla(String codigoLocusId, String valor);

	/**
	 * Verificar se o HLA informado é válido com a possibilidade de antígeno (Ex.: A*01 será válido)
	 * 
	 * @param codigo do locus a ser validado.
	 * @param valor a ser validado.
	 */
	List<CampoMensagem> validarHlaComAntigeno(String codigoLocusId, String valor);
	
	/**
	 * Método para validar o HLA informado por uma lista.
	 * 
	 * @param locusExames
	 * @param campos
	 */
	void validarHlaComListaLocusExames(List<LocusExame> locusExames, List<CampoMensagem> campos);

	/**
	 * Método que retorna um exame.
	 * 
	 * @param id
	 */
	T obterExame(Long id);

	/**
	 * Valida o exame quanto aos valores de locus possíveis e os seus respectivos valores alélicos.
	 * Caso ocorra alguma inconsistência, será retornada uma lista com mensagens explicando cada
	 * inconsistência ocorrida.
	 * 
	 * @param exame exame a ser validado.
	 * @return campos de erro contendo o resultado da validação do exame (mensagens a serem exibidas).
	 */
	List<CampoMensagem> validarExame(T exame);
	
	
	
	/**
	 * Valida a lista de exames quanto aos valores de locus possíveis e os seus respectivos valores alélicos.
	 * Caso ocorra alguma inconsistência, será retornada uma lista com mensagens explicando cada
	 * inconsistência ocorrida
	 * 
	 * @param exames lista de exames a serem validados.
	 * @return campos de erro contendo o resultado da validação do exame (mensagens a serem exibidas). 
	 */
	List<CampoMensagem> validarExames(List<T> exames);
	
}
