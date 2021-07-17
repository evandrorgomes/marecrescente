package br.org.cancer.modred.controller.dto.genotipo;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pizão
 * 
 * Classe que representa o tipo alelo no algoritmo que trata o genótipo do paciente.
 */
public class Alelo extends ValorAlelo {

	/**
	 * Construtor customizado, extendendo a classe abstrata.
	 * Além disso, monta a lista de valores possíveis (nesse caso, somente ele é o valor)
	 * e informado o tipo referente a classe.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo referente ao locus.
	 * @param grupos - grupos em que o valorAlelo faz parte. 
	 */
	public Alelo(String codigoLocus, String valorAlelo, List<String> grupos) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(Arrays.asList(valorAlelo));
		this.setGrupos(grupos);
		this.setComposicaoAlelo(ComposicaoAlelo.ALELO);
	}

	@Override
	public ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo) {
		if(this.getValor().length() > alelo.getValor().length()){
			return this;
		}
		else if(this.getValor().length() < alelo.getValor().length()){
			return alelo;
		}
		return retornarAleloMaisRecente(alelo);
	}

}
