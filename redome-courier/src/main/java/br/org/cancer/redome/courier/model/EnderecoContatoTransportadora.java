package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * Classe de endereços de transportadora.
 * @author Filipe Paes
 *
 */
@Entity
@DiscriminatorValue(value = "TRAN")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoContatoTransportadora extends EnderecoContato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -353835008155309696L;

	@ManyToOne
	@JoinColumn(name = "TRAN_ID")
	@JsonIgnore
	private Transportadora transportadora;

	@Builder(toBuilder = true)
	private EnderecoContatoTransportadora(Long id, @Length(max = 9) String cep, @NotNull Pais pais, String numero,
			@Length(max = 255) String bairro, @Length(max = 50) String complemento,
			@Length(max = 255) String nomeLogradouro, Municipio municipio, @Length(max = 100) String tipoLogradouro,
			boolean excluido, boolean principal, boolean correspondencia, Transportadora transportadora) {
		super(id, cep, pais, numero, bairro, complemento, nomeLogradouro, municipio, tipoLogradouro, excluido, principal,
				correspondencia);
		this.transportadora = transportadora;
	}


}
