package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.RegistroContatoView;


/**
 * Tipos de ocorrencia de ligações telefonicas realizadas nos pedido de contato.
 * @author Filipe Paes
 *
 */
@Entity
@SequenceGenerator(name = "REGISTRO_TIPO_OCORRENCIA", sequenceName = "SQ_RETO_ID", allocationSize = 1)
@Table(name = "REGISTRO_TIPO_OCORRENCIA")
public class RegistroTipoOcorrencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842865142915413063L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REGISTRO_TIPO_OCORRENCIA")
	@Column(name = "RETO_ID")
	@JsonView(RegistroContatoView.Consulta.class)
	private Long id;
	
	@Column(name = "RETO_TX_DESCRICAO")
	@JsonView(RegistroContatoView.Consulta.class)
	private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

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
		RegistroTipoOcorrencia other = (RegistroTipoOcorrencia) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} 
		else if (!id.equals(other.id)) {
			return false;
		}	
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else if (!nome.equals(other.nome)) {
			return false;
		}
		
		return true;
	}
	
	
	
	

}
