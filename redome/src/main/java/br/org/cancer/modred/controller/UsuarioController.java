package br.org.cancer.modred.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.page.JsonPage;
import br.org.cancer.modred.controller.view.UsuarioView;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Recurso;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.vo.AlterarSenhaVo;

/**
 * Classe controladora responsável por fazer o dispatch das requisições
 * relacionadas à manutenção de usuários da plataforma redome.
 * 
 * @author Thiago Moraes
 *
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private MessageSource messageSource;

	/**
	 * Recupera as informações sobre um determinado usuario a partir de sua
	 * chave de identificação.
	 * 
	 * @param idUsuario
	 *            - chave de identificação do usuario de acesso.
	 * 
	 * @return ResponseEntity<Usuario> - a instância da classe usuario
	 *         identificada pelo id informado como parâmetro do acionamento
	 *         deste método.
	 */
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_USUARIO + "')")
	@RequestMapping(value = "/api/usuarios/{id}", method = RequestMethod.GET)
	@JsonView(UsuarioView.Consultar.class)
	public ResponseEntity<? extends Usuario> obterUsuario(@PathVariable(name = "id", required = true) Long idUsuario) {
		return new ResponseEntity<Usuario>(usuarioService.obterUsuario(idUsuario), HttpStatus.OK);
	}

	/**
	 * Lista os usuários de acordo com os filtros (opcionais).
	 * 
	 * @param pagina
	 *            pagina do resultado.
	 * @param quantidadeRegistros
	 *            quantidade de registros por página.
	 * @param login
	 *            filtro opcional.
	 * @param nome
	 *            filtro opcional.
	 * @param email
	 *            filtro opcional.
	 * @return lista de usuários encontrados paginada.
	 */
	@RequestMapping(value="/api/usuarios", method = RequestMethod.GET)
	@JsonView(UsuarioView.Listar.class)
	public ResponseEntity<JsonPage> listarUsuarios(@RequestParam Integer pagina,
			@RequestParam Integer quantidadeRegistros, @RequestParam(required = false) String login,
			@RequestParam(required = false) String nome, @RequestParam(required = false) String email) {
		if (pagina == null) {
			pagina = new Integer(0);
		}

		PageRequest pageRequest = (quantidadeRegistros != null && quantidadeRegistros.intValue() > 0)
				? new PageRequest(pagina, quantidadeRegistros) : null;

		return new ResponseEntity<JsonPage>(
				new JsonPage(UsuarioView.Listar.class, usuarioService.listarUsuariosAtivos(pageRequest, nome, email, login)),
				HttpStatus.OK);
	}

	/**
	 * Lista usuários com o perfil de analista de busca ordenados pelo nome.
	 * 
	 * @return ResponseEntity<List<Usuario>> - Listagem ordenada de usuarios com
	 *         o perfil de ANALISTA_BUSCA
	 */
	@JsonView(UsuarioView.ListaBasica.class)
	@RequestMapping(value = "/api/usuarios/analistasdebusca", method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listarAnalistasDeBusca() {
		return new ResponseEntity<List<Usuario>>(usuarioService.listarAnalistasDeBusca(), HttpStatus.OK);
	}
	
	
	/**
	 * Salva o relacionamento entre usuario e centro de avaliação para
	 * organização de tarefas.
	 * 
	 * @param usuario
	 *            a ser gravado.
	 * @param id
	 *            ID do usuário.
	 * 
	 * @return ResponseEntity<String> resposta HTTP.
	 */
	@PreAuthorize("hasPermission('RELACIONAR_ANALISTA_AO_CENTRO')")
	@RequestMapping(value = "/api/usuarios/{id}/relacaocentro", method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> salvaUsuarioCentroParaTarefa(
			@PathVariable(name = "id", required = true) Long id,
			@RequestBody(required = true) Usuario usuario) {
		usuarioService.salvarUsuarioCentroParaTarefas(id, usuario);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "analistabusca.atualizado.sucesso"));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	/**
	 * Serviço para atualização dos dados de acesso do usuário, 
	 * basicamente, o nome, login e email.
	 * 
	 * @param idUsuario ID do usuário a ser atualizado.
	 * @param login novo login.
	 * @param email novo e-mail.
	 * @param nome novo nome.
	 * @return mensagem de sucesso, caso ocorra.
	 */
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_USUARIO + "')")
	@RequestMapping(value = "/api/usuarios/{id}/acesso", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> alterarDadosAcessoUsuario(
			@PathVariable(name = "id", required = true) Long idUsuario, 
			@RequestParam(required = false) String login,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String nome) {
		
		usuarioService.alterarAcesso(idUsuario, login, nome, email);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.atualizado.sucesso", login));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	/**
	 * Serviço para atualização dos perfis de acesso do usuário.
	 * Adiciona ou remove os perfis, de acordo com o que tiver sido selecionado.
	 * 
	 * @param idUsuario ID do usuário a ser atualizado.
	 * @param perfisDeAcesso lista com os perfis atualizados para o usuário.
	 * @param bscup id do banco de sangue.
	 * @return mensagem de sucesso, caso ocorra.
	 */
	@PreAuthorize("hasPermission('" + Recurso.ALTERAR_USUARIO + "')")
	@RequestMapping(value = "/api/usuarios/{id}/perfis", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> alterarPerfisUsuario(
			@PathVariable(name = "id", required = true) Long idUsuario, 
			@RequestBody(required = false) List<Perfil> perfisDeAcesso,
			@RequestParam(required = false) Long bscup) {
		
		Usuario usuarioAtualizado = usuarioService.alterarPerfisAcesso(idUsuario, perfisDeAcesso, bscup);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.atualizado.sucesso", usuarioAtualizado.getUsername()));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	/**
	 * Salva um novo usuário com os perfis informados no Redome.
	 * 
	 * @param usuario usuário a ser salvo.
	 * @return mensagem de sucesso, caso ocorra.
	 */
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_USUARIO + "')")
	@RequestMapping(value="/api/usuarios", method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> salvarUsuario(@RequestBody(required = true) Usuario usuario) {
		usuarioService.criarUsuarioSenhaTemporaria(usuario);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.criado.sucesso", usuario.getUsername()));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	@PreAuthorize("hasPermission('" + Recurso.CADASTRAR_USUARIO + "')")
	@RequestMapping(value = "/api/usuarios/bscup", method = RequestMethod.POST)
	public ResponseEntity<CampoMensagem> salvaUsuarioBscup(@RequestBody(required = true) UsuarioBancoSangueCordao usuario) {
		return salvarUsuario(usuario);
	}

	/**
	 * Inativa o usuário informado.
	 * 
	 * @param idUsuario ID do usuário.
	 * @return mensagem de sucesso.
	 */
	@PreAuthorize("hasPermission('" + Recurso.INATIVAR_USUARIO + "')")
	@RequestMapping(value = "/api/usuarios/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CampoMensagem> inativarUsuario(@PathVariable(name = "id", required = true) Long idUsuario) {
		
		final Usuario usuarioInativado = usuarioService.inativar(idUsuario);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.inativado.sucesso", usuarioInativado.getUsername()));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	/**
	 * Rest para lembrar a senha do usuário.
	 * @param email email do usuario.
	 * @return mensagem de sucesso.
	 */	
	@RequestMapping(value = "/public/lembrarsenha", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> lembrarSenha(@RequestBody(required = true) String email) {
		
		usuarioService.lembrarSenha(email);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.lembrar.senha.sucesso"));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}
	
	/**
	 * Rest para alterar senha.
	 * @param alterarSenhaVo vo de senha de usuário.
	 * @return mensagem de sucesso.
	 */	
	@RequestMapping(value = "/api/usuarios/alterarsenha", method = RequestMethod.PUT)
	public ResponseEntity<CampoMensagem> alterarsenha(
			@RequestBody(required = true) AlterarSenhaVo alterarSenhaVo) {
		
		usuarioService.alterarSenha(alterarSenhaVo);

		final CampoMensagem campoMensagem = new CampoMensagem(
				AppUtil.getMensagem(messageSource, "usuario.alterar.senha.sucesso"));

		return new ResponseEntity<CampoMensagem>(campoMensagem, HttpStatus.OK);
	}

	/**
	 * Lista usuários do laboratório.
	 * 
	 * @return ResponseEntity<List<Usuario>> - Listagem ordenada de usuarios com o perfil de LABORATORIO
	 */
	@JsonView(UsuarioView.ListaBasica.class)
	@RequestMapping(value = "/api/usuarios/laboratorio", method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listarUsuariosLaboratorio() {
		return new ResponseEntity<List<Usuario>>(usuarioService.listarUsuariosLaboratoriosDisponiveis(), HttpStatus.OK);
	}

}
