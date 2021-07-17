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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de arquivos de resposta de resultado de workup. Dentro desta estarão as referencias para o storage dos arquivos de
 * resultado de workup inputados pelo centro de coleta.
 * 
 * @author Filipe Paes.
 */
@Entity
@SequenceGenerator(name = "SQ_ARRW_ID", sequenceName = "SQ_ARRW_ID", allocationSize = 1)
@Table(name = "ARQUIVO_RESULTADO_WORKUP")
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ArquivoResultadoWorkup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARRW_ID")
	@Column(name = "ARRW_ID")
	private Long id;

	@Column(name = "ARRW_TX_CAMINHO")
	private String caminho;

	@Column(name = "ARRW_TX_DESCRICAO")
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "REWO_ID")
	@JsonIgnore
	private ResultadoWorkup resultadoWorkup;

}