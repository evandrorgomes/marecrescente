package br.org.cancer.modred.service;

import java.util.List;

import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Classe de métodos de negocio de Banco de Sangue de Cordão.
 * @author Filipe Paes
 *
 */
public interface IBancoSangueCordaoService extends IService<BancoSangueCordao, Long>{
	
	/**
	 * Obtem registro por id brasilcord.
	 * @param idBrasilCord
	 * @return registro brasilcord.
	 */
	BancoSangueCordao obterBancoPorIdBrasilcord(Long idBrasilCord);
	
	/**
	 * Cria ou atualiza as informações de um banco de cordão.
	 * 
	 * @param bancoSangueCordao um novo banco de sangue ou um
	 * já existente, mas com as informações mais atualizadas.
	 * 
	 * @return ID do banco na base do ModRed. 
	 */
	Long salvarBancoCordao(BancoSangueCordao bancoSangueCordao);
	
	/**
	 * Lista os usuários associados ao banco de cordão informado.
	 * 
	 * @param idBancoSangueCordao ID do banco.
	 * @return lista de usuários que pertencem ao banco.
	 */
	List<UsuarioBancoSangueCordao> listarUsuariosAtivos(Long idBancoSangueCordao);
}
