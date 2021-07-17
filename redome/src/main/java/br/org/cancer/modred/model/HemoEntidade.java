package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.org.cancer.modred.model.domain.ClassificacaoHemoEntidade;

/**
 * Classe que representa uma hemoentidade (hemocentro ou hemonucleo), responsável por armazenar a amostra do doador ou paciente
 * durante as fases no processo de transplante.
 * 
 * @author Pizão.
 *
 */
@Entity
@Table(name = "HEMO_ENTIDADE")
@SequenceGenerator(name = "SQ_HEEN_ID", sequenceName = "SQ_HEEN_ID", allocationSize = 1)
public class HemoEntidade implements Serializable {

	private static final long serialVersionUID = -4086521317584754299L;

	@Id
	@Column(name = "HEEN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_HEEN_ID")
	private Long id;

	@Column(name = "HEEN_TX_NOME")
	private String nome;

	@Column(name = "HEEN_IN_SELECIONAVEL")
	private Boolean selecionavel;

	@Enumerated(EnumType.STRING)
	@Column(name = "HEEN_TX_CLASSIFICACAO")
	private ClassificacaoHemoEntidade classificacao;
	
	@OneToOne(mappedBy="hemoEntidade")
	private EnderecoContatoHemoEntidade endereco;
	
	@Column(name = "HEEN_ID_HEMOCENTRO_REDOMEWEB")
	private Long idHemocentroRedomeWeb;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="HEEN_ID_HEMOCENTRO", nullable=true)
    @NotFound(action=NotFoundAction.IGNORE)   
    private HemoEntidade hemocentro;	

	public HemoEntidade() {
		super();
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

	public Boolean getSelecionavel() {
		return selecionavel;
	}

	public void setSelecionavel(Boolean selecionavel) {
		this.selecionavel = selecionavel;
	}

	public ClassificacaoHemoEntidade getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(ClassificacaoHemoEntidade classificacao) {
		this.classificacao = classificacao;
	}

	/**
	 * @return the endereco
	 */
	public EnderecoContatoHemoEntidade getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(EnderecoContatoHemoEntidade endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return id + " | " + nome + " | " + selecionavel + " | " + classificacao;
	}

	/**
	 * @return the idHemocentroRedomeWeb
	 */
	public Long getIdHemocentroRedomeWeb() {
		return idHemocentroRedomeWeb;
	}

	/**
	 * @param idHemocentroRedomeWeb the idHemocentroRedomeWeb to set
	 */
	public void setIdHemocentroRedomeWeb(Long idHemocentroRedomeWeb) {
		this.idHemocentroRedomeWeb = idHemocentroRedomeWeb;
	}

	/**
	 * @return the hemocentro
	 */
	public HemoEntidade getHemocentro() {
		return hemocentro;
	}

	/**
	 * @param hemocentro the hemocentro to set
	 */
	public void setHemocentro(HemoEntidade hemocentro) {
		this.hemocentro = hemocentro;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( classificacao == null ) ? 0 : classificacao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( nome == null ) ? 0 : nome.hashCode() );
		result = prime * result + ( ( selecionavel == null ) ? 0 : selecionavel.hashCode() );
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!( obj instanceof HemoEntidade )) {
			return false;
		}
		HemoEntidade other = (HemoEntidade) obj;
		if (classificacao != other.classificacao) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		}
		else
			if (!nome.equals(other.nome)) {
				return false;
			}
		if (selecionavel == null) {
			if (other.selecionavel != null) {
				return false;
			}
		}
		else
			if (!selecionavel.equals(other.selecionavel)) {
				return false;
			}
		return true;
	}

}