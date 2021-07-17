package br.org.cancer.redome.tarefa.exception;

import java.util.ArrayList;
import java.util.List;

import br.org.cancer.redome.tarefa.util.CampoMensagem;

/**
 * Classe customizada de exceção para encapsular erros de validação na camada
 * de negócio da aplicação.
 * 
 * @author Cintia Oliveira
 *
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 5426702009759111353L;
    
    private List<CampoMensagem> erros;
    
    /**
     * @param message m
     * @param erros lista de erros
     */
    public ValidationException(String message, List<CampoMensagem> erros) {
        super(message);
        
        if (erros != null && !erros.isEmpty()){
            this.erros = new ArrayList<>();
            this.erros.addAll(erros);
        }
    }

    /**
     * @return the erros
     */
    public List<CampoMensagem> getErros() {
        return erros;
    }
    
    /**
     * @param erros the erros to set
     */
    public void setErros(List<CampoMensagem> erros) {
        this.erros = erros;
    }
}
