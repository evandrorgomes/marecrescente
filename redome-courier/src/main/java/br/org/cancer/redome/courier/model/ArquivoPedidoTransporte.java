package br.org.cancer.redome.courier.model;

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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe de arquivos de pedido de transporte. Estes arquivos são os que acompanham o currier
 * nas viajens ao qual eles transportam as medulas.
 * @author Filipe Paes
 * 
 */
@Entity
@SequenceGenerator(name = "SQ_ARPT_ID", sequenceName = "SQ_ARPT_ID", allocationSize = 1)
@Table(name="ARQUIVO_PEDIDO_TRANSPORTE")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class ArquivoPedidoTransporte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ARPT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ARPT_ID")
	private Long id;

	@Column(name="ARPT_TX_CAMINHO")
	private String caminho;

	@ManyToOne
	@JoinColumn(name="PETR_ID")
	private PedidoTransporte pedidoTransporte;
	
	@Column(name="ARPT_TX_DESCRICAO_ALTERACAO")
	private String descricaoAlteracao;
	

	/**
	 * Método para obter somente o nome do arquivo de exame.
	 * 
	 * @return nome nome
	 */
	public String obterNomeArquivo() {
		String[] partes = this.getCaminho().split("/");
		if (partes.length > 1) {
			return partes[partes.length - 1];
		}
		return null;
	}

	/**
	 * Método para obter somente o diretorio de exame.
	 * 
	 * @return dir dir
	 */
	public String obterDiretorioArquivo() {
		String[] result = this.getCaminho().split("/");
		if (result.length > 1) {
			return result[0];
		}
		else {
			return null;
		}
	}
	
	

}