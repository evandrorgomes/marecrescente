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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * Itens relacionados ao tipo de checklist para conclusão de tarefas;.
 * 
 * @author ergomes
 */
@Entity
@Table(name = "ITENS_CHECKLIST")
@SequenceGenerator(name = "SQ_ITEC_ID", sequenceName = "SQ_ITEC_ID", allocationSize = 1)
@Data
public class ItemChecklist implements Serializable {
	
	private static final long serialVersionUID = -5613164537368243014L;

	@Id
	@Column(name = "ITEC_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ITEC_ID")
	private Long id;

	@Column(name = "ITEC_TX_NM_ITEM")
	private String nome;

	@ManyToOne
	@JoinColumn(name = "CACH_ID")
	@JsonIgnore
	private CategoriaChecklist categoriaChecklist;

	@Transient
	private Boolean resposta;
	
}