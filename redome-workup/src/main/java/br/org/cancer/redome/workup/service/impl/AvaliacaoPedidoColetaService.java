package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ConsultaTarefasAvaliacaoPedidoColetaDTO;
import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.AvaliacaoPedidoColeta;
import br.org.cancer.redome.workup.model.AvaliacaoResultadoWorkup;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IAvaliacaoPedidoColetaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IAvaliacaoPedidoColetaService;
import br.org.cancer.redome.workup.service.IAvaliacaoResultadoWorkupService;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.CustomPageImpl;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Prescrição.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class AvaliacaoPedidoColetaService extends AbstractService<AvaliacaoPedidoColeta, Long> implements
		IAvaliacaoPedidoColetaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoPedidoColetaService.class);

	@Autowired
	private IAvaliacaoPedidoColetaRepository avaliacaoPedidoColetaRepository;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	private IAvaliacaoResultadoWorkupService avaliacaoResultadoWorkupService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	private IPedidoColetaService pedidoColetaService;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;

	@Override
	public IRepository<AvaliacaoPedidoColeta, Long> getRepository() {
		return avaliacaoPedidoColetaRepository;
	}
	
	@Override
	public Page<ConsultaTarefasAvaliacaoPedidoColetaDTO> listarTarefasAvaliacaoPedidoColeta(int pagina,
			int quantidadeRegistros) {
	
		Long idUsuarioLogado = usuarioService.obterIdUsuarioLogado();
	
		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_PEDIDO_COLETA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(idUsuarioLogado)
				.pageable(PageRequest.of(pagina, quantidadeRegistros))
				.perfilResponsavel(Arrays.asList(Perfis.MEDICO_REDOME.getId()))
				.build();
		
		CustomPageImpl<TarefaDTO> tarefas = null;
		try {
			 tarefas = tarefaHelper.pageTarefas(filtro);
		} catch (JsonProcessingException e) {
			 LOGGER.error("listarTarefasAvaliacaoPedidoColeta", e);
			 throw new BusinessException("erro.interno");
		}
	
		if (tarefas != null &&  CollectionUtils.isNotEmpty(tarefas.getContent())) {
			List<ConsultaTarefasAvaliacaoPedidoColetaDTO> lista = tarefas.stream()
					.map(tarefa -> {
						final AvaliacaoResultadoWorkup avaliacaoResultadoWorkup = avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorId(tarefa.getRelacaoEntidade());
						final SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup().getSolicitacao()); 
						final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
						final String nomePaciente = solicitacao.getMatch().getBusca().getPaciente().getNome();
						final Long idDoador = solicitacao.getMatch().getDoador().getId();
						final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
						final LocalDate dataColeta = avaliacaoResultadoWorkup.getResultadoWorkup().getPedidoWorkup().getDataColeta();
						
						return ConsultaTarefasAvaliacaoPedidoColetaDTO.builder()
								.idTarefa(tarefa.getId())
								.idStatusTarefa(tarefa.getStatus())
								.idAvaliacaoResultadoWorkup(avaliacaoResultadoWorkup.getId())
								.rmr(rmr)
								.nomePaciente(nomePaciente)
								.idDoador(idDoador)
								.identificacaoDoador(identificacaoDoador)
								.dataColeta(dataColeta)
								.build();
					})
					.collect(Collectors.toList());
			
			return new CustomPageImpl<ConsultaTarefasAvaliacaoPedidoColetaDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.getTotalElements());
		}
	
		return new CustomPageImpl<ConsultaTarefasAvaliacaoPedidoColetaDTO>();
	}
	
	@Override
	public void prosseguirPedidoColeta(Long idAvaliacaoResultadoWorkup) {
		AvaliacaoResultadoWorkup avaliacaoResultado = avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup);
		validarAvaliacaoResultadoWorkupProssguirColetaInviavel(avaliacaoResultado);
		validarAvaliacaoPedidoColetaExistente(avaliacaoResultado.getId());
		
		AvaliacaoPedidoColeta avaliacaoPedidoColeta = AvaliacaoPedidoColeta.builder()
				.avaliacaoResultadoWorkup(avaliacaoResultado)
				.pedidoProssegue(true)
				.usuario(usuarioService.obterIdUsuarioLogado())
				.build();
		
		save(avaliacaoPedidoColeta);
		
		pedidoWorkupService.finalizarWorkupNacional(avaliacaoResultado.getResultadoWorkup().getPedidoWorkup().getId());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(avaliacaoResultado.getResultadoWorkup().getPedidoWorkup().getSolicitacao(), 
				FasesWorkup.AGUARDANDO_AGENDAMENTO_COLETA.getId() );
		
		fecharTarefaAvaliarPedidoColeta(avaliacaoResultado.getId(), solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.RESULTADO_PEDIDO_WORKUP_INVIAVEL_APROVADO_PARA_DOADOR, solicitacao);
		
		pedidoColetaService.criarPedidoColeta(solicitacao);
		
		criarNotificacaoProsseguePedidoColeta(solicitacao);		
		
	}
	
	private void validarAvaliacaoResultadoWorkupProssguirColetaInviavel(AvaliacaoResultadoWorkup avaliacao) {
		if (!avaliacao.getProsseguir()) {
			throw new BusinessException("erro.avaliacao.pedido.coleta.avaliacao.resultado.workup.nao.prosseguir"); 
		}
		if (!avaliacao.getResultadoWorkup().getColetaInviavel()) {
			throw new BusinessException("erro.avaliacao.pedido.coleta.resultado.workup.coleta.viavel");
		}
		
	}
	
	private void validarAvaliacaoPedidoColetaExistente(Long idAvaliacaoResultadoWorkup) {
		AvaliacaoPedidoColeta avaliacaoPedidoColetaEncontrada = obterAvaliacaoPedidoColetaPelaAvaliacaoResultadoWorkupId(idAvaliacaoResultadoWorkup);
		if (avaliacaoPedidoColetaEncontrada != null) {
			throw new BusinessException("erro.avaliacao.pedido.coleta.ja.cadastrada");
		}
	}
	
	private AvaliacaoPedidoColeta obterAvaliacaoPedidoColetaPelaAvaliacaoResultadoWorkupId(Long idAvaliacaoResultadoWorkup) {		
		return avaliacaoPedidoColetaRepository.findByAvaliacaoResultadoWorkupId(idAvaliacaoResultadoWorkup).orElse(null);
	}
	
	private void fecharTarefaAvaliarPedidoColeta(Long idAvaliacaoResultadoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_PEDIDO_COLETA.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idAvaliacaoResultadoWorkup)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAvaliarResultadoWorkup", e);
			throw new BusinessException("erro.interno");
		}
		
	}
	
	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao, SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr(); 
		
		String[] parametros = {solicitacao.getMatch().getDoador().getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}
	
	private void criarNotificacaoProsseguePedidoColeta(SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		criarNotificacao(Perfis.MEDICO_TRANSPLANTADOR, rmr, String.format("Pedido de coleta do doador %s para o paciente %d foi aprovado pelo médido do REDOME..", identificacaoDoador, rmr));
		criarNotificacao(Perfis.ANALISTA_WORKUP, rmr, "O resultado de avaliação do pedido de coleta foi aprovado.");	
		
	}
	
	private void criarNotificacao(Perfis perfil, Long rmr, String descricao) {
		
		final CategoriasNotificacao categoria = CategoriasNotificacao.AVALIACAO_PEDIDO_COLETA;
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(descricao)
				.categoriaId(categoria.getId())
				.idPerfil(perfil.getId())
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
	}
		
	@Override
	public void naoProsseguirPedidoColeta(Long idAvaliacaoResultadoWorkup, String justificativa) {
		AvaliacaoResultadoWorkup avaliacaoResultado = avaliacaoResultadoWorkupService.obterAvaliacaoResultadoWorkupPorId(idAvaliacaoResultadoWorkup);
		validarResultadoWorkupColetaInviavel(avaliacaoResultado.getResultadoWorkup());
		validarJustificativa(avaliacaoResultado, justificativa);
		validarAvaliacaoPedidoColetaExistente(avaliacaoResultado.getId());
		
		AvaliacaoPedidoColeta avaliacaoPedidoColeta = AvaliacaoPedidoColeta.builder()
				.avaliacaoResultadoWorkup(avaliacaoResultado)
				.pedidoProssegue(false)
				.usuario(usuarioService.obterIdUsuarioLogado())
				.build();
		
		save(avaliacaoPedidoColeta);
		
		pedidoWorkupService.cancelarWorkupNacional(avaliacaoResultado.getResultadoWorkup().getPedidoWorkup().getId());
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.obterSolicitacaoWorkup(avaliacaoResultado.getResultadoWorkup().getPedidoWorkup().getSolicitacao());
		fecharTarefaAvaliarPedidoColeta(avaliacaoResultado.getId(), solicitacao);
		
		criarLogEvolucao(TipoLogEvolucao.RESULTADO_PEDIDO_WORKUP_INVIAVEL_NAO_APROVADO_PARA_DOADOR, solicitacao);		
		
		criarNotificacaoNaoProsseguePedidoColeta(solicitacao, justificativa);
		
	}

	private void validarJustificativa(AvaliacaoResultadoWorkup avaliacaoResultado, String justificativa) {
		if (avaliacaoResultado.getProsseguir() && StringUtils.isEmpty(justificativa)) {
			throw new BusinessException("erro.avaliacao.pedido.coleta.nao.prosseguir.sem.justificativa");
		}
		
	}

	private void validarResultadoWorkupColetaInviavel(ResultadoWorkup resultadoWorkup) {
		if (!resultadoWorkup.getColetaInviavel()) {
			throw new BusinessException("erro.avaliacao.pedido.coleta.resultado.workup.coleta.viavel");
		}
	}
	
	private void criarNotificacaoNaoProsseguePedidoColeta(SolicitacaoWorkupDTO solicitacao, String justificativa) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		if (StringUtils.isNotEmpty(justificativa)) {
			justificativa = "Justificativa: " + justificativa;
		}
		else {
			justificativa = "";
		}
		
		criarNotificacao(Perfis.MEDICO_TRANSPLANTADOR, rmr, String.format("Pedido de coleta do doador %s para o paciente %d foi reprovado pelo médido do REDOME. %s", identificacaoDoador, rmr, justificativa));
		criarNotificacao(Perfis.ANALISTA_WORKUP, rmr, String.format("O resultado de avaliação do pedido de coleta foi reprovado. %s", justificativa));	
		
	}


}
