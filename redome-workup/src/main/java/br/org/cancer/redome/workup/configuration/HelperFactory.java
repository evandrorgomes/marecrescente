package br.org.cancer.redome.workup.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.helper.TarefaHelper;

@Configuration
public class HelperFactory {
	
	@Bean
	@Lazy(true)
	public ITarefaHelper tarefaHelper() {
		return new TarefaHelper();
	}

}
