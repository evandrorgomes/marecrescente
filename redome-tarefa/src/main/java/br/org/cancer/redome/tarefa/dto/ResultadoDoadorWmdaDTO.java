package br.org.cancer.redome.tarefa.dto;

/**
 * Classe para informações do doador WMDA de Medula.
 * @author ergomes
 *
 */
public class ResultadoDoadorWmdaDTO {
	
	private String id;
	private String grid;
	private String donSer;
	private String donorType;
	private String donPool;
	private String birthDate;
	private String status;
	private String contactDate;
	private String sex;
	private String ethn;
	private String ccr5;
	private Idm idm	;
	private String abo;
	private String rhesus;
	private String regAccredStatus;
	private String nmbrMarr;
	private String nmbrPbsc;
	private WmdaHla hla	;
	private String matchClass;
	private String matchGrade;
	private String nmdpGrade;
	private String p0;
	private String p1;
	private String p2;
	private String pa;
	private String pb;
	private String pc;
	private String pdr;
	private String pdq;
	private String diffString;
	private String inexp;
	private TceDigest tceDigest;
	
	private String vol;
	private String tnc;
	private String cd34pc;
	
	private String jsonDonors;
	
	public ResultadoDoadorWmdaDTO() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the grid
	 */
	public String getGrid() {
		return grid;
	}

	/**
	 * @param grid the grid to set
	 */
	public void setGrid(String grid) {
		this.grid = grid;
	}

	/**
	 * @return the donSer
	 */
	public String getDonSer() {
		return donSer;
	}

	/**
	 * @param donSer the donSer to set
	 */
	public void setDonSer(String donSer) {
		this.donSer = donSer;
	}

	/**
	 * @return the donorType
	 */
	public String getDonorType() {
		return donorType;
	}

	/**
	 * @param donorType the donorType to set
	 */
	public void setDonorType(String donorType) {
		this.donorType = donorType;
	}

	/**
	 * @return the donPool
	 */
	public String getDonPool() {
		return donPool;
	}

