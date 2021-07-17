/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Disponibilidade;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Será executada quando a avaliação de prescrição é realizada pelo médico redome e
 * a mesma, independente do motivo, é reprovada.
 * Neste momento, todos os médicos do centro de transplante deverão ser notificados.
 * 
 */
@Component
public class ConfirmacaoDataRealizacaoWorkupObserver extends EntityObserver<Disponibilidade> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean condition(Disponibilidade disponibilidade) {
		return disponibilidade.getPedidoWorkup() != null && disponibilidade.getDataAceite() == null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void process(Disponibilidade disponibilidade) {
		final Match match = disponibilidade.getPedidoWorkup().getSolicitacao().getMatch();
		final Paciente paciente = match.getBusca().getPaciente();
		final IDoador doador = (IDoador) match.getDoador();
		final CentroTransplante centroTransplante = match.getBusca().getCentroTransplante();
				
		CategoriasNotificacao.PRESCRICAO.getConfiguracao()
			.criar()
			.comParceiro(centroTransplante.getId())
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comPaciente(paciente.getRmr())
			.comDescricao(
				AppUtil.getMensagem(messageSource, "pedidoworkup.solicitar.confirmacao.data.realizacao.centrotransplante.notificacao", 
						doador.getIdentificacao(), paciente.getRmr())
			)
			.apply();
	}

}
