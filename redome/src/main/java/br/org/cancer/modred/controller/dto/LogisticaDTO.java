package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import br.org.cancer.modred.model.ArquivoVoucherLogistica;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.EmailContatoDoador;
import br.org.cancer.modred.model.EnderecoContatoDoador;
import br.org.cancer.modred.model.TransporteTerrestre;

/**
 * Informações sobre o detalhe da logística necessária ao doador ou material.
 * 
 * @author Pizão.
 *
 */
public class LogisticaDTO implements Serializable {
	private static final long serialVersionUID = -3652795976912962282L;

	private Long idDoador;
	private Long pedidoLogisticaId;
	private Long tarefaId;
	
	private LocalDate dataInicio;
	private LocalDate dataFinal;
	
	private CentroTransplante centroColeta;
	
	private List<EnderecoContatoDoador> enderecos;
	private List<ContatoTelefonicoDoador> telefones;
	private List<EmailContatoDoador> emails;
	
	private List<TransporteTerrestre> transporteTerrestre;
	private List<ArquivoVoucherLogistica> aereos;
	private List<ArquivoVoucherLogistica> hospedagens;
	
	private Long rmr;
	
	private String nomeDoador;
	
	private String observacao;
	
	private Long dmr;

	
	public Long getIdDoador() {
		return idDoador;
	}

	public void setIdDoador(Long idDoador) {
		this.idDoador = idDoador;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<EnderecoContatoDoador> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<EnderecoContatoDoador> enderecos) {
		this.enderecos = enderecos;
	}

	public List<ContatoTelefonicoDoador> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<ContatoTelefonicoDoador> telefones) {
		this.telefones = telefones;
	}

	public List<EmailContatoDoador> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailContatoDoador> emails) {
		this.emails = emails;
	}

	public List<TransporteTerrestre> getTransporteTerrestre() {
		return transporteTerrestre;
	}

	public void setTransporteTerrestre(List<TransporteTerrestre> transporteTerrestre) {
		this.transporteTerrestre = transporteTerrestre;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Long getPedidoLogisticaId() {
		return pedidoLogisticaId;
	}

	public void setPedidoLogisticaId(Long pedidoLogisticaId) {
		this.pedidoLogisticaId = pedidoLogisticaId;
	}

	public List<ArquivoVoucherLogistica> getAereos() {
		return aereos;
	}

	public void setAereos(List<ArquivoVoucherLogistica> aereos) {
		this.aereos = aereos;
	}

	public List<ArquivoVoucherLogistica> getHospedagens() {
		return hospedagens;
	}

	public void setHospedagens(List<ArquivoVoucherLogistica> hospedagens) {
		this.hospedagens = hospedagens;
	}

	public CentroTransplante getCentroColeta() {
		return centroColeta;
	}

	public void setCentroColeta(CentroTransplante centroColeta) {
		this.centroColeta = centroColeta;
	}

	public Long getTarefaId() {
		return tarefaId;
	}

	public void setTarefaId(Long tarefaId) {
		this.tarefaId = tarefaId;
	}

	public Long getRmr() {
		return rmr;
	}

	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	public String getNomeDoador() {
		return nomeDoador;
	}

	public void setNomeDoador(String nomeDoador) {
		this.nomeDoador = nomeDoador;
	}

	public Long getDmr() {
		return dmr;
	}

	public void setDmr(Long dmr) {
		this.dmr = dmr;
	}
	
}
