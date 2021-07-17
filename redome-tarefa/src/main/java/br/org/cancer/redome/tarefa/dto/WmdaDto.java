package br.org.cancer.redome.tarefa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe para envio de dados para WMDA.
 * @author Filipe Paes
 *
 */
@JsonInclude(Include.NON_NULL)
public class WmdaDto {
	
	private String pId;
	private WmdaHla hla;
	private WmdaIdm idm;
	private String birthDate;
	private WmdaDiagnosis diagnosis;
	private String disPha;
	private String ethn;
	private String pool;
	private String grafId;
	private String abo;
	private String rhesus;
	private Integer weight;
	private String sex;
	private Boolean  legalTerms;
	private Boolean urgent;
	private String comment;
	
	/**
	 * 
	 */
	public WmdaDto() {
		super();
	}

	/**
	 * @return the pId
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId the pId to set
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return the hla
	 */
	public WmdaHla getHla() {
		return hla;
	}
	/**
	 * @param hla the hla to set
	 */
	public void setHla(WmdaHla hla) {
		this.hla = hla;
	}
	/**
	 * @return the idm
	 */
	public WmdaIdm getIdm() {
		return idm;
	}
	/**
	 * @param idm the idm to set
	 */
	public void setIdm(WmdaIdm idm) {
		this.idm = idm;
	}
	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return birthDate;
	}
	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * @return the diagnosis
	 */
	public WmdaDiagnosis getDiagnosis() {
		return diagnosis;
	}
	/**
	 * @param diagnosis the diagnosis to set
	 */
	public void setDiagnosis(WmdaDiagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}
	/**
	 * @return the disPha
	 */
	public String getDisPha() {
		return disPha;
	}
	/**
	 * @param disPha the disPha to set
	 */
	public void setDisPha(String disPha) {
		this.disPha = disPha;
	}
	/**
	 * @return the ethn
	 */
	public String getEthn() {
		return ethn;
	}
	/**
	 * @param ethn the ethn to set
	 */
	public void setEthn(String ethn) {
		this.ethn = ethn;
	}
	/**
	 * @return the pool
	 */
	public String getPool() {
		return pool;
	}
	/**
	 * @param pool the pool to set
	 */
	public void setPool(String pool) {
		this.pool = pool;
	}
	/**
	 * @return the grafId
	 */
	public String getGrafId() {
		return grafId;
	}
	/**
	 * @param grafId the grafId to set
	 */
	public void setGrafId(String grafId) {
		this.grafId = grafId;
	}
	/**
	 * @return the abo
	 */
	public String getAbo() {
		return abo;
	}
	/**
	 * @param abo the abo to set
	 */
	public void setAbo(String abo) {
		this.abo = abo;
	}
	/**
	 * @return the rhesus
	 */
	public String getRhesus() {
		return rhesus;
	}
	/**
	 * @param rhesus the rhesus to set
	 */
	public void setRhesus(String rhesus) {
		this.rhesus = rhesus;
	}
	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
	/**
	 * @return the legalTerms
	 */
	public Boolean getLegalTerms() {
		return legalTerms;
	}
	/**
	 * @param legalTerms the legalTerms to set
	 */
	public void setLegalTerms(Boolean legalTerms) {
		this.legalTerms = legalTerms;
	}
	/**
	 * @return the urgent
	 */
	public Boolean getUrgent() {
		return urgent;
	}
	/**
	 * @param urgent the urgent to set
	 */
	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public class WmdaDiagnosis{
		private String diag;
		private String diagText;
		private String diagDate;
		
		/**
		 * @return the diag
		 */
		public String getDiag() {
			return diag;
		}
		/**
		 * @param diag the diag to set
		 */
		public void setDiag(String diag) {
			this.diag = diag;
		}
		/**
		 * @return the diagText
		 */
		public String getDiagText() {
			return diagText;
		}
		/**
		 * @param diagText the diagText to set
		 */
		public void setDiagText(String diagText) {
			this.diagText = diagText;
		}
		/**
		 * @return the diagDate
		 */
		public String getDiagDate() {
			return diagDate;
		}
		/**
		 * @param diagDate the diagDate to set
		 */
		public void setDiagDate(String diagDate) {
			this.diagDate = diagDate;
		}
		
		
	}
	public class WmdaIdm {
		private String cmvStatus;

		/**
		 * @return the cmvStatus
		 */
		public String getCmvStatus() {
			return cmvStatus;
		}

		/**
		 * @param cmvStatus the cmvStatus to set
		 */
		public void setCmvStatus(String cmvStatus) {
			this.cmvStatus = cmvStatus;
		}
		
		
	}
	public class WmdaHla{
		private WmdaFieldsDna a;
		private WmdaFieldsDna b;
		private WmdaFieldsDna c;
		private WmdaFieldsDna drb1;
		private WmdaFieldsDna dqb1;
		private WmdaFieldsDna dpb1;
		private WmdaFieldsDna drb3;
		private WmdaFieldsDna drb4;
		private WmdaFieldsDna drb5;
		private WmdaFieldsDna dpa1;
		private WmdaFieldsDna dqa1;
		/**
		 * @return the a
		 */
		public WmdaFieldsDna getA() {
			return a;
		}
		/**
		 * @param a the a to set
		 */
		public void setA(WmdaFieldsDna a) {
			this.a = a;
		}
		/**
		 * @return the b
		 */
		public WmdaFieldsDna getB() {
			return b;
		}
		/**
		 * @param b the b to set
		 */
		public void setB(WmdaFieldsDna b) {
			this.b = b;
		}
		/**
		 * @return the c
		 */
		public WmdaFieldsDna getC() {
			return c;
		}
		/**
		 * @param c the c to set
		 */
		public void setC(WmdaFieldsDna c) {
			this.c = c;
		}
		/**
		 * @return the drb1
		 */
		public WmdaFieldsDna getDrb1() {
			return drb1;
		}
		/**
		 * @param drb1 the drb1 to set
		 */
		public void setDrb1(WmdaFieldsDna drb1) {
			this.drb1 = drb1;
		}
		/**
		 * @return the dqb1
		 */
		public WmdaFieldsDna getDqb1() {
			return dqb1;
		}
		/**
		 * @param dqb1 the dqb1 to set
		 */
		public void setDqb1(WmdaFieldsDna dqb1) {
			this.dqb1 = dqb1;
		}
		/**
		 * @return the dpb1
		 */
		public WmdaFieldsDna getDpb1() {
			return dpb1;
		}
		/**
		 * @param dpb1 the dpb1 to set
		 */
		public void setDpb1(WmdaFieldsDna dpb1) {
			this.dpb1 = dpb1;
		}
		/**
		 * @return the drb3
		 */
		public WmdaFieldsDna getDrb3() {
			return drb3;
		}
		/**
		 * @param drb3 the drb3 to set
		 */
		public void setDrb3(WmdaFieldsDna drb3) {
			this.drb3 = drb3;
		}
		/**
		 * @return the drb4
		 */
		public WmdaFieldsDna getDrb4() {
			return drb4;
		}
		/**
		 * @param drb4 the drb4 to set
		 */
		public void setDrb4(WmdaFieldsDna drb4) {
			this.drb4 = drb4;
		}
		/**
		 * @return the drb5
		 */
		public WmdaFieldsDna getDrb5() {
			return drb5;
		}
		/**
		 * @param drb5 the drb5 to set
		 */
		public void setDrb5(WmdaFieldsDna drb5) {
			this.drb5 = drb5;
		}
		/**
		 * @return the dpa1
		 */
		public WmdaFieldsDna getDpa1() {
			return dpa1;
		}
		/**
		 * @param dpa1 the dpa1 to set
		 */
		public void setDpa1(WmdaFieldsDna dpa1) {
			this.dpa1 = dpa1;
		}
		/**
		 * @return the dqa1
		 */
		public WmdaFieldsDna getDqa1() {
			return dqa1;
		}
		/**
		 * @param dqa1 the dqa1 to set
		 */
		public void setDqa1(WmdaFieldsDna dqa1) {
			this.dqa1 = dqa1;
		}
	}
	
	public class WmdaDna{
		private String field1;
		private String field2;
		/**
		 * @return the field1
		 */
		public String getField1() {
			return field1;
		}
		/**
		 * @param field1 the field1 to set
		 */
		public void setField1(String field1) {
			this.field1 = field1;
		}
		/**
		 * @return the field2
		 */
		public String getField2() {
			return field2;
		}
		/**
		 * @param field2 the field2 to set
		 */
		public void setField2(String field2) {
			this.field2 = field2;
		}
		
		
	}
	
	public class WmdaFieldsDna{
		private WmdaDna dna;
		
		

		public WmdaFieldsDna(WmdaDna dna) {
			super();
			this.dna = dna;
		}

		/**
		 * @return the dna
		 */
		public WmdaDna getDna() {
			return dna;
		}

		/**
		 * @param dna the dna to set
		 */
		public void setDna(WmdaDna dna) {
			this.dna = dna;
		}
	}

}
