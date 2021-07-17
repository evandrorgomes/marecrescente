package br.org.cancer.modred.util;

import org.apache.commons.lang3.RandomStringUtils;

import br.org.cancer.modred.exception.BusinessException;

/**
 * Gerador de caracteres.
 * 
 * Gera uma String com tamanho especifico com caractes randomicos [0..9, A..Z, a..z].
 * 
 * @author brunosousa
 *
 * */
public class GeradorCaracteres {
	
	private String maiusculos = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String minusculos = "abcdefghijklmnopqrstuvwxyz";
    private String numeros = "1234567890";
    private String especiais = "!@$%&_.";
    
    private boolean usarMaiusculos = true;
    private boolean usarMinusculos = true;
    private boolean usarNumeros = true;
    private boolean usarEspeciais = true;
        
    private int tamanho = 8;
	
	/**
	 * Construtor privado.
	 * Não faz sentido deixar insnciar essa classe
	 * 
	 */
	private GeradorCaracteres(Integer tamanho) {
		if (tamanho != null) {
			this.tamanho = tamanho;
		}
	}
    
    /**
     * String randomica de tamanho length, contendo apenas caracters alfanumericos.
     * @param tamanho
     */
    public static GeradorCaracteres tamanho(Integer tamanho) {
    	return new GeradorCaracteres(tamanho);        
    }
    
    public GeradorCaracteres semMaiusculos() {
    	this.usarMaiusculos = false;
    	return this;
    }
    
    public GeradorCaracteres semMinusculos() {
    	this.usarMinusculos = false;
    	return this;
    }
    
    public GeradorCaracteres semNumeros() {
    	this.usarNumeros = false;
    	return this;
    }
    
    public GeradorCaracteres semCaracteresEspeciais() {
    	this.usarEspeciais = false;
    	return this;
    }
    
    /**
     * Gera a string randômica.
     * @return string randômica.
     */
    public String gerar() {
    	String caracteres = "";
    	
    	if (this.usarEspeciais) {
    		caracteres += this.especiais;
    	}
    	
    	if (this.usarMaiusculos) {
    		caracteres += this.maiusculos;
    	}
    	
    	if (this.usarMinusculos) {
    		caracteres += this.minusculos;
    	}
    	
    	if (this.usarNumeros) {
    		caracteres += this.numeros;
    	}
    	
    	if ("".equals(caracteres)) {
    		throw new BusinessException("");
    	}
    	    	
    	return RandomStringUtils.random(this.tamanho, caracteres);    	
    	
    }
	
	

}
