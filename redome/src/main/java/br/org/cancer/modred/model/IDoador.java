package br.org.cancer.modred.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.SolicitacaoView;

/**
 * Interface que define a identificação do doador.
 * Para cada tipo, um novo atributo é utilizado.
 * 
 * @param <T> define o tipo de retorno da identificação do doador. 
 * @author Pizão
 *
 */
public interface IDoador<T> extends Serializable {

	/**
	 * Cria um retorno para a identificação do doador,
	 * de acordo com o tipo especificado.
	 * 
	 * @return identificação do doador.
	 */
	@JsonView({ SolicitacaoView.detalhe.class, SolicitacaoView.listar.class})
	T getIdentificacao();
	
	/**
	 * Identifica quando o doador, medula ou cordão, é nacional.
	 * @return TRUE quando for nacional.
	 */
	boolean isNacional();
	
	/**
	 * Identifica quando o doador, medula ou cordão, é internacional.
	 * @return TRUE quando for internacional.
	 */
	boolean isInternacional();
	
	/**
	 * Identifica quando o doador é de medula.
	 * @return TRUE quando for medula.
	 */
	boolean isMedula();
	
	/**
	 * Identifica quando o doador é de cordão.
	 * @return TRUE quando for cordão.
	 */
	boolean isCordao();
	
	/**
	 * Indica a fase que o doador está e, caso ocorra match,
	 * define a coluna que será exibido no carrousel.
	 *  
	 * @return Fase que encontra-se o usuário.
	 */
	String getFase();

	
	/**
	 * Identifica quando o doador está inativo.
	 * @return TRUE caso esteja inativo.
	 */
	Boolean isInativo();
}
