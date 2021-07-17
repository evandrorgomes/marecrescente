package br.org.cancer.modred.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.gateway.sms.GatewayBuilder;
import br.org.cancer.modred.gateway.sms.GlobalGatewaySms;
import br.org.cancer.modred.gateway.sms.IRetornoSms;
import br.org.cancer.modred.gateway.sms.StatusSms;
import br.org.cancer.modred.model.Configuracao;
import br.org.cancer.modred.model.ContatoTelefonicoDoador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.MotivoStatusDoador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.model.PedidoContatoSms;
import br.org.cancer.modred.model.SmsEnviado;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposSolicitacao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IConfiguracaoRepository;
import br.org.cancer.modred.persistence.IPedidoContatoSmsRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IDoadorNacionalService;
import br.org.cancer.modred.service.IPedidoContatoSmsService;
import br.org.cancer.modred.service.ISmsEnviadoService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de implementação das funcionalidades envolvendo a entidade
 * PedidoContatoSms.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class PedidoContatoSmsService extends AbstractLoggingService<PedidoContatoSms, Long>
		implements IPedidoContatoSmsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoContatoSmsService.class);

	private static final String MENSAGEM_SMS = "Prezado(a) %s (cod. doador %d) "
			+ "encontramos uma possivel compatibilidade com um de nossos pacientes e precisamos realizar novos testes para confirmação. "
			+ "Por favor, entre em contato em um de nossos telefones (a ligação poderá ser feita a cobrar) para maiores esclarecimentos: (21)2505-5691/2505-5639. "
			+ "Equipe Redome";

	@Autowired
	private IPedidoContatoSmsRepository pedidoContatoSmsRepository;

	@Autowired
	private IDoadorNacionalService doadorService;
	
	@Autowired
	private ISmsEnviadoService smsEnviadoService;

	@Override
	public IRepository<PedidoContatoSms, Long> getRepository() {
		return pedidoContatoSmsRepository;
	}

	@Override
	public Paciente obterPaciente(PedidoContatoSms entity) {
		return null;
	}

	@Override
	public String[] obterParametros(PedidoContatoSms entity) {
		return new String[] {};
	}

	@Autowired
	private IConfiguracaoRepository configuracaoRepository;

	@Override
	public PedidoContatoSms obterPedidoContatoSmsPorId(Long id) {
		PedidoContatoSms pedidoContatoSms = findById(id);
		if (pedidoContatoSms == null) {
			throw new BusinessException("erro.pedido.nao.encontrado");
		}
		return pedidoContatoSms;
	}

	@Override
	public PedidoContatoSms criarPedidoContatoSms(PedidoContato pedidoContato) {
		PedidoContatoSms pedidoContatoSms = new PedidoContatoSms(pedidoContato);

		final DoadorNacional doador = (DoadorNacional) pedidoContato.getSolicitacao().getMatch().getDoador();
		if (doador.getContatosTelefonicos() == null) {
			return null;
		}
		List<SmsEnviado> listaSmsEnviado = doador.getContatosTelefonicos().stream()
				.filter(ContatoTelefonicoDoador::naoEstaExcluido).filter(ContatoTelefonicoDoador::iniciaCom7Ou8ou9)
				.map(contatoTelefonico -> enviarSms(pedidoContatoSms, contatoTelefonico)).collect(Collectors.toList());

		if (listaSmsEnviado == null || listaSmsEnviado.isEmpty()) {
			return null;
		}

		pedidoContatoSms.setSmsEnviados(listaSmsEnviado);

		save(pedidoContatoSms);

		criarTarefa(pedidoContatoSms);

		return pedidoContatoSms;
	}

	private SmsEnviado enviarSms(PedidoContatoSms pedidoContatoSms, ContatoTelefonicoDoador contatoTelefonico) {
		try {
			DoadorNacional doador = (DoadorNacional) pedidoContatoSms.getPedidoContato().getDoador();
			IRetornoSms retornoSms = GatewayBuilder.build(GlobalGatewaySms.class)
					.enviar(String.format("%d%d", contatoTelefonico.getCodArea(),
							AppUtil.formatarTelefoneCelular(contatoTelefonico.getNumero())),
							String.format(MENSAGEM_SMS, doador.getNome(), doador.getDmr()));
			final SmsEnviado smsEnviado = new SmsEnviado(pedidoContatoSms, contatoTelefonico);
			if (retornoSms != null) {
				smsEnviado.setIdentificacaoGatewaySms(retornoSms.getIdentificador());
				smsEnviado.setStatusSms(retornoSms.getStatus());
			}
			return smsEnviado;
		} catch (InstantiationException | IllegalAccessException | IOException | URISyntaxException e) {
			return new SmsEnviado(pedidoContatoSms, contatoTelefonico);
		}
	}

	private void criarTarefa(PedidoContatoSms pedidoContatoSms) {
		final Long rmr = pedidoContatoSms.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente()
				.getRmr();

		TarefaDTO tarefaSms = TiposTarefa.SMS.getConfiguracao().criarTarefa().comRmr(rmr)
				.comObjetoRelacionado(pedidoContatoSms.getId()).apply();

		TiposTarefa.VERIFICAR_STATUS_SMS_ENVIADO.getConfiguracao().criarTarefa().comRmr(rmr).comTarefaPai(tarefaSms)
				.apply();

	}

	@Override
	public PedidoContatoSms finalizarPedidoContatoSms(Long idPedidoContatoSms) {
		LOGGER.debug("FECHANDO PEDIDO DE CONTATO SMS");

		PedidoContatoSms pedidoContatoSms = obterPedidoContatoSmsPorId(idPedidoContatoSms);
		pedidoContatoSms.setAberto(false);
		pedidoContatoSmsRepository.save(pedidoContatoSms);

		finalizarTarefa(pedidoContatoSms);

		return pedidoContatoSms;
	}

	@Override
	public void finalizarPedidoContatoSmsInativandoDoador(Long idPedidoContatoSms) {

		LOGGER.debug("FECHANDO PEDIDO DE CONTATO SMS INATIVANDO DOADOR");

		PedidoContatoSms pedidoContatoSms = finalizarPedidoContatoSms(idPedidoContatoSms);

		inativarDoadorCasoContactadoENaoProsseguir(pedidoContatoSms.getPedidoContato());

		LOGGER.debug("PEDIDO DE CONTATO SMS FECHADO E DOADOR INATIVO");
	}

	private void finalizarTarefa(PedidoContatoSms pedidoContatoSms) {
		final Long rmr = pedidoContatoSms.getPedidoContato().getSolicitacao().getMatch().getBusca().getPaciente()
				.getRmr();

		TarefaDTO tarefaSms = TiposTarefa.SMS.getConfiguracao().fecharTarefa().comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA)).comObjetoRelacionado(pedidoContatoSms.getId()).apply();

		TiposTarefa.VERIFICAR_STATUS_SMS_ENVIADO.getConfiguracao().fecharTarefa().comRmr(rmr)
				.comStatus(Arrays.asList(StatusTarefa.ABERTA)).comTarefaPai(tarefaSms.getId()).apply();

	}

	private void inativarDoadorCasoContactadoENaoProsseguir(PedidoContato pedidoContato) {
		final DoadorNacional doador = (DoadorNacional) pedidoContato.getSolicitacao().getMatch().getDoador();
		doadorService.inativarDoadorFase3NaoRetornoSms(doador.getId(), StatusDoador.INATIVO_PERMANENTE,
				MotivoStatusDoador.NAO_CONTACTADO);
	}

	@Override
	public Boolean existePedidoContatoSmsDentroDoPrazo(PedidoContato pedido) {
		if (pedido.getTipoContato() == TiposSolicitacao.FASE_3.getId()) {
			PedidoContatoSms pedidoContatoSms = pedidoContatoSmsRepository
					.obterPedidoSmsPorIdPedidoContato(pedido.getId());
			long dias = ChronoUnit.DAYS.between(pedidoContatoSms.getDataCriacao(), LocalDateTime.now());
			Configuracao quantidade = configuracaoRepository
					.findByChave(Configuracao.TEMPO_MAXIMO_CONSIDERAR_RETORNO_CONTATO_DOADOR_FASE3);
			if (dias < Long.parseLong(quantidade.getValor())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void atualizarStatusSmsEnviados(Long id) {
		
		PedidoContatoSms pedido = obterPedidoContatoSmsPorId(id);
		pedido.getSmsEnviados().forEach(smsEnviado -> {
			
			smsEnviadoService.atualizarStatus(smsEnviado);
			
		});
		
		if (pedido.getSmsEnviados().stream().anyMatch(smsEnviado -> StatusSms.LEITURA.equals(smsEnviado.getStatusSms()))) {
			finalizarPedidoContatoSms(pedido.getId());
		}

	}

}
