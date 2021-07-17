package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.AvaliacaoNovaBusca;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusAvaliacaoNovaBusca;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * Observer destinado a notificar os usuários do perfil AVALIADOR_NOVA_BUSCA
 * quando uma nova solicitação de busca é incluída.
 * 
 * @author Filipe Paes
 *
 */
@Component
public class AvaliacaoNovaBuscaSolicitadaObserver extends EntityObserver<AvaliacaoNovaBusca>{

	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected boolean condition(AvaliacaoNovaBusca avaliacao) {
		return StatusAvaliacaoNovaBusca.AGUARDANDO_AVALIACAO.equals(avaliacao.getStatus());
	}

	@Override
	protected void process(AvaliacaoNovaBusca avaliacao) {
		final Paciente paciente = avaliacao.getPaciente();
		
		CategoriasNotificacao.AVALIACAO_NOVA_BUSCA.getConfiguracao()
			.criar()
			.paraPerfil(Perfis.AVALIADOR_NOVA_BUSCA)
			.comPaciente(paciente.getRmr())
			.comDescricao(AppUtil.getMensagem(messageSource, "avaliacao.nova.busca.paciente.criada"))
			.apply();
	}
}
