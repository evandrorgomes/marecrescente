package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.AvaliacaoPrescricao;
import br.org.cancer.modred.model.Busca;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.FonteCelula;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.LogEvolucao;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Prescricao;
import br.org.cancer.modred.model.annotations.log.CreateLog;
import br.org.cancer.modred.model.domain.OrdenacaoTarefa;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoLogEvolucao;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.persistence.IAvaliacaoPrescricaoRepository;
import br.org.cancer.modred.persistence.IRepository;
import br.org.cancer.modred.service.IAvaliacaoPrescricaoService;
import br.org.cancer.modred.service.IFonteCelulaService;
import br.org.cancer.modred.service.IPedidoColetaService;
import br.org.cancer.modred.service.IPedidoWorkupService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.impl.invocation.AbstractLoggingService;
import br.org.cancer.modred.util.AppUtil;

/**
 * Classe de acesso as funcionalidades que envolvem a entidade Prescrição.
 * 
 * @author bruno.sousa
 *
 */
@Service
@Transactional
public class AvaliacaoPrescricaoService extends AbstractLoggingService<AvaliacaoPrescricao, Long> implements
		IAvaliacaoPrescricaoService {

	@Autowired
	private IAvaliacaoPrescricaoRepository avaliacaoPrescricaoRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IPedidoWorkupService pedidoWorkupService;

	@Autowired
	private ISolicitacaoService solicitacaoService;

	@Autowired
	private IFonteCelulaService fonteCelulaService;
	
	@Autowired
	private IPedidoColetaService pedidoColetaService;

	
	@Override
	public IRepository<AvaliacaoPrescricao, Long> getRepository() {
		return avaliacaoPrescricaoRepository;
	}

	@Override
	public AvaliacaoPrescricao criarAvaliacaoPrescricao(Long rmr, Prescricao prescricao) {
		AvaliacaoPrescricao avaliacaoPrescricao = new AvaliacaoPrescricao();
		avaliacaoPrescricao.setPrescricao(prescricao);
		avaliacaoPrescricao.setDataCriacao(LocalDateTime.now());
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		prescricao.setAvaliacaoPrescricao(avaliacaoPrescricao);

		AvaliacaoPrescricao avaliacaoPrescricaoRetorno = save(avaliacaoPrescricao);
		
		TiposTarefa.AVALIAR_PRESCRICAO.getConfiguracao().criarTarefa()
			.comRmr(rmr)
			.comObjetoRelacionado(avaliacaoPrescricao.getId())
			.apply();
		
		return avaliacaoPrescricaoRetorno;
	}

	@CreateLog
	@Override
	public String aprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, Long idFonteCelulaDescartada,
			String justificativaDescarteFonteCelula) {
		if (idAvaliacaoPrescricao == null) {
			throw new BusinessException("erro.avaliacao.prescricao.nulo");
		}
		AvaliacaoPrescricao avaliacaoPrescricao = avaliacaoPrescricaoRepository.findById(idAvaliacaoPrescricao).orElse(null);
		if (avaliacaoPrescricao == null) {
			throw new BusinessException("erro.avaliacao.prescricao.invalida");
		}

		FonteCelula fonteCelula = null;
		if (idFonteCelulaDescartada != null) {
			fonteCelula = fonteCelulaService.findById(idFonteCelulaDescartada);
			if (fonteCelula == null) {
				throw new BusinessException("erro.fontecelula.id.invalido");
			}
			if (justificativaDescarteFonteCelula == null || "".equals(justificativaDescarteFonteCelula)) {
				throw new BusinessException("erro.fontecelula.justificativa.nulo");
			}
			avaliacaoPrescricao.setFonteCelula(fonteCelula);
			avaliacaoPrescricao.setJustificativaDescarteFonteCelula(justificativaDescarteFonteCelula);
		}
		avaliacaoPrescricao.setAprovado(true);
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		if ( avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getDoador().isMedula() ) {
			pedidoWorkupService.criarPedidoWorkup(avaliacaoPrescricao.getPrescricao().getSolicitacao());
		}
		else {
			pedidoColetaService.criarPedidoColetaCordao(avaliacaoPrescricao.getPrescricao().getSolicitacao());
		}
		
		if (avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getDoador().isInternacional()) {
			criarTarefaAutorizacaoPaciente(avaliacaoPrescricao.getPrescricao());
		}
		
		fecharTarefaAvaliacaoPrescricao(avaliacaoPrescricao);
		return AppUtil.getMensagem(messageSource, "avaliacao.aprovada.sucesso");

	}
	
	private void fecharTarefaAvaliacaoPrescricao(AvaliacaoPrescricao avaliacaoPrescricao) {
		TiposTarefa.AVALIAR_PRESCRICAO.getConfiguracao().fecharTarefa()
			.comObjetoRelacionado(avaliacaoPrescricao.getId())
			.comRmr(avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.apply();		
	}
	
	private void criarTarefaAutorizacaoPaciente(Prescricao prescricao) {
		TiposTarefa.AUTORIZACAO_PACIENTE.getConfiguracao().criarTarefa()
			.comObjetoRelacionado(prescricao.getId())
			.comRmr(prescricao.getSolicitacao().getMatch().getBusca().getPaciente().getRmr())
			.comParceiro(prescricao.getSolicitacao().getMatch().getBusca().getCentroTransplante().getId())
			.comUsuario(prescricao.getSolicitacao().getUsuario())
			.apply();
	}

	@CreateLog
	@Override
	public String reprovarAvaliacaoPrescricao(Long idAvaliacaoPrescricao, String justificativaReprovacao) {
		if (idAvaliacaoPrescricao == null) {
			throw new BusinessException("erro.avaliacao.prescricao.nulo");
		}

		AvaliacaoPrescricao avaliacaoPrescricao = avaliacaoPrescricaoRepository.findById(idAvaliacaoPrescricao).orElse(null);
		if (avaliacaoPrescricao == null) {
			throw new BusinessException("erro.avaliacao.prescricao.invalida");
		}

		if (justificativaReprovacao == null || "".equals(justificativaReprovacao)) {
			throw new BusinessException("erro.justificativa.obrigatoria");
		}
		avaliacaoPrescricao.setAprovado(false);
		avaliacaoPrescricao.setDataAtualizacao(LocalDateTime.now());
		avaliacaoPrescricao.setJustificativaCancelamento(justificativaReprovacao);
		save(avaliacaoPrescricao);

		solicitacaoService.cancelarSolicitacao(avaliacaoPrescricao.getPrescricao().getSolicitacao().getId(), null, null, null, null);
		fecharTarefaAvaliacaoPrescricao(avaliacaoPrescricao);
				
		return AppUtil.getMensagem(messageSource, "avaliacao.reprovada.sucesso");

	}
	
	
	private void criarTarefaCadastrarPrescricao(Match match) {
		
		if (match.getDoador().isMedula()) {
			criarTarefaCadastrarPrescricaoMedula(match.getBusca());
		}
		else {
			criarTarefaCadastrarPrescricaoCordao(match.getBusca());
		}
		
	}
	
	private void criarTarefaCadastrarPrescricaoMedula(Busca busca) {
		TiposTarefa.CADASTRAR_PRESCRICAO_MEDULA.getConfiguracao()
			.criarTarefa()
			.comRmr(busca.getPaciente().getRmr())
			.comParceiro(busca.getCentroTransplante().getId())
			.apply();
	}
	
	private void criarTarefaCadastrarPrescricaoCordao(Busca busca) {
				
		TiposTarefa.CADASTRAR_PRESCRICAO_CORDAO.getConfiguracao()
			.criarTarefa()
			.comRmr(busca.getPaciente().getRmr())
			.comParceiro(busca.getCentroTransplante().getId())
			.apply();
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.cancer.modred.service.IAvaliacaoPrescricaoService#obterAvaliacao(java.lang.Long)
	 */
	@Override
	public AvaliacaoPrescricao obterAvaliacao(Long id) {
		if (id == null) {
			throw new BusinessException("erro.avaliacao.prescricao.nulo");
		}

		AvaliacaoPrescricao avaliacaoPrescricao = avaliacaoPrescricaoRepository.findById(id).get();
		if (avaliacaoPrescricao == null) {
			throw new BusinessException("erro.avaliacao.prescricao.invalida");
		}

		Paciente paciente = avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getBusca().getPaciente();
		avaliacaoPrescricao.getPrescricao().getSolicitacao().setPaciente(paciente);
		
		
		return avaliacaoPrescricao;
	}

	@Override
	public Paciente obterPaciente(AvaliacaoPrescricao avaliacaoPrescricao) {
		return avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getBusca().getPaciente();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String[] obterParametros(AvaliacaoPrescricao avaliacaoPrescricao) {
		IDoador doador = (IDoador) avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getDoador();		
		return StringUtils.split(doador.getIdentificacao().toString(), ';');
	}
	
	@Override
	public LogEvolucao criarLog(AvaliacaoPrescricao avaliacaoPrescricao, TipoLogEvolucao tipoLog, Perfis[] perfisExcluidos) {
		
		if (tipoLog == null || TipoLogEvolucao.INDEFINIDO.equals(tipoLog)) {
			
			if (avaliacaoPrescricao.getAprovado()) {
				tipoLog =  obterTipoLogEvolucaoPrescricaoAprovada(avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getDoador());
			}
			else {
				tipoLog =  obterTipoLogEvolucaoPrescricaoReprovada(avaliacaoPrescricao.getPrescricao().getSolicitacao().getMatch().getDoador());
			}
		}
		
		return super.criarLog(avaliacaoPrescricao, tipoLog, perfisExcluidos);
	}
	
	private TipoLogEvolucao obterTipoLogEvolucaoPrescricaoAprovada(Doador doador) {
		if (doador.isMedula()) {
			return TipoLogEvolucao.PRESCRICAO_APROVADA_PARA_MEDULA;
		}
		return TipoLogEvolucao.PRESCRICAO_APROVADA_PARA_CORDAO;
	}
	
	private TipoLogEvolucao obterTipoLogEvolucaoPrescricaoReprovada(Doador doador) {
		if (doador.isMedula() ) {
			return TipoLogEvolucao.PRESCRICAO_REPROVADA_PARA_MEDULA;
		}
		return TipoLogEvolucao.PRESCRICAO_REPROVADA_PARA_CORDAO;
	}
	
	@Override
	public Page<TarefaDTO> listarAvaliacoesPendentes(PageRequest paginacao) {

		Comparator<TarefaDTO> comparator = OrdenacaoTarefa.getComparator(OrdenacaoTarefa.PRESCRICAO.getChave(), null);
		
		return TiposTarefa.AVALIAR_PRESCRICAO.getConfiguracao()
			.listarTarefa()
			.comStatus(Arrays.asList(StatusTarefa.ABERTA))
			.comPaginacao(paginacao)
			.comOrdenacao(comparator)
			.paraTodosUsuarios()
			.apply().getValue();
	}

}
