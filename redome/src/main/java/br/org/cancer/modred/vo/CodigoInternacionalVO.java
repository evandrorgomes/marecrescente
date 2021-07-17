package br.org.cancer.modred.vo;

import java.io.Serializable;

/**
 * Classe de códigos internacionais para preencher a combo no cadastro de telefone.
 * 
 * @author bruno.sousa
 *
 */
public class CodigoInternacionalVO implements Serializable {
    
    private static final long serialVersionUID = 266252108615567201L;

    private String codigo;
    private String texto;
    private String apresentacao;
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
    
    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }
    
    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    /**
     * @return the apresentacao
     */
    public String getApresentacao() {
        return apresentacao;
    }
    
    /**
     * @param apresentacao the apresentacao to set
     */
    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }
    
    
    
    
}
