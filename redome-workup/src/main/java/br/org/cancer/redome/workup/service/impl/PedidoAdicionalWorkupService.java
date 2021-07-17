package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.DoadorDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoWorkupDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.INotificacaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.PedidoAdicionalWorkup;
import br.org.cancer.redome.workup.model.PedidoWorkup;
import br.org.cancer.redome.workup.model.ResultadoWorkup;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposPedidosWorkup;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IPedidoAdicionalWorkupRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IArquivoPedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.service.IPedidoAdicionalWorkupService;
import br.org.cancer.redome.workup.service.IPedidoWorkupService;
import br.org.cancer.redome.workup.service.IResultadoWorkupService;
import br.org.cancer.redome.workup.service.IStorageService;
import br.org.cancer.redome.workup.service.IUsuarioService;
import br.org.cancer.redome.workup.service.impl.custom.AbstractService;

/**
 * Classe de implementação dos métodos de negócio para pedido adicional de workup.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class PedidoAdicionalWorkupService extends AbstractService<PedidoAdicionalWorkup, Long> implements IPedidoAdicionalWorkupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PedidoAdicionalWorkupService.class);
	
	@Autowired
	private IPedidoAdicionalWorkupRepository pedidoAdicionalWorkupRepository;

	@Autowired
	private IPedidoWorkupService pedidoWorkupService;
	
	@Autowired
	private IArquivoPedidoAdicionalWorkupService arquivoPedidoAdicionalWorkupService;
	
	@Autowired
	private IResultadoWorkupService resultadoWorkupService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	@Lazy(true)
	private INotificacaoFeign notificacaoFeign;
	
	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;

	@SuppressWarnings({ "rawtypes", "unused" })
	@Autowired
	private IStorageService storageService;
	
	@Override
	public IRepository<PedidoAdicionalWorkup, Long> getRepository() {
		return pedidoAdicionalWorkupRepository;
	}
	
	@Override
	public String finalizarPedidoAdicionalWorkup(PedidoAdicionalWorkup pedidoAdicionalWorkup) {

		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(pedidoAdicionalWorkup.getPedidoWorkup());
		
		arquivoPedidoAdicionalWorkupService.salvarArquivosExamesAdicionaisWorkup(pedidoAdicionalWorkup.getId(), pedidoAdicionalWorkup.getArquivosPedidoAdicionalWorkup());
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_AVALIACAO_RESULTADO_WORKUP.getId());
		
		if(pedidoWorkup.getTipo().equals(TiposPedidosWorkup.NACIONAL.getId())) {

			fecharTarefaInformarExameAdicionalWorkup(pedidoAdicionalWorkup.getId(), solicitacao, TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL);
			criarTarefaAvaliarResultadoWorkup(pedidoWorkup.getId(), solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL);

		}else {

			fecharTarefaInformarExameAdicionalWorkup(pedidoAdicionalWorkup.getId(), solicitacao, TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL);
			criarTarefaAvaliarResultadoWorkup(pedidoWorkup.getId(), solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL);
		}
		
		criarLogEvolucao(TipoLogEvolucao.INCLUIDO_EXAME_ADICIONAL_WORKUP_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());
		
		return "pedido.adicional.workup.exame.adicional.finalizado.sucesso";
	}
	
	@Override
	public String criarPedidoAdicionalDoadorNacional(Long idPedidoWorkup, String descricao) {

		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(idPedidoWorkup);
		
		if (StringUtils.isEmpty(descricao)) {
			throw new BusinessException("erro.avaliacao.resultado.workup.exame.adicional.descricao");
		}
		
		PedidoAdicionalWorkup pedidoAdicional = PedidoAdicionalWorkup.builder()
				.pedidoWorkup(pedidoWorkup.getId())
				.descricao(descricao)
				.dataCriacao(LocalDateTime.now())
				.build();
		
		save(pedidoAdicional);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_EXAME_ADICIONAL_DOADOR.getId() );
		
		fecharTarefaAvaliarResultadoWorkup(idPedidoWorkup, solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_NACIONAL);

		criarLogEvolucao(TipoLogEvolucao.INCLUIR_EXAME_ADICIONAL_WORKUP_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());

		criarTarefaInformarExameAdicionalWorkup(pedidoAdicional, solicitacao, 
				TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_NACIONAL, 
				Perfis.ANALISTA_WORKUP);
		
		return "avaliacao.resulado.workup.solicitar.exame.adicional";
	}

	@Override
	public String criarPedidoAdicionalDoadorInternacional(Long idPedidoWorkup, String descricao) {

		PedidoWorkup pedidoWorkup = pedidoWorkupService.obterPedidoWorkup(idPedidoWorkup);
		
		if (StringUtils.isEmpty(descricao)) {
			throw new BusinessException("erro.avaliacao.resultado.workup.exame.adicional.descricao");
		}
		
		PedidoAdicionalWorkup pedidoAdicional = PedidoAdicionalWorkup.builder()
				.pedidoWorkup(pedidoWorkup.getId())
				.descricao(descricao)
				.dataCriacao(LocalDateTime.now())
				.build();
		
		save(pedidoAdicional);
		
		SolicitacaoWorkupDTO solicitacao = solicitacaoFeign.atualizarFaseWorkup(pedidoWorkup.getSolicitacao(), 
				FasesWorkup.AGUARDANDO_EXAME_ADICIONAL_DOADOR.getId() );
		
		fecharTarefaAvaliarResultadoWorkup(idPedidoWorkup, solicitacao, TiposTarefa.AVALIAR_RESULTADO_WORKUP_INTERNACIONAL);
	
		criarLogEvolucao(TipoLogEvolucao.INCLUIR_EXAME_ADICIONAL_WORKUP_PARA_DOADOR, solicitacao.getMatch().getBusca().getPaciente().getRmr(), solicitacao.getMatch().getDoador());

		criarTarefaInformarExameAdicionalWorkup(pedidoAdicional, solicitacao, 
				TiposTarefa.INFORMAR_EXAME_ADICIONAL_WORKUP_INTERNACIONAL, 
				Perfis.ANALISTA_WORKUP_INTERNACIONAL);
		
		return "avaliacao.resulado.workup.solicitar.exame.adicional";
	}
	
	@Override
	public PedidoAdicionalWorkup obterPedidoAdicionalWorkup(Long idPedidoAdicional) {
		if (idPedidoAdicional == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoAdicionalWorkupRepository.findById(idPedidoAdicional).orElseThrow(() -> new BusinessException("erro.nao.existe", "Pedido Adicional"));
	}

	@Override
	public List<PedidoAdicionalWorkup> listarPedidosAdicionaisWorkupPorIdPedidoWorkup(Long idPedidoWorkup){
		if (idPedidoWorkup == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return pedidoAdicionalWorkupRepository.findByPedidoWorkup(idPedidoWorkup);
	}
	
	private void criarTarefaAvaliarResultadoWorkup(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao, TiposTarefa tiposTarefa) {
		
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupPeloPedidoWorkupId(idPedidoWorkup);
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getId();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);

		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo) 
				.tipoTarefa(new TipoTarefaDTO(tiposTarefa.getId()))
				.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
				.status(StatusTarefa.ABERTA.getId())
				.relacaoParceiro(idCentroTransplante)
				.relacaoEntidade(resultadoWorkup.getId())
				.build(); 
		
		tarefaHelper.criarTarefa(tarefa);
	}

	private void criarTarefaInformarExameAdicionalWorkup(PedidoAdicionalWorkup pedidoAdicional, SolicitacaoWorkupDTO solicitacao, TiposTarefa tiposTarefa, Perfis perfis) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getId();

		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo)
				.tipoTarefa(new TipoTarefaDTO(tiposTarefa.getId()))
				.perfilResponsavel(perfis.getId())
				.relacaoEntidade(pedidoAdicional.getId())
				.status(StatusTarefa.ATRIBUIDA.getId())
				.objetoRelacaoParceiro(idCentroTransplante)
				.usuarioResponsavel(solicitacao.getUsuarioResponsavel().getId())
				.build();
		
		tarefaHelper.criarTarefa(tarefa);		
	}
	
	private void fecharTarefaAvaliarResultadoWorkup(Long idPedidoWorkup, SolicitacaoWorkupDTO solicitacao, TiposTarefa tiposTarefa) {
		
		ResultadoWorkup resultadoWorkup = resultadoWorkupService.obterResultadoWorkupPeloPedidoWorkupId(idPedidoWorkup);

		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tiposTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(resultadoWorkup.getId())
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAvaliarResultadoWorkup", e);
			throw new BusinessException("erro.interno");
		}
	}

	private void fecharTarefaInformarExameAdicionalWorkup(Long idPedidoAdicional, SolicitacaoWorkupDTO solicitacao, TiposTarefa tiposTarefa) {
		
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(tiposTarefa.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idPedidoAdicional)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		
		try {
			tarefaHelper.encerrarTarefa(filtroDTO, false);
		} catch (JsonProcessingException e) {
			LOGGER.error("fecharTarefaAvaliarResultadoWorkup", e);
			throw new BusinessException("erro.interno");
		}
	}

	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, DoadorDTO doador) {
		
		String[] parametros = {doador.getIdentificacao()};
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}

}
