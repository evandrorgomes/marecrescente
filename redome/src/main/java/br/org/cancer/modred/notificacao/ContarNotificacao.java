package br.org.cancer.modred.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.INotificacaoFeign;
import br.org.cancer.modred.helper.AutowireHelper;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe para centralizar a listagem de notificações.
 * 
 * @author brunosousa
 *
 */
public class ContarNotificacao implements IContarNotificacao {
	
	public static IUsuarioService usuarioService;
	public static INotificacaoFeign notificacaoFeign;
	
	private Long rmr;
	private Boolean lido;
	
	public ContarNotificacao(Long rmr) {
		this.rmr = rmr;
	}

	@Override
	public IContarNotificacao somenteLidas() {
		this.lido = true;
		return this;
	}
	
	@Override
	public IContarNotificacao somenteNaoLidas() {
		this.lido = false;
		return this;
	}

	@Override
	public IContarNotificacao comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	@Override
	public Long apply() {
		
		if(this.rmr == null) {
			throw new BusinessException("erro.mensagem.notificacao.rmr.invalido");
		}
		
		if (usuarioService == null || notificacaoFeign == null) {
			AutowireHelper.autowire(this);
		}
		
		return notificacaoFeign.contarNotificacoes(this.rmr, this.lido);
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		ContarNotificacao.usuarioService = usuarioService;
	}

	/**
	 * @param notificacaoFeign the notificacaoFeign to set
	 */
	@Autowired
	@Lazy(true)
	public void setNotificacaoFeign(INotificacaoFeign notificacaoFeign) {
		ContarNotificacao.notificacaoFeign = notificacaoFeign;
	}

}
