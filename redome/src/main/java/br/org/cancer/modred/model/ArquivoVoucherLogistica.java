package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.org.cancer.modred.model.annotations.EnumValues;
import br.org.cancer.modred.model.domain.TiposVoucher;
import br.org.cancer.modred.util.ArquivoUtil;

/**
 * Vouchers, como hospedagem e passagens aéreas, anexado ao processo de workup do doador.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "ARQUIVO_VOUCHER_LOGISTICA")
@SequenceGenerator(name = "SQ_ARVL_ID", sequenceName = "SQ_ARVL_ID", allocationSize = 1)
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ArquivoVoucherLogistica implements Serializable {

	private static final long serialVersionUID = 159747301664836819L;
	
	/**
	 * Variável estática representando o tamanho máximo que o nome do arquivo pode conter.
	 */
	public static final int TAMANHO_MAXIMO_NOME_ARQUIVO = 257;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARVL_ID")
	@Column(name = "ARVL_ID")
	private Long id;

	@Column(name = "ARVL_TX_COMENTARIO")
	@Length(max = 100)
	private String comentario;

	@Column(name = "ARVL_TX_CAMINHO")
	@Length(max = 263)
	private String caminho;

	@Column(name = "ARVL_IN_EXCLUIDO")
	@NotNull
	private Boolean excluido;

	/**
	 * Indica o tipo do voucher
	 * 1-hospedagem, 2-transporte aereo.
	 */
	@EnumValues(TiposVoucher.class)
	@Column(name = "ARVL_NR_TIPO")
	@NotNull
	private Integer tipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PELO_ID")
	@JsonProperty(access = Access.WRITE_ONLY)
	private PedidoLogistica pedidoLogistica;
	

	public ArquivoVoucherLogistica() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public PedidoLogistica getPedidoLogistica() {
		return pedidoLogistica;
	}

	public void setPedidoLogistica(PedidoLogistica pedidoLogistica) {
		this.pedidoLogistica = pedidoLogistica;
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
		result = prime * result + ( ( caminho == null ) ? 0 : caminho.hashCode() );
		result = prime * result + ( ( comentario == null ) ? 0 : comentario.hashCode() );
		result = prime * result + ( ( excluido == null ) ? 0 : excluido.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( pedidoLogistica == null ) ? 0 : pedidoLogistica.hashCode() );
		result = prime * result + ( ( tipo == null ) ? 0 : tipo.hashCode() );
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
		if (!( obj instanceof ArquivoVoucherLogistica )) {
			return false;
		}
		ArquivoVoucherLogistica other = (ArquivoVoucherLogistica) obj;
		if (caminho == null) {
			if (other.caminho != null) {
				return false;
			}
		}
		else
			if (!caminho.equals(other.caminho)) {
				return false;
			}
		if (comentario == null) {
			if (other.comentario != null) {
				return false;
			}
		}
		else
			if (!comentario.equals(other.comentario)) {
				return false;
			}
		if (excluido == null) {
			if (other.excluido != null) {
				return false;
			}
		}
		else
			if (!excluido.equals(other.excluido)) {
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
		if (pedidoLogistica == null) {
			if (other.pedidoLogistica != null) {
				return false;
			}
		}
		else
			if (!pedidoLogistica.equals(other.pedidoLogistica)) {
				return false;
			}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		}
		else
			if (!tipo.equals(other.tipo)) {
				return false;
			}
		return true;
	}
	
	/**
	 * Retorna o nome do arquivo com timestamp.
	 * 
	 * @return
	 */
	public String nomeComTimestamp() {
		return ArquivoUtil.obterNomeArquivoComTimestampPorCaminhoArquivo(this.caminho);
	}


}