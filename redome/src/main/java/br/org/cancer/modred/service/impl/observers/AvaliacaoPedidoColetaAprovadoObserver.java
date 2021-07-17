/**
 * 
 */
package br.org.cancer.modred.service.impl.observers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import br.org.cancer.modred.model.AvaliacaoPedidoColeta;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.service.impl.custom.EntityObserver;
import br.org.cancer.modred.util.AppUtil;

/**
 * @author Pizão
 * 
 * Estratégia a ser executada quando a avaliação do pedido de coleta
 * é aprovado (prossegue).
 * 
 */
@Component
public class AvaliacaoPedidoColetaAprovadoObserver extends EntityObserver<AvaliacaoPedidoColeta> {

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public boolean condition(AvaliacaoPedidoColeta avaliacao) {
		return avaliacao.getPedidoProssegue() != null 
					&& Boolean.TRUE.equals(avaliacao.getPedidoProssegue());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void process(AvaliacaoPedidoColeta avaliacao) {
		final Match match = avaliacao.getSolicitacao().getMatch();
		IDoador doador  = (IDoador) match.getDoador();
				
		final CentroTransplante centroTransplante = match.getBusca().getCentroTransplante();
		final Long rmr = match.getBusca().getPaciente().getRmr();
		
		final String textoNotificacao =
				AppUtil.getMensagem(messageSource, 
						"notificacao.medico.resultado_avaliacao_pedido_coleta_aprovado", doador.getIdentificacao().toString(), rmr);
				
		CategoriasNotificacao.AVALIACAO_PEDIDO_COLETA.getConfiguracao()
			.criar()
			.comPaciente(rmr)
			.paraPerfil(Perfis.MEDICO_TRANSPLANTADOR)
			.comParceiro(centroTransplante.getId())
			.comDescricao(textoNotificacao)
			.apply();
		
		final Usuario analistaWorkup = 
				avaliacao.getSolicitacao().getPedidoWorkup().getUsuarioResponsavel();
		
		CategoriasNotificacao.AVALIACAO_PEDIDO_COLETA.getConfiguracao()
			.criar()
			.paraUsuario(analistaWorkup.getId())
			.comPaciente(rmr)
			.comParceiro(centroTransplante.getId())
			.comDescricao(AppUtil.getMensagem(messageSource, 
					"notificacao.analista_workup.resultado_avaliacao_pedido_coleta_aprovado"))
			.apply();
	}

}
