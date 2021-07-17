package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface que descreve os métodos de negócio que devem ser implementados pelas classes de serviços que operam sobre a
 * entidade Perfil. Essa entidade atua em função do mecanismo de autorização da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
public interface IPerfilService extends IService<Perfil, Long> {
	
	/**
	 * Lista os perfis disponíveis para serem cadastrados pelo Redome.
	 * 
	 * @return lista de perfis.
	 */
	List<Perfil> listarDisponiveisParaRedome();
	
}
