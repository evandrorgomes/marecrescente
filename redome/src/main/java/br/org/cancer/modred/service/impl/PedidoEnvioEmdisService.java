package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.PedidoEnvioEmdis;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IPedidoEnvioEmdisRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IBuscaService;
import br.org.cancer.modred.service.IPedidoEnvioEmdisService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.impl.config.Equals;
import br.org.cancer.modred.service.impl.config.IsNull;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Implementacao da interface {@link IPedidoEnvioEmdisService}.
 * @author Filipe Paes.
 *
 */
@Service
public class PedidoEnvioEmdisService  extends  AbstractService<PedidoEnvioEmdis, Long> implements IPedidoEnvioEmdisService{

	@Autowired
	private IPedidoEnvioEmdisRepository pedidoEnvioEmdisRepository;
	
	@Autowired
	private ISolicitacaoService solicitacaoService;
	
	@Autowired
	private IBuscaService buscaService;
	
	@Override
	public IRepository<PedidoEnvioEmdis, Long> getRepository() {
		return pedidoEnvioEmdisRepository;
	}

	@Override
	public void criarPedido(Long idBusca) {
		if(existePedidoEmAberto(idBusca)) {
			throw new BusinessException("erro.pedido.envio.emdis.pedido_existente");
		}
		Busca busca = buscaService.obterBusca(idBusca);
		
		Solicitacao solicitarPedidoDeEnvioPacienteEmdis = solicitacaoService.solicitarPedidoDeEnvioPacienteEmdis(busca);
		PedidoEnvioEmdis pedidoEnvio = new PedidoEnvioEmdis();
		
		pedidoEnvio.setDataCriacao(LocalDateTime.now());
		pedidoEnvio.setSolicitacao(solicitarPedidoDeEnvioPacienteEmdis);
		save(pedidoEnvio);
		
		TiposTarefa.ENVIAR_PACIENTE_PARA_EMDIS_FOLLOWUP.getConfiguracao()
		.criarTarefa()
		.comRmr(busca.getPaciente().getRmr())
		.comObjetoRelacionado(pedidoEnvio.getId())
		.apply();

	}

	private boolean existePedidoEmAberto(Long idBusca) {
		return !CollectionUtils.isEmpty(this.find(new Equals<Long>("solicitacao.busca.id", idBusca), new IsNull("dataRetornoEmdis")));
	}

}
