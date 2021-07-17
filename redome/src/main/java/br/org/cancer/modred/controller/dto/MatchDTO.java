package br.org.cancer.modred.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.TipoPermissividade;

/**
 * Classe de DTO para informações do match.
 * 
 * @author Fillipe Queiroz
 */
public class MatchDTO {

	private Long id;
	private Long idDoador;
	private String idRegistro;
	private String registroOrigem;
	private Long dmr;
	private String nome;
	private String sexo;
	private LocalDate dataNascimento;
	private LocalDateTime dataAtualizacao;
	private BigDecimal peso;
	private String abo;
	private String mismatch;
	private int outrosProcessos;
	private Boolean possuiRessalva;
	private Boolean possuiComentario;
	private String classificacao;
	private Boolean temPrescricao;
	private Long idProcesso;
	private Long idPedidoExame;
	private Long idTarefa;
	private Long idTipoTarefa;
	private Long idSolicitacao;
	private BigDecimal quantidadeTCNPorKilo;
	private BigDecimal quantidadeCD34PorKilo;
	private Long tipoDoador;
	private String idBscup;
	private String locusPedidoExameParaPaciente;
	private String locusPedidoExame;
	private Long rmrPedidoExame;
	private Boolean possuiGenotipoDivergente;
	private String fase;
	private String tipoMatch;
	private Boolean disponibilizado;
	private Long tipoSolicitacao;
	private StatusDoador statusDoador;
	private Long respostaQtdGestacaoDoador;
	private Integer somaPesoQualificacao;
	private TipoPermissividade tipoPermissividade;
	private Integer ordenacaoWmdaMatch;
	
	private List<BuscaChecklistDTO> buscaChecklist;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the idDoador
	 */
	public Long getIdDoador() {
		return idDoador;
	}

	/**
	 * @param idDoador the idDoador to set
	 */
	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the dataNascimento
	 */
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	/**
	 * @param dataNascimento the dataNascimento to set
	 */
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
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
	 * @return the mismatch
	 */
	public String getMismatch() {
		return mismatch;
	}

	/**
	 * @param mismatch the mismatch to set
	 */
	public void setMismatch(String mismatch) {
		this.mismatch = mismatch;
	}

	/**
	 * Quantidade de processos envolvidos pelo doador.
	 * @return
	 */
	public int getOutrosProcessos() {
		return outrosProcessos;
	}

	/**
	 * @param outrosProcessos the outrosProcessos to set
	 */
	public void setOutrosProcessos(int outrosProcessos) {
		this.outrosProcessos = outrosProcessos;
	}

	/**
	 * @return the possuiRessalva
	 */
	public Boolean getPossuiRessalva() {
		return possuiRessalva;
	}

	/**
	 * @param possuiRessalva the possuiRessalva to set
	 */
	public void setPossuiRessalva(Boolean possuiRessalva) {
		this.possuiRessalva = possuiRessalva;
	}

	/**
	 * @return the possuiComentario
	 */
	public Boolean getPossuiComentario() {
		return possuiComentario;
	}

	/**
	 * @param possuiComentario the possuiComentario to set
	 */
	public void setPossuiComentario(Boolean possuiComentario) {
		this.possuiComentario = possuiComentario;
	}

	/**
	 * @return the classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao the classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	/**
	 * @return the dataAtualizacao
	 */
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	/**
	 * @param dataAtualizacao the dataAtualizacao to set
	 */
	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
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
	 * @return the dmr
	 */
	public Long getDmr() {
		return dmr;
	}

