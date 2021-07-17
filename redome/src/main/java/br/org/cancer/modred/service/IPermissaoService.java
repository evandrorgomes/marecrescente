package br.org.cancer.modred.service;

/**
 * Interface que descreve os métodos de negócio que devem ser implementados pelas classes de serviços que operam sobre a
 * entidade Permissao. Essa entidade atua em função do mecanismo de autorização da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */


public interface IPermissaoService {
	
	Boolean usuarioLogadoPossuiPermissao(String recurso);
	
}
