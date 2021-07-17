package br.org.cancer.redome.tarefa.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe que representa um lócus.
 * 
 * @author Rafael Pizão
 *
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Locus implements Serializable {

    private static final long serialVersionUID = -2314826389349549556L;
    
    
    public static final String LOCUS_A = "A";
    public static final String LOCUS_B = "B";
    public static final String LOCUS_DRB1 = "DRB1";
    public static final String LOCUS_C = "C";
    public static final String LOCUS_DQB1 = "DQB1";
    public static final String LOCUS_DPA1 = "DPA1";
    public static final String LOCUS_DPB1 = "DPB1";
    public static final String LOCUS_DRB3 = "DRB3";
    public static final String LOCUS_DRB4 = "DRB4";
    public static final String LOCUS_DRB5 = "DRB5";
    
    private String codigo;
    
    private Integer ordem;
        
}
