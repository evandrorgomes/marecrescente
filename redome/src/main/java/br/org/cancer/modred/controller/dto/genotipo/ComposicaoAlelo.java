package br.org.cancer.modred.controller.dto.genotipo;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.interfaces.EnumType;

/**
 * @author Pizão
 * 
 * Classe enum que representa os tipos possíveis de alelo contidos em um
 * exame de HLA.
 */
public enum ComposicaoAlelo implements EnumType<Integer>{
	SOROLOGICO(0, Resolucao.BAIXA), 
	ANTIGENO(1, Resolucao.BAIXA), 
	NMDP(2, Resolucao.MEDIA), 
	GRUPO_G(3, Resolucao.MEDIA), 
	GRUPO_P(4, Resolucao.MEDIA),
	ALELO(5, Resolucao.ALTA);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComposicaoAlelo.class);
	
	private Integer peso;
	private Resolucao resolucao;
	
	
	ComposicaoAlelo(Integer peso, Resolucao resolucao){
		this.peso = peso;
		this.resolucao = resolucao;
	}

	public Integer getPeso() {
		return peso;
	}
	
	public Resolucao getResolucao(){
		return this.resolucao;
	}
	
	public Integer getId() {
		return peso;
	}
	
	/**
	 * Verifica qual a composição do valor alélico informado.
	 * 
	 * @param valorAlelico valor a ser verificado.
	 * @return o enum representando a composição.
	 */
	public static ComposicaoAlelo obterTipo(String valorAlelico){
		boolean naoTemProteina = !valorAlelico.contains(":");
		
		if(naoTemProteina && valorAlelico.startsWith(Sorologico.PREFIXO_SOROLOGIA)){
			return ComposicaoAlelo.SOROLOGICO;
		}
		else if(naoTemProteina || valorAlelico.endsWith(Antigeno.SUFIXO_NOVO)){
			return ComposicaoAlelo.ANTIGENO;
		}
		else if(valorAlelico.endsWith(Antigeno.SUFIXO_XX)){
			return ComposicaoAlelo.ANTIGENO;
		}
		else{
			String[] valores = valorAlelico.split(":");
			String proteina = valores[1];
			
			if(isNmdp(proteina)){
				return ComposicaoAlelo.NMDP;
			}
			else if(isGrupoP(proteina)){
				return ComposicaoAlelo.GRUPO_P;
			}
			else if(valores.length == 3 && isGrupoG(valorAlelico)){
				return ComposicaoAlelo.GRUPO_G;
			}
			else if(isAlelo(proteina)){
				return ComposicaoAlelo.ALELO;
			}
			LOGGER.error("Valor alelo não reconhecido. "
					+ "Não foi possível descobrir o tipo a partir o valor recebido (" + valorAlelico + ").");
			throw new BusinessException("erro.interno");
		}
	}

	private static boolean terminarComLetra(String proteina, String letra) {
		return proteina.endsWith(letra);
	}

	private static boolean isNmdp(String valor) {
		return StringUtils.isAlpha(valor);
	}
	
	private static boolean isGrupoP(String valor) {
		return terminarComLetra(valor, "P");
	}
	
	private static boolean isGrupoG(String valor) {
		return terminarComLetra(valor, "G");
	}
	
	private static boolean isAlelo(String proteina){
		boolean isCorreto = StringUtils.isNumeric(proteina);
		if(!isCorreto){
			String parteNumericaSegundoGrupo = proteina.substring(0, proteina.length()-1);
			String ultimoCaracterGrupo = proteina.substring(proteina.length()-1);
			isCorreto = StringUtils.isNumeric(parteNumericaSegundoGrupo) && StringUtils.isAlpha(ultimoCaracterGrupo);
		}
		return isCorreto;
	}
	
	/**
	 * Transforma o ID passado em um enum conhecido desta classe.
	 * Caso não consiga, uma exceção será lançada indicando a falha, provavelmente,
	 * de programação ou banco.
	 * 
	 * @param id ID a ser "convertido" em enum.
	 * @return enum convertido a partir do ID.
	 */
	public static ComposicaoAlelo valueOf(Integer id) {
		Optional<ComposicaoAlelo> composicaoAlelo = Arrays.asList(values()).stream().filter(value -> {
			return value.getId().equals(id);
		}).findFirst();
		
		if(composicaoAlelo.isPresent()){
			return composicaoAlelo.get();
		}
		throw new IllegalArgumentException("ComposicaoAlelo com ID " + id + " não é conhecida.");
	}
	
	/**
	 * Especifica as resoluções possíveis para o valor do alelo.
	 * 
	 * @author Pizão
	 *
	 */
	public enum Resolucao {
		BAIXA, MEDIA, ALTA
	}
}

