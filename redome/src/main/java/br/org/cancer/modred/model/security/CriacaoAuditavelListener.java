package br.org.cancer.modred.model.security;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.helper.AutowireHelper;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Listener para preenchimento dos dados de auditoria na criação das entidades
 * que extendem a classe CriacaoAuditavel.
 * 
 * @author Bruno Souza
 *
 */
@Component
public class CriacaoAuditavelListener {

	public static IUsuarioService usuarioService;
	

	/**
	 * Método utilizado para popular os campos de data de criação e usuário logado
	 * antes de persistir.
	 * 
	 * @param auditavel objeto a ser preenchido com os dados de auditoria.
	 */
	@PrePersist
	public void preencherDadosAuditoria(CriacaoAuditavel auditavel) {
		
		if (usuarioService == null) {		
			AutowireHelper.autowire(this);
		}
		
		auditavel.setDataCriacao(LocalDateTime.now());
		auditavel.setUsuario(usuarioService.obterUsuarioLogado());
	}


	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		CriacaoAuditavelListener.usuarioService = usuarioService;
	}
	
	
	
	
	

}
