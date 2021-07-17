package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe para agrupar os itens de checklist.
 * @author ergomes
 */
@Entity
@Table(name = "CATEGORIA_CHECKLIST")
@SequenceGenerator(name = "SQ_CACH_ID", sequenceName = "SQ_CACH_ID", allocationSize = 1)

@Data @AllArgsConstructor 
@NoArgsConstructor(force = true)
@Getter @Setter
public class CategoriaChecklist implements Serializable {

	private static final long serialVersionUID = -4768281029913734481L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CACH_ID")
	@Column(name = "CACH_ID")
	private Long id;

	@Column(name = "CACH_TX_NM_CATEGORIA")
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TIPC_ID")
	@JsonIgnore
	private TipoChecklist tipo;

	@OneToMany(mappedBy = "categoriaChecklist")
	private List<ItemChecklist> itens;
	
}