package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe de persistencia de doador para a tabela doador.
 * 
 * @author ergomes
 * 
 */
@JsonTypeInfo(
		  use = JsonTypeInfo.Id.NAME,
		  defaultImpl = Void.class
)
@JsonSubTypes({ 
@Type(value = DoadorDTO.class, name = "doadorDTO"),
@Type(value = DoadorNacionalDTO.class, name = "doadorNacionalDTO")
})
public class DoadorDTO implements Serializable {

	private static final long serialVersionUID = 2993043646708163097L;

	private Long id;
	private LocalDate dataCadastro;
	private RegistroDTO registroOrigem;
	private String sexo;
	private String abo;
	private LocalDate dataNascimento;
	private LocalDateTime dataAtualizacao;
	private StatusDoadorDTO statusDoador;
	private LocalDate dataRetornoInatividade;
	private BigDecimal peso;
	private String grid;
	private Integer idade;
	
	
	
	/**
	 * 
	 */
	public DoadorDTO() {
		super();
	}
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
	 * @return the dataCadastro
	 */
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	/**
	 * @param dataCadastro the dataCadastro to set
	 */
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
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
	 * @return the statusDoador
	 */
	public StatusDoadorDTO getStatusDoador() {
		return statusDoador;
	}
	/**
	 * @param statusDoador the statusDoador to set
	 */
	public void setStatusDoador(StatusDoadorDTO statusDoador) {
		this.statusDoador = statusDoador;
	}
	/**
	 * @return the dataRetornoInatividade
	 */
	public LocalDate getDataRetornoInatividade() {
		return dataRetornoInatividade;
	}
	/**
	 * @param dataRetornoInatividade the dataRetornoInatividade to set
	 */
	public void setDataRetornoInatividade(LocalDate dataRetornoInatividade) {
		this.dataRetornoInatividade = dataRetornoInatividade;
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
	 * @return the registroOrigem
	 */
	public RegistroDTO getRegistroOrigem() {
		return registroOrigem;
	}
	/**
	 * @param registroOrigem the registroOrigem to set
	 */
	public void setRegistroOrigem(RegistroDTO registroOrigem) {
		this.registroOrigem = registroOrigem;
	}
	
	/**
	 * @return the idade
	 */
	public Integer getIdade() {
		return idade;
	}
	/**
	 * @param idade the idade to set
	 */
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abo == null) ? 0 : abo.hashCode());
		result = prime * result + ((dataAtualizacao == null) ? 0 : dataAtualizacao.hashCode());
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((dataRetornoInatividade == null) ? 0 : dataRetornoInatividade.hashCode());
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((statusDoador == null) ? 0 : statusDoador.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
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
		DoadorDTO other = (DoadorDTO) obj;
		if (abo == null) {
			if (other.abo != null) {
				return false;
			}
		} 
		else if (!abo.equals(other.abo)){
			return false;
		}
		if (dataAtualizacao == null) {
			if (other.dataAtualizacao != null) {
				return false;
			}
		} 
		else if (!dataAtualizacao.equals(other.dataAtualizacao)){
			return false;
		}
		if (dataCadastro == null) {
			if (other.dataCadastro != null) {
				return false;
			}
		} 
		else if (!dataCadastro.equals(other.dataCadastro)) {
			return false;
		}
		if (dataNascimento == null) {
			if (other.dataNascimento != null) {
				return false;
			}
		} 
		else if (!dataNascimento.equals(other.dataNascimento)){
			return false;
		}
		if (dataRetornoInatividade == null) {
			if (other.dataRetornoInatividade != null) {
				return false;
			}
		} 
		else if (!dataRetornoInatividade.equals(other.dataRetornoInatividade)){
			return false;
		}
		if (grid == null) {
			if (other.grid != null) {
				return false;
			}
		} 
		else if (!grid.equals(other.grid)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}
		if (peso == null) {
			if (other.peso != null) {
				return false;
			}
		} 
		else if (!peso.equals(other.peso)){
			return false;
		}
		if (sexo == null) {
			if (other.sexo != null) {
				return false;
			}
		} 
		else if (!sexo.equals(other.sexo)){
			return false;
		}
		if (statusDoador == null) {
			if (other.statusDoador != null) {
				return false;
			}
		} 
		else if (!statusDoador.equals(other.statusDoador)){
			return false;
		}
		return true;
	}
	
}