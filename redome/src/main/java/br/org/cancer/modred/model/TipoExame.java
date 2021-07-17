package br.org.cancer.modred.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.BuscaView;
import br.org.cancer.modred.controller.view.LaboratorioView;
import br.org.cancer.modred.controller.view.PedidoExameView;
import br.org.cancer.modred.controller.view.TipoExameView;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

/**
 * Referência ao tipo de exame.
 * 
 * @author bruno sousa.
 */
@Entity
@Table(name = "TIPO_EXAME")
public class TipoExame implements Serializable {
	
	private static final long serialVersionUID = -4462617827180995547L;

	@Id
	@Column(name = "TIEX_ID")
	@JsonView({TipoExameView.Listar.class, PedidoExameView.Detalhe.class})
	private Long id;

	@Column(name = "TIEX_TX_DESCRICAO")
	@JsonView({	TipoExameView.Listar.class, PedidoExameView.Detalhe.class, 
				BuscaView.UltimoPedidoExame.class, PedidoExameView.ListarTarefas.class, LaboratorioView.ListarReceberExame.class})
	private String descricao;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(	name = "TIPO_EXAME_LOCUS",
				joinColumns =
	{ @JoinColumn(name = "TIEX_ID") },
				inverseJoinColumns =
	{ @JoinColumn(name = "LOCU_ID") })
	@JsonView(PedidoExameView.Detalhe.class)
	@IgnoreSizeOf
	private List<Locus> locus;
	

	public TipoExame() {}

	public TipoExame(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

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
		TipoExame other = (TipoExame) obj;
		if (descricao == null) {
			if (other.descricao != null) {
				return false;
			}
		}
		else
			if (!descricao.equals(other.descricao)) {
				return false;
			}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else
			if (!id.equals(other.id)) {
				return false;
			}
		return true;
	}

	/**
	 * Retorna a lista de lócus associados ao tipo de exame.
	 * @return lista de lócus.
	 */
	public List<Locus> getLocus() {
		return locus;
	}

	public void setLocus(List<Locus> locus) {
		this.locus = locus;
	}
	
}