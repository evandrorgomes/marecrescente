package br.org.cancer.redome.tarefa.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe para pesquisa de dados para WMDA.
 * @author ergomes
 *
 */
public class WmdaSearchDto {
	
	private Boolean repeatable;
	private List<WmdaRequests> requests;
	
	/**
	 * 
	 */
	public WmdaSearchDto() {
		super();
	}
	
	/**
	 * @return the repeatable
	 */
	public Boolean getRepeatable() {
		return repeatable;
	}
	/**
	 * @param repeatable the repeatable to set
	 */
	public void setRepeatable(Boolean repeatable) {
		this.repeatable = repeatable;
	}
	/**
	 * @return the requests
	 */
	public List<WmdaRequests> getRequests() {
		return requests;
	}
	/**
	 * @param requests the requests to set
	 */
	public void setRequests(List<WmdaRequests> requests) {
		this.requests = requests;
	}
	
	public class WmdaRequests {
		
		private Long matchEngine;
		private String searchType;
		
		private WmdaSearchParameters searchParameters;
		
		/**
		 * 
		 */
		public WmdaRequests() {
			super();
		}
		
		/**
		 * @param matchEngine
		 * @param searchType
		 * @param searchParameters
		 */
		public WmdaRequests(Long matchEngine, String searchType) {
			super();
			this.matchEngine = matchEngine;
			this.searchType = searchType;
			this.searchParameters = new WmdaSearchParameters();
		}
		/**
		 * @return the matchEngine
		 */
		public Long getMatchEngine() {
			return matchEngine;
		}
		/**
		 * @param matchEngine the matchEngine to set
		 */
		public void setMatchEngine(Long matchEngine) {
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
		
		/**
		 * @return the searchParameters
		 */
		public WmdaSearchParameters getSearchParameters() {
			return searchParameters;
		}
		/**
		 * @param searchParameters the searchParameters to set
		 */
		public void setSearchParameters(WmdaSearchParameters searchParameters) {
			this.searchParameters = searchParameters;
		}


		public class WmdaSearchParameters{
			
			@JsonInclude(Include.ALWAYS)
			private String hlaDifferences;
			
			/**
			 * @param hlaDifferences
			 */
			public WmdaSearchParameters() {
				super();
				this.hlaDifferences = null;
			}

			/**
			 * @return the hlaDifferences
			 */
			public String getHlaDifferences() {
				return hlaDifferences;
			}

			/**
			 * @param hlaDifferences the hlaDifferences to set
			 */
			public void setHlaDifferences(String hlaDifferences) {
				this.hlaDifferences = hlaDifferences;
			}
			
		}
		
	} 


}
