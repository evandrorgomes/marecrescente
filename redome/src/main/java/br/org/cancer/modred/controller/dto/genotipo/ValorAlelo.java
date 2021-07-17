package br.org.cancer.modred.controller.dto.genotipo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Exame;

/**
 * @author Pizão
 *
 * Classe abstrata criada para generalizar as validações em cima do valor alélico, independente do seu tipo.
 * Foi necessário guardar alguns atributos como exame, posição e código locus para que seja possível identificar
 * o exame que está sendo verificado e, se necessário, saber a ordem cronológica para o paciente.
 */
public abstract class ValorAlelo {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValorAlelo.class);
	
	private String codigoLocus;
	private String valor;
	private List<String> valoresCompativeis;
	private Exame exame;
	private Integer posicao;
	private ComposicaoAlelo composicaoAlelo;
	private Boolean divergente;
	private List<String> grupos;
	
	/**
	 * Construtor customizado para obrigar a instanciar ser criada 
	 * com código locus e valor alelo, além proteger a informação de
	 * possíveis alterações.
	 * 
	 * @param codigoLocus código do locus.
	 * @param valorAlelo vlaor do alelo referente ao locus.
	 */
	public ValorAlelo(String codigoLocus, String valorAlelo) {
		this.codigoLocus = codigoLocus;
		this.valor = valorAlelo;
	}

	/**
	 * Retorna o valor do alelo informado.
	 * 
	 * @return
	 */
	public String getValor(){
		return valor;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public List<String> getValoresCompativeis() {
		return valoresCompativeis;
	}

	protected void setValoresCompativeis(List<String> valoresCompativeis) {
		this.valoresCompativeis = valoresCompativeis;
	}
	
	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public Integer getPesoResolucao(){
		return this.composicaoAlelo.getPeso();
	}
	
	/**
	 * Caso os alelos sejam do mesmo "tipo", realiza o desempate entre os dois
	 * para determinar o de maior resolução.
	 * 
	 * @param valorAlelo alelo para ser comparado.
	 * @return alelo de maior resolução.
	 */
	public abstract ValorAlelo desempatarMelhorResolucao(ValorAlelo alelo);
	
	/**
	 * Verifica se, o valor passado no parâmetro, é compatível com o valor informado.
	 * 
	 * @param alelo a ser verificado a compatibilidade.
	 * @return a verificação se é compatível (TRUE) ou não (FALSE).
	 */
	public boolean discrepante(ValorAlelo alelo){
		List<String> valoresThis = null;
		List<String> valoresRef = null;
		
		if(this instanceof Nmdp || this instanceof GrupoP || this instanceof GrupoG || 
					alelo instanceof Nmdp || alelo instanceof GrupoP || alelo instanceof GrupoG){
			valoresThis = obterValoresCompativeisReduzidos(2);
			valoresRef = alelo.obterValoresCompativeisReduzidos(2);
		}
		else if(this instanceof Sorologico || alelo instanceof Sorologico || 
					this instanceof Antigeno || alelo instanceof Antigeno || 
						this instanceof Alelo || alelo instanceof Alelo){
			/**
			 * Realizo um teste com todo conjunto antes, pois pode ocorrer de um alelo específico 
			 * estar contido, por inteiro, na lista de possibilidade de um grupo NMDP, por exemplo,
			 * e nesse caso não preciso reduzir as listas para comparação.
			 */
			valoresThis = getValoresCompativeis();
			valoresRef = alelo.getValoresCompativeis();
			boolean isCompativel = 
				(valoresThis.containsAll(valoresRef) || valoresRef.containsAll(valoresThis));
			
			if(!isCompativel){
				Integer reducaoThis = obterMenorReducao();
				Integer reducaoAlelo = alelo.obterMenorReducao();
				Integer reducao = reducaoThis < reducaoAlelo ? reducaoThis : reducaoAlelo;
				
				valoresThis = obterValoresCompativeisReduzidos(reducao);
				valoresRef = alelo.obterValoresCompativeisReduzidos(reducao);
			}
			
		}
		else {
			LOGGER.error("Formato/Valor alelo ({} / {}) não reconhecidos.", alelo.getClass(), alelo.valor);
			throw new BusinessException("erro.interno");
		}
		
		return !(valoresThis.containsAll(valoresRef) || valoresRef.containsAll(valoresThis) || mesmoGrupo(alelo));
		
	}
	
	private Boolean mesmoGrupo(ValorAlelo alelo) {
		if (this.grupos == null || alelo.getGrupos() == null) {
			return false;
		}
		return !Collections.disjoint(new HashSet<>(this.grupos), alelo.getGrupos());
	}
	
	/**
	 * Obtém a melhor resolução entre os dois alelos, o {{this}} e o alelo
	 * passado no parâmetro, e retorna o de melhor resolução.
	 * 
	 * @param alelo a ser comparado.
	 * @return o alelo de melhor resolução.
	 */
	public ValorAlelo melhorResolucao(ValorAlelo alelo){
		if(this.getPesoResolucao() > alelo.getPesoResolucao()){
			return this;
		}
		else if(this.getPesoResolucao() < alelo.getPesoResolucao()){
			return alelo;
		}
		return desempatarMelhorResolucao(alelo);
	}
	
	/**
	 * Retorna o valor do alelo no formato (Formato 00:00).
	 * 
	 * @param valorAlelo valor do alelo a ser reduzido.
	 * @param reducao quantidade de grupos que o valor deve ser reduzido.
	 * @return valor do alelo com a redução.
	 */
	public String obterValorReduzido(String valorAlelo, Integer reducao) {
		String valorFormatadoComparacao = "";
		String[] valorSplitado = valorAlelo.split(":");
		
		if(valorSplitado.length > reducao){
			for (int i = 0; i < reducao; i++) {
				valorFormatadoComparacao += valorSplitado[i];
				if(i < (reducao - 1)){
					valorFormatadoComparacao += ":";
				}
			}
		}
		else {
			valorFormatadoComparacao = valorAlelo;
		}
		return valorFormatadoComparacao;
	}
	
	/**
	 * Retorna uma lista de valores reduzidos para comparação (Formato 00:00).
	 * 
	 * @param reducao quantidade de grupos que o valor deve ser reduzido.
	 * @return lista de valores reduzidos.
	 */
	public List<String> obterValoresCompativeisReduzidos(Integer reducao){
		List<String> valoresParciais = new ArrayList<String>();
		List<String> valoresCompativeis = getValoresCompativeis();

		if (CollectionUtils.isNotEmpty(valoresCompativeis)) {
			valoresCompativeis.forEach(valor -> {
				valoresParciais.add(obterValorReduzido(valor, reducao));
			});
		}
		return valoresParciais.stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * Retorna a quantidade de divisões (separados por dois pontos), o valor do 
	 * alelo possui.
	 * 
	 * @return quantidade de grupos.
	 */
	private Integer obterQuantidadeGrupos(String valorAlelo){
		String[] valorSplitado = valorAlelo.split(":");
		return valorSplitado.length;
	}
	
	/**
	 * Retorna o valor com menor grupamento entre os dois informados.
	 * 
	 * @return menor grupamento de alelo entre os dois.
	 */
	public Integer obterMenorReducao(){
		List<String> valoresAlelo = getValoresCompativeis();
		Integer menorReducao = 0;
		
		for (String valorAlelo : valoresAlelo) {
			Integer reducaoAlelo = obterQuantidadeGrupos(valorAlelo);
			if(menorReducao < reducaoAlelo){
				menorReducao = reducaoAlelo;
			}
		}
		
		return menorReducao;
	}
	
	/**
	 * Retorna o alelo mais antigo entre o {{this}} e o {{alelo}}.
	 * 
	 * @param alelo alelo usado na comparação.
	 * @return alelo mais antigo.
	 */
	public ValorAlelo retornarAleloMaisAntigo(ValorAlelo alelo){
		if(this.getExame().getDataCriacao().isBefore(alelo.getExame().getDataCriacao())){
			return this;
		}
		return alelo;
	}
	
	/**
	 * Retorna o alelo mais novo entre o {{this}} e o {{alelo}}.
	 * 
	 * @param alelo alelo usado na comparação.
	 * @return alelo mais novo.
	 */
	public ValorAlelo retornarAleloMaisRecente(ValorAlelo alelo){
		if(this.getExame().getDataCriacao().isAfter(alelo.getExame().getDataCriacao())){
			return this;
		}
		return alelo;
	}

	public ComposicaoAlelo getComposicaoAlelo() {
		return composicaoAlelo;
	}

	protected void setComposicaoAlelo(ComposicaoAlelo composicaoAlelo) {
		this.composicaoAlelo = composicaoAlelo;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public String getCodigoLocus() {
		return codigoLocus;
	}
	
	
	
	/**
	 * @return the divergente
	 */
	public Boolean getDivergente() {
		return divergente;
	}

	/**
	 * @param divergente the divergente to set
	 */
	public void setDivergente(Boolean divergente) {
		this.divergente = divergente;
	}
	

	/**
	 * @return the grupo
	 */
	public List<String> getGrupos() {
		return grupos;
	}

	/**
	 * @param grupo the grupo to set
	 */
	protected void setGrupos(List<String> grupos) {
		this.grupos = grupos;
	}

	@Override
	public String toString(){
		return codigoLocus + "*" + valor + "(" + posicao + ")";
	}
}
