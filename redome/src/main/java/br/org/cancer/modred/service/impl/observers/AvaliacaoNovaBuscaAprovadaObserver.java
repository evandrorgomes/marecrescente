package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.AvaliacaoNovaBusca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.StatusAvaliacaoNovaBusca;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * Observador para notificação ao médico responsável pelo paciente
 * no caso de reprovação.
 * @author Filipe paes
 *
 */
@Component
public class AvaliacaoNovaBuscaAprovadaObserver extends EntityObserver<AvaliacaoNovaBusca>{

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected boolean condition(AvaliacaoNovaBusca avaliacao) {
		return StatusAvaliacaoNovaBusca.APROVADA.equals(avaliacao.getStatus());
	}

	@Override
	protected void process(AvaliacaoNovaBusca avaliacao) {
		final Paciente paciente = avaliacao.getPaciente();
		final Usuario usuario = paciente.getMedicoResponsavel().getUsuario();
		
		CategoriasNotificacao.AVALIACAO_NOVA_BUSCA.getConfiguracao()
			.criar()
			.paraUsuario(usuario.getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(AppUtil.getMensagem(messageSource, "notificacao.nova.busca.aprovada.sucesso"))
			.apply();
	}

}
