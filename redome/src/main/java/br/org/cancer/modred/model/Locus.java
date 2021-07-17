package br.org.cancer.modred.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.dto.genotipo.ComposicaoAlelo;
import br.org.cancer.modred.controller.view.ExameDoadorView;
import br.org.cancer.modred.controller.view.ExameView;
import br.org.cancer.modred.controller.view.GenotipoView;
import br.org.cancer.modred.controller.view.PacienteView;
import br.org.cancer.modred.controller.view.PedidoExameView;

/**
 * Classe que representa um lócus.
 * 
 * @author Rafael Pizão
 *
 */
@Entity
@Table(name = "LOCUS")
public class Locus implements Serializable {
    private static final long serialVersionUID = -2314826389349549556L;

    public static final String LOCUS_A = "A";
    public static final String LOCUS_B = "B";
    public static final String LOCUS_DRB1 = "DRB1";
    public static final String LOCUS_C = "C";
    public static final String LOCUS_DQB1 = "DQB1";
    public static final String LOCUS_DPA1 = "DPA1";
    public static final String LOCUS_DPB1 = "DPB1";
    public static final String LOCUS_DRB3 = "DRB3";
    public static final String LOCUS_DRB4 = "DRB4";
    public static final String LOCUS_DRB5 = "DRB5";
    
    @Id
    @Column(name = "LOCU_ID")
    @NotNull
    @JsonView({ExameView.ListaExame.class, ExameView.ConferirExame.class,PedidoExameView.Detalhe.class,
    	PacienteView.Detalhe.class, PedidoExameView.ListarTarefas.class, PedidoExameView.ObterParaEditar.class,
    	PacienteView.Rascunho.class, ExameDoadorView.ExameListaCombo.class, PacienteView.IdentificacaoCompleta.class, 
    	GenotipoView.ListaExame.class, GenotipoView.Divergente.class})
    private String codigo;
    
    @Column(name = "LOCU_NR_ORDEM")
    @JsonView({PedidoExameView.Detalhe.class, PedidoExameView.ObterParaEditar.class, PacienteView.Detalhe.class, 
    			PacienteView.Rascunho.class, PacienteView.IdentificacaoCompleta.class, GenotipoView.ListaExame.class})
    private Integer ordem;
    
    @Column(name = "LOCU_NR_PESO_FASE2")
    private BigDecimal pesoFase2;
    
    
	/**
	 * Construtor padrão.
	 */
	public Locus() {}

	/**
	 * Construtor sobrecarregado que recebe o código do lócus.
	 * 
	 * @param codigo
	 */
	public Locus(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Construtor sobrecarregado que recebe o código do lócus e a ordem.
	 * 
	 * @param codigo
	 * @param ordem
	 */
	public Locus(String codigo, Integer ordem) {
		this(codigo);
		this.ordem = ordem;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem the ordem to set
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	/**
	 * Implementação do método hashCode para a classe Locus.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( codigo == null ) ? 0 : codigo.hashCode() );
		return result;
	}

	/**
	 * Implementação do método equals para a classe Locus.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Locus other = (Locus) obj;
		if (codigo == null) {
			if (other.codigo != null) {
				return false;
			}
		}
		else
			if (!codigo.equals(other.codigo)) {
				return false;
			}
		return true;
	}

	/**
	 * Define o peso que cada locus tem na definição de
	 * qual fase o doador está, de acordo com o HLA
	 * (se a soma for 1 ou maior, é Fase 2. Caso contrário, Fase 1).
	 * Este é parte do critério, pois os valores alélicos precisam estar em
	 * média ou alta.
	 * @see {@link ComposicaoAlelo}.
	 * 
	 * @return o peso decimal de cada locus.
	 */
	public BigDecimal getPesoFase2() {
		return pesoFase2;
	}

	public void setPesoFase2(BigDecimal pesoFase2) {
		this.pesoFase2 = pesoFase2;
	}
}
