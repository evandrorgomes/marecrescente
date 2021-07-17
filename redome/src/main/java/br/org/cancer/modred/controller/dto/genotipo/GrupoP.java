package br.org.cancer.modred.controller.dto.genotipo;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pizão
 *
 * Classe que representa o GRUPO P no algoritmo que trata o genótipo do paciente.
 */
public class GrupoP extends ValorAlelo {
	
	/**
	 * Construtor customizado, extendendo a classe abstrata.
	 * Além disso, monta a lista de valores possíveis e informado o tipo referente a classe.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo referente ao locus.
	 * @param valoresCompativeis valores a serem utilizados na conferência de discrepância.
	 */
	public GrupoP(String codigoLocus, String valorAlelo, List<String> valoresCompativeis) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(valoresCompativeis);
		this.setGrupos(Arrays.asList(valorAlelo));
		this.setComposicaoAlelo(ComposicaoAlelo.GRUPO_P);
	}

	@Override
	public ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo) {
		return this;
	}

}
