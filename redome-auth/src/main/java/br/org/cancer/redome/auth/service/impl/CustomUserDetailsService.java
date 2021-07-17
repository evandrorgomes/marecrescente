package br.org.cancer.redome.auth.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.auth.controller.dto.UsuarioDTO;
import br.org.cancer.redome.auth.exception.BusinessException;
import br.org.cancer.redome.auth.feign.client.ITransportadoraFeign;
import br.org.cancer.redome.auth.model.CustomUser;
import br.org.cancer.redome.auth.model.Perfil;
import br.org.cancer.redome.auth.model.Permissao;
import br.org.cancer.redome.auth.model.Usuario;
import br.org.cancer.redome.auth.persistence.IPermissaoRepository;
import br.org.cancer.redome.auth.persistence.IUsuarioRepository;
import br.org.cancer.redome.auth.service.IUsuarioService;

/**
 * Responsável em obter os dados do usuário para autenticação. Versão customizada de classe do Spring Security.
 * 
 * @author Cintia Oliveira
 *
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService, IUsuarioService {
	
	private final String  passwordPattern = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,}$";
	
	@Value("classpath:modred_public_key.der")
	private Resource publicKeyFile;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired 
	private IPermissaoRepository permissaoRepository;
	
	@Autowired
	@Lazy(true)
	private ITransportadoraFeign transportadoraFeign;
	
	 /* 
	 * @Autowired private ICentroTransplanteService centroTransplanteService;
	 * 
	 * @Autowired private IBancoSangueCordaoService bancoSangueService;
	 * 
	 * @Autowired private IUsuarioBancoSangueCordaoService
	 * usuarioBancoSangueService;
	 * 
	 * @Autowired private IRelatorioService relatorioService;
	 * 
	 * @Autowired private IProcessoService processoService;
	 */
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*
	 * @Autowired private EmailConfig emailConfig;
	 */	
	
	/*
	 * @Override public IRepository<Usuario, Long> getRepository() { return
	 * usuarioRepository; }
	 */
	
	/*
	 * @Override protected List<CampoMensagem> validateEntity(Usuario usuario) {
	 * if(verificarSeLoginDuplicado(usuario)){ throw new
	 * BusinessException("erro.mensagem.usuario.username.existe"); }
	 * 
	 * return super.validateEntity(usuario); }
	 */
	
	@Override
	public CustomUser obterCustomUserLogado(Authentication authentication) {
		CustomUser user = null;

		if (authentication.getPrincipal() instanceof UserDetails) {
			user = (CustomUser) authentication.getPrincipal();
		}
		else {
			user = (CustomUser) this.loadUserByUsername(authentication
					.getPrincipal()
					.toString());
		}

		return user;
	}

	@Override
	public Usuario obterUsuarioPorUsername(String username) {
		return usuarioRepository.obterUsuarioPorUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.obterUsuarioPorUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

		return buildUserDetails(usuario);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	private User buildUserDetails(Usuario usuario) {
		List<Permissao> permissoes = permissaoRepository.findAllByUsuario(usuario);
				
		return usuario.composeUserSecurity(permissoes);	
		
	}	

	@Override
	public Usuario obterUsuarioLogado() {
		Usuario usuario = null;
		Long usuarioLogadoId = obterUsuarioLogadoId();

		if (usuarioLogadoId != null) {
			usuario = usuarioRepository.findById(usuarioLogadoId).get();
		}

		return usuario;
	}

	/*
	 * @Override public Boolean usuarioLogadoPossuiPerfil(Perfis perfil) { Usuario
	 * usuarioLogado = obterUsuarioLogado(); return localizarPerfil(usuarioLogado,
	 * perfil); }
	 */

	/*
	 * @Override public boolean usuarioPossuiPerfil(Usuario usuario, Perfis perfil)
	 * { if(CollectionUtils.isEmpty(usuario.getPerfis())){ usuario =
	 * usuarioRepository.findOne(usuario.getId()); } return localizarPerfil(usuario,
	 * perfil); }
	 */

	/*
	 * private boolean localizarPerfil(Usuario usuario, Perfis perfil) { return
	 * usuario.getPerfis().stream().anyMatch(p -> perfil.getId().equals(p.getId()));
	 * }
	 */

	@Override
	public Long obterUsuarioLogadoId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			CustomUser customUser = obterCustomUserLogado(authentication);
			return customUser.getId();
		}
		return null;
	}
	
	private String criptografar(String senha){
		return passwordEncoder.encode(senha);
	}
	
	/*
	 * @Override public Usuario criarUsuarioSenhaTemporaria(String login, String
	 * nome, String email, Perfis... perfis){ final String senha =
	 * gerarSenhaTemporaria(); Usuario usuario = new Usuario();
	 * usuario.setUsername(login); usuario.setSenhadescriptografada(senha);
	 * usuario.setPassword(criptografar(senha)); usuario.setNome(nome);
	 * usuario.setAtivo(Boolean.TRUE); usuario.setEmail(email);
	 * 
	 * usuario.setPerfis( Arrays.asList(perfis).stream().map(perfil -> { return new
	 * Perfil(perfil.getId()); }).collect(Collectors.toList()) ); return
	 * save(usuario); }
	 */
	
	
	@Override
	public Usuario criarUsuarioSenhaTemporaria(Usuario usuario) {
		/*
		 * final String senha = gerarSenhaTemporaria();
		 * usuario.setSenhadescriptografada(senha);
		 * usuario.setPassword(criptografar(senha)); usuario.setAtivo(Boolean.TRUE);
		 * 
		 * save(usuario);
		 * 
		 * enviarSenhaPorEmail(usuario, Relatorios.EMAIL_ENVIO_SENHA.getCodigo());
		 * 
		 * return usuario;
		 */
		return null;
	}
	 
	
	/*
	 * @Override public void enviarSenhaPorEmail(Usuario usuario, String
	 * codigoRelatorio) { final Relatorio relatorio =
	 * relatorioService.obterRelatorioPorCodigo(codigoRelatorio);
	 * 
	 * final String assunto = "";
	 * 
	 * final String textoEmail = new HtmlReportGenerator()
	 * .comParametro(ParametrosRelatorios.NOME_USUARIO, usuario.getNome())
	 * .comParametro(ParametrosRelatorios.LOGIN_USUARIO, usuario.getUsername())
	 * .comParametro(ParametrosRelatorios.SENHA_USUARIO,
	 * usuario.getSenhadescriptografada()) .gerarHTML(relatorio);
	 * 
	 * enviarEmail(usuario.getEmail(), assunto, textoEmail); }
	 */
	
	/**
	 * Gera a senha inicial criptografada.
	 * 
	 * @return senha inicial criptografada.
	 */
	/*
	 * private String gerarSenhaTemporaria(){ return
	 * GeradorCaracteres.tamanho(8).gerar(); }
	 */

	@Override
	public Usuario obterUsuario(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Page<Usuario> listarUsuariosAtivos(PageRequest pageRequest, String nome, String email, String login) {
		return usuarioRepository.listarUsuariosAtivos(pageRequest, 
				nome != null ? "%" + nome + "%" : null, 
						email != null ? "%" + email + "%" : null,
							login != null ? "%" + login + "%" : null);
	}

	
	@Override
	public List<Usuario> listarUsuariosPorCentroTransplanteEPerfil(Long idCentroTransplante, Long idPerfil){
		return null;
		//List<Usuario> usuarios = usuarioRepository.listarUsuariosPorCentroTransplanteEPerfil(idCentroTransplante, idPerfil);
		//return usuarios;
	}

	/*
	 * @Override public List<Usuario> listarAnalistasDeBusca() { return
	 * usuarioRepository.listarUsuariosPorPerfil(Perfis.ANALISTA_BUSCA.getId()); }
	 */

	
	@Override
	public void salvarUsuarioCentroParaTarefas(Long id, Usuario usuario) {
		/*
		 * Usuario usuarioCarregado = obterUsuario(id);
		 * if(usuario.getCentrosTransplantesParaTarefas().isEmpty()){
		 * usuarioCarregado.setCentrosTransplantesParaTarefas(null);; } else{
		 * List<CentroTransplante> centros = new ArrayList<CentroTransplante>();
		 * usuario.getCentrosTransplantesParaTarefas().forEach(c->{
		 * centros.add(centroTransplanteService.findOne(c.getId())); });
		 * usuarioCarregado.setCentrosTransplantesParaTarefas(centros); }
		 * usuarioRepository.save(usuarioCarregado);
		 */
	}

	/*
	 * @Override public List<Usuario> listarUsuarios(Perfis perfil) { return
	 * usuarioRepository.listarUsuariosPorPerfil(perfil.getId()); }
	 */

	@Override
	public void enviarEmail(Long idUsuario) {
		//Usuario usuario = usuarioRepository.findOne(idUsuario);
		//enviarEmail(usuario.getEmail(), "", "");
	}

	/*
	 * @Override public void enviarEmail(Perfis perfil) { List<Usuario>
	 * listarUsuarios = listarUsuarios(perfil);
	 * 
	 * if(CollectionUtils.isEmpty(listarUsuarios)){ throw new
	 * BusinessException("Não existem usuários ativos (Perfil: " +
	 * Perfis.ADMIN.name() +
	 * ") para tratar da avaliação do pré cadastro do médico."); }
	 * 
	 * listarUsuarios.forEach(usuario -> { enviarEmail(usuario.getId()); }); }
	 */

	@Override
	public void enviarEmail(String email, String assunto, String texto) {
				
		/*
		 * emailConfig .de("sismatch@inca.gov.br") .para(email)
		 * //.comCopiaOculta(env.getProperty("cobranca.hemocentro.email.copia"))
		 * //.para("sheila.prado@cancer.org.br") //para teste
		 * //.comCopiaOculta("elizabete.poly@cancer.org.br") //para teste
		 * .comAssunto(assunto) .comMensagem(texto) .sendAutenticado();
		 */
	}

	@Override
	public boolean usuarioLogadoPertenceAoCentroTransplante(Long idCentroTransplante) {
		/*
		 * Usuario usuario = obterUsuarioLogado(); if
		 * (usuario.getCentroTransplanteUsuarios() != null &&
		 * !usuario.getCentroTransplanteUsuarios().isEmpty()) { return
		 * usuario.getCentroTransplanteUsuarios() .stream()
		 * .map(centroTransplanteUsuario ->
		 * centroTransplanteUsuario.getCentroTransplante()) .anyMatch(centroTransplante
		 * -> centroTransplante.getId().equals(idCentroTransplante)); }
		 */
		
		return false;
	}

	@Override
	public boolean verificarSeLoginDuplicado(Usuario usuario) {
		Usuario usuarioEncontrado;
		
		if(usuario.getId() == null){
			usuarioEncontrado = 
					usuarioRepository.obterUsuarioPorUsername(usuario.getUsername());
		}
		else {
			usuarioEncontrado = usuarioRepository.obterUsuarioPorUsername(usuario);
		}
		return usuarioEncontrado != null;
	}

	@Override
	public void alterarAcesso(Long idUsuario, String login, String nome, String email) {
		/*
		 * update( Arrays.asList( new UpdateSet<String>("username", login), new
		 * UpdateSet<String>("nome", nome), new UpdateSet<String>("email", email)),
		 * Arrays.asList(new Equals<Long>("id", idUsuario)) );
		 */
	}

	@Override
	public Usuario alterarPerfisAcesso(Long idUsuario, List<Perfil> perfisDeAcesso, Long idBancoSangue) {
		/*
		 * Usuario usuario = findOne(idUsuario); usuario.setPerfis(perfisDeAcesso);
		 * 
		 * if(idBancoSangue == null){ return save(usuario); } else { BancoSangueCordao
		 * bscup = bancoSangueService.findOne(idBancoSangue); UsuarioBancoSangueCordao
		 * usuarioBscup = new UsuarioBancoSangueCordao(usuario, bscup); return
		 * usuarioBancoSangueService.save(usuarioBscup); }
		 */
		return null;
	}

	@Override
	public Usuario inativar(Long idUsuario) {
		return null;
		/*
		 * Usuario usuario = usuarioRepository.findOne(idUsuario);
		 * usuario.setAtivo(Boolean.FALSE); List<Tarefa> tarefas =
		 * processoService.obterTarefasPorStatusEUsuarioResponsavel(StatusTarefa.
		 * ATRIBUIDA.getId(),idUsuario); tarefas.stream().forEach(t->{
		 * processoService.removerAtribuicaoTarefa(t.getId()); }); return save(usuario);
		 */
	}

	@Override
	public void lembrarSenha(String email) {
		/*
		 * Page<Usuario> usuarios = listarUsuariosAtivos(null, null, email, null); if
		 * (usuarios != null && !usuarios.getContent().isEmpty()) { Usuario usuario =
		 * usuarios.getContent().get(0);
		 * usuario.setSenhadescriptografada(gerarSenhaTemporaria());
		 * usuario.setPassword(criptografar(usuario.getSenhadescriptografada()));
		 * save(usuario);
		 * 
		 * enviarSenhaPorEmail(usuario, Relatorios.LEMBRAR_SENHA.getCodigo()); } else {
		 * throw new BusinessException("erro.mensagem.usuario.nao.encontrado"); }
		 */
		
	}

	//@Override
	//public void alterarSenha(AlterarSenhaVo alterarSenhaVo) {
		/*
		 * if (alterarSenhaVo == null) { throw new
		 * BusinessException("erro.parametros.invalidos"); } if
		 * (alterarSenhaVo.getSenhaAtual() == null ||
		 * "".equals(alterarSenhaVo.getSenhaAtual())) { throw new
		 * BusinessException("erro.parametros.invalidos"); } if
		 * (alterarSenhaVo.getNovaSenha() == null ||
		 * "".equals(alterarSenhaVo.getNovaSenha())) { throw new
		 * BusinessException("erro.parametros.invalidos"); } if
		 * (alterarSenhaVo.getNovaSenha().equals(alterarSenhaVo.getSenhaAtual())) {
		 * throw new
		 * BusinessException("erro.mensagem.usuario.senha.nova.igual.senha.atual"); }
		 * Usuario usuario = obterUsuarioLogado();
		 * 
		 * if (!passwordEncoder.matches(alterarSenhaVo.getSenhaAtual(),
		 * usuario.getPassword())) { throw new
		 * BusinessException("erro.mensagem.usuario.senha.atual.nao.confere"); }
		 * 
		 * if (!alterarSenhaVo.getNovaSenha().matches(passwordPattern)) { throw new
		 * BusinessException("erro.mensagem.usuario.nova.senha.inválida"); }
		 * 
		 * usuario.setPassword(criptografar(alterarSenhaVo.getNovaSenha()));
		 * 
		 * save(usuario);
		 */
	//}

	@Override
	public List<Usuario> listarUsuariosTransportadorasDisponiveis() {
		return null;
		//return usuarioRepository.listarUsuariosPorPerfilEQueNaoTemTransportadora(Perfis.TRANSPORTADORA.getId());
	}

	@Override
	public List<Usuario> listarUsuariosLaboratoriosDisponiveis() {
		return null;
		//return usuarioRepository.listarUsuariosPorPerfilEQueNaoTemLaboratorio(Perfis.LABORATORIO.getId());
	}
	
	@Override
	public UsuarioDTO obterUsuarioDTO(Long id) {
		Usuario usuario = obterUsuario(id);
		if (usuario  == null || !usuario.isAtivo()) {
			return null;
		}
		
		return UsuarioDTO.builder().usuario(usuario).build();
	}
	
	@Override
	public void atribuirUsuariosParaTransportadora(Long idTransportadora, List<Long> usuarios) {
		validarTransportadora(idTransportadora);
		validarUsuarios(usuarios, idTransportadora);
		
		usuarios.forEach(id -> {
			Usuario usuario = usuarioRepository.findById(id).get();
			usuario.setTransportadora(idTransportadora);
			usuarioRepository.save(usuario);
		});
		
		
	}

	private void validarUsuarios(List<Long> usuarios, Long idTransportadora) {
		if (CollectionUtils.isEmpty(usuarios)) {
			throw new BusinessException("");
		}
		long qtde = usuarios.stream()
			.filter(id -> {
				Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new BusinessException(""));
				Boolean temPerfilTransportadora = usuario.getPerfis().stream().anyMatch(perfil -> perfil.getId().equals(17L));
				if (!temPerfilTransportadora) {
					return false;
				}
				Boolean pertenceAlgumaTransportadora = usuario.getTransportadora() != null;
				if (pertenceAlgumaTransportadora && !idTransportadora.equals(usuario.getTransportadora())) {
					return false;
				}
				
				return true; 
			})
			.count();
		if (qtde != usuarios.size()) {
			throw new BusinessException("");
		}
		
	}

	private void validarTransportadora(Long idTransportadora) {
		if (idTransportadora == null) {
			throw new BusinessException("");
		}
/*		TransportadoraDTO transportadora = transportadoraFeign.obterTransportadora(idTransportadora);
		if (transportadora == null) {
			throw new BusinessException("");
		}
	*/	
	}
	
	

}
