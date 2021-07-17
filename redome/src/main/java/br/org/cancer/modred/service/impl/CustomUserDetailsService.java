package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.configuration.EmailConfig;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.ListaTarefaDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Laboratorio;
import br.org.cancer.modred.model.Relatorio;
import br.org.cancer.modred.model.UsuarioBancoSangueCordao;
import br.org.cancer.modred.model.domain.ParametrosRelatorios;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.Relatorios;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Perfil;
import br.org.cancer.modred.model.security.Permissao;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPermissaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.persistence.IUsuarioRepository;
import br.org.cancer.modred.service.IBancoSangueCordaoService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IRelatorioService;
import br.org.cancer.modred.service.IUsuarioBancoSangueCordaoService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.UpdateSet;
import br.org.cancer.modred.service.impl.custom.AbstractService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.GeradorCaracteres;
import br.org.cancer.modred.vo.AlterarSenhaVo;
import br.org.cancer.modred.vo.report.HtmlReportGenerator;

/**
 * Responsável em obter os dados do usuário para autenticação. Versão customizada de classe do Spring Security.
 * 
 * @author Cintia Oliveira
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomUserDetailsService extends AbstractService<Usuario, Long> implements UserDetailsService, IUsuarioService {
	
	private final String  passwordPattern = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,}$";
	
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private IPermissaoRepository permissaoRepository;
	
	private ICentroTransplanteService centroTransplanteService;
	
	private IBancoSangueCordaoService bancoSangueService;
	
	private IUsuarioBancoSangueCordaoService usuarioBancoSangueService;
	
	private IRelatorioService relatorioService;
	
	@Autowired
	@Lazy(true)
	private ITarefaFeign tarefaFeign;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailConfig emailConfig;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public IRepository<Usuario, Long> getRepository() {
		return usuarioRepository;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(Usuario usuario) {
		if(verificarSeLoginDuplicado(usuario)){
			throw new BusinessException("erro.mensagem.usuario.username.existe");
		}
		
		return super.validateEntity(usuario);
	}
	
	@Override
	public CustomUser obterCustomUserLogado(Authentication authentication) {
		CustomUser user = null;

/*		boolean isClientOnly = false;
		if (authentication instanceof OAuth2Authentication) {
			isClientOnly = ((OAuth2Authentication)authentication).isClientOnly();			
		}
		if (isClientOnly ) {
			return null;
		}*/
		
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

	@Override
	public Boolean usuarioLogadoPossuiPerfil(Perfis perfil) {
		Usuario usuarioLogado = obterUsuarioLogado();
		return localizarPerfil(usuarioLogado, perfil);
	}

	@Override
	public boolean usuarioPossuiPerfil(Usuario usuario, Perfis perfil) {
		if(CollectionUtils.isEmpty(usuario.getPerfis())){
			usuario = usuarioRepository.findById(usuario.getId()).get();
		}
		return localizarPerfil(usuario, perfil);
	}

	private boolean localizarPerfil(Usuario usuario, Perfis perfil) {
		return usuario.getPerfis().stream().anyMatch(p -> perfil.getId().equals(p.getId()));
	}

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
	
	@Override
	public Usuario criarUsuarioSenhaTemporaria(String login, String nome, String email, Perfis... perfis){
		final String senha = gerarSenhaTemporaria();
		Usuario usuario = new Usuario();
		usuario.setUsername(login);
		usuario.setSenhadescriptografada(senha);
		usuario.setPassword(criptografar(senha));
		usuario.setNome(nome);
		usuario.setAtivo(Boolean.TRUE);
		usuario.setEmail(email);
		
		usuario.setPerfis(
			Arrays.asList(perfis).stream().map(perfil -> { 
				return new Perfil(perfil.getId());
			}).collect(Collectors.toList())
		);
		return save(usuario);
	}
	
	@Override
	public Usuario criarUsuarioSenhaTemporaria(Usuario usuario) {
		final String senha = gerarSenhaTemporaria();
		usuario.setSenhadescriptografada(senha);
		usuario.setPassword(criptografar(senha));
		usuario.setAtivo(Boolean.TRUE);
		
		save(usuario);
		
		enviarSenhaPorEmail(usuario, Relatorios.EMAIL_ENVIO_SENHA.getCodigo());
		
		return usuario;
	}
	
	@Override
	public void enviarSenhaPorEmail(Usuario usuario, String codigoRelatorio) {
		final Relatorio relatorio = relatorioService.obterRelatorioPorCodigo(codigoRelatorio);
		
		final String assunto = "";
		
		final String textoEmail = new HtmlReportGenerator()
			.comParametro(ParametrosRelatorios.NOME_USUARIO, usuario.getNome())
			.comParametro(ParametrosRelatorios.LOGIN_USUARIO, usuario.getUsername())
			.comParametro(ParametrosRelatorios.SENHA_USUARIO, usuario.getSenhadescriptografada())			
			.gerarHTML(relatorio);
		
		enviarEmail(usuario.getEmail(), assunto, textoEmail);
	}
	
	/**
	 * Gera a senha inicial criptografada.
	 * 
	 * @return senha inicial criptografada.
	 */
	private String gerarSenhaTemporaria(){
		return GeradorCaracteres.tamanho(8).gerar();
	}

	@Override
	public Usuario obterUsuario(Long id) {
		return findById(id);
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
		List<Usuario> usuarios = usuarioRepository.listarUsuariosPorCentroTransplanteEPerfil(idCentroTransplante, idPerfil);
		return usuarios;
	}

	@Override
	public List<Usuario> listarAnalistasDeBusca() {
		return usuarioRepository.listarUsuariosPorPerfil(Perfis.ANALISTA_BUSCA.getId());
	}

	
	@Override
	public void salvarUsuarioCentroParaTarefas(Long id, Usuario usuario) {
		Usuario usuarioCarregado = obterUsuario(id);
		if(usuario.getCentrosTransplantesParaTarefas().isEmpty()){
			usuarioCarregado.setCentrosTransplantesParaTarefas(null);;
		}
		else{
			List<CentroTransplante> centros = new ArrayList<CentroTransplante>();
			usuario.getCentrosTransplantesParaTarefas().forEach(c->{
				centros.add(centroTransplanteService.findById(c.getId()));
			});
			usuarioCarregado.setCentrosTransplantesParaTarefas(centros);
		}
		usuarioRepository.save(usuarioCarregado);
	}

	@Override
	public List<Usuario> listarUsuarios(Perfis perfil) {
		return usuarioRepository.listarUsuariosPorPerfil(perfil.getId());
	}

	@Override
	public void enviarEmail(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		enviarEmail(usuario.getEmail(), "", "");
	}

	@Override
	public void enviarEmail(Perfis perfil) {
		List<Usuario> listarUsuarios = listarUsuarios(perfil);
			
		if(CollectionUtils.isEmpty(listarUsuarios)){
			throw new BusinessException("Não existem usuários ativos (Perfil: " + Perfis.ADMIN.name() + ") para tratar da avaliação do pré cadastro do médico.");
		}
		
		listarUsuarios.forEach(usuario -> {
			enviarEmail(usuario.getId());
		});
	}

	@Override
	public void enviarEmail(String email, String assunto, String texto) {
				
		emailConfig
			.de("sismatch@inca.gov.br")
			.para(email)
			//.comCopiaOculta(env.getProperty("cobranca.hemocentro.email.copia"))
			//.para("sheila.prado@cancer.org.br") //para teste
			//.comCopiaOculta("elizabete.poly@cancer.org.br") //para teste
			.comAssunto(assunto)
			.comMensagem(texto)
			.sendAutenticado();
	}

	@Override
	public boolean usuarioLogadoPertenceAoCentroTransplante(Long idCentroTransplante) {
		Usuario usuario = obterUsuarioLogado();
		if (usuario.getCentroTransplanteUsuarios() != null && !usuario.getCentroTransplanteUsuarios().isEmpty()) {
			return usuario.getCentroTransplanteUsuarios()
						.stream()
						.map(centroTransplanteUsuario -> centroTransplanteUsuario.getCentroTransplante())
						.anyMatch(centroTransplante -> centroTransplante.getId().equals(idCentroTransplante));
		}
		
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
		update(
			Arrays.asList(
					new UpdateSet<String>("username", login), 
					new UpdateSet<String>("nome", nome), 
					new UpdateSet<String>("email", email)),
			Arrays.asList(new Equals<Long>("id", idUsuario))
		);
	}

	@Override
	public Usuario alterarPerfisAcesso(Long idUsuario, List<Perfil> perfisDeAcesso, Long idBancoSangue) {
		Usuario usuario = findById(idUsuario);
		usuario.setPerfis(perfisDeAcesso);
		
		if(idBancoSangue == null){
			return save(usuario);
		}
		else {
			BancoSangueCordao bscup = bancoSangueService.findById(idBancoSangue);
			UsuarioBancoSangueCordao usuarioBscup = new UsuarioBancoSangueCordao(usuario, bscup);
			return usuarioBancoSangueService.save(usuarioBscup);
		}
	}

	@Override
	public Usuario inativar(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		usuario.setAtivo(Boolean.FALSE);
		
		ListaTarefaDTO parametros = new ListaTarefaDTO();
		parametros.setIdUsuarioResponsavel(usuario.getId());
		parametros.setIdUsuarioResponsavel(usuario.getId());
		parametros.setStatusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()));
		
		String parametrosJson;
		try {
			parametrosJson = objectMapper.writeValueAsString(parametros);
		} catch (JsonProcessingException e) {
			throw new BusinessException("");
		}
		String filtro = Base64Utils.encodeToString(parametrosJson.getBytes());
		
		Page<TarefaDTO> tarefas = tarefaFeign.listarTarefas(filtro);
		tarefas.stream().forEach( tarefa -> {
			tarefaFeign.removerAtribuicaoTarefa(tarefa.getId());
		});
		return save(usuario);
	}

	@Override
	public void lembrarSenha(String email) {
		Page<Usuario> usuarios = listarUsuariosAtivos(null, null, email, null);
		if (usuarios != null && !usuarios.getContent().isEmpty()) {
			Usuario usuario  = usuarios.getContent().get(0);
			usuario.setSenhadescriptografada(gerarSenhaTemporaria());
			usuario.setPassword(criptografar(usuario.getSenhadescriptografada()));
			save(usuario);
			
			enviarSenhaPorEmail(usuario, Relatorios.LEMBRAR_SENHA.getCodigo());
		}
		else {
			throw new BusinessException("erro.mensagem.usuario.nao.encontrado");
		}
		
	}

	@Override
	public void alterarSenha(AlterarSenhaVo alterarSenhaVo) {
		if (alterarSenhaVo == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		if (alterarSenhaVo.getSenhaAtual() == null || "".equals(alterarSenhaVo.getSenhaAtual())) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		if (alterarSenhaVo.getNovaSenha() == null || "".equals(alterarSenhaVo.getNovaSenha())) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		if (alterarSenhaVo.getNovaSenha().equals(alterarSenhaVo.getSenhaAtual())) {
			throw new BusinessException("erro.mensagem.usuario.senha.nova.igual.senha.atual");
		}
		Usuario usuario = obterUsuarioLogado();
		
		if (!passwordEncoder.matches(alterarSenhaVo.getSenhaAtual(), usuario.getPassword())) {
			throw new BusinessException("erro.mensagem.usuario.senha.atual.nao.confere");
		}
		
		if (!alterarSenhaVo.getNovaSenha().matches(passwordPattern)) {
			throw new BusinessException("erro.mensagem.usuario.nova.senha.inválida");
		}
		
		usuario.setPassword(criptografar(alterarSenhaVo.getNovaSenha()));
		
		save(usuario);
	}

	@Override
	public List<Usuario> listarUsuariosLaboratoriosDisponiveis() {
		return usuarioRepository.listarUsuariosPorPerfilEQueNaoTemLaboratorio(Perfis.LABORATORIO.getId());
	}

	/**
	 * @param centroTransplanteService the centroTransplanteService to set
	 */
	@Autowired
	public void setCentroTransplanteService(ICentroTransplanteService centroTransplanteService) {
		this.centroTransplanteService = centroTransplanteService;
	}

	/**
	 * @param bancoSangueService the bancoSangueService to set
	 */
	@Autowired
	public void setBancoSangueService(IBancoSangueCordaoService bancoSangueService) {
		this.bancoSangueService = bancoSangueService;
	}

	/**
	 * @param usuarioBancoSangueService the usuarioBancoSangueService to set
	 */
	@Autowired
	public void setUsuarioBancoSangueService(IUsuarioBancoSangueCordaoService usuarioBancoSangueService) {
		this.usuarioBancoSangueService = usuarioBancoSangueService;
	}

	/**
	 * @param relatorioService the relatorioService to set
	 */
	@Autowired
	public void setRelatorioService(IRelatorioService relatorioService) {
		this.relatorioService = relatorioService;
	}

	/**
	 * @param usuarioRepository the usuarioRepository to set
	 */
	@Autowired
	public void setUsuarioRepository(IUsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public List<Usuario> listarUsuarioPorPerfil(Long idPerfil) {
		return usuarioRepository.listarUsuariosPorPerfil(idPerfil);
	}

	@Override
	public List<Usuario> listarUsuarioPorPerfilEParceiro(Long idPerfil, Class<?> parceiro, Long idParceiro) {
		
		if (idPerfil == null || parceiro == null || idParceiro == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		
		if (CentroTransplante.class.equals(parceiro)) {
			return listarUsuariosPorCentroTransplanteEPerfil(idParceiro, idPerfil);
		}		
		else if (Laboratorio.class.equals(parceiro)) {
			return listarUsuariosPorLaboratorioEPerfil(idParceiro, idPerfil);
		}

		throw new BusinessException("erro.interno");
	}
	
	private List<Usuario> listarUsuariosPorLaboratorioEPerfil(Long idLaboratorio, Long idPerfil){
		return usuarioRepository.listarUsuariosPorLaboratorioEPerfil(idLaboratorio, idPerfil);
	}
	

}
