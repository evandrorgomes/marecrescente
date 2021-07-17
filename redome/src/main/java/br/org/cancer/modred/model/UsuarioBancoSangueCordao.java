package br.org.cancer.modred.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.org.cancer.modred.controller.view.UsuarioView;
import br.org.cancer.modred.model.security.CustomUser;
import br.org.cancer.modred.model.security.Permissao;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Entidade que define os atributos do usuário do banco 
 * de cordão (BrasilCord).
 * 
 * @author Pizão
 *
 */
@Entity
@PrimaryKeyJoinColumn(name="USUA_ID")
@Table(name = "USUARIO_BANCO_SANGUE_CORDAO")
public class UsuarioBancoSangueCordao extends Usuario {
	private static final long serialVersionUID = 8930329031293146226L;

	@ManyToOne
	@JoinColumn(name = "BASC_ID")
	@JsonView({ UsuarioView.Listar.class, UsuarioView.Consultar.class} )
	private BancoSangueCordao bancoSangue;

	public UsuarioBancoSangueCordao() {
		super();
	}
	
	public UsuarioBancoSangueCordao(Usuario usuario, BancoSangueCordao bscup) {
		super(usuario);
		this.bancoSangue = bscup;
	}
	
	
	@Override
	public CustomUser composeUserSecurity(List<Permissao> permissoes) {
		CustomUser userBscup = super.composeUserSecurity(permissoes);
		
		userBscup.setBancoSangue(
				new BancoSangueCordao(this.bancoSangue.getId(), this.bancoSangue.getNome()));
		
		return userBscup;
	}

	public BancoSangueCordao getBancoSangue() {
		return bancoSangue;
	}

	public void setBancoSangue(BancoSangueCordao bancoSangue) {
		this.bancoSangue = bancoSangue;
	}
	
}
