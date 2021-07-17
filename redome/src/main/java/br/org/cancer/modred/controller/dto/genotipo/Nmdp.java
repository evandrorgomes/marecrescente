package br.org.cancer.modred.controller.dto.genotipo;

import java.util.List;

/**
 * @author Pizão
 *
 * Classe que representa o NMDP no algoritmo que trata o genótipo do paciente.
 */
public class Nmdp extends ValorAlelo{
	
	private String codigo;
	private String antigeno;

	/**
	 * Construtor customizado para obrigar a instanciar ser criada 
	 * com código locus e valor alelo, além proteger a informação de
	 * possíveis alterações.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo vlaor do alelo referente ao locus.
	 * @param valoresCompativeis valores contidos na lista de compatíveis com o código NMPD informado e
	 * que serão utilizados para definição discrepância.
	 * @param grupos valores do grupo P ou G que os valores conpativeis fazem parte
	 */
	public Nmdp(String codigoLocus, String valorAlelo, List<String> valoresCompativeis, List<String> grupos) {
		super(codigoLocus, valorAlelo);
		this.setValoresCompativeis(valoresCompativeis);
		this.setGrupos(grupos);
		this.antigeno = obterAntigeno(valorAlelo);
		this.codigo = obterCodigoNmdp(valorAlelo);
		this.setComposicaoAlelo(ComposicaoAlelo.NMDP);
	}
	
	/**
	 * Extrai, a partir do valor informado, a informação 
	 * referente ao antígeno.
	 * 
	 * @param valorAlelo a ser extraída a informação.
	 * @return o antígeno referente ao valor do alelo.
	 */
	public static String obterAntigeno(String valorAlelo) {
		String[] valorSplitado = valorAlelo.split(":");
		return valorSplitado[0];
	}
	
	/**
	 * Extrai, a partir do valor informado, a informação 
	 * referente ao código NMDP.
	 * 
	 * @param valorAlelo a ser extraída a informação.
	 * @return o código NMDP referente ao valor do alelo.
	 */
	public static String obterCodigoNmdp(String valorAlelo) {
		String[] valorSplitado = valorAlelo.split(":");
		return valorSplitado[1];
	}
	
	/**
	 * Retorna o antígeno (informação antes dos : iniciais)
	 * do valor alélico informado.
	 * 
	 * @return string com o valor do antígeno.
	 */
	public String getAntigeno() {
		return antigeno;
	}
	
	/**
	 * Retorna o código NMDP do alelo informado.
	 * 
	 * @return string com o código NMDP.
	 */
	public String getCodigo() {
		return codigo;
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
}
