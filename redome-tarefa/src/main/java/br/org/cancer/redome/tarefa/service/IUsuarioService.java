package br.org.cancer.redome.tarefa.service;

import br.org.cancer.redome.tarefa.model.Usuario;

public interface IUsuarioService {
	
	Usuario obterUsuarioLogado();
	
	Long obterIdUsuarioLogado();

}
