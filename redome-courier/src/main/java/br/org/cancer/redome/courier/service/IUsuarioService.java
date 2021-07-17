package br.org.cancer.redome.courier.service;

import br.org.cancer.redome.courier.security.Usuario;

public interface IUsuarioService {
	
	Usuario obterUsuarioLogado();
	
	Long obterIdUsuarioLogado();
	
	Long obterIdTransportadora();

}
