package br.org.cancer.modred.controller.dto.genotipo;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pizão
 * 
 * Classe que representa o GRUPO G no algoritmo que trata o genótipo do paciente.
 */
public class GrupoG extends ValorAlelo{

	/**
	 * Construtor customizado, extendendo a classe abstrata.
	 * Além disso, monta a lista de valores possíveis (nesse caso, somente ele é o valor)
	 * e informado o tipo referente a classe.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo referente ao locus.
	 * @param valoresCompativeis valores possíveis a serem utilizados na conferência de discrepância.
	 */
	public GrupoG(String codigoLocus, String valorAlelo, List<String> valoresCompativeis) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(valoresCompativeis);
		this.setGrupos(Arrays.asList(valorAlelo));
		this.setComposicaoAlelo(ComposicaoAlelo.GRUPO_G);
	}

	@Override
	public ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo) {
		return this;
	}

}
