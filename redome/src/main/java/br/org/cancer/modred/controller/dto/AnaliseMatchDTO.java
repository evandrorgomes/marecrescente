package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.org.cancer.modred.model.CentroTransplante;

/**
 * Classe de DTO para informações com as listas de matchs.
 * 
 * @author Fillipe Queiroz
 */
public class AnaliseMatchDTO {

	private Long rmr;
	private Long buscaId;
	private BigDecimal peso; 
	private List<MatchDTO> listaFase1;
	private List<MatchDTO> listaFase2;
	private List<MatchDTO> listaFase3;
	private List<MatchDTO> listaExameFase2;
	private List<MatchDTO> listaExameFase3;
	private List<MatchDTO> listaDisponibilidade;
	private Boolean temPrescricao;	
	private CentroTransplante centroTransplanteConfirmado;
	private String abo;
	private Long totalMedula;
	private Long totalCordao;
	private Long totalSolicitacaoMedula;
	private Long totalSolicitacaoCordao;
	private Boolean aceitaMismatch;
	private String locusMismatch;
	private Boolean temExameAnticorpo;
	private String dataAnticorpo;
	private String resultadoExameAnticorpo;
	private String transplantePrevio;
	private String doadoresPrescritos;
	private List<BuscaChecklistDTO> buscaChecklist;
	private Long totalHistoricoFase1;
	private Long totalHistoricoFase2;
	private Long totalHistoricoFase3;
	
	private String nomeMedicoResponsavel;
	private String nomeCentroAvaliador;
	 
	private Long quantidadePrescricoes;
	private Long tipoDoadorComPrescricao;
	
	private Integer qtdMatchWmdaMedula = 0;
	private Integer qtdMatchWmdaMedulaImportado = 0;
	private Integer qtdMatchWmdaCordao = 0;
	private Integer qtdMatchWmdaCordaoImportado = 0;

	
	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the listaFase1
	 */
	public List<MatchDTO> getListaFase1() {
		return listaFase1;
	}

	/**
	 * @param listaFase1 the listaFase1 to set
	 */
	public void setListaFase1(List<MatchDTO> listaFase1) {
		this.listaFase1 = listaFase1;
	}

	/**
	 * @return the listaFase2
	 */
	public List<MatchDTO> getListaFase2() {
		return listaFase2;
	}

	/**
	 * @param listaFase2 the listaFase2 to set
	 */
	public void setListaFase2(List<MatchDTO> listaFase2) {
		this.listaFase2 = listaFase2;
	}

	/**
	 * @return the listaFase3
	 */
	public List<MatchDTO> getListaFase3() {
		return listaFase3;
	}

	/**
	 * @param listaFase3 the listaFase3 to set
	 */
	public void setListaFase3(List<MatchDTO> listaFase3) {
		this.listaFase3 = listaFase3;
	}

	/**
	 * @return the listaExameFase2
	 */
	public List<MatchDTO> getListaExameFase2() {
		return listaExameFase2;
	}

	/**
	 * @param listaExameFase2 the listaExameFase2 to set
	 */
	public void setListaExameFase2(List<MatchDTO> listaExameFase2) {
		this.listaExameFase2 = listaExameFase2;
	}

	/**
	 * @return the listaExameFase3
	 */
	public List<MatchDTO> getListaExameFase3() {
		return listaExameFase3;
	}

	/**
	 * @param listaExameFase3 the listaExameFase3 to set
	 */
	public void setListaExameFase3(List<MatchDTO> listaExameFase3) {
		this.listaExameFase3 = listaExameFase3;
	}

	/**
	 * @return the listaDisponibilidade
	 */
	public List<MatchDTO> getListaDisponibilidade() {
		return listaDisponibilidade;
	}

	/**
	 * @param listaDisponibilidade the listaDisponibilidade to set
	 */
	public void setListaDisponibilidade(List<MatchDTO> listaDisponibilidade) {
		this.listaDisponibilidade = listaDisponibilidade;
	}

	public Long getBuscaId() {
		return buscaId;
	}

	public void setBuscaId(Long buscaId) {
		this.buscaId = buscaId;
	}

	/**
	 * @return lista com todas as listas
	 */
	public List<MatchDTO> getAllLists() {
		List<MatchDTO> allLists = new ArrayList<MatchDTO>();
		if(listaFase1 != null){
			allLists.addAll(listaFase1);
		}
		if(listaFase2 != null){
			allLists.addAll(listaFase2);
		}
		if(listaFase3 != null){
			allLists.addAll(listaFase3);
		}
		if(listaExameFase2 != null){
			allLists.addAll(listaExameFase2);
		}
		if(listaExameFase3 != null){
			allLists.addAll(listaExameFase3);
		}
		if(listaDisponibilidade != null){
			allLists.addAll(listaDisponibilidade);
		}

		return allLists;
	}

	/**
	 * @return the temPrescricao
	 */
	public Boolean getTemPrescricao() {
		return temPrescricao;
	}

	/**
	 * @param temPrescricao the temPrescricao to set
	 */
	public void setTemPrescricao(Boolean temPrescricao) {
		this.temPrescricao = temPrescricao;
	}

	
	/**
	 * @return the peso
	 */
	public BigDecimal getPeso() {
		return peso;
	}

	
	/**
	 * @param peso the peso to set
	 */
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	/**
	 * O centro de transplante confirmado para o paciente.
	 * 
	 * @return centro de transplante associado a busca.
	 */
	public CentroTransplante getCentroTransplanteConfirmado() {
		return centroTransplanteConfirmado;
	}

	public void setCentroTransplanteConfirmado(CentroTransplante centroTransplanteConfirmado) {
		this.centroTransplanteConfirmado = centroTransplanteConfirmado;
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
	 * @return the totalMedula
	 */
	public Long getTotalMedula() {
		return totalMedula;
	}

	
	/**
	 * @param totalMedula the totalMedula to set
	 */
	public void setTotalMedula(Long totalMedula) {
		this.totalMedula = totalMedula;
	}

	
	/**
	 * @return the totalCordao
	 */
	public Long getTotalCordao() {
		return totalCordao;
	}

	
	/**
	 * @param totalCordao the totalCordao to set
	 */
	public void setTotalCordao(Long totalCordao) {
		this.totalCordao = totalCordao;
	}

	/**
	 * @return the aceitaMismatch
	 */
	public Boolean getAceitaMismatch() {
		return aceitaMismatch;
	}

	/**
	 * @param aceitaMismatch the aceitaMismatch to set
	 */
	public void setAceitaMismatch(Boolean aceitaMismatch) {
		this.aceitaMismatch = aceitaMismatch;
	}

	/**
	 * @return the locusMismatch
	 */
	public String getLocusMismatch() {
		return locusMismatch;
	}

	/**
	 * @param locusMismatch the locusMismatch to set
	 */
	public void setLocusMismatch(String locusMismatch) {
		this.locusMismatch = locusMismatch;
	}

	
	
	

	/**
	 * @return the temExameAnticorpo
	 */
	public Boolean getTemExameAnticorpo() {
		return temExameAnticorpo;
	}

	/**
	 * @param temExameAnticorpo the temExameAnticorpo to set
	 */
	public void setTemExameAnticorpo(Boolean temExameAnticorpo) {
		this.temExameAnticorpo = temExameAnticorpo;
	}
	
	

	/**
	 * @return the dataAnticorpo
	 */
	public String getDataAnticorpo() {
		return dataAnticorpo;
	}

	/**
	 * @param dataAnticorpo the dataAnticorpo to set
	 */
	public void setDataAnticorpo(String dataAnticorpo) {
		this.dataAnticorpo = dataAnticorpo;
	}

	/**
	 * @return the resultadoExameAnticorpo
	 */
	public String getResultadoExameAnticorpo() {
		return resultadoExameAnticorpo;
	}

	/**
	 * @param resultadoExameAnticorpo the resultadoExameAnticorpo to set
	 */
	public void setResultadoExameAnticorpo(String resultadoExameAnticorpo) {
		this.resultadoExameAnticorpo = resultadoExameAnticorpo;
	}

	/**
	 * @return the transplantePrevio
	 */
	public String getTransplantePrevio() {
		return transplantePrevio;
	}

	/**
	 * @param transplantePrevio the transplantePrevio to set
	 */
	public void setTransplantePrevio(String transplantePrevio) {
		this.transplantePrevio = transplantePrevio;
	}
	
	/**
	 * @return the doadoresPrescritos
	 */
	public String getDoadoresPrescritos() {
		return doadoresPrescritos;
	}

	/**
	 * @param doadoresPrescritos the doadoresPrescritos to set
	 */
	public void setDoadoresPrescritos(String doadoresPrescritos) {
		this.doadoresPrescritos = doadoresPrescritos;
	}

	/**
	 * Lista de itens de checklist a serem exibidos para o paciente.
	 * 
	 * @return lista de DTOs contendo a descrição e prazo do ocorrido.
	 */
	public List<BuscaChecklistDTO> getBuscaChecklist() {
		return buscaChecklist;
	}

	public void setBuscaChecklist(List<BuscaChecklistDTO> buscaChecklist) {
		this.buscaChecklist = buscaChecklist;
	}

	/**
	 * @return the totalHistoricoFase1
	 */
	public Long getTotalHistoricoFase1() {
		return totalHistoricoFase1;
	}

	/**
	 * @param totalHistoricoFase1 the totalHistoricoFase1 to set
	 */
	public void setTotalHistoricoFase1(Long totalHistoricoFase1) {
		this.totalHistoricoFase1 = totalHistoricoFase1;
	}

	/**
	 * @return the totalHistoricoFase2
	 */
	public Long getTotalHistoricoFase2() {
		return totalHistoricoFase2;
	}

	/**
	 * @param totalHistoricoFase2 the totalHistoricoFase2 to set
	 */
	public void setTotalHistoricoFase2(Long totalHistoricoFase2) {
		this.totalHistoricoFase2 = totalHistoricoFase2;
	}

	/**
	 * @return the totalHistoricoFase3
	 */
	public Long getTotalHistoricoFase3() {
		return totalHistoricoFase3;
	}

	/**
	 * @param totalHistoricoFase3 the totalHistoricoFase3 to set
	 */
	public void setTotalHistoricoFase3(Long totalHistoricoFase3) {
		this.totalHistoricoFase3 = totalHistoricoFase3;
	}

	/**
	 * @return the totalSolicitacaoMedula
	 */
	public Long getTotalSolicitacaoMedula() {
		return totalSolicitacaoMedula;
	}

	/**
	 * @param totalSolicitacaoMedula the totalSolicitacaoMedula to set
	 */
	public void setTotalSolicitacaoMedula(Long totalSolicitacaoMedula) {
		this.totalSolicitacaoMedula = totalSolicitacaoMedula;
	}

	/**
	 * @return the totalSolicitacaoCordao
	 */
	public Long getTotalSolicitacaoCordao() {
		return totalSolicitacaoCordao;
	}

	/**
	 * @param totalSolicitacaoCordao the totalSolicitacaoCordao to set
	 */
	public void setTotalSolicitacaoCordao(Long totalSolicitacaoCordao) {
		this.totalSolicitacaoCordao = totalSolicitacaoCordao;
	}

	/**
	 * @return the nomeMedicoResponsavel
	 */
	public String getNomeMedicoResponsavel() {
		return nomeMedicoResponsavel;
	}

	/**
	 * @param nomeMedicoResponsavel the nomeMedicoResponsavel to set
	 */
	public void setNomeMedicoResponsavel(String nomeMedicoResponsavel) {
		this.nomeMedicoResponsavel = nomeMedicoResponsavel;
	}

	/**
	 * @return the nomeCentroAvaliador
	 */
	public String getNomeCentroAvaliador() {
		return nomeCentroAvaliador;
	}

	/**
	 * @param nomeCentroAvaliador the nomeCentroAvaliador to set
	 */
	public void setNomeCentroAvaliador(String nomeCentroAvaliador) {
		this.nomeCentroAvaliador = nomeCentroAvaliador;
	}

	/**
	 * @return the quantidadePrescricoes
	 */
	public Long getQuantidadePrescricoes() {
		return quantidadePrescricoes;
	}

	/**
	 * @param quantidadePrescricoes the quantidadePrescricoes to set
	 */
	public void setQuantidadePrescricoes(Long quantidadePrescricoes) {
		this.quantidadePrescricoes = quantidadePrescricoes;
	}

	/**
	 * @return the tipoDoadorComPrescricao
	 */
	public Long getTipoDoadorComPrescricao() {
		return tipoDoadorComPrescricao;
	}

	/**
	 * @param tipoDoadorComPrescricao the tipoDoadorComPrescricao to set
	 */
	public void setTipoDoadorComPrescricao(Long tipoDoadorComPrescricao) {
		this.tipoDoadorComPrescricao = tipoDoadorComPrescricao;
	}

	/**
	 * @return the qtdMatchWmdaMedula
	 */
	public Integer getQtdMatchWmdaMedula() {
		return qtdMatchWmdaMedula;
	}

	/**
	 * @param qtdMatchWmdaMedula the qtdMatchWmdaMedula to set
	 */
	public void setQtdMatchWmdaMedula(Integer qtdMatchWmdaMedula) {
		this.qtdMatchWmdaMedula = qtdMatchWmdaMedula;
	}

	/**
	 * @return the qtdMatchWmdaMedulaImportado
	 */
	public Integer getQtdMatchWmdaMedulaImportado() {
		return qtdMatchWmdaMedulaImportado;
	}

	/**
	 * @param qtdMatchWmdaMedulaImportado the qtdMatchWmdaMedulaImportado to set
	 */
	public void setQtdMatchWmdaMedulaImportado(Integer qtdMatchWmdaMedulaImportado) {
		this.qtdMatchWmdaMedulaImportado = qtdMatchWmdaMedulaImportado;
	}

	/**
	 * @return the qtdMatchWmdaCordao
	 */
	public Integer getQtdMatchWmdaCordao() {
		return qtdMatchWmdaCordao;
	}

	/**
	 * @param qtdMatchWmdaCordao the qtdMatchWmdaCordao to set
	 */
	public void setQtdMatchWmdaCordao(Integer qtdMatchWmdaCordao) {
		this.qtdMatchWmdaCordao = qtdMatchWmdaCordao;
	}

	/**
	 * @return the qtdMatchWmdaCordaoImportado
	 */
	public Integer getQtdMatchWmdaCordaoImportado() {
		return qtdMatchWmdaCordaoImportado;
	}

	/**
	 * @param qtdMatchWmdaCordaoImportado the qtdMatchWmdaCordaoImportado to set
	 */
	public void setQtdMatchWmdaCordaoImportado(Integer qtdMatchWmdaCordaoImportado) {
		this.qtdMatchWmdaCordaoImportado = qtdMatchWmdaCordaoImportado;
	}
	
}
