package br.org.cancer.modred.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.org.cancer.modred.util.DateUtils;

/**
 * Classe de DTO para paginacao de busca.
 * 
 * @author Filipe Paes
 */
public class BuscaPaginacaoDTO {
	private Long idBusca;
	private Long rmr;
	private String nome;
	private Integer idade;
	private Float score;
	private LocalDateTime dataAvaliacao;
	private String nomeCentro;
	private String nomeCid;
	private String codigoCid;
	public static final String CAMPO_RMR = "PACI_NR_RMR";
	private Long totalRegistros;
	private LocalDate prazoExpirar;
	private Long prazoExpirarDiasUteis;
	private LocalDateTime dataUltimaAnalise;
	private String nomeCentroAvaliador;
	private LocalDateTime dataUltimaEvolucao;

	/**
	 * @return the idBusca
	 */
	public Long getIdBusca() {
		return idBusca;
	}

	/**
	 * @param idBusca
	 *            the idBusca to set
	 */
	public void setIdBusca(Long idBusca) {
		this.idBusca = idBusca;
	}

	/**
	 * @return the rmr
	 */
	public Long getRmr() {
		return rmr;
	}

	/**
	 * @param rmr
	 *            the rmr to set
	 */
	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the idade
	 */
	public Integer getIdade() {
		return idade;
	}

	/**
	 * @param idade
	 *            the idade to set
	 */
	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	/**
	 * @return the score
	 */
	public Float getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Float score) {
		this.score = score;
	}

	/**
	 * @return the dataAvaliacao
	 */
	public LocalDateTime getDataAvaliacao() {
		return dataAvaliacao;
	}

	/**
	 * @param dataAvaliacao
	 *            the dataAvaliacao to set
	 */
	public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	/**
	 * @return the nomeCentro
	 */
	public String getNomeCentro() {
		return nomeCentro;
	}

	/**
	 * @param nomeCentro
	 *            the nomeCentro to set
	 */
	public void setNomeCentro(String nomeCentro) {
		this.nomeCentro = nomeCentro;
	}

	/**
	 * @return the nomeCid
	 */
	public String getNomeCid() {
		return nomeCid;
	}

	/**
	 * @param nomeCid
	 *            the nomeCid to set
	 */
	public void setNomeCid(String nomeCid) {
		this.nomeCid = nomeCid;
	}

	/**
	 * @return the codigoCid
	 */
	public String getCodigoCid() {
		return codigoCid;
	}

	/**
	 * @param codigoCid
	 *            the codigoCid to set
	 */
	public void setCodigoCid(String codigoCid) {
		this.codigoCid = codigoCid;
	}
	
	public String getDataAging(){
		return this.dataAvaliacao != null? DateUtils.obterAging(dataAvaliacao):null;
	}

	/**
	 * Guarda o total do resultado da pesquisa, utilizado
	 * na paginação.
	 * @return tamanho a ser paginado.
	 */
	public Long getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * Indica quantos dias, a partir da criação, o 
	 * item do checklist de busca estará dentro do prazo.
	 * Após expirado o tempo, está fora do prazo.
	 * 
	 * @return números de dias para realização do item inserido.
	 */
	public LocalDate getPrazoExpirar() {
		return prazoExpirar;
	}

	public void setPrazoExpirar(LocalDate prazoExpirar) {
		this.prazoExpirar = prazoExpirar;
	}

	/**
	 * Retorna o total de dias úteis restante ou não para realização
	 * de uma determinado item de checklist.
	 * 
	 * @return prazo em dias úteis.
	 */
	public Long getPrazoExpirarDiasUteis() {
		return prazoExpirarDiasUteis;
	}

	public void setPrazoExpirarDiasUteis(Long prazoExpirarDiasUteis) {
		this.prazoExpirarDiasUteis = prazoExpirarDiasUteis;
	}

	/**
	 * Nome do centro avaliador, associado ao paciente e ao analista de busca.
	 * @return nome do centro.
	 */
	public String getNomeCentroAvaliador() {
		return nomeCentroAvaliador;
	}

	public void setNomeCentroAvaliador(String nomeCentroAvaliador) {
		this.nomeCentroAvaliador = nomeCentroAvaliador;
	}

	/**
	 * @return the dataUltimaAnalise
	 */
	public LocalDateTime getDataUltimaAnalise() {
		return dataUltimaAnalise;
	}

	/**
	 * @param dataUltimaAnalise the dataUltimaAnalise to set
	 */
	public void setDataUltimaAnalise(LocalDateTime dataUltimaAnalise) {
		this.dataUltimaAnalise = dataUltimaAnalise;
	}

	/**
	 * @return the dataUltimaEvolucao
	 */
	public LocalDateTime getDataUltimaEvolucao() {
		return dataUltimaEvolucao;
	}

	/**
	 * @param dataUltimaEvolucao the dataUltimaEvolucao to set
	 */
	public void setDataUltimaEvolucao(LocalDateTime dataUltimaEvolucao) {
		this.dataUltimaEvolucao = dataUltimaEvolucao;
	}
	
}