	/**
	 * @param dmr the dmr to set
	 */
	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}

	/**
	 * @return the idRegistro
	 */
	public String getIdRegistro() {
		return idRegistro;
	}

	/**
	 * @param idRegistro the idRegistro to set
	 */
	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	/**
	 * @return the registroOrigem
	 */
	public String getRegistroOrigem() {
		return registroOrigem;
	}

	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(String registroOrigem) {
		this.registroOrigem = registroOrigem;
	}

	/**
	 * @return the idProcesso
	 */
	public Long getIdProcesso() {
		return idProcesso;
	}

	/**
	 * @param idProcesso the idProcesso to set
	 */
	public void setIdProcesso(Long idProcesso) {
		this.idProcesso = idProcesso;
	}

	/**
	 * @return the idPedidoExame
	 */
	public Long getIdPedidoExame() {
		return idPedidoExame;
	}

	/**
	 * @param idPedidoExame the idPedidoExame to set
	 */
	public void setIdPedidoExame(Long idPedidoExame) {
		this.idPedidoExame = idPedidoExame;
	}

	/**
	 * @return the idTarefa
	 */
	public Long getIdTarefa() {
		return idTarefa;
	}

	/**
	 * @param idTarefa the idTarefa to set
	 */
	public void setIdTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
	}

	/**
	 * @return the idTipoTarefa
	 */
	public Long getIdTipoTarefa() {
		return idTipoTarefa;
	}

	/**
	 * @param idTipoTarefa the idTipoTarefa to set
	 */
	public void setIdTipoTarefa(Long idTipoTarefa) {
		this.idTipoTarefa = idTipoTarefa;
	}

	/**
	 * @return the idSolicitacao
	 */
	public Long getIdSolicitacao() {
		return idSolicitacao;
	}

	/**
	 * @param idSolicitacao the idSolicitacao to set
	 */
	public void setIdSolicitacao(Long idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	
	/**
	 * @return the quantidadeTCNPorKilo
	 */
	public BigDecimal getQuantidadeTCNPorKilo() {
		return quantidadeTCNPorKilo;
	}

	
	/**
	 * @param quantidadeTCNPorKilo the quantidadeTCNPorKilo to set
	 */
	public void setQuantidadeTCNPorKilo(BigDecimal quantidadeTCNPorKilo) {
		this.quantidadeTCNPorKilo = quantidadeTCNPorKilo;
	}
	
	/**
	 * @return the quantidadeCD34PorKilo
	 */
	public BigDecimal getQuantidadeCD34PorKilo() {
		return quantidadeCD34PorKilo;
	}
	
	/**
	 * @param quantidadeCD34PorKilo the quantidadeCD34PorKilo to set
	 */
	public void setQuantidadeCD34PorKilo(BigDecimal quantidadeCD34PorKilo) {
		this.quantidadeCD34PorKilo = quantidadeCD34PorKilo;
	}

	/**
	 * @return the tipoDoador
	 */
	public Long getTipoDoador() {
		return tipoDoador;
	}
	
	/**
	 * @param tipoDoador the tipoDoador to set
	 */
	public void setTipoDoador(Long tipoDoador) {
		this.tipoDoador = tipoDoador;
	}

	
	/**
	 * @return the idBscup
	 */
	public String getIdBscup() {
		return idBscup;
	}

	
	/**
	 * @param idBscup the idBscup to set
	 */
	public void setIdBscup(String idBscup) {
		this.idBscup = idBscup;
	}

	/**
	 * @return the locusPedidoExameParaPaciente
	 */
	public String getLocusPedidoExameParaPaciente() {
		return locusPedidoExameParaPaciente;
	}

	/**
	 * @param locusPedidoExameParaPaciente the locusPedidoExameParaPaciente to set
	 */
	public void setLocusPedidoExameParaPaciente(String locusPedidoExameParaPaciente) {
		this.locusPedidoExameParaPaciente = locusPedidoExameParaPaciente;
	}

	/**
	 * @return the locusPedidoExame
	 */
	public String getLocusPedidoExame() {
		return locusPedidoExame;
	}

	/**
	 * @param locusPedidoExame the locusPedidoExame to set
	 */
	public void setLocusPedidoExame(String locusPedidoExame) {
		this.locusPedidoExame = locusPedidoExame;
	}

	/**
	 * @return the rmrPedidoExame
	 */
	public Long getRmrPedidoExame() {
		return rmrPedidoExame;
	}

	/**
	 * @param rmrPedidoExame the rmrPedidoExame to set
	 */
	public void setRmrPedidoExame(Long rmrPedidoExame) {
		this.rmrPedidoExame = rmrPedidoExame;
	}

	/**
	 * Lista de itens de checklist a serem exibidos para o doador.
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
	 * @return the possuiGenotipoDivergente
	 */
	public Boolean getPossuiGenotipoDivergente() {
		return possuiGenotipoDivergente;
	}

	/**
	 * @param possuiGenotipoDivergente the possuiGenotipoDivergente to set
	 */
	public void setPossuiGenotipoDivergente(Boolean possuiGenotipoDivergente) {
		this.possuiGenotipoDivergente = possuiGenotipoDivergente;
	}

	/**
	 * @return the fase
	 */
	public String getFase() {
		return fase;
	}

	/**
	 * @param fase the fase to set
	 */
	public void setFase(String fase) {
		this.fase = fase;
	}

	/**
	 * @return the tipoMatch
	 */
	public String getTipoMatch() {
		return tipoMatch;
	}

	/**
	 * @param tipoMatch the tipoMatch to set
	 */
	public void setTipoMatch(String tipoMatch) {
		this.tipoMatch = tipoMatch;
	}

	/**
	 * @return the disponibilizado
	 */
	public Boolean getDisponibilizado() {
		return disponibilizado;
	}

	/**
	 * @param disponibilizado the disponibilizado to set
	 */
	public void setDisponibilizado(Boolean disponibilizado) {
		this.disponibilizado = disponibilizado;
	}

	/**
	 * @return the tipoSolicitacao
	 */
	public Long getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	/**
	 * @param tipoSolicitacao the tipoSolicitacao to set
	 */
	public void setTipoSolicitacao(Long tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	/**
	 * @return the somaPesoQualificacao
	 */
	public Integer getSomaPesoQualificacao() {
		return somaPesoQualificacao;
	}

	/**
	 * @param somaPesoQualificacao the somaPesoQualificacao to set
	 */
	public void setSomaPesoQualificacao(Integer somaPesoQualificacao) {
		this.somaPesoQualificacao = somaPesoQualificacao;
	}	

	/**
	 * @return the statusDoador
	 */
	public StatusDoador getStatusDoador() {
		return statusDoador;
	}

	/**
	 * @param statusDoador the statusDoador to set
	 */
	public void setStatusDoador(StatusDoador statusDoador) {
		this.statusDoador = statusDoador;
	}

	/**
	 * @return the respostaQtdGestacaoDoador
	 */
	public Long getRespostaQtdGestacaoDoador() {
		return respostaQtdGestacaoDoador;
	}

	/**
	 * @param respostaQtdGestacaoDoador the respostaQtdGestacaoDoador to set
	 */
	public void setRespostaQtdGestacaoDoador(Long respostaQtdGestacaoDoador) {
		this.respostaQtdGestacaoDoador = respostaQtdGestacaoDoador;
	}

	/**
	 * @return the tipoPermissividade
	 */
	public TipoPermissividade getTipoPermissividade() {
		return tipoPermissividade;
	}

	/**
	 * @param tipoPermissividade the tipoPermissividade to set
	 */
	public void setTipoPermissividade(TipoPermissividade tipoPermissividade) {
		this.tipoPermissividade = tipoPermissividade;
	}

	/**
	 * @return the ordenacaoWmdaMatch
	 */
	public Integer getOrdenacaoWmdaMatch() {
		return ordenacaoWmdaMatch;
	}

	/**
	 * @param ordenacaoWmdaMatch the ordenacaoWmdaMatch to set
	 */
	public void setOrdenacaoWmdaMatch(Integer ordenacaoWmdaMatch) {
		this.ordenacaoWmdaMatch = ordenacaoWmdaMatch;
	}
	
}
