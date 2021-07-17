package br.org.cancer.modred.controller.dto;

/**
 * DTO que representa o status do paciente.
 * 
 * @author ergomes
 */
public class StatusPacienteDTO {

	private Long id;
	private String descricao;
	private Integer ordem; 

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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}
	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}
}
