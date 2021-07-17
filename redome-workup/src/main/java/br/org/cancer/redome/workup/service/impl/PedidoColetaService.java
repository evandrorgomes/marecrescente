package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.NotificacaoDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.PedidoColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaDoadorColeta;
import br.org.cancer.redome.workup.model.PedidoLogisticaMaterialColetaNacional;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.domain.CategoriasNotificacao;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusPedidosColeta;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IPedidoColetaRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IContagemCelulaService;
import br.org.cancer.redome.workup.service.IPedidoColetaService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;
import br.org.cancer.redome.workup.util.AppUtil;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade pedido de coleta.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PedidoColetaService extends AbstractService<PedidoColeta, Long> implements IPedidoColetaService {

	@Autowired
	private IPedidoColetaRepository pedidoColetaRepository;
		
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
			
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	private IPedidoWorkupService pedidoWorkupService;

	@Autowired
	private PedidoLogisticaMaterialColetaNacionalService pedidoLogisticaMaterialColetaNacionalService;

	@Autowired
	private PedidoLogisticaDoadorColetaService pedidoLogisticaDoadorColetaService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IContagemCelulaService contagemCelulaService;
	
	@Override
	public IRepository<PedidoColeta, Long> getRepository() {
		return pedidoColetaRepository;
	}
	
	@Override
	public PedidoColeta criarPedidoColeta(SolicitacaoWorkupDTO solicitacao) {
		if (solicitacao.solicitacaoWorkupInternacional()) {
			return criarPedidoColetaInternacional(solicitacao);
		}
		else if (solicitacao.solicitacaoWorkupNacional()) {
			return criarPedidoColetaNacional(solicitacao);
		}
		return null;
	}

	@SuppressWarnings("static-access")
	@Override
	public String agendarPedidoColeta(Long idPedidoColeta, PedidoColeta pedidoColeta) throws JsonProcessingException {
		
		PedidoColeta pedidoColetaRecuperado = this.obterPedidoColetaPorId(idPedidoColeta); 
		if (pedidoColetaRecuperado == null) {
			throw new BusinessException("pedido.coleta.erro.nao.encontrado");
		}
		
		validarAgendamentoPedidoColeta(pedidoColeta);
		
		PedidoColeta pedidoColetaNovo = pedidoColeta.builder()
			.id(pedidoColetaRecuperado.getId())
			.solicitacao(pedidoColetaRecuperado.getSolicitacao())
			.status(StatusPedidosColeta.AGENDADO.getId())
			.build();
		save(pedidoColetaNovo);

		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoColetaRecuperado.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_DEFINICAO_LOGISTICA.getId());

		this.fecharTarefaAgendarColetaNacional(idPedidoColeta, solicitacao);
		
		this.criarLogisticaDoadorColeta(pedidoColetaNovo, solicitacao);
		this.criarLogisticaMaterialColeta(pedidoColetaNovo, solicitacao);
		this.criarContagemCelula(pedidoColetaNovo, solicitacao);

		this.criarLogEvolucao(TipoLogEvolucao.AGENDAMENTO_COLETA_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		this.criarNotificacaoParaAnalistaWorkup(solicitacao);
		
		return "agendar.pedido.coleta.mensagem.sucesso";
	}

	private void criarContagemCelula(PedidoColeta pedidoColeta, SolicitacaoWorkupDTO solicitacao) {
		PedidoWorkup pedidoWorkup = this.pedidoWorkupService.obterPedidoWorkupPorSolicitacao(solicitacao.getId());
		this.contagemCelulaService.criarPedidoContagemCelula(pedidoWorkup.getId());
		this.criarTarefaCadastrarContagemCelula(pedidoWorkup.getId(), solicitacao);
	}

	private void criarTarefaCadastrarContagemCelula(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.CADASTRAR_COLETA_CONTAGEM_CELULA.getId()))
				.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoEntidade(idPedidoWorkup)
				.relacaoParceiro(solicitacao.getIdCentroColeta())
				.build(); 

		tarefaHelper.criarTarefa(tarefa);	
		
	}

	@Override
	public PedidoColeta obterPedidoColetaPorId(Long idPedidoColeta) {
		if (idPedidoColeta == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoColetaRepository.findById(idPedidoColeta).orElseThrow(() -> new BusinessException("pedido.coleta.erro.nao.encontrado")); 
	}

	@Override
	public PedidoColeta obterPedidoColetaEDatasWorkupPorId(Long idPedidoColeta) {
		if (idPedidoColeta == null) {
			throw new BusinessException("erro.id.nulo");
		}
		PedidoColeta pedidoColeta = obterPedidoColetaPorId(idPedidoColeta);
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupPorSolicitacao(pedidoColeta.getSolicitacao());
		pedidoColeta.setDataColeta(pedidoWorkup.getDataColeta().atStartOfDay());
		pedidoColeta.setDataInternacao(pedidoWorkup.getDataInternacao().atStartOfDay());
		
		return pedidoColeta;
	}
	
	private PedidoColeta criarPedidoColetaNacional(SolicitacaoWorkupDTO solicitacao) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupPorSolicitacao(solicitacao.getId());
		
		PedidoColeta pedido = PedidoColeta.builder()
				.centroColeta(pedidoWorkup.getCentroColeta())
				.solicitacao(solicitacao.getId())
				.build();		
		save(pedido);
		criarTarefaAgendarColetaNacional(pedido.getId(), solicitacao);
		
		return pedido; 
	}	
	
	private PedidoColeta criarPedidoColetaInternacional(SolicitacaoDTO solicitacao) {
		
		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkupPorSolicitacao(solicitacao.getId());
		
		PedidoColeta pedido = PedidoColeta.builder()
				.centroColeta(pedidoWorkup.getCentroColeta())
				.solicitacao(solicitacao.getId())
				.build();		
		return save(pedido);
	}
	
	private void criarTarefaAgendarColetaNacional(Long idPedidoColeta, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.AGENDAR_COLETA_NACIONAL.getId()))
				.perfilResponsavel(Perfis.MEDICO_CENTRO_COLETA.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoParceiro(solicitacao.getIdCentroColeta())
				.relacaoEntidade(idPedidoColeta)
				.build(); 

		tarefaHelper.criarTarefa(tarefa);		
	}

	private void criarLogisticaMaterialColeta(PedidoColeta pedidoColeta, SolicitacaoWorkupDTO solicitacao) {
		PedidoLogisticaMaterialColetaNacional pedidoLogistica = this.pedidoLogisticaMaterialColetaNacionalService.criarPedidoLogisticaMaterialColetaNacional(pedidoColeta);
		this.criarTarefaInformarLogisticaMaterial(pedidoLogistica.getId(), solicitacao);
	}

	private void criarLogisticaDoadorColeta(PedidoColeta pedidoColeta, SolicitacaoWorkupDTO solicitacao) {
		PedidoLogisticaDoadorColeta pedidoLogistica = this.pedidoLogisticaDoadorColetaService.criarPedidoLogisticaDoadorColeta(pedidoColeta);
		this.criarTarefaInformarLogisticaDoador(pedidoLogistica.getId(), solicitacao);
	}

	private void criarTarefaInformarLogisticaMaterial(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_MATERIAL_COLETA_NACIONAL.getId()))
				.perfilResponsavel(Perfis.ANALISTA_LOGISTICA.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.relacaoEntidade(idPedidoLogistica)
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
				.build(); 

		tarefaHelper.criarTarefa(tarefa);		
	}

	
	private void criarTarefaInformarLogisticaDoador(Long idPedidoLogistica, SolicitacaoWorkupDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.INFORMAR_LOGISTICA_DOADOR_COLETA.getId()))
				.perfilResponsavel(Perfis.ANALISTA_LOGISTICA.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.relacaoEntidade(idPedidoLogistica)
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
				.build(); 

		tarefaHelper.criarTarefa(tarefa);		
	}
	
	private void fecharTarefaAgendarColetaNacional(Long idPedidoColeta, SolicitacaoWorkupDTO solicitacao) throws JsonProcessingException {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idUsuarioLogado = usuarioService.obterIdUsuarioLogado();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AGENDAR_COLETA_NACIONAL.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(idUsuarioLogado)
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoColeta)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		tarefaHelper.encerrarTarefa(filtroDTO, false);
	}

	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
	}

	private void criarNotificacaoParaAnalistaWorkup(SolicitacaoWorkupDTO solicitacao) {
		
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		
		final CategoriasNotificacao categoria = CategoriasNotificacao.AGENDAMENTO_COLETA;
		NotificacaoDTO notificacao = NotificacaoDTO.builder()
				.rmr(rmr)
				.descricao(AppUtil.getMensagem(messageSource, "pedido.coleta.agendado.centro_transplantador.notificacao", identificacaoDoador, rmr) )
				.categoriaId(categoria.getId())
				.idPerfil(categoria.getPerfil() != null ? categoria.getPerfil().getId() : null)
				.lido(false)
				.build();
		
		notificacaoFeign.criarNotificacao(notificacao);
	}

	private void validarAgendamentoPedidoColeta(PedidoColeta pedidoColeta) {
		if(pedidoColeta.getDataInicioGcsf() != null) {
			validarValorNulo("dataInicioGcsf", pedidoColeta.getDataInicioGcsf());
			validarDataMenor("dataInicioGcsf", pedidoColeta.getDataInicioGcsf(), "Data Atual", LocalDateTime.now());
			validarDataMenor("dataInicioGcsf", pedidoColeta.getDataInicioGcsf(), "Data da Coleta", pedidoColeta.getDataColeta());
		}
	}
}
