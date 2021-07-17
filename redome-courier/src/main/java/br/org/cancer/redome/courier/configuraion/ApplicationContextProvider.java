package br.org.cancer.redome.courier.configuraion;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Classe criada para obter beans do spring onde não é possível injetar.
 * 
 * @author bruno.sousa
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext APPLICATION_CONTEXT = null;

	/**
	 * Método que obtem um bean específico pelo nome.
	 * 
	 * @param nome
	 * @return
	 */
	public static Object obterBean(String nome) {
		return APPLICATION_CONTEXT.getBean(nome);
	}
	
	/**
	 * Método que obtem um bean específico pela classe.
	 * 
	 * @param nome
	 * @return
	 */
	public static <T> T obterBean(Class<T> classe) {
		return APPLICATION_CONTEXT.getBean(classe);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.APPLICATION_CONTEXT = applicationContext;
	}
	
	
	/**
	 * Método que obtem um bean específico pela classe.
	 * 
	 * @param nome
	 * @return
	 */
	public Object obterBeanJunit(Class<?> classe) {
		return obterBean(classe);
	}

}
