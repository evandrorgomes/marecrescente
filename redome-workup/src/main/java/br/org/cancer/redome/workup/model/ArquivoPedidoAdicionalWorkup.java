package br.org.cancer.redome.workup.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de arquivo de pedido adicional de workup. Dentro desta estarão as referencias para o storage dos arquivos de
 * pedido adicional de workup inputados pelo analista de workup.
 * 
 * @author ergomes.
 */
@Entity
@SequenceGenerator(name = "SQ_APAW_ID", sequenceName = "SQ_APAW_ID", allocationSize = 1)
@Table(name = "ARQUIVO_PEDIDO_ADICIONAL_WORKUP")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArquivoPedidoAdicionalWorkup implements Serializable {

	private static final long serialVersionUID = -785189410402324818L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_APAW_ID")
	@Column(name = "APAW_ID")
	private Long id;

	@Column(name = "APAW_TX_CAMINHO")
	private String caminho;
	
	@Column(name = "APAW_TX_DESCRICAO")
	private String descricao;

	@Column(name = "PEAW_ID")
	private Long pedidoAdicional;
}