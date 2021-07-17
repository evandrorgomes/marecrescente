package br.org.cancer.modred.controller.dto.genotipo;

import java.util.Arrays;

/**
 * @author Pizão
 * 
 * Classe que representa o ANTIGENO no algoritmo que trata o genótipo do paciente.
 */
public class Antigeno extends ValorAlelo {
	public static final String SUFIXO_NOVO = "NOVO";
	public static final String SUFIXO_XX = "XX";

	/**
	 * Construtor customizado, extendendo a classe abstrata.
	 * Além disso, monta a lista de valores possíveis e informado o tipo referente a classe.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo referente ao locus.
	 */
	public Antigeno(String codigoLocus, String valorAlelo) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(Arrays.asList(valorAlelo));
		this.setComposicaoAlelo(ComposicaoAlelo.ANTIGENO);
	}

	@Override
	public ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo) {
		return retornarAleloMaisRecente(alelo);
	}
	
	@Override
	public String obterValorReduzido(String valorAlelo, Integer reducao) {
		return valorAlelo.endsWith(Antigeno.SUFIXO_NOVO) ? valorAlelo.split(":")[0] : valorAlelo;
	}


}
