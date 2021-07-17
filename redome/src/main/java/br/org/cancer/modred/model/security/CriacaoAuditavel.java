package br.org.cancer.modred.model.security;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import br.org.cancer.modred.service.impl.custom.EntityObservable;

/**
 * Classe abstrata utilizada como referência nas entities com auditoria somente de criação.
 * 
 * @author bruno.sousa
 *
 */
@MappedSuperclass
@EntityListeners(CriacaoAuditavelListener.class)
public abstract class CriacaoAuditavel extends EntityObservable {

	protected abstract void setDataCriacao(LocalDateTime dataCriacao);

	protected abstract void setUsuario(Usuario usuario);
	

}
