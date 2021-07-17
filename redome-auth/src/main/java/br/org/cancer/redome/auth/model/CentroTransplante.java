package br.org.cancer.redome.auth.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe que representa o centro transplante (avaliadores e/ou transplantadores) do cadastro do paciente. Selecionado apennas
 * quando o mesmo é não aparentado {@see Paciente}.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "CENTRO_TRANSPLANTE")
@SequenceGenerator(name = "SQ_CETR_ID", sequenceName = "SQ_CETR_ID", allocationSize = 1)
public class CentroTransplante implements Serializable, Comparable<CentroTransplante> {

	private static final long serialVersionUID = -3749821347178713784L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CETR_ID")
	@Column(name = "CETR_ID")
	private Long id;

	@Column(name = "CETR_TX_NOME")
	private String nome;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "FUNCAO_CENTRO_TRANSPLANTE",
				joinColumns =
	{ @JoinColumn(name = "CETR_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "FUTR_ID") })
	private List<FuncaoTransplante> funcoes;
	
	@Override
	public int compareTo(CentroTransplante o) {	
		return getId().compareTo(o.getId());
	}
	
	public CentroTransplante() {
		super();
	}

	public CentroTransplante(Long id, String nome) {
		this();
		this.id = id;
		this.nome = nome;
	}

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

	public List<FuncaoTransplante> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<FuncaoTransplante> funcoes) {
		this.funcoes = funcoes;
	}
	
	
	
	

}