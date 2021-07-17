package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.org.cancer.redome.workup.observable.EntityObservable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de um formulario pós coleta.
 * 
 * @author elizabete.poly
 *
 */
@Entity
@SequenceGenerator(name = "SQ_FOPO_ID", sequenceName = "SQ_FOPO_ID", allocationSize = 1)
@Table(name="FORMULARIO_POSCOLETA")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)

public class FormularioPosColeta extends EntityObservable implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	/**
	 * Identificador do formulário pós coleta.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_FOPO_ID")
	@Column(name = "FOPO_ID")
	private Long id;
	
	/**
	 * Identificador do pedido de workup.
	 */
	
	@Column(name = "PEWO_ID")
	private Long pedidoWorkup;
	
	/**
	 * Usúario responsável pelo preenchimento do formulário pós coleta
	 */
	@Column(name = "USUA_ID")
	private Long usuarioResponsavel;
	
	/**
	 * Data da criação do formulário pós coleta.
	 */
	@Column(name = "FOPO_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	/**
	 * Data de atualização do formulário pós coleta.
	 */
	@Column(name = "FOPO_DT_ATUALIZACAO")
	private LocalDateTime dataAtualizacao = LocalDateTime.now();
			
}
