package br.org.cancer.modred.service.integracao;

import java.util.List;

import br.org.cancer.modred.controller.dto.doador.MensagemErroIntegracao;
import br.org.cancer.modred.controller.dto.doador.SolicitacaoRedomewebDTO;
import br.org.cancer.modred.model.SolicitacaoRedomeweb;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoExame.
 * 
 * TODO: Refatorar esta classe para que ela faça ligação com solicitação e não direto a paciente.
 * 
 * @author Pizão
 */
public interface IIntegracaoSolicitacaoRedomeWebService extends IService<SolicitacaoRedomeweb, Long> {

	List<MensagemErroIntegracao> atualizarPedidoExameIntegracaoDoadorNacional(List<SolicitacaoRedomewebDTO> solicitacoes);
	
	
}