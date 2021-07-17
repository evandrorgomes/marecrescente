package br.org.cancer.modred.model.formulario;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.FormularioView;
import br.org.cancer.modred.model.domain.TiposPergunta;

/**
 * Classe que representa a pergunta de um formulário.
 * 
 * @author bruno.sousa
 *
 */
public class Pergunta implements Serializable {
	
	private static final long serialVersionUID = 6490501007969927857L;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String marcador;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String id;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String descricao;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String tipo;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean possuiDependencia = false;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Long tamanho = 0L;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String resposta;
	
	private String valorDefault = "";
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private List<OpcaoPergunta> opcoes;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private List<PerguntaDependente> dependentes;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private FormatoNumerico formatoNumerico;
	
	private CondicaoPergunta condicao;
	
	private Boolean removerPergunta = false;

	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean comValidacao = true;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private Boolean comErro = false;
	
	@JsonView(value = { FormularioView.Formulario.class, FormularioView.FormularioRespondido.class})
	private String alinhamento = "V";
	
	/**
	 * Contrutor padrão.
	 */
	public Pergunta() {
	}
	
	/**
	 * @return the marcador
	 */
	public String getMarcador() {
		return marcador;
	}

	/**
	 * @param marcador the marcador to set
	 */
	public void setMarcador(String marcador) {
		this.marcador = marcador;
	}

	/**
	 * Identificador da pergunta <b>Token</b>.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	
	/**
	 * Identificador da pergunta <b>Token</b>.
	 * 
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * Descrição da pergunta.
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	
	/**
	 * Descrição da pergunta.
	 * 
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	/**
	 * Tipo da Pergunta.
	 * 
	 * @return the tipo
	 */
	public TiposPergunta getTipo() {
		return TiposPergunta.valueOf(tipo);
	}

	
	/**
	 * Tipo da Pergunta.
	 * 
	 * @param tipo the tipo to set
	 */
	public void setTipo(TiposPergunta tipo) {
		if (tipo != null) {
			this.tipo = tipo.toString();
		}
	}

	
	/**
	 * Informa se a pergunta possui depedência de outra pergunta.
	 * 
	 * @return the possuiDependencia
	 */
	public Boolean getPossuiDependencia() {
		return possuiDependencia;
	}
	
	/**
	 * Informa se a pergunta possui depedência de outra pergunta.
	 * 
	 * @param possuiDependencia the possuiDependencia to set
	 */
	public void setPossuiDependencia(Boolean possuiDependencia) {
		this.possuiDependencia = possuiDependencia;
	}
	
	/**
	 * Informa a quantidade de caracteres para os tipos TEXT e TEXTAREA.
	 * 
	 * @return the tamanho
	 */
	public Long getTamanho() {
		return tamanho;
	}
	
