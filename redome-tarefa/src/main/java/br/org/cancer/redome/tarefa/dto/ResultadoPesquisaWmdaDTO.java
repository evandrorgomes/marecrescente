package br.org.cancer.redome.tarefa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe para pesquisa de dados para WMDA.
 * @author ergomes
 *
 */
@JsonInclude(Include.NON_NULL)
public class ResultadoPesquisaWmdaDTO {
	
	private Long searchId;
	private String wmdaId;
	private String jsonWmda;
	
	private List<Requests> requests;
	private List<ResultadoDoadorWmdaDTO> donors;
	
	/**
	 * 
	 */
	public ResultadoPesquisaWmdaDTO() {
		super();
	}

	/**
	 * @param searchId
	 * @param requests
	 */
	public ResultadoPesquisaWmdaDTO(Long searchId, List<Requests> requests) {
		super();
		this.searchId = searchId;
		this.requests = requests;
	}

	/**
	 * @return the searchId
	 */
	public Long getSearchId() {
		return searchId;
	}

	/**
	 * @param searchId the searchId to set
	 */
	public void setSearchId(Long searchId) {
		this.searchId = searchId;
	}

	/**
	 * @return the requests
	 */
	public List<Requests> getRequests() {
		return requests;
	}

	/**
	 * @param requests the requests to set
	 */
	public void setRequests(List<Requests> requests) {
		this.requests = requests;
	}

	/**
	 * @return the wmdaId
	 */
	public String getWmdaId() {
		return wmdaId;
	}

	/**
	 * @param wmdaId the wmdaId to set
	 */
	public void setWmdaId(String wmdaId) {
		this.wmdaId = wmdaId;
	}

	/**
	 * @return the jsonWmda
	 */
	public String getJsonWmda() {
		return jsonWmda;
	}

	/**
	 * @param jsonWmda the jsonWmda to set
	 */
	public void setJsonWmda(String jsonWmda) {
		this.jsonWmda = jsonWmda;
	}
	
	/**
	 * @return the donors
	 */
	public List<ResultadoDoadorWmdaDTO> getDonors() {
		return donors;
	}

	/**
	 * @param donors the donors to set
	 */
	public void setDonors(List<ResultadoDoadorWmdaDTO> donors) {
		this.donors = donors;
	}


	public static class Requests {
		
		private Long searchResultsId;
		private Integer matchEngine;
		private String searchType;
		
		public Requests() {
			super();
		}

		/**
		 * @param searchResultsId
		 * @param matchEngine
		 * @param searchType
		 */
		public Requests(Long searchResultsId, Integer matchEngine, String searchType) {
			super();
			this.searchResultsId = searchResultsId;
			this.matchEngine = matchEngine;
			this.searchType = searchType;
		}

		/**
		 * @return the searchResultsId
		 */
		public Long getSearchResultsId() {
			return searchResultsId;
		}
		/**
		 * @param searchResultsId the searchResultsId to set
		 */
		public void setSearchResultsId(Long searchResultsId) {
			this.searchResultsId = searchResultsId;
		}
		/**
		 * @return the matchEngine
		 */
		public Integer getMatchEngine() {
			return matchEngine;
		}
		/**
		 * @param matchEngine the matchEngine to set
		 */
		public void setMatchEngine(Integer matchEngine) {
			this.matchEngine = matchEngine;
		}
		/**
		 * @return the searchType
		 */
		public String getSearchType() {
			return searchType;
		}
		/**
		 * @param searchType the searchType to set
		 */
		public void setSearchType(String searchType) {
			this.searchType = searchType;
		}
	}
	
	
}
