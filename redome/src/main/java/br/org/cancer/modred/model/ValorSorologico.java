package br.org.cancer.modred.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe com os valores possíveis para sorologia de acordo com o valor chave
 * informado.
 * 
 * @author Pizão
 *
 */
@Entity
@Table(name = "VALOR_SOROLOGICO")
public class ValorSorologico implements Serializable {
	private static final long serialVersionUID = -8223830705778608249L;

	@EmbeddedId
	private ValorSorologicoPk id;

	@Column(name = "VASO_TX_VALORES")
	@NotNull
	private String valores;

	/**
	 * ID composto da entidade composto por locus e valor sorológico.
	 * 
	 * @return ID da entidade.
	 */
	public ValorSorologicoPk getId() {
		return id;
	}

	public void setId(ValorSorologicoPk id) {
		this.id = id;
	}

	/**
	 * Valores válidos possíveis para o antígeno.
	 * 
	 * @return string contendo os valores separados por ponto e vírgula (;).
	 */
	public String getValores() {
		return valores;
	}

	public void setValores(String valores) {
		this.valores = valores;
	}

}
