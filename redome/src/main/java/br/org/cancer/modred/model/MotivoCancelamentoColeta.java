package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.org.cancer.modred.model.domain.MotivosCancelamentoColeta;

/**
 * Classe que representa os motivos de cancelamento possíveis 
 * para o pedido de coleta.
 * 
 * @author Pizão
 */
@Entity
@Table(name = "MOTIVO_CANCELAMENTO_COLETA")
public class MotivoCancelamentoColeta implements Serializable {
	private static final long serialVersionUID = -3627737113736739014L;

	/**
	 * Enum que representa o ID da tabela no banco.
	 */
	@Id
	@Column(name = "MOCC_TX_CODIGO")
	private MotivosCancelamentoColeta id;

	/**
	 * Descrição de motivo de cancelamento.
	 */
	@Column(name = "MOCC_DESCRICAO")
	private String descricao;

	
	public MotivoCancelamentoColeta() {
		super();
	}
	
	/**
	 * Construtor para facilitar a criação do componente somente com o ID.
	 * 
	 * @param id ID do motivo.
	 */
	public MotivoCancelamentoColeta(MotivosCancelamentoColeta id) {
		super();
		this.id = id;
	}

	public MotivosCancelamentoColeta getId() {
		return id;
	}

	public void setId(MotivosCancelamentoColeta id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


}