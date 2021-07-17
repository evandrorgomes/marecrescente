/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.StatusAvaliacao;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Será executado quando a avaliação do paciente é reprovada, o
 * médico responsável pelo paciente é notificado.
 * 
 */
@Component
public class AvaliacaoPacienteReprovadoObserver extends EntityObserver<Avaliacao> {

	@Autowired
	private MessageSource messageSource;

	
	@Override
	public boolean condition(Avaliacao avaliacao) {
		return StatusAvaliacao.FECHADA.getId().equals(avaliacao.getStatus()) 
					&& Boolean.FALSE.equals(avaliacao.getAprovado());
	}

	@Override
	public void process(Avaliacao avaliacao) {
		final Paciente paciente = avaliacao.getPaciente();
		final Usuario usuario = paciente.getMedicoResponsavel().getUsuario();
		
		CategoriasNotificacao.AVALIACAO_PACIENTE.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(AppUtil.getMensagem(messageSource, "avaliacao.nao.aprovado.notificacao.medico"))
			.apply();
	}

}
