package br.org.cancer.redome.workup.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Tipo de checklist que identifica se o item envolve Medula ou Cordão internacional.
 * 
 * @author ergomes
 */
@Entity
@Table(name = "TIPO_CHECKLIST")
@SequenceGenerator(name = "SQ_TIPC_ID", sequenceName = "SQ_TIPC_ID", allocationSize = 1)

@Data @AllArgsConstructor 
@NoArgsConstructor(force = true)
@Getter @Setter
public class TipoChecklist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TIPC_ID")
	@Column(name = "TIPC_ID")
	private Long id;

	@Column(name = "TIPC_TX_NM_TIPO")
	private String nome;

	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	private List<CategoriaChecklist> categorias;

	
	
}