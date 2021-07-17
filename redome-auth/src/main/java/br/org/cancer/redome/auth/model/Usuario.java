package br.org.cancer.redome.auth.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import br.org.cancer.redome.auth.util.AsymmetricCryptography;

/**
 * Classe para representar as características e comportamentos do usuário.
 * 
 * @author bruno.sousa
 *
 */
@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "SQ_USUA_ID", sequenceName = "SQ_USUA_ID", allocationSize = 1)
public class Usuario implements Serializable {

	private static final long serialVersionUID = 3939069671930132719L;

	// private static final Logger LOG = LoggerFactory.getLogger(Usuario.class);
	
	// private static final String ALGORITHM = "RSA";
	

	/**
	 * Chave que identifica com exclusividade uma instância desta classe.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUA_ID")
	@Column(name = "USUA_ID")
	private Long id;
	/**
	 * O nome completo do usuário.
	 */
	@Column(name = "USUA_TX_NOME")
	private String nome;
	/**
	 * O username é o elemento usado para logar no sistema.
	 */
	@NotNull
	@Column(name = "USUA_TX_USERNAME")
	private String username;

	/**
	 * A password.
	 */
	@NotNull
	@Column(name = "USUA_TX_PASSWORD")
	private String password;

	@NotNull
	@Column(name = "USUA_IN_ATIVO")
	private boolean ativo;

	/**
	 * Lista de perfis.
	 */
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "USUARIO_PERFIL", joinColumns = { @JoinColumn(name = "USUA_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "PERF_ID") })
	private List<Perfil> perfis;

	@Column(name = "USUA_TX_EMAIL")
	private String email;

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "USUA_ID")
	private List<CentroTransplanteUsuario> centroTransplanteUsuarios;
	
	@Column(name = "TRAN_ID")
	private Long transportadora;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "LABO_ID")
	private Laboratorio laboratorio;

	/**
	 * Atributo utilizado somente na criação do usuário para o envio do email com a
	 * senha criada. O Spring não possui rotina ara descritografar a senha.
	 */
	@Transient
	private String senhadescriptografada;

	/**
	 * Método construtor da classe Usuario.
	 * 
	 * @return Usuario - instância da classe Usuario.
	 */
	public Usuario() {
		super();
	}

	/**
	 * Construtor passando por referencia o usuario e a senha.
	 * 
	 * @param username do usuário
	 * @param password senha do usuário
	 */
	public Usuario(String username, String password) {
		this();
		this.username = username;
		this.password = password;
	}

	/**
	 * Construtor de usuario preparado para receber a id do usuario.
	 * 
	 * @param id do usuário
	 */
	public Usuario(Long id) {
		this();
		this.id = id;
	}

	/**
	 * Construtor para facilitar testes unitários.
	 * 
	 * @param username do usuário
	 */
	public Usuario(String username) {
		this();
		this.username = username;
	}

	/**
	 * Construtor.
	 * 
	 * @param id       do usuário
	 * @param username do usuário
	 * @param nome     do usuário
	 */
	public Usuario(Long id, String username, String nome) {
		this(id);
		this.username = username;
		this.nome = nome;
	}

	/**
	 * Construtor com usuário parametrizado.
	 * 
	 * @param usuario a ser inicializado.
	 */
	public Usuario(Usuario usuario) {
		this();
		setId(usuario.id);
		setNome(usuario.nome);
		setUsername(usuario.username);
		setPassword(usuario.password);
		setAtivo(usuario.ativo);
		setPerfis(usuario.perfis);
		setEmail(usuario.email);
	}

	/**
	 * Método para obter a chave que identifica com exclusividade cada instância
	 * desta classe.
	 * 
	 * @return Long - a chave de identificação da instância da classe.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Método para definir a chave que identifica com exclusividade a instância
	 * desta classe.
	 * 
	 * @param id - a chave de identificação da instância da classe.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Método para obter o nome do usuario.
	 * 
	 * @return String - o nome do usuario.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Método para definir o nome do usuario.
	 * 
	 * @param nome - o nome do usuario.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método para obter a password do usuario.
	 * 
	 * @return String - a password do usuario.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Método para definir a password do usuario.
	 * 
	 * @param String - a password do usuário.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Método para obter o username do usuario.
	 * 
	 * @return String - o username do usuario.
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Método para definir o username do usuario.
	 * 
	 * @param username - o username do usuário.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the ativo
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * @param ativo the ativo to set
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	/**
	 * Método para obter o perfil do usuario.
	 * 
	 * @return List<Perfil> - lista com o perfil.
	 */
	public List<Perfil> getPerfis() {
		return this.perfis;
	}

	/**
	 * Método para definir o perfil do usuário.
	 * 
	 * @param List<Perfil> - Lista com o perfil.
	 */
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}
	
	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Usuario other = (Usuario) obj;

		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the senhadescriptografada
	 */
	public String getSenhadescriptografada() {
		return senhadescriptografada;
	}

	/**
	 * @param senhadescriptografada the senhadescriptografada to set
	 */
	public void setSenhadescriptografada(String senhadescriptografada) {
		this.senhadescriptografada = senhadescriptografada;
	}

	/**
	 * Configura os dados necessários para acesso do usuário, utilizado pelo
	 * SpringSecurity, para configurar o acesso para o usuário logado.
	 * 
	 * @param permissoes permissões de acesso do usuário logado.
	 * @return uma representação do usuário específica para o Spring.
	 */
	public CustomUser composeUserSecurity(List<Permissao> permissoes) {
		Set<String> perfis = new TreeSet<String>();
		Set<String> recursos = new TreeSet<String>();
		Set<CentroTransplante> centros = new TreeSet<CentroTransplante>();

		if (permissoes != null && !permissoes.isEmpty()) {
			permissoes.forEach(permissao -> {
				recursos.add(permissao.getId().getRecurso().getSigla());
				perfis.add(permissao.getId().getPerfil().getDescricao());
			});
		}

		this.getCentroTransplanteUsuarios().stream().map(ctUsuario -> {
			final CentroTransplante centroTransplante = ctUsuario.getCentroTransplante();
			final CentroTransplante centroSimplificado = new CentroTransplante(centroTransplante.getId(), centroTransplante.getNome());
			centroSimplificado.setFuncoes(new ArrayList<>());
			centroSimplificado.getFuncoes().addAll(centroTransplante.getFuncoes());
			return centroSimplificado;			
		}).forEach(centro -> centros.add(centro) );

		PrivateKey chavePrivada = AsymmetricCryptography.getPrivateKey("modred_private_key.der");
		
		CustomUser user = new CustomUser(this, permissoes, recursos, perfis, centros);		
		user.setEncryptedId(AsymmetricCryptography.encryptText(this.id.toString(), chavePrivada));
		if (transportadora != null) {
			user.setTransportadora(AsymmetricCryptography.encryptText(this.transportadora.toString(), chavePrivada));
		}

		return user;
	}

	public List<CentroTransplanteUsuario> getCentroTransplanteUsuarios() {
		return centroTransplanteUsuarios;
	}

	public void setCentroTransplanteUsuarios(List<CentroTransplanteUsuario> centroTransplanteUsuarios) {
		this.centroTransplanteUsuarios = centroTransplanteUsuarios;
	}

	public Long getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(Long transportadora) {
		this.transportadora = transportadora;
	}
	

}