package br.org.cancer.modred.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.org.cancer.modred.controller.dto.ContatoDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.BancoSangueCordao;
import br.org.cancer.modred.model.CordaoNacional;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.DoadorNacional;
import br.org.cancer.modred.model.IDoador;
import br.org.cancer.modred.model.MotivoStatusDoador;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.StatusDoador;
import br.org.cancer.modred.model.domain.TiposDoador;
import br.org.cancer.modred.model.interfaces.IDoadorHeader;
import br.org.cancer.modred.persistence.IDoadorRepository;
import br.org.cancer.modred.service.IDoadorService;
import br.org.cancer.modred.service.IMatchService;
import br.org.cancer.modred.service.IMotivoStatusDoadorService;
import br.org.cancer.modred.service.ISolicitacaoService;
import br.org.cancer.modred.service.IStatusDoadorService;
import br.org.cancer.modred.service.impl.custom.AbstractService;

/**
 * Classe de funcionalidades envolvendo a entidade Doador.
 * 
 * @author Pizão.
 * @param <T> classe que extende de doador.
 *
 */
@Service
@Transactional(propagation=Propagation.REQUIRED)
public class DoadorService<T extends Doador> extends AbstractService<Doador, Long> implements IDoadorService {

	@Qualifier("IDoadorRepository")
	@Autowired
	private IDoadorRepository doadorRepositorio; 
	
	@Override
	public IDoadorRepository getRepository(){
		return doadorRepositorio;
	}

	private IMotivoStatusDoadorService motivoStatusDoadorService;

	private IStatusDoadorService statusDoadorService;

	private ISolicitacaoService solicitacaoService;
	
	private IMatchService matchService;
	

	/**
	 * Atualiza o status do doador informando motivo e, caso inativado temporariamente, a data de retorno.
	 * 
	 * @param idDoador ID do doador.
	 * @param statusId novo status do doador.
	 * @param motivoStatusId motivo do status estar sendo atualizado.
	 * @param dataRetorno data de retorno, caso inativo temporariamente.
	 * @return doador com status atualizado.
	 */
	@Override
	public Doador salvarAtualizacaoStatusDoador(Long idDoador, Long statusId, Long motivoStatusId, LocalDate dataRetorno) {
		Doador doador = getRepository().findById(idDoador).get();
		doador.setStatusDoador(statusDoadorService.obterStatusPorId(statusId));

		if (motivoStatusId != null) {
			doador.setMotivoStatusDoador(new MotivoStatusDoador(motivoStatusId));
		}
		else {
			doador.setMotivoStatusDoador(null);
		}

		doador.setDataRetornoInatividade(dataRetorno);

		return getRepository().save(doador);
	}

	@Override
	public Doador inativarDoador(Long idDoador, Long motivoStatusId, Long tempoInatividade) {
		boolean inativoTemporario = !StringUtils.isEmpty(tempoInatividade);
		Long statusDoadorId = null;
		LocalDate dataRetornoInatividade = null;
		Doador doadorLocalizado = this.findById(idDoador);

		if (inativoTemporario) {
			statusDoadorId = StatusDoador.INATIVO_TEMPORARIO;
			dataRetornoInatividade = calcularRetornoInatividade(tempoInatividade);
		}
		else {
			statusDoadorId = StatusDoador.INATIVO_PERMANENTE;
		}
		//if(doadorLocalizado.getTipoDoador().getId().equals(TiposDoador.NACIONAL.getId())) {
		//	TODO: descomentar este código quando entrar em produção.
		//	DoadorNacional doadorNacional = (DoadorNacional) doadorLocalizado;
		//	alterarStatusDoadorRedomeWS.inativar(doadorNacional.getDmr());			
		//}
		solicitacaoService.cancelarReferenciasDoador(idDoador);
		return salvarAtualizacaoStatusDoador(idDoador, statusDoadorId, motivoStatusId, dataRetornoInatividade);
	}

	@Override
	public void inativarDoadorFase3NaoRetornoSms(Long idDoador, Long statusDoadorId, Long motivoStatusId) {
		solicitacaoService.cancelarReferenciasDoador(idDoador);
		salvarAtualizacaoStatusDoador(idDoador, statusDoadorId, motivoStatusId, null);
	}
	
	private LocalDate calcularRetornoInatividade(Long tempoInatividade) {
		boolean inativoTemporario = !StringUtils.isEmpty(tempoInatividade);
		return inativoTemporario ? new Date(tempoInatividade).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContatoDTO atualizarStatusDoador(Long id, Long statusId, Long motivoStatusId, Long timeRetornoInatividade) {
		Doador doador = getRepository().findById(id).get();

		boolean estaInativando = motivoStatusId != null;
		if (estaInativando) {
			statusId = recuperaStatusInativoPorMotivoEnviado(motivoStatusId);
		}

		if (naoMudouStatus(doador, statusId, motivoStatusId, timeRetornoInatividade)) {
			throw new BusinessException("erro.nao.mudou.status");
		}

		if (estaInativando) {
			doador = inativarDoador(id, motivoStatusId, timeRetornoInatividade);
		}
		else {
			doador = salvarAtualizacaoStatusDoador(id, statusId, motivoStatusId, calcularRetornoInatividade(
					timeRetornoInatividade));
		}
		
		return obterDoadorParaContatoPorDmr(doador.getId(), true);
	}
	/**
	 *	{@inheritDoc} 
	 */
	@Override
	public ContatoDTO obterDoadorParaContatoPorDmr(Long id, boolean exibirUltimaFase) {

		ContatoDTO contatoDto = new ContatoDTO();		
		contatoDto.setDoador(getRepository().findById(id).get());
		if (exibirUltimaFase) {
			List<Solicitacao> solicitacoes = solicitacaoService.obterSolicitacoesEmAbertoPorIdDoador(id);
			if (!solicitacoes.isEmpty()) {
				contatoDto.setSolicitacao(solicitacoes.get(0));
			}
		}

		return contatoDto;
	}

	private Long recuperaStatusInativoPorMotivoEnviado(Long motivoStatusId) {
		Long statusId;
		MotivoStatusDoador motivoStatusDoador = motivoStatusDoadorService.obterMotivoPorId(motivoStatusId);

		if (motivoStatusDoador.getStatusDoador().getTempoObrigatorio()) {
			statusId = StatusDoador.INATIVO_TEMPORARIO;
		}
		else {
			statusId = StatusDoador.INATIVO_PERMANENTE;
		}
		return statusId;
	}

	private boolean naoMudouStatus(Doador doador, Long statusId, Long motivoStatusId, Long timeRetornoInatividade) {
		return ehMesmoStatus(doador, statusId) && ehMesmoMotivo(doador, motivoStatusId) && temMesmoTempoRetornoInatividade(doador,
				timeRetornoInatividade);
	}

	private boolean ehMesmoMotivo(Doador doador, Long motivoStatusId) {
		if (motivoStatusId == null && doador.getMotivoStatusDoador() == null) {
			return true;
		}
		if (doador.getMotivoStatusDoador() != null && motivoStatusId != null) {
			if (doador.getMotivoStatusDoador().getId().equals(motivoStatusId)) {
				return true;
			}
		}

		return false;
	}

	private boolean temMesmoTempoRetornoInatividade(Doador doador, Long timeRetornoInatividade) {

		if (doador.getDataRetornoInatividade() == null && timeRetornoInatividade == null) {
			return true;
		}

		if (doador.getDataRetornoInatividade() != null && timeRetornoInatividade != null) {
			LocalDate dataRetorno = calcularRetornoInatividade(timeRetornoInatividade);
			if (doador.getDataRetornoInatividade().equals(dataRetorno)) {
				return true;
			}
		}

		return false;
	}

	private boolean ehMesmoStatus(Doador doador, Long statusId) {
		return doador.getStatusDoador().getId().equals(statusId);
	}

	@Override
	public StatusDoador obterStatusDoadorPorId(Long idDoador) {
		return getRepository().obterStatusDoadorPorId(idDoador);
	}

	@Override
	public int obterQuantidadeProcessosAtivosDoador(Long idDoador) {
		return getRepository().obterQuantidadeProcessosAtivosDoador(idDoador);
	}

	@Override
	public Long obterDoadorPorSolicitacao(Long solicitacaoId) {
		return getRepository().obterDoadorPorSolicitacao(solicitacaoId);
	}

	@Override
	public Long obterDmrDoadorPorSolicitacao(Long solicitacaoId) {
		return getRepository().obterDmrDoadorPorSolicitacao(solicitacaoId);
	}
	
	@Override
	public IDoadorHeader obterDadosIdentificadaoPorDoador(Long idDoador) {
		if (idDoador == null) {
			throw new BusinessException("erro.parametros.invalidos", "");
		}

		Doador doadorEncontrado = doadorRepositorio.carregarDoadorSomenteComIdentificacao(idDoador);
		if (doadorEncontrado == null) {
			throw new BusinessException("mensagem.nenhum.registro.encontrado", "doador");
		}

		return criarDoadorHeader(doadorEncontrado);
	}
	
	private IDoadorHeader criarDoadorHeader(Doador doador) {
		return new IDoadorHeader() {
			
			@Override
			public StatusDoador getStatusDoador() {
				return doador.getStatusDoador();
			}
			
			@Override
			public String getSexo() {
				return doador.getSexo();
			}
			
			@Override
			public Registro getRegistroOrigem() {
				return doador.getRegistroOrigem();
			}
			
			@Override
			public String getNome() {
				return doador.getTipoDoador().getId().equals(TiposDoador.NACIONAL.getId()) ? ((DoadorNacional)doador).getNome() : null;
			}
			
			@SuppressWarnings("rawtypes")
			@Override			
			public String getIdentificacao() {
				return ((IDoador)doador).getIdentificacao().toString();
			}
			
			@Override
			public Long getIdTipoDoador() {
				return doador.getIdTipoDoador();
			}
			
			@Override
			public Long getId() {
				return doador.getId();
			}
			
			@Override
			public LocalDate getDataNascimento() {
				return doador.getDataNascimento();
			}
			
			@Override
			public BancoSangueCordao getBancoSangueCordao() {
				return doador.getTipoDoador().getId().equals(TiposDoador.CORDAO_NACIONAL.getId()) ? ((CordaoNacional)doador).getBancoSangueCordao() : null;
			}
		};
	}
	
	@Override
	public Doador obterDoador(Long id) {
		final Doador doador = findById(id);
		if (doador == null) {
			throw new BusinessException("erro.doador.doador_nao_existente_na_base");
		}
		return doador;
	}
	
	/**
	 * @param solicitacaoService the solicitacaoService to set
	 */
	@Autowired
	public void setSolicitacaoService(ISolicitacaoService solicitacaoService) {
		this.solicitacaoService = solicitacaoService;
	}

	/**
	 * @param motivoStatusDoadorService the motivoStatusDoadorService to set
	 */
	@Autowired
	public void setMotivoStatusDoadorService(IMotivoStatusDoadorService motivoStatusDoadorService) {
		this.motivoStatusDoadorService = motivoStatusDoadorService;
	}

	/**
	 * @param statusDoadorService the statusDoadorService to set
	 */
	@Autowired
	public void setStatusDoadorService(IStatusDoadorService statusDoadorService) {
		this.statusDoadorService = statusDoadorService;
	}

	/**
	 * @param matchService the matchService to set
	 */
	@Autowired
	public void setMatchService(IMatchService matchService) {
		this.matchService = matchService;
	}

	@Override
	public void salvarSemValidacao(Doador doador) {
		this.doadorRepositorio.save(doador);
	}

}
