package br.org.cancer.redome.workup.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.AprovarAvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacaoPrescricaoDTO;
import br.org.cancer.redome.workup.dto.AvaliacoesPrescricaoDTO;
import br.org.cancer.redome.workup.dto.CriarLogEvolucaoDTO;
import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.PrescricaoEvolucaoDTO;
import br.org.cancer.redome.workup.dto.ProcessoDTO;
import br.org.cancer.redome.workup.dto.SolicitacaoDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.dto.TipoTarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ILogEvolucaoFeign;
import br.org.cancer.redome.workup.feign.client.ISolicitacaoFeign;
import br.org.cancer.redome.workup.helper.AvaliacaoPrescricaoComparator;
import br.org.cancer.redome.workup.helper.ITarefaHelper;
import br.org.cancer.redome.workup.model.AvaliacaoPrescricao;
import br.org.cancer.redome.workup.model.FonteCelula;
import br.org.cancer.redome.workup.model.Prescricao;
import br.org.cancer.redome.workup.model.PrescricaoMedula;
import br.org.cancer.redome.workup.model.domain.FasesWorkup;
import br.org.cancer.redome.workup.model.domain.Perfis;
import br.org.cancer.redome.workup.model.domain.StatusTarefa;
import br.org.cancer.redome.workup.model.domain.TipoLogEvolucao;
import br.org.cancer.redome.workup.model.domain.TipoProcesso;
import br.org.cancer.redome.workup.model.domain.TiposSolicitacao;
import br.org.cancer.redome.workup.model.domain.TiposTarefa;
import br.org.cancer.redome.workup.persistence.IAvaliacaoPrescricaoRepository;
import br.org.cancer.redome.workup.persistence.IRepository;
import br.org.cancer.redome.workup.service.IAvaliacaoPrescricaoService;
import br.org.cancer.redome.workup.service.IDistribuicaoWorkupService;
import br.org.cancer.redome.workup.service.IFonteCelulaService;
import br.org.cancer.redome.workup.service.IPrescricaoService;
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
public class AvaliacaoPrescricaoService extends AbstractService<AvaliacaoPrescricao, Long> implements
		IAvaliacaoPrescricaoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoPrescricaoService.class);

	@Autowired
	private IAvaliacaoPrescricaoRepository avaliacaoPrescricaoRepository;

	@Autowired
	@Lazy(true)
	private ILogEvolucaoFeign logEvolucaoFeign;
		
	@Autowired
	@Lazy(true)
	private ISolicitacaoFeign solicitacaoFeign;
	
	@Autowired
	private IFonteCelulaService fonteCelulaService;
	
	@Autowired
	@Lazy(true)
	private ITarefaHelper tarefaHelper;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IDistribuicaoWorkupService distribuicaoWorkupService;
	
	@Autowired
	private IPrescricaoService prescricaoService; 
	
	@Override
	public IRepository<AvaliacaoPrescricao, Long> getRepository() {
		return avaliacaoPrescricaoRepository;
	}

	@Override
	public AvaliacaoPrescricao criarAvaliacaoPrescricao(Long rmr, Prescricao prescricao) {
		LOGGER.info("Criar Avaliação de Prescrição. {0}", prescricao.toString() );
		
		AvaliacaoPrescricao avaliacaoPrescricao = new AvaliacaoPrescricao();
		avaliacaoPrescricao.setPrescricao(prescricao);
		avaliacaoPrescricao.setDataCriacao(LocalDateTime.now());
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());		

		AvaliacaoPrescricao avaliacaoPrescricaoRetorno = save(avaliacaoPrescricao);
		
		criarTarefaAvaliacaoPrescricao(rmr, avaliacaoPrescricaoRetorno.getId());
				
		return avaliacaoPrescricaoRetorno;
	}
	
	private void criarTarefaAvaliacaoPrescricao(Long rmr, Long idAvaliacaoPrescricao) {
		
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo)
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.AVALIAR_PRESCRICAO.getId()))
				.perfilResponsavel(Perfis.MEDICO_REDOME.getId())
				.relacaoEntidade(idAvaliacaoPrescricao)
				.status(StatusTarefa.ABERTA.getId())
				.build();
		
		tarefaHelper.criarTarefa(tarefa);
	}
	
	@Override
	public void aprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, AprovarAvaliacaoPrescricaoDTO aprovarAvaliacaoPrescricaoDTO) throws JsonProcessingException {
		LOGGER.info("Aprovar Avaliação de Prescrição. {0}", idAvaliacaoPrescricao );
		AvaliacaoPrescricao avaliacaoPrescricao = obterAvaliacao(idAvaliacaoPrescricao);
		if (avaliacaoPrescricao.getAprovado() != null) {
			throw new BusinessException("erro.avaliacao.prescricao.ja.realizada" );
		}
		if (avaliacaoPrescricao.getPrescricao().isPrescricaoMedula() != null && avaliacaoPrescricao.getPrescricao().isPrescricaoMedula()) {
			aprovarAvaliacaoPrescricaoMedula(avaliacaoPrescricao, aprovarAvaliacaoPrescricaoDTO);
		}
		else if (avaliacaoPrescricao.getPrescricao().isPrescricaoCordao() != null && avaliacaoPrescricao.getPrescricao().isPrescricaoCordao()) {
			aprovarAvaliacaoPrescricaoCordao(avaliacaoPrescricao);			
		}
	}

	private void aprovarAvaliacaoPrescricaoMedula(AvaliacaoPrescricao avaliacaoPrescricao, AprovarAvaliacaoPrescricaoDTO aprovarAvaliacaoPrescricaoDTO) throws JsonProcessingException {

		PrescricaoMedula prescricaoMedula = (PrescricaoMedula) avaliacaoPrescricao.getPrescricao();
		if (aprovarAvaliacaoPrescricaoDTO != null && aprovarAvaliacaoPrescricaoDTO.getIdFonteCelulaDescartada() != null) {
			validarFonteCelulaDescartadaEJustificativa(aprovarAvaliacaoPrescricaoDTO.getIdFonteCelulaDescartada(), 
					aprovarAvaliacaoPrescricaoDTO.getJustificativaDescarteFonteCelula(), prescricaoMedula.getFonteCelulaOpcao1(), prescricaoMedula.getFonteCelulaOpcao2());
			avaliacaoPrescricao.setFonteCelula(fonteCelulaService.obterFonteCelula(aprovarAvaliacaoPrescricaoDTO.getIdFonteCelulaDescartada()));
			avaliacaoPrescricao.setJustificativaDescarteFonteCelula(aprovarAvaliacaoPrescricaoDTO.getJustificativaDescarteFonteCelula());
		}
		avaliacaoPrescricao.setAprovado(true);
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		
		SolicitacaoDTO solicitacao = atualizarSolicitacaoFaseWorkup(avaliacaoPrescricao.getPrescricao().getSolicitacao()); 

		if (tipoSolicitacaoInternacional(solicitacao.getTipoSolicitacao().getId())) {
			criarTarefaAutorizacaoPaciente(avaliacaoPrescricao.getPrescricao().getId(), solicitacao);
		}

		fecharTarefaAvaliacaoPrescricao(avaliacaoPrescricao.getId(), solicitacao.getMatch().getBusca().getPaciente().getRmr());		
		
		criarLogEvolucaoAvaliacaoPrescricaoAprovada(solicitacao);
		
		distribuicaoWorkupService.criarDistribuicao(solicitacao);
		
		save(avaliacaoPrescricao);
	}
	
	private void aprovarAvaliacaoPrescricaoCordao(AvaliacaoPrescricao avaliacaoPrescricao) throws JsonProcessingException {
		
		avaliacaoPrescricao.setAprovado(true);
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		
		SolicitacaoDTO solicitacao = atualizarSolicitacaoFaseWorkup(avaliacaoPrescricao.getPrescricao().getSolicitacao()); 

		if (tipoSolicitacaoInternacional(solicitacao.getTipoSolicitacao().getId())) {
			criarTarefaAutorizacaoPaciente(avaliacaoPrescricao.getPrescricao().getId(), solicitacao);
		}

		fecharTarefaAvaliacaoPrescricao(avaliacaoPrescricao.getId(), solicitacao.getMatch().getBusca().getPaciente().getRmr());		
		
		criarLogEvolucaoAvaliacaoPrescricaoAprovada(solicitacao);
		
		distribuicaoWorkupService.criarDistribuicao(solicitacao);
		
		save(avaliacaoPrescricao);
		
	}
	
	private SolicitacaoDTO atualizarSolicitacaoFaseWorkup(Long idSolicitacao) {
		return solicitacaoFeign.atualizarFaseWorkup(idSolicitacao, FasesWorkup.AGUARDANDO_DISTRIBUICAO_WORKUP.getId());		
	}
	
	private boolean tipoSolicitacaoInternacional(Long idTipoSolicitacao) {
		if (idTipoSolicitacao == null) {
			throw new BusinessException("erro.id.nulo");
		}
		return TiposSolicitacao.WORKUP_DOADOR_INTERNACIONAL.getId().equals(idTipoSolicitacao) ||
				TiposSolicitacao.CORDAO_INTERNACIONAL.getId().equals(idTipoSolicitacao);

	}

	private void validarFonteCelulaDescartadaEJustificativa(Long idFonteCelulaDescartada,
			String justificativaDescarteFonteCelula, FonteCelula fonteCelulaPrescricaoOpcao1, 
			FonteCelula fonteCelulaPrescricaoOpcao2) {
		if (idFonteCelulaDescartada != null) {
			if (fonteCelulaPrescricaoOpcao1 != null && fonteCelulaPrescricaoOpcao2 == null) {
				throw new BusinessException("erro.avaliacao.prescricao.fontecelula.descartar.somente.uma.fontecelula");
			}
			
			if (!idFonteCelulaDescartada.equals(fonteCelulaPrescricaoOpcao1.getId()) && !idFonteCelulaDescartada.equals(fonteCelulaPrescricaoOpcao2.getId())) {
				throw new BusinessException("erro.avaliacao.prescricao.fontecelula.nao.faz.parte.prescricao");
			}
						
			fonteCelulaService.obterFonteCelula(idFonteCelulaDescartada);

			if (justificativaDescarteFonteCelula == null || "".equals(justificativaDescarteFonteCelula)) {
				throw new BusinessException("erro.avaliacao.prescricao.fontecelula.justificativa.nulo");
			}
		}
	}

	private void fecharTarefaAvaliacaoPrescricao(Long idAvaliacaoPrescricao, Long rmr) throws JsonProcessingException {
		
		ListaTarefaDTO filtroDTO = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_PRESCRICAO.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.rmr(rmr)
				.relacaoEntidadeId(idAvaliacaoPrescricao)
				.tipoProcesso(TipoProcesso.BUSCA.getId())
				.build();
		tarefaHelper.encerrarTarefa(filtroDTO, false);
		
	}
	
	private void criarTarefaAutorizacaoPaciente(Long idPrescricao, SolicitacaoDTO solicitacao) {
		Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		Long idCentroTransplante = solicitacao.getMatch().getBusca().getCentroTransplante().getId();
		
		ProcessoDTO processo = new ProcessoDTO(TipoProcesso.BUSCA);
		processo.setRmr(rmr);
		
		TarefaDTO tarefa = TarefaDTO.builder()
				.processo(processo)
				.tipoTarefa(new TipoTarefaDTO(TiposTarefa.AUTORIZACAO_PACIENTE.getId()))
				.perfilResponsavel(Perfis.MEDICO_TRANSPLANTADOR.getId())
				.relacaoEntidade(idPrescricao)
				.relacaoParceiro(idCentroTransplante)
				.status(StatusTarefa.ABERTA.getId())
				.build();
		
		tarefaHelper.criarTarefa(tarefa);
		
	}

	@Override
	public void reprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, String justificativaReprovacao) throws JsonProcessingException {
		
		LOGGER.info("Reprovar Avaliação de Prescrição. {0}", idAvaliacaoPrescricao );
		AvaliacaoPrescricao avaliacaoPrescricao = obterAvaliacao(idAvaliacaoPrescricao);
		if (avaliacaoPrescricao.getAprovado() != null) {
			throw new BusinessException("erro.avaliacao.prescricao.ja.realizada" );
		}
		
		avaliacaoPrescricao.setAprovado(false);
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		avaliacaoPrescricao.setJustificativaCancelamento(justificativaReprovacao);
		save(avaliacaoPrescricao);
		
		SolicitacaoDTO solicitacao = prescricaoService.cancelarPrescricao(avaliacaoPrescricao.getPrescricao().getId());
		
		fecharTarefaAvaliacaoPrescricao(avaliacaoPrescricao.getId(), solicitacao.getMatch().getBusca().getPaciente().getRmr());
		
		criarLogEvolucaoAvaliacaoPrescricaoReprovada(solicitacao);		
		
	}
	
	@Override
	public AvaliacaoPrescricao obterAvaliacao(Long id) {

		if (id == null) {
			throw new BusinessException("erro.id.nulo");
		}

		return avaliacaoPrescricaoRepository.findById(id).orElseThrow(() -> new BusinessException("erro.nao.existe", "Avaliação de prescrição"));
	}
	
	@Override
	public Page<AvaliacoesPrescricaoDTO> listarAvaliacoesPendentes(int pagina, int quantidadeRegistros) throws JsonProcessingException {
		
		ListaTarefaDTO filtro = ListaTarefaDTO.builder()
				.idsTiposTarefa(Arrays.asList(TiposTarefa.AVALIAR_PRESCRICAO.getId()))
				.statusTarefa(Arrays.asList(StatusTarefa.ABERTA.getId(), StatusTarefa.ATRIBUIDA.getId()))
				.idUsuarioLogado(usuarioService.obterIdUsuarioLogado())
				.build();
		
		List<TarefaDTO> tarefas = tarefaHelper.listarTarefas(filtro);
		if (CollectionUtils.isEmpty(tarefas)) {
			return new CustomPageImpl<>();
		}
		
		List<AvaliacoesPrescricaoDTO> lista = tarefas.stream()			
			.map(tarefa -> tarefa.toBuilder().objetoRelacaoEntidade(obterAvaliacao(tarefa.getRelacaoEntidade())).build())
			.sorted(new AvaliacaoPrescricaoComparator())
			.skip(pagina * quantidadeRegistros)
			.limit(quantidadeRegistros)
			.map(tarefa -> {
				AvaliacaoPrescricao avaliacao = (AvaliacaoPrescricao) tarefa.getObjetoRelacaoEntidade();
				SolicitacaoDTO solicitacao = solicitacaoFeign.obterSolicitacao(avaliacao.getPrescricao().getSolicitacao());
				
				return AvaliacoesPrescricaoDTO.builder()
							.idTarefa(tarefa.getId())
							.idStatusTarefa(tarefa.getStatus())
							.idAvaliacaoPrescricao(avaliacao.getId())
							.rmr(tarefa.getProcesso().getRmr())
							.nome(tarefa.getProcesso().getNomePaciente())
							.identificacaoDoador(solicitacao.getMatch().getDoador().getIdentificacao())
							.tipoDoador(solicitacao.getMatch().getDoador().getTipoDoador())
							.dataColeta(avaliacao.getPrescricao().menorDataColeta())
							.build();
				
			})			
			.collect(Collectors.toList());
				
		return new CustomPageImpl<AvaliacoesPrescricaoDTO>(lista, PageRequest.of(pagina, quantidadeRegistros), tarefas.size());

		/*
		 * Comparator<TarefaDTO> comparator =
		 * OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PRESCRICAO.getChave(), null);
		 * 
		 * return TiposTarefa.AVALIAR_PRESCRICAO.getConfiguracao() .listarTarefa()
		 * .comStatus(Arrays.asList(StatusTarefa.ABERTA)) .comPaginacao(paginacao)
		 * .comOrdenacao(comparator) .paraTodosUsuarios() .apply().getValue();
		 */
	}
	
	private void criarLogEvolucaoAvaliacaoPrescricaoAprovada(SolicitacaoDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		if (solicitacao.solicitacaoWorkupCordao()) {
			criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_APROVADA_PARA_CORDAO, rmr, identificacaoDoador);
		}
		else {
			criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_APROVADA_PARA_MEDULA, rmr, identificacaoDoador);
		}
	}
	
	
	private void criarLogEvolucao(TipoLogEvolucao tipoLogEvolucao,Long rmr, String...parametros) {
		
		logEvolucaoFeign.criarLogEvolucao(CriarLogEvolucaoDTO.builder()
				.tipo(tipoLogEvolucao.name())
				.rmr(rmr)
				.parametros(parametros )
				.build());
				
	}
	
	private void criarLogEvolucaoAvaliacaoPrescricaoReprovada(SolicitacaoDTO solicitacao) {
		final Long rmr = solicitacao.getMatch().getBusca().getPaciente().getRmr();
		final String identificacaoDoador = solicitacao.getMatch().getDoador().getIdentificacao();
		if (solicitacao.solicitacaoWorkupCordao()) {
			criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_REPROVADA_PARA_CORDAO, rmr, identificacaoDoador);
		}
		else {
			criarLogEvolucao(TipoLogEvolucao.PRESCRICAO_REPROVADA_PARA_MEDULA, rmr, identificacaoDoador);
		}
	}
	
	@Override
	public AvaliacaoPrescricaoDTO obterAvaliacaoPrescricao(Long id) {

		AvaliacaoPrescricao avaliacaoPrescricao = obterAvaliacao(id);
		PrescricaoEvolucaoDTO prescricaoEvolucao = prescricaoService.obterPrescricaoComEvolucaoPorIdPrescricao(avaliacaoPrescricao.getPrescricao().getId());
		
		return AvaliacaoPrescricaoDTO.builder()
				.idAvaliacaoPrescricao(avaliacaoPrescricao.getId())
				.prescricaoEvolucaoDTO(prescricaoEvolucao)
				.build();
	}
	
	@Override
	public AvaliacaoPrescricao obterAvaliacaoPeloIdPrescricao(Long idPrescricao) {		
		return avaliacaoPrescricaoRepository.findByPrescricaoId(idPrescricao);
	}
	

}
