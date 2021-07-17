package br.org.cancer.redome.courier.configuraion;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.cancer.redome.courier.model.security.Auditoria;

/**
 * Listener de auditoria do Hibernate Envers.
 * 
 * @author Cintia Oliveira
 *
 */
public class AuditoriaListener implements RevisionListener {

    @Override
    public void newRevision(Object revision) {
        Auditoria auditoria = (Auditoria) revision;
        if(SecurityContextHolder.getContext().getAuthentication() != null){
        	
        	auditoria.setUsuario(SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

}
