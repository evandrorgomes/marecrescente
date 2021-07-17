package br.org.cancer.modred.helper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Classe helper para injeção de dependencia em outras classes.
 * 
 * @author Bruno Souza
 *
 */
@Component
public class AutowireHelper implements ApplicationContextAware {

	private static ApplicationContext APPLICATION_CONTEXT;

	private AutowireHelper() {}

	public static void autowire(Object classToAutowire) {
		AutowireHelper.APPLICATION_CONTEXT.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		AutowireHelper.APPLICATION_CONTEXT = applicationContext;
	}
}
