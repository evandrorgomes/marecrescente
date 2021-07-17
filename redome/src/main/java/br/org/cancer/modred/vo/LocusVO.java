package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe para passagem de parâmetro entre front e back-end para validação do HLA.
 * 
 * @author Queiroz
 */
public class LocusVO implements Serializable {
    private static final long serialVersionUID = -8536060874590895638L;

    private String codigo;
    private String primeiroAlelo;
    private String segundoAlelo;

    /**
     * Código do locus.
     * 
     * @return codigo do locus
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Seta o codigo do locus.
     * 
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o valor do primeiro alelo.
     * @return
     */
    public String getPrimeiroAlelo() {
        return primeiroAlelo;
    }

    /**
     * Seta o valor do primeiro alelo.
     * @param primeiroAlelo
     */
    public void setPrimeiroAlelo(String primeiroAlelo) {
        this.primeiroAlelo = primeiroAlelo;
    }

    /**
     * Retorna o valor do segundo alelo.
     * @return
     */
    public String getSegundoAlelo() {
        return segundoAlelo;
    }

    /**
     * Seta o valor do segundo alelo.
     * @param segundoAlelo
     */
    public void setSegundoAlelo(String segundoAlelo) {
        this.segundoAlelo = segundoAlelo;
    }
}
