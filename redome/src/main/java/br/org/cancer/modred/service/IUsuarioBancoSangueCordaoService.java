
package br.org.cancer.modred.service;

import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Serviço para operações referentes a classe UsuarioBancoSangueCordao.
 * 
 * @author Pizão
 *
 */
public interface IUsuarioBancoSangueCordaoService extends IService<UsuarioBancoSangueCordao, Long>{
	
	/**
	 * Insere e associa um usuário ao banco de sangue de cordão.
	 * 
	 * @param idUsuario ID do usuário.
	 * @param idBancoSangue ID do banco de sangue.
	 * @return quantidade de registros incluídos.
	 */
	int associarUsuarioBanco(Long idUsuario, Long idBancoSangue);
	
}
