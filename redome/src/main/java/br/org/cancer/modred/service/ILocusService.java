package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.Locus;
import br.org.cancer.modred.model.LocusExame;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Interface para métodos de negócio de Locus.
 * 
 * @author Filipe Paes
 *
 */
public interface ILocusService extends IService<Locus, String>{
	/**
	 * Método para obter lista de locus.
	 * 
	 * @return List<Locus> listagem de locus
	 */
	List<Locus> listarLocus();

	/**
	 * Verifica se os locus informados são válidos.
	 * 
	 * @param listaLocusParaComparar
	 *            lista de locus exame a ser verificados.
	 * @param campos
	 *            campos de erro, que serão preenchidos de acordo com a
	 *            validação.
	 */
	void validarSeLocusSaoValidosPorCodigo(List<LocusExame> listaLocusParaComparar, List<CampoMensagem> campos);

	/**
	 * Método para recuperar um LocusExame através do id do exame o grupo
	 * alélico.
	 * 
	 * @param exameId
	 *            id do exame
	 * @param grupoAlelico
	 *            grupo alelico
	 * @param primeiroAlelo
	 *            primeiro alelo
	 * @param segundoAlelo
	 *            segundo alelo
	 * @return locus exame
	 */
	LocusExame obterLocusExamePorExameIdGrupoAlelicoAlelos(Long exameId, String grupoAlelico, String primeiroAlelo,
			String segundoAlelo);

	/**
	 * Lista os locus exames associados ao ID do exame informado.
	 * 
	 * @param exameId
	 *            ID do exame que será pesquisado.
	 * @return lista de locus exames com os valores de primeiro e segundo
	 *         alelos.
	 */
	List<LocusExame> listarLocusExames(Long exameId);
	
	/**
	 * Método para remover uma lista de LocusExame. 
	 * 
	 * @param locusExames
	 */
	void delete(List<LocusExame> locusExames); 

}
