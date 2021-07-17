package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe para armazenamento das respostas dos itens de checklist.
 * 
 * @author ergomes
 * 
 */
@Entity
@Table(name = "RESPOSTA_CHECKLIST")
@SequenceGenerator(name = "SQ_RESC_ID", sequenceName = "SQ_RESC_ID", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RespostaChecklist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RESC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_RESC_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ITEC_ID")
	private ItemChecklist item;

	@Column(name = "RESC_IN_RESPOSTA")
	private Boolean resposta;

	@Column(name = "PELO_ID")
	private Long pedidoLogistica;

}