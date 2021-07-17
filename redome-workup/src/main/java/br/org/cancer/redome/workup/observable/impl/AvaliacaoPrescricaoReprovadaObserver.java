/**
 * 
 */
package br.org.cancer.redome.workup.observable.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;
import br.org.cancer.redome.workup.observable.EntityObserver;

/**
 * @author Pizão
 * 
 * Será executada quando a avaliação de prescrição é realizada pelo médico redome e
 * a mesma, independente do motivo, é reprovada.
 * Neste momento, todos os médicos do centro de transplante deverão ser notificados.
 * 
 */
@Component
public class AvaliacaoPrescricaoReprovadaObserver extends EntityObserver<AvaliacaoPrescricao> {

	@Autowired
	private MessageSource messageSource;
		
	@Override
	public boolean condition(AvaliacaoPrescricao avaliacaoPrescricao) {
		return Boolean.FALSE.equals(avaliacaoPrescricao.getAprovado());
	}

	@Override
	public void process(AvaliacaoPrescricao avaliacaoPrescricao) {
		/*final Match match = avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch();
		final Paciente paciente = match.getBusca().getPaciente();
		final IDoador<?> doador = (IDoador<?>) match.getDoador();
		final CentroTransplante centroTransplante = match.getBusca().getCentroTransplante();
		
		CategoriasNotificacao.PRESCRICAO.getConfiguracao()
			.criar()
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(centroTransplante.getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(
				AppUtil.getMensagem(messageSource, "avaliacao.reprovada.notificacao.centro.transplante.mensagem", doador.getIdentificacao())
			)
			.apply();*/
	}

}
