package br.org.cancer.modred.controller.dto.doador;

import java.io.Serializable;

import br.org.cancer.modred.model.domain.ClassificacaoHemoEntidade;

/**
 * Classe que representa uma hemoentidade (hemocentro ou hemonucleo), responsável por armazenar a amostra do doador ou paciente
 * durante as fases no processo de transplante.
 * 
 * @author ergomes
 *
 */
public class HemoEntidadeDTO implements Serializable {

	private static final long serialVersionUID = -2756043472541589475L;

	private Long id;
	private String nome;
	private Boolean selecionavel;
	private ClassificacaoHemoEntidade classificacao;
	private EnderecoContatoHemoEntidadeDTO endereco;
	private Long idHemocentroRedomeWeb;
	
	private HemoEntidadeDTO hemocentro;

	/**
	 * 
	 */
	public HemoEntidadeDTO() {}

	public HemoEntidadeDTO(Long idHemocentroRedomeWeb) {
		super();
		this.idHemocentroRedomeWeb = idHemocentroRedomeWeb; 
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
	public EnderecoContatoHemoEntidadeDTO getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco the endereco to set
	 */
	public void setEndereco(EnderecoContatoHemoEntidadeDTO endereco) {
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
	public HemoEntidadeDTO getHemocentro() {
		return hemocentro;
	}

	/**
	 * @param hemocentro the hemocentro to set
	 */
	public void setHemocentro(HemoEntidadeDTO hemocentro) {
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
		if (!( obj instanceof HemoEntidadeDTO )) {
			return false;
		}
		HemoEntidadeDTO other = (HemoEntidadeDTO) obj;
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