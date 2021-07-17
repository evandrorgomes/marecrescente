package br.org.cancer.redome.tarefa.model.domain;

import br.org.cancer.redome.tarefa.helper.IConfiguracaoProcessServer;
import br.org.cancer.redome.tarefa.process.server.IProcessadorTarefa;

public interface ITiposTarefa {
	
	
	public Class<? extends IProcessadorTarefa> getClasseProcessador();	
	
	public static Class<? extends IProcessadorTarefa> obterClasseProcessador(Long id) {
		return null;
	}
	
	
	
	public static TiposTarefa valueOf(Long value) {
		return null;
	}
	
	Long getId();
	IConfiguracaoProcessServer getConfiguracao();

}
