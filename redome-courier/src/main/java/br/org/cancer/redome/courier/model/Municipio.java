package br.org.cancer.redome.courier.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe de persistencia de Municipio.
 * 
 * @author brunosousa
 *
 */
@Entity
@Table(name = "MUNICIPIO")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class Municipio implements Serializable {

	private static final long serialVersionUID = -9097977928192779169L;
	
    @Id
    @Column(name = "MUNI_ID")
    private Long id;
    
    @Column(name = "MUNI_TX_NOME")
    private String descricao;
    
    @Column(name = "MUNI_TX_CODIGO_IBGE")
    private String codigoIbge;
    
    @Column(name = "MUNI_TX_CODIGO_DNE")
    private String codigoDne;
    
	@ManyToOne
	@JoinColumn(name = "UF_SIGLA")
	private Uf uf;
		
	@Override
	public String toString() {
		return descricao + ", " + uf;
	}


}
