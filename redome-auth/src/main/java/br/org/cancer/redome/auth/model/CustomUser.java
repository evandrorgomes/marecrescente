package br.org.cancer.redome.auth.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Representação customizada do usuário do sistema para utilização pelo Spring
 * Security.
 * 
 * @author Cintia Oliveira
 *
 */
public class CustomUser extends User {

	private static final long serialVersionUID = -1017252752357656584L;

	private Long id;
	private String nome;
	private List<Permissao> permissoes;
	private Set<String> recursos;
	private Set<CentroTransplante> centros;

	
	/**
	 * Banco de sangue associado ao usuário logado.
	 * Aplicável apenas para usuários do BrasilCord.
	 */
	private BancoSangueCordao bancoSangue;
	
	private String transportadora;
	
	private String encryptedId;

	/**
	 * Construtor.
	 * 
	 * @param usuario
	 *            do sistema
	 * @param permissoes
	 *            do usuário
	 * @param recursos
	 *            permitidos ao usuário
	 * @param perfis
	 *            do usuário
	 * @param centros
	 *            do usuário           
	 */
	public CustomUser(Usuario usuario, List<Permissao> permissoes, Set<String> recursos, Set<String> perfis,
			Set<CentroTransplante> centros) {
			//Set<CentroTransplante> centros) {
		super(usuario.getUsername(), usuario.getPassword(), usuario.isAtivo(), true, true, true,
				loadGrantedAuthorities(perfis));
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.permissoes = permissoes;
		this.recursos = recursos;
		this.centros = centros;
	}

	private static List<GrantedAuthority> loadGrantedAuthorities(Set<String> perfis) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		if (!perfis.isEmpty()) {
			perfis.forEach(perfil -> authorities.add(new SimpleGrantedAuthority(perfil)));
		}

		return authorities;

	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the permissoes
	 */
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * @param permissoes
	 *            the permissoes to set
	 */
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	/**
	 * @return the recursos
	 */
	public Set<String> getRecursos() {
		return recursos;
	}

	/**
	 * @param recursos
	 *            the recursos to set
	 */
	public void setRecursos(Set<String> recursos) {
		this.recursos = recursos;
	}

	/**
	 * @return the centros
	 */
	public Set<CentroTransplante> getCentros() { 
		return centros; 
	}
	

	/**
	 * @param centros
	 *            the centros to set
	 */
	 public void setCentros(Set<CentroTransplante> centros) { 
		 this.centros =	 centros; 
	 }
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CustomUser other = (CustomUser) obj;
		if (getUsername() == null) {
			if (other.getUsername() != null) {
				return false;
			}
		} 
		else if (!getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

	
	public BancoSangueCordao getBancoSangue() { 
		return bancoSangue; 
	}
	  
	public void setBancoSangue(BancoSangueCordao bancoSangue) { 
		this.bancoSangue = bancoSangue; 
	}

	public String getTransportadora() {
		return transportadora;
	}

	public void setTransportadora(String transportadora) {
		this.transportadora = transportadora;
	}

	public String getEncryptedId() {
		return encryptedId;
	}

	public void setEncryptedId(String encryptedId) {
		this.encryptedId = encryptedId;
	}
	
	
	

}