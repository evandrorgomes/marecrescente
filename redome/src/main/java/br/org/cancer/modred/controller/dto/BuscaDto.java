package br.org.cancer.modred.controller.dto;

import java.time.LocalDateTime;

import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.StatusBusca;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe Dto para {@link Busca}
 * @author Filipe Paes
 *
 */
public class BuscaDto {
	
	private Long id;

	private Long paciente;

	private Long status;

	private Long usuario;
	
	private Integer aceitaMismatch;
	
	private Long idCentroTransplante;
	
	private LocalDateTime dataUltimaAnalise;
	
	private String wmdaId;

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
	 * @return the paciente
	 */
	public Long getPaciente() {
		return paciente;
	}

	/**
	 * @param paciente the paciente to set
	 */
	public void setPaciente(Long paciente) {
		this.paciente = paciente;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the usuario
	 */
	public Long getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	/**
	 * @return the aceitaMismatch
	 */
	public Integer getAceitaMismatch() {
		return aceitaMismatch;
	}

	/**
	 * @param aceitaMismatch the aceitaMismatch to set
	 */
	public void setAceitaMismatch(Integer aceitaMismatch) {
		this.aceitaMismatch = aceitaMismatch;
	}
	
	

	/**
	 * @return the idCentroTransplante
	 */
	public Long getIdCentroTransplante() {
		return idCentroTransplante;
	}

	/**
	 * @param idCentroTransplante the idCentroTransplante to set
	 */
	public void setIdCentroTransplante(Long idCentroTransplante) {
		this.idCentroTransplante = idCentroTransplante;
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

	public Busca parse() {
		Busca busca = new Busca();
		busca.setId(this.id);
		busca.setAceitaMismatch(this.aceitaMismatch);
		busca.setDataUltimaAnalise(this.dataUltimaAnalise);
		Paciente paciente = new Paciente();
		paciente.setRmr(this.paciente);
		paciente.setWmdaId(this.wmdaId);
		busca.setPaciente(paciente);
		busca.setStatus(new StatusBusca(this.status));
		busca.setUsuario(new Usuario(this.usuario));
		return busca;
	}
	
	public BuscaDto parse(Busca busca) {
		this.id = busca.getId();
		this.aceitaMismatch = busca.getAceitaMismatch();
		this.dataUltimaAnalise = busca.getDataUltimaAnalise();
		this.idCentroTransplante = busca.getCentroTransplante() != null && busca.getCentroTransplante().getId() != null ?busca.getCentroTransplante().getId(): null;
		this.paciente = busca.getPaciente().getRmr();
		this.wmdaId = busca.getPaciente().getWmdaId() != null ? busca.getPaciente().getWmdaId() : null;
		this.status = busca.getStatus().getId();
		this.usuario = busca.getUsuario() != null && busca.getUsuario().getId() != null? busca.getUsuario().getId(): null;
		return this;
	}
	
	
	
}
