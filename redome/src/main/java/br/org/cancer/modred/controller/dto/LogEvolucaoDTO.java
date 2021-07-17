package br.org.cancer.modred.controller.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.LogEventoView;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;

/**
 * Classe de DTO para informações sobre as atualizações o log de evolução.
 * 
 * @author Pizão
 */
public class LogEvolucaoDTO implements Serializable{
	private static final long serialVersionUID = 5492148644708448484L;
	
	@JsonView(LogEventoView.Lista.class)
	private Long rmr;
	
	@JsonView(LogEventoView.Lista.class)
	private TipoLogEvolucao tipoLogEvolucao;
	
	@JsonView(LogEventoView.Lista.class)
	private String descricao;
	
	@JsonView(LogEventoView.Lista.class)
	private LocalDateTime dataInclusao;
	
	@JsonView(LogEventoView.Lista.class)
	private String usuarioResponsavel;
	
	@JsonView(LogEventoView.Lista.class)
	private String parametros;
	
	private List<Long> pefisExcluidos;
	
	@JsonView(LogEventoView.Lista.class)
	private Long totalRegistros;

	@Override
	public String toString(){
		return getDescricao();
	}

	/**
	 * Data que o log foi incluído.
	 * @return data da inclusão.
	 */
	public LocalDateTime getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(LocalDateTime dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	/**
	 * Mensagem de descrição do log (é uma "tradução" do tipo de evento do log informado).
	 * 
	 * @return texto descrevendo o log.
	 */
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Retorna o RMR do paciente associado ao log.
	 * 
	 * @return RMR do paciente.
	 */
	public Long getRmr() {
		return rmr;
	}

	public void setRmr(Long rmr) {
		this.rmr = rmr;
	}

	/**
	 * Tipo do log de evolução. Isto define qual a mensagem referente
	 * a essa evolução.
	 * 
	 * @return Tipo do log de evolução.
	 */
	public TipoLogEvolucao getTipoLogEvolucao() {
		return tipoLogEvolucao;
	}

	public void setTipoLogEvolucao(TipoLogEvolucao tipoLogEvolucao) {
		this.tipoLogEvolucao = tipoLogEvolucao;
	}

	/**
	 * Parametros, faz referência a informação que deverá 
	 * compor a mensagem exibida no log.
	 * 
	 * @return vetor de parametros.
	 */
	public String[] getParametros() {
		if (parametros != null) {
			return parametros.split(";");
		}
		return null;
	}

	/**
	 * Seta os parametros.
	 * 
	 * @param parametros para serem utilizados na mensagem
	 */
	public void setParametros(String[] parametros) {
		if (parametros != null) {
			this.parametros = String.join(";", parametros);
		}
		else {
			this.parametros = null;
		}
	}

	/**
	 * @return the pefisExcluidos
	 */
	public List<Long> getPefisExcluidos() {
		return pefisExcluidos;
	}

	/**
	 * @param pefisExcluidos the pefisExcluidos to set
	 */
	public void setPefisExcluidos(List<Long> pefisExcluidos) {
		this.pefisExcluidos = pefisExcluidos;
	}

	/**
	 * @return the totalRegistros
	 */
	public Long getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Long totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the usuarioResponsavel
	 */
	public String getUsuarioResponsavel() {
		return usuarioResponsavel;
	}

	/**
	 * @param usuarioResponsavel the usuarioResponsavel to set
	 */
	public void setUsuarioResponsavel(String usuarioResponsavel) {
		this.usuarioResponsavel = usuarioResponsavel;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	
}
