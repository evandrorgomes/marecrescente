package br.org.cancer.redome.tarefa.service;

import java.io.IOException;

import br.org.cancer.redome.tarefa.dto.PacienteWmdaDTO;
import br.org.cancer.redome.tarefa.dto.ResultadoPesquisaWmdaDTO;

/**
 * Interage com o sistema do WMDA através de serviços REST. 
 * @author ergomes
 *
 */
public interface IWmdaService {
	
	/**
	 * Envia os dados de hla dos pacientes liberados para busca
	 * para o WMDA.
	 * @param paciente
	 * @return retorno do servidor. 
	 */
	ResultadoPesquisaWmdaDTO criarPacienteWmda(PacienteWmdaDTO paciente) throws IOException;

	/**
	 * Envia os dados para atualização de hla dos pacientes liberados para busca
	 * para o WMDA.
	 * @param paciente
	 * @return retorno do servidor. 
	 */
	ResultadoPesquisaWmdaDTO atualizarPacienteWmda(PacienteWmdaDTO paciente) throws IOException;
	
	
	/**
	 * Envia o WmdaId do paciente liberados para busca SearchId do WMDA.
	 * 
	 * @param wmdaid
	 * @param tipoBusca
	 * @return retorno do servidor. 
	 */
	ResultadoPesquisaWmdaDTO buscarSearchIdPorWmdaId(String wmdaId, String tipoBusca);

	/**
	 * Envia o WmdaId do paciente liberados para busca searchResultsId do WMDA.
	 * 
	 * @param wmdaid
	 * @return retorno do servidor. 
	 */
	ResultadoPesquisaWmdaDTO buscarSearchResultsIdPorWmdaId(String wmdaId);

	/**
	 * Obtem doadores compativeis e liberados para busca através do searchResultsId do WMDA.
	 * 
	 * @param String wmdaid
	 * @param Long searchResultId
	 * @return ResultadoPesquisaWmdaDTO - Json com as informações dos doadores. 
	 */
	ResultadoPesquisaWmdaDTO buscarSearchResultsDoadores(String wmdaId, Long searchResultId) throws IOException;

	/**
	 * Convertem doadores compativeis e liberados para busca através do searchResultsId do WMDA.
	 * 
	 * @param String jsonWmda
	 * @return ResultadoPesquisaWmdaDTO - Json com as informações dos doadores. 
	 */
	ResultadoPesquisaWmdaDTO converterMatchsDoadoresWmdaParaResultadoPesquisa(String jsonWmda) throws IOException;
}
