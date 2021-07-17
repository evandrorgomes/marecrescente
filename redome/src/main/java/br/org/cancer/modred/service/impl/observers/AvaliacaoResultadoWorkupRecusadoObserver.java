package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.AvaliacaoResultadoWorkup;
import br.org.cancer.modred.service.impl.custom.EntityObserver;

/**
 * @author Pizão
 * 
 * Estratégia a ser executada quando a avaliação do resultado de workup
 * é recusado.
 * 
 */
@Component
public class AvaliacaoResultadoWorkupRecusadoObserver extends EntityObserver<AvaliacaoResultadoWorkup> {

	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	public boolean condition(AvaliacaoResultadoWorkup avaliacaoResultado) {
		return Boolean.FALSE.equals(avaliacaoResultado.getSolicitaColeta())
				&& avaliacaoResultado.getJustificativa() != null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void process(AvaliacaoResultadoWorkup avaliacaoResultado) {
/*		final ResultadoWorkupDTO resultadoWorkup = avaliacaoResultado.getResultadoWorkup();
		final PedidoWorkup pedidoWorkup = resultadoWorkup.getPedidoWorkup();
		final IDoador doador = (IDoador) pedidoWorkup.getSolicitacao().getMatch().getDoador();
		final Paciente paciente = pedidoWorkup.getSolicitacao().getMatch().getBusca().getPaciente();
		
		String mensagem = null;
		
		mensagem = AppUtil.getMensagem(messageSource, 
				doador.isNacional() ?
				"centro_transplantador.doador.notificacao.reprovacao_coleta_nacional" : "doador.internacional.notificacao.reprovacao_coleta", 
				doador.getIdentificacao().toString(), paciente.getRmr());

		CategoriasNotificacao.PEDIDO_WORKUP.getConfiguracao()
			.criar()
			.paraUsuario(resultadoWorkup.getUsuario().getId())
			.comPaciente(paciente.getRmr())
			.comDescricao(mensagem)
			.apply();
	*/	
	}

}
