package br.org.cancer.modred.model.interfaces;

import java.io.Serializable;

import br.org.cancer.modred.model.Locus;

/**
 * Define os métodos de acesso aos objetos Qualificação Match.
 * 
 * @author Pizão.
 *
 */
public interface IQualificacaoMatch extends Serializable{

	Long getId();
	void setId(Long id);

	Locus getLocus();
	void setLocus(Locus locus);

	Integer getPosicao();
	void setPosicao(Integer posicao);

	String getQualificacao();
	void setQualificacao(String qualificacao);
	
	String getGenotipo();
	void setGenotipo(String genotipo);
	
	Integer getTipo();
	void setTipo(Integer tipo);
	
	String getProbabilidade();
	void setProbabilidade(String probabilidade);
	
	IMatch getMatch();
	//void setMatch(Match match);
	
}
