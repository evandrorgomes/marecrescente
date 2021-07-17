package br.org.cancer.redome.workup.service;

import br.org.cancer.redome.workup.model.security.Usuario;

public interface IUsuarioService {
	
	Usuario obterUsuarioLogado();
	
	Long obterIdUsuarioLogado();

}