	/**
	 * Informa a quantidade de caracteres para os tipos TEXT e TEXTAREA.
	 * 
	 * @param tamanho the tamanho to set
	 */
	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}
	
	/**
	 * Valor da resposta da pergunta.
	 * 
	 * @return the resposta
	 */
	public String getResposta() {
		return resposta;
	}
	
	/**
	 * Valor da resposta da pergunta.
	 * 
	 * @param resposta the resposta to set
	 */
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
	/**
	 * Valor default para a pergunta, podendo ser um valor qualquer ou um token.
	 * Caso exista valor nesta propriedade, será colocada na prorpriedade resposta.
	 * 
	 * @return the valorDefault
	 */
	public String getValorDefault() {
		return valorDefault;
	}
	
	/**
	 * Valor default para a pergunta, podendo ser um valor qualquer ou um token.
	 * Caso exista valor nesta propriedade, será colocada na prorpriedade resposta.
	 * 
	 * @param valorDefault the valorDefault to set
	 */
	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}
	
	/**
	 * Lista de opções para os tipos RADIO, CHECK ou SELECT.
	 * 
	 * @return the opcoes
	 */
	public List<OpcaoPergunta> getOpcoes() {
		return opcoes;
	}
	
	/**
	 * Lista de opções para os tipos RADIO, CHECK ou SELECT.
	 * 
	 * @param opcoes the opcoes to set
	 */
	public void setOpcoes(List<OpcaoPergunta> opcoes) {
		this.opcoes = opcoes;
	}
	
	/**
	 * Lista de perguntas dependentes.
	 * 
	 * @return the dependentes
	 */
	public List<PerguntaDependente> getDependentes() {
		return dependentes;
	}
	
	/**
	 * Lista de perguntas dependentes.
	 * 
	 * @param dependentes the dependentes to set
	 */
	public void setDependentes(List<PerguntaDependente> dependentes) {
		this.dependentes = dependentes;
	}

	/**
	 * @return the condicao
	 */
	public CondicaoPergunta getCondicao() {
		return condicao;
	}

	/**
	 * @param condicao the condicao to set
	 */
	public void setCondicao(CondicaoPergunta condicao) {
		this.condicao = condicao;
	}

	/**
	 * @return the removerPergunta
	 */
	public Boolean getRemoverPergunta() {
		return removerPergunta;
	}

	/**
	 * @param removerPergunta the removerPergunta to set
	 */
	public void setRemoverPergunta(Boolean removerPergunta) {
		this.removerPergunta = removerPergunta;
	}

	/**
	 * @return the formatoNumerico
	 */
	public FormatoNumerico getFormatoNumerico() {
		return formatoNumerico;
	}

	/**
	 * @param formatoNumerico the formatoNumerico to set
	 */
	public void setFormatoNumerico(FormatoNumerico formatoNumerico) {
		this.formatoNumerico = formatoNumerico;
	}
	
	/**
	 * @return the comValidacao
	 */
	public Boolean getComValidacao() {
		return comValidacao;
	}

	/**
	 * @param comValidacao the comValidacao to set
	 */
	public void setComValidacao(Boolean comValidacao) {
		this.comValidacao = comValidacao;
	}

	/**
	 * @return the comErro
	 */
	public Boolean getComErro() {
		return comErro;
	}

	/**
	 * @param comErro the comErro to set
	 */
	public void setComErro(Boolean comErro) {
		this.comErro = comErro;
	}

	/**
	 * @return the alinhamento
	 */
	public String getAlinhamento() {
		return alinhamento;
	}

	/**
	 * @param alinhamento the alinhamento to set
	 */
	public void setAlinhamento(String alinhamento) {
		this.alinhamento = alinhamento;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( dependentes == null ) ? 0 : dependentes.hashCode() );
		result = prime * result + ( ( descricao == null ) ? 0 : descricao.hashCode() );
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( opcoes == null ) ? 0 : opcoes.hashCode() );
		result = prime * result + ( ( possuiDependencia == null ) ? 0 : possuiDependencia.hashCode() );
		result = prime * result + ( ( resposta == null ) ? 0 : resposta.hashCode() );
		result = prime * result + ( ( tamanho == null ) ? 0 : tamanho.hashCode() );
		result = prime * result + ( ( tipo == null ) ? 0 : tipo.hashCode() );
		result = prime * result + ( ( valorDefault == null ) ? 0 : valorDefault.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!( obj instanceof Pergunta )) {
			return false;
		}
		Pergunta other = (Pergunta) obj;
		if (dependentes == null) {
			if (other.dependentes != null) {
				return false;
			}
		}
		else
			if (!dependentes.equals(other.dependentes)) {
				return false;
			}
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
		if (opcoes == null) {
			if (other.opcoes != null) {
				return false;
			}
		}
		else
			if (!opcoes.equals(other.opcoes)) {
				return false;
			}
		if (possuiDependencia == null) {
			if (other.possuiDependencia != null) {
				return false;
			}
		}
		else
			if (!possuiDependencia.equals(other.possuiDependencia)) {
				return false;
			}
		if (resposta == null) {
			if (other.resposta != null) {
				return false;
			}
		}
		else
			if (!resposta.equals(other.resposta)) {
				return false;
			}
		if (tamanho == null) {
			if (other.tamanho != null) {
				return false;
			}
		}
		else
			if (!tamanho.equals(other.tamanho)) {
				return false;
			}
		if (tipo == null) {
			if (other.tipo != null) {
				return false;
			}
		}
		else
			if (!tipo.equals(other.tipo)) {
				return false;
			}
		if (valorDefault == null) {
			if (other.valorDefault != null) {
				return false;
			}
		}
		else
			if (!valorDefault.equals(other.valorDefault)) {
				return false;
			}
		return true;
	}

}
