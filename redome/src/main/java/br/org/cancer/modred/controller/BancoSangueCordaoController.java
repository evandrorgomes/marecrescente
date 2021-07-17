package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.service.IBancoSangueCordaoService;

/**
 * Classe que disponibiliza serviços envolvendo a classe BancoSangueCordao.
 * Serve como ponto de acesso para o BrasilCord se comunicar diretamente com o
 * ModRed, facilitando o processamento.
 * 
 * @author Pizão
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class BancoSangueCordaoController {

	@Autowired
	private IBancoSangueCordaoService bancoSangueCordaoService;

	/**
	 * Cria ou atualiza o banco de sangue de cordão na base do ModRed. Este
	 * serviço existe para o BrasilCord possa, ao receber uma atualização nos
	 * dados do banco de sangue, atualizar também a base do ModRed, mantendo
	 * ambas com os últimos dados o quanto antes possível.
	 * 
	 * @param bancoSangueCordao
	 *            banco de sangue com as informações atualizadas.
	 * @return o ID do banco de sangue criado/atualizado.
	 */
	@RequestMapping(value = "/api/bscup", method = RequestMethod.POST)
	@PreAuthorize("#oauth2.hasScope('brasilcord_rest_api')")
	public Long salvarBancoCordao(@RequestBody(required = true) BancoSangueCordao bancoSangueCordao) {
		return bancoSangueCordaoService.salvarBancoCordao(bancoSangueCordao);
	}

	/**
	 * Lista os usuários associados ao banco de sangue de cordão informado.
	 * Serviço utilizado pelo sistema do BrasilCord, para listar os usuários.
	 * 
	 * @param idBscup ID do banco do sangue de cordão.
	 * @return lista de usuários banco de cordão.
	 */
	@RequestMapping(value = "/api/bscup/{id}/usuarios", method = RequestMethod.GET)
	@PreAuthorize("#oauth2.hasScope('brasilcord_rest_api')")
	public List<UsuarioBancoSangueCordao> listarUsuariosAtivosPorBanco(
			@PathVariable(name = "id", required = false) Long idBscup) {
		return bancoSangueCordaoService.listarUsuariosAtivos(idBscup);
	}
	
	@RequestMapping(value = "/api/bscup", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_USUARIO + "') || hasPermission('" + Recurso.ALTERAR_USUARIO + "')")
	public List<BancoSangueCordao> listarBscups() {
		return bancoSangueCordaoService.findAll();
	}
}
