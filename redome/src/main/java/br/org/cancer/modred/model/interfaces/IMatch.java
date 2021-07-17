package br.org.cancer.modred.model.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 * Interface para padronização de acesso ao métodos do objeto Match, utilizados
 * para match e match preliminar.
 * 
 * @author Pizão
 *
 */
public interface IMatch extends Serializable{
	
	List<? extends IQualificacaoMatch> getQualificacoes();
	
}
