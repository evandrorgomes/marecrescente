package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.org.cancer.modred.controller.dto.PedidoTransferenciaCentroDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.CentroTransplante;
import br.org.cancer.modred.model.Evolucao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoTransferenciaCentro;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.domain.TiposTransferenciaCentro;
import br.org.cancer.modred.persistence.IPedidoTransferenciaCentroRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoService;
import br.org.cancer.modred.service.ICentroTransplanteService;
import br.org.cancer.modred.service.IPacienteService;
import br.org.cancer.modred.service.IPedidoTransferenciaCentroService;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.CampoMensagem;

/**
 * Classe de negócios de para pedido de transferência centro.
 * 
 * @author brunosousa
 *
 */
@Service
@Transactional
public class PedidoTransferenciaCentroService extends AbstractLoggingService<PedidoTransferenciaCentro, Long> implements IPedidoTransferenciaCentroService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoTransferenciaCentroService.class);

	@Autowired
	private IPedidoTransferenciaCentroRepository pedidoTransferenciaCentroRepository;
	
	@Autowired
	private ICentroTransplanteService centroTransplanteService;
	
	@Autowired
	private IPacienteService pacienteService;
	
	@Autowired
	private IAvaliacaoService avaliacaoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private MessageSource messageSource;
	
	
	@Override
	public IRepository<PedidoTransferenciaCentro, Long> getRepository() {
		return pedidoTransferenciaCentroRepository;
	}

	@Override
	public Paciente obterPaciente(PedidoTransferenciaCentro entity) {
		return entity.getPaciente();
	}

	@Override
	public String[] obterParametros(PedidoTransferenciaCentro entity) {

		return new String[]{StringUtils.quote(entity.getCentroAvaliadorDestino().getNome())};
	}

	@CreateLog(TipoLogEvolucao.SOLICITADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR)
	@Override
	public void solicitarTransferenciaCentroAvaliador(Long rmr, Long idCentroAvaliadorDestino) {
		LOGGER.info("Processo de transferência de centro para o paciente RMR " + rmr + " iniciado.");
		try {
			Paciente paciente = pacienteService.obterPaciente(rmr);
			
			PedidoTransferenciaCentro pedidoNaoAprovado = 
					obterPedidoTransferenciaCentroEmAprovacaoPorPaciente(rmr, TiposTransferenciaCentro.AVALIADOR);
			if (Optional.ofNullable(pedidoNaoAprovado).isPresent()) {
				throw new BusinessException("erro.pedido.transferencia.centroavaliador.ja.solicitado");
			}
			
			if(avaliacaoService.verificarSeAvaliacaoEmAndamento(rmr)){
				throw new BusinessException("erro.pedido.transferencia.ultimaavaliacao.nao.iniciada");
			}
			
			CentroTransplante centroAvaliadorDestino = 
					centroTransplanteService.obterCentroTransplante(idCentroAvaliadorDestino);
			
			if (paciente.getCentroAvaliador().equals(centroAvaliadorDestino)) {
				throw new BusinessException("erro.pedido.transferencia.mesmo.centroavaliador");
			}
			
			Avaliacao avaliacao = avaliacaoService.obterUltimaAvaliacaoPorPaciente(rmr);
			
			if(avaliacao.jaAvaliada()){
				PedidoTransferenciaCentro pedido = new PedidoTransferenciaCentro();
				pedido.setTipoTransferenciaCentro(TiposTransferenciaCentro.AVALIADOR);
				pedido.setPaciente(paciente);
				pedido.setCentroAvaliadorOrigem(paciente.getCentroAvaliador());
				pedido.setCentroAvaliadorDestino(centroAvaliadorDestino);
						
				save(pedido);
				
				criarTarefaPedidoTransferenciaCentro(paciente, centroAvaliadorDestino.getId(), pedido.getId());
			}
			else {
				avaliacaoService.alterarCentroAvaliador(avaliacao.getId(), centroAvaliadorDestino);
			}
			
		}
		finally {
			LOGGER.info("Processo de transferência de centro para o paciente " + rmr + " terminado.");	
		}
		
	}

	private void criarTarefaPedidoTransferenciaCentro(Paciente paciente, Long idCentroAvaliador, Long idObjetoRelacionado) {
					
		TiposTarefa.PEDIDO_TRANSFERENCIA_CENTRO.getConfiguracao()
			.criarTarefa()
			.comRmr(paciente.getRmr())
			.comParceiro(idCentroAvaliador)
			.comObjetoRelacionado(idObjetoRelacionado)			
			.apply();
	}
	
	private PedidoTransferenciaCentro obterPedidoTransferenciaCentroEmAprovacaoPorPaciente(Long rmr, TiposTransferenciaCentro tipoTransferenciaCentro) {
		return pedidoTransferenciaCentroRepository.findByPacienteRmrAndAprovadoIsNullAndTipoTransferenciaCentro(rmr, tipoTransferenciaCentro);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JsonViewPage<TarefaDTO> listarTarefas(Boolean atribuidosAMin, Long idParceiro, PageRequest pageRequest) {

		List<StatusTarefa> status = new ArrayList<>();
		status.add(StatusTarefa.ABERTA);
		if (atribuidosAMin) {
			status.add(StatusTarefa.ATRIBUIDA);
		}
	
		return TiposTarefa.PEDIDO_TRANSFERENCIA_CENTRO.getConfiguracao()
			.listarTarefa()
			.comParceiros(Arrays.asList(idParceiro))
			.comStatus(status)
			.comPaginacao(pageRequest)
			.apply();
	
	}
	
	@CreateLog(TipoLogEvolucao.APROVADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR)
	@Override
	public void aceitarTransferencia(Long idPedidoTransferencia) {
		PedidoTransferenciaCentro pedidoTransferencia = findById(idPedidoTransferencia);
		pedidoTransferencia.setAprovado(Boolean.TRUE);
		pedidoTransferencia.setUsuario(usuarioService.obterUsuarioLogado());
		pedidoTransferencia.setDataAtualizacao(LocalDateTime.now());
		save(pedidoTransferencia);
		
		final Paciente paciente = pedidoTransferencia.getPaciente();
		pacienteService.transferirCentroAvaliador(
				paciente.getRmr(), pedidoTransferencia.getCentroAvaliadorDestino());
		
		fecharTarefaPedidoTransferenciaCentro(pedidoTransferencia, 
				AppUtil.getMensagem(messageSource, 
				"notificacao.reprovacao.transferencia.centro.avaliador.descricao",
				paciente.getRmr(), pedidoTransferencia.getCentroAvaliadorDestino().getNome()));
		
	}
	
	private void fecharTarefaPedidoTransferenciaCentro(PedidoTransferenciaCentro pedidoTransferencia, String descricaoNotificacao ){
		TiposTarefa.PEDIDO_TRANSFERENCIA_CENTRO.getConfiguracao()
				.fecharTarefa()
				.comObjetoRelacionado(pedidoTransferencia.getId())
				.comRmr(pedidoTransferencia.getPaciente().getRmr())
				.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
				.comUsuario(usuarioService.obterUsuarioLogado())
				.finalizarProcesso()
				.apply();
	}

	@CreateLog(TipoLogEvolucao.REPROVADO_PEDIDO_TRANSFERENCIA_CENTRO_AVALIADOR)
	@Override
	public void reprovarTransferencia(Long idPedidoTransferencia, String justificativa) {
		PedidoTransferenciaCentro pedidoTransferencia = findById(idPedidoTransferencia);
		pedidoTransferencia.setAprovado(Boolean.FALSE);
		pedidoTransferencia.setUsuario(usuarioService.obterUsuarioLogado());
		pedidoTransferencia.setDataAtualizacao(LocalDateTime.now());
		pedidoTransferencia.setJustificativa(justificativa);
		save(pedidoTransferencia);
		
		fecharTarefaPedidoTransferenciaCentro(pedidoTransferencia, 
				AppUtil.getMensagem(messageSource, "notificacao.reprovacao.transferencia.centro.avaliador.descricao",
						pedidoTransferencia.getPaciente().getRmr(), 
						pedidoTransferencia.getCentroAvaliadorDestino().getNome(),
						pedidoTransferencia.getJustificativa()));
		
	}

	@Override
	public PedidoTransferenciaCentro obterPedidoNaoAvaliado(Long rmr) {
		return pedidoTransferenciaCentroRepository.obterPedidoAberto(rmr);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoTransferenciaCentro obterPedidoTransferenciaCentro(Long idPedidoTransporteCentro) {
		final PedidoTransferenciaCentro pedido = findById(idPedidoTransporteCentro);
		if (!Optional.ofNullable(pedido).isPresent()) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "Registro");
		}
						
		return pedido;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PedidoTransferenciaCentroDTO obterPedidoTransferenciaCentroDTO(Long idPedidoTransporteCentro) {
		final PedidoTransferenciaCentro pedido = obterPedidoTransferenciaCentro(idPedidoTransporteCentro);
		final Evolucao ultimaEvolucao = pacienteService.obterUltimaEvolucao(pedido.getPaciente().getRmr());
		
		PedidoTransferenciaCentroDTO pedidoDTO = new PedidoTransferenciaCentroDTO();
		pedidoDTO.setPedidoTransferenciaCentro(pedido);
		pedidoDTO.setUltimaEvolucao(ultimaEvolucao);
		
		return pedidoDTO;
	}
	
	@Override
	protected List<CampoMensagem> validateEntity(PedidoTransferenciaCentro entity) {
		List<CampoMensagem> validateResult = super.validateEntity(entity);
		if (Optional.ofNullable(entity.getAprovado()).isPresent()) {
			if (!entity.getAprovado() && StringUtils.isEmpty(entity.getJustificativa())) {
				validateResult.add(new CampoMensagem("justificativa",
						AppUtil.getMensagem(messageSource, "javax.validation.constraints.NotNull.message")));
			}
		}
		
		return validateResult;		
	}


}
