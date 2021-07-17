package br.org.cancer.modred.controller.dto.doador;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe qu representa um exame.
 * 
 * @author ergomes
 *
 */
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
@JsonSubTypes({ 
@Type(value = DoadorDTO.class, name = "exameDTO"),
@Type(value = ExameDoadorNacionalDTO.class, name = "exameDoadorNacionalDTO"),
@Type(value = ExameDoadorInternacionalDTO.class, name = "exameDoadorInternacionalDTO"),
})
public class ExameDTO {

	protected Long id;
	protected LocalDateTime dataCriacao;
	protected Integer statusExame;
	protected List<LocusExameDTO> locusExames;
	protected List<MetodologiaDTO> metodologias;

	
	/**
	 * Construtor padrão.
	 */
	public ExameDTO() {
		super();
	}
	
	/**
	 * Construtor opcional.
	 * Cria uma instância de exame apenas com o ID informado.
	 * 
	 * @param exameId ID do exame.
	 */
	public ExameDTO(Long exameId) {
		super();
		this.id = exameId;
	}
	
	
	/**
	 * Construtor opcional.
	 * 
	 * @param id do exame	 
	 * @param locusExames do exame
	 */
	public ExameDTO(Long id, List<LocusExameDTO> locusExames) {
		this(id);
		this.locusExames = locusExames;
	}

	/**
	 * @return the dataCriacao
	 */
	// @Column(name = "EXAM_DT_CRIACAO")
	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	protected void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	/**
	 * @return the statusExame
	 */
	public Integer getStatusExame() {
		return statusExame;
	}

	/**
	 * @param statusExame the statusExame to set
	 */
	public void setStatusExame(Integer statusExame) {
		this.statusExame = statusExame;
	}


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the locusExames
	 */
	public List<LocusExameDTO> getLocusExames() {
		return locusExames;
	}

	/**
	 * @return the metodologias
	 */
	public List<MetodologiaDTO> getMetodologias() {
		return metodologias;
	}

	/**
	 * @param metodologias the metodologias to set
	 */
	public void setMetodologias(List<MetodologiaDTO> metodologias) {
		this.metodologias = metodologias;
	}

	/**
	 * @param locusExames the locusExames to set
	 */
	public void setLocusExames(List<LocusExameDTO> locusExames) {
		this.locusExames = locusExames;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dataCriacao == null ) ? 0 : dataCriacao.hashCode() );		
		return result;
	}

	/**
	 * Implementação do método equals para a classe Exame.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ExameDTO other = (ExameDTO) obj;
		if (dataCriacao == null) {
			if (other.dataCriacao != null) {
				return false;
			}
		}
		else
			if (!dataCriacao.equals(other.dataCriacao)) {
				return false;
			}		
		return true;
	}

	
}
