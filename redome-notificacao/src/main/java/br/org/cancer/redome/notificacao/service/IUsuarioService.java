package br.org.cancer.redome.notificacao.service;

import br.org.cancer.redome.notificacao.model.Usuario;

public interface IUsuarioService {
	
	Usuario obterUsuarioLogado();
	
	Long obterIdUsuarioLogado();

}
