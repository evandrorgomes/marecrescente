package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de um pedido de mais informações de parte de um médico, será respondido pelo perfil de analista_workup e
 * será montado um dialogo entre o médico e o analista.
 * 
 * @author ergomes
 *
 */
@Entity
@Table(name = "PEDIDO_ADICIONAL_WORKUP")
@SequenceGenerator(name = "SQ_PEAW_ID", sequenceName = "SQ_PEAW_ID", allocationSize = 1)

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor()
@Builder
@Data
public class PedidoAdicionalWorkup implements Serializable {

	private static final long serialVersionUID = 5170101253604507919L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PEAW_ID")
	@Column(name = "PEAW_ID")
	private Long id;

	@Column(name = "PEAW_TX_DESCRICAO")
	@NotNull
	private String descricao;

	@Column(name = "PEAW_DT_CRIACAO")
	@NotNull
	private LocalDateTime dataCriacao;

	@Column(name = "PEWO_ID")
	private Long pedidoWorkup;

	@OneToMany(mappedBy="pedidoAdicional", cascade=CascadeType.ALL)
	private List<ArquivoPedidoAdicionalWorkup> arquivosPedidoAdicionalWorkup;

}