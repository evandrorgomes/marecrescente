package br.org.cancer.modred.controller.dto.genotipo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author Pizão
 * 
 * Classe que representa o SOROLOGICO no algoritmo que trata o genótipo do paciente.
 */
public class Sorologico extends ValorAlelo {

	public static final String PREFIXO_SOROLOGIA = "s";
	
	/**
	 * Construtor customizado, extendendo a classe abstrata.
	 * Além disso, monta a lista de valores possíveis (nesse caso, somente ele é o valor)
	 * e informado o tipo referente a classe.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo valor do alelo referente ao locus.
	 * @param valoresCompativeis valores compatíveis com o valor sorologico informado.
	 */
	public Sorologico(String codigoLocus, String valorAlelo, List<String> valoresCompativeis) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(obterValoresNormalizadosParaComparacao(valoresCompativeis));
		this.setComposicaoAlelo(ComposicaoAlelo.SOROLOGICO);
	}
	
	/**
	 * Retorna uma lista de valores normalizados para comparação.
	 * Isso é feito para facilitar a comparação com os outros valores de antígeno, que possuem 2 dígitos.
	 * Ex.: Se valor 3 passará a 03.
	 * 
	 * @return lista de valores normalizados para comparação.
	 */
	private List<String> obterValoresNormalizadosParaComparacao(List<String> valoresCompativeis){
		List<String> valoresParciais = new ArrayList<String>();

		if (CollectionUtils.isNotEmpty(valoresCompativeis)) {
			valoresCompativeis.forEach(valor -> {
				valoresParciais.add(valor.length() == 1 ? "0".concat(valor) : valor);
			});
		}
		return valoresParciais.stream().collect(Collectors.toList());
	}
	
	@Override
	public ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo) {
		int valoresThis = getValoresCompativeis().size();
		int valoresRef = alelo.getValoresCompativeis().size();
		
		if(valoresThis < valoresRef){
			return this;
		}
		else if(valoresThis > valoresRef){
			return alelo;
		}
		return retornarAleloMaisRecente(alelo);
	}
	
	@Override
	public String obterValorReduzido(String valorAlelo, Integer reducao) {
		if(valorAlelo.length() <= reducao){
			return valorAlelo;
		}
		return valorAlelo.substring(valorAlelo.length() - reducao);
	}

}