	/**
	 * @param donPool the donPool to set
	 */
	public void setDonPool(String donPool) {
		this.donPool = donPool;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the contactDate
	 */
	public String getContactDate() {
		return contactDate;
	}

	/**
	 * @param contactDate the contactDate to set
	 */
	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
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
	 * @return the ccr5
	 */
	public String getCcr5() {
		return ccr5;
	}

	/**
	 * @param ccr5 the ccr5 to set
	 */
	public void setCcr5(String ccr5) {
		this.ccr5 = ccr5;
	}

	/**
	 * @return the idm
	 */
	public Idm getIdm() {
		return idm;
	}

	/**
	 * @param idm the idm to set
	 */
	public void setIdm(Idm idm) {
		this.idm = idm;
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
	 * @return the regAccredStatus
	 */
	public String getRegAccredStatus() {
		return regAccredStatus;
	}

	/**
	 * @param regAccredStatus the regAccredStatus to set
	 */
	public void setRegAccredStatus(String regAccredStatus) {
		this.regAccredStatus = regAccredStatus;
	}

	/**
	 * @return the nmbrMarr
	 */
	public String getNmbrMarr() {
		return nmbrMarr;
	}

	/**
	 * @param nmbrMarr the nmbrMarr to set
	 */
	public void setNmbrMarr(String nmbrMarr) {
		this.nmbrMarr = nmbrMarr;
	}

	/**
	 * @return the nmbrPbsc
	 */
	public String getNmbrPbsc() {
		return nmbrPbsc;
	}

	/**
	 * @param nmbrPbsc the nmbrPbsc to set
	 */
	public void setNmbrPbsc(String nmbrPbsc) {
		this.nmbrPbsc = nmbrPbsc;
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
	 * @return the matchClass
	 */
	public String getMatchClass() {
		return matchClass;
	}

	/**
	 * @param matchClass the matchClass to set
	 */
	public void setMatchClass(String matchClass) {
		this.matchClass = matchClass;
	}

	/**
	 * @return the matchGrade
	 */
	public String getMatchGrade() {
		return matchGrade;
	}

	/**
	 * @param matchGrade the matchGrade to set
	 */
	public void setMatchGrade(String matchGrade) {
		this.matchGrade = matchGrade;
	}

	/**
	 * @return the nmdpGrade
	 */
	public String getNmdpGrade() {
		return nmdpGrade;
	}

	/**
	 * @param nmdpGrade the nmdpGrade to set
	 */
	public void setNmdpGrade(String nmdpGrade) {
		this.nmdpGrade = nmdpGrade;
	}

	/**
	 * @return the p0
	 */
	public String getP0() {
		return p0;
	}

	/**
	 * @param p0 the p0 to set
	 */
	public void setP0(String p0) {
		this.p0 = p0;
	}

	/**
	 * @return the p1
	 */
	public String getP1() {
		return p1;
	}

	/**
	 * @param p1 the p1 to set
	 */
	public void setP1(String p1) {
		this.p1 = p1;
	}

	/**
	 * @return the p2
	 */
	public String getP2() {
		return p2;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(String p2) {
		this.p2 = p2;
	}

	/**
	 * @return the pa
	 */
	public String getPa() {
		return pa;
	}

	/**
	 * @param pa the pa to set
	 */
	public void setPa(String pa) {
		this.pa = pa;
	}

	/**
	 * @return the pb
	 */
	public String getPb() {
		return pb;
	}

	/**
	 * @param pb the pb to set
	 */
	public void setPb(String pb) {
		this.pb = pb;
	}

	/**
	 * @return the pc
	 */
	public String getPc() {
		return pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(String pc) {
		this.pc = pc;
	}

	/**
	 * @return the pdr
	 */
	public String getPdr() {
		return pdr;
	}

	/**
	 * @param pdr the pdr to set
	 */
	public void setPdr(String pdr) {
		this.pdr = pdr;
	}

	/**
	 * @return the pdq
	 */
	public String getPdq() {
		return pdq;
	}

	/**
	 * @param pdq the pdq to set
	 */
	public void setPdq(String pdq) {
		this.pdq = pdq;
	}

	/**
	 * @return the diffString
	 */
	public String getDiffString() {
		return diffString;
	}

	/**
	 * @param diffString the diffString to set
	 */
	public void setDiffString(String diffString) {
		this.diffString = diffString;
	}

	/**
	 * @return the inexp
	 */
	public String getInexp() {
		return inexp;
	}

	/**
	 * @param inexp the inexp to set
	 */
	public void setInexp(String inexp) {
		this.inexp = inexp;
	}

	/**
	 * @return the tceDigest
	 */
	public TceDigest getTceDigest() {
		return tceDigest;
	}

	/**
	 * @param tceDigest the tceDigest to set
	 */
	public void setTceDigest(TceDigest tceDigest) {
		this.tceDigest = tceDigest;
	}

	/**
	 * @return the vol
	 */
	public String getVol() {
		return vol;
	}

	/**
	 * @param vol the vol to set
	 */
	public void setVol(String vol) {
		this.vol = vol;
	}

	/**
	 * @return the tnc
	 */
	public String getTnc() {
		return tnc;
	}

	/**
	 * @param tnc the tnc to set
	 */
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}

	/**
	 * @return the cd34pc
	 */
	public String getCd34pc() {
		return cd34pc;
	}

	/**
	 * @param cd34pc the cd34pc to set
	 */
	public void setCd34pc(String cd34pc) {
		this.cd34pc = cd34pc;
	}

	/**
	 * @return the jsonDonors
	 */
	public String getJsonDonors() {
		return jsonDonors;
	}

	/**
	 * @param jsonDonors the jsonDonors to set
	 */
	public void setJsonDonors(String jsonDonors) {
		this.jsonDonors = jsonDonors;
	}




	public static class Idm {
		private String antiCmv;
		private String antiCmvDate;
		
		public Idm() {
			super();
		}
		/**
		 * @return the antiCmv
		 */
		public String getAntiCmv() {
			return antiCmv;
		}
		/**
		 * @param antiCmv the antiCmv to set
		 */
		public void setAntiCmv(String antiCmv) {
			this.antiCmv = antiCmv;
		}
		/**
		 * @return the antiCmvDate
		 */
		public String getAntiCmvDate() {
			return antiCmvDate;
		}
		/**
		 * @param antiCmvDate the antiCmvDate to set
		 */
		public void setAntiCmvDate(String antiCmvDate) {
			this.antiCmvDate = antiCmvDate;
		}
	}

	public static class WmdaHla{
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

		public WmdaHla() {
			super();
		}

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
	
	public static class WmdaFieldsDna{
		private WmdaDna dna;
		private WmdaSer ser;

		public WmdaFieldsDna() {
			super();
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

		/**
		 * @return the ser
		 */
		public WmdaSer getSer() {
			return ser;
		}

		/**
		 * @param ser the ser to set
		 */
		public void setSer(WmdaSer ser) {
			this.ser = ser;
		}
	}
	
	public static class WmdaDna{
		private String field1;
		private String field2;

		public WmdaDna() {
			super();
		}

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
	
	public static class WmdaSer{
		private String field1;
		private String field2;

		public WmdaSer() {
			super();
		}

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
	
    public static class TceDigest{
	    private String countPer;
	    private String countNph;
	    private String countNpg;
	    private String probPer;
	    private String probNph;
	    private String probNpg;

	    public TceDigest() {
			super();
		}

		/**
		 * @return the countPer
		 */
		public String getCountPer() {
			return countPer;
		}

		/**
		 * @param countPer the countPer to set
		 */
		public void setCountPer(String countPer) {
			this.countPer = countPer;
		}

		/**
		 * @return the countNph
		 */
		public String getCountNph() {
			return countNph;
		}

		/**
		 * @param countNph the countNph to set
		 */
		public void setCountNph(String countNph) {
			this.countNph = countNph;
		}

		/**
		 * @return the countNpg
		 */
		public String getCountNpg() {
			return countNpg;
		}

		/**
		 * @param countNpg the countNpg to set
		 */
		public void setCountNpg(String countNpg) {
			this.countNpg = countNpg;
		}

		/**
		 * @return the probPer
		 */
		public String getProbPer() {
			return probPer;
		}

		/**
		 * @param probPer the probPer to set
		 */
		public void setProbPer(String probPer) {
			this.probPer = probPer;
		}

		/**
		 * @return the probNph
		 */
		public String getProbNph() {
			return probNph;
		}

		/**
		 * @param probNph the probNph to set
		 */
		public void setProbNph(String probNph) {
			this.probNph = probNph;
		}

		/**
		 * @return the probNpg
		 */
		public String getProbNpg() {
			return probNpg;
		}

		/**
		 * @param probNpg the probNpg to set
		 */
		public void setProbNpg(String probNpg) {
			this.probNpg = probNpg;
		}
	    
   }
}
