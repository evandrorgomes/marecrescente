package br.org.cancer.modred.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.controller.dto.RespostaPendenciaDTO;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Avaliacao;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.TipoPendencia;
import br.org.cancer.modred.model.domain.StatusPendencias;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IAvaliacaoRepository;
import br.org.cancer.modred.persistence.IPacienteRepository;
import br.org.cancer.modred.persistence.IPendenciaRepository;
import br.org.cancer.modred.persistence.IStatusPendenciaRepository;
import br.org.cancer.modred.persistence.ITipoPendenciaRepository;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe de negócios para pendência.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class PendenciaService implements IPendenciaService {

	@Autowired
	private IPendenciaRepository pendenciaRepository;

	private MessageSource messageSource;

	private Validator validator;

	@Autowired
	private IStatusPendenciaRepository statusPendenciaRepository;

	@Autowired
	private ITipoPendenciaRepository tipoPendenciaRepository;

	@Autowired
	private IAvaliacaoRepository avaliacaoRepository;


	@Autowired
	private IPacienteRepository pacienteRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizarStatusDePendencia(Long idPendencia, StatusPendencia status) {
		Pendencia pendencia = pendenciaRepository.findById(idPendencia).orElse(null);
		if (pendencia == null) {
			throw new BusinessException("erro.buscar.pendencia");
		}

		if (status == null || status.getId() == null) {
			throw new BusinessException("erro.statuspendencia.nula");
		}
		Usuario avaliador = pendencia.getAvaliacao().getMedicoResponsavel().getUsuario();
		Usuario medicoResponsavel = pendencia.getAvaliacao().getPaciente().getMedicoResponsavel().getUsuario();
		if (StatusPendencias.CANCELADA.getId().equals(status.getId())) {
			if (StatusPendencias.ABERTA.getId().equals(pendencia.getStatusPendencia().getId())) {
				fecharTarefa(pendencia, medicoResponsavel);
			}
			else
				if (StatusPendencias.RESPONDIDA.getId().equals(pendencia.getStatusPendencia().getId())) {
					fecharTarefa(pendencia, avaliador);
				}
		}
		else
			if (StatusPendencias.FECHADA.getId().equals(status.getId())
					&& StatusPendencias.RESPONDIDA.getId().equals(pendencia.getStatusPendencia().getId())) {
				fecharTarefa(pendencia, avaliador);
			}
		pendencia.setStatusPendencia(status);
		pendenciaRepository.save(pendencia);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizarStatusDePendenciaParaFechado(Long idPendencia) {
		this.atualizarStatusDePendencia(idPendencia, new StatusPendencia(StatusPendencias.FECHADA.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizarStatusDePendenciaParaCancelado(Long idPendencia) {
		this.atualizarStatusDePendencia(idPendencia, new StatusPendencia(StatusPendencias.CANCELADA.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizarStatusDePendenciaParaAberto(Long idPendencia) {
		this.atualizarStatusDePendencia(idPendencia, new StatusPendencia(StatusPendencias.ABERTA.getId()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void criarPendencia(Pendencia pendencia) {
		List<CampoMensagem> campos = new ConstraintViolationTransformer(validator.validate(pendencia)).transform();
		if (campos.isEmpty()) {
			validar(pendencia, campos);
		}
		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}
		else {
			pendencia.setAvaliacao(avaliacaoRepository.findById(pendencia.getAvaliacao().getId()).get());
			pendenciaRepository.save(pendencia);
			atribuirTarefaParaMedicoResponsavel(pendencia);
		}
	}

	private void atribuirTarefaParaMedicoResponsavel(Pendencia pendencia) {

		// precisamos pegar o objeto completo, que contenha o médico responsável
		Long rmr = pendencia.getAvaliacao().getPaciente().getRmr();
		Paciente paciente = pacienteRepository.findById(rmr).get();
		
		TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().criarTarefa()
			.comRmr(paciente.getRmr())
			.comStatus(StatusTarefa.ATRIBUIDA)
			.comUsuario(paciente.getMedicoResponsavel().getUsuario())
			.comObjetoRelacionado(pendencia.getId())
			.apply();
		
	}

	private TarefaDTO fecharTarefa(Pendencia pendencia, Usuario usuario) {
		TarefaDTO tarefaLoc = TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().obterTarefa()
			.comRmr(pendencia.getAvaliacao().getPaciente().getRmr())
			.comObjetoRelacionado(pendencia.getId())
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuario)
			.apply();
		
		if (tarefaLoc != null) {
			return TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().fecharTarefa()
					.comTarefa(tarefaLoc.getId())
					.apply();					
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void validar(Pendencia pendencia, List<CampoMensagem> erros) {
		validarStatusPendencia(pendencia, erros);
		validarTipoPendencia(pendencia, erros);
	}

	private void validarTipoPendencia(Pendencia pendencia, List<CampoMensagem> campos) {
		if (pendencia.getTipoPendencia() != null && pendencia.getTipoPendencia().getId() != null) {
			if (tipoPendenciaRepository.findById(pendencia.getTipoPendencia().getId()) == null) {
				campos.add(new CampoMensagem("pendencia.tipo.invalido",
						messageSource.getMessage("pendencia.tipo.invalido", null, LocaleContextHolder.getLocale())));
			}
		}
	}

	private void validarStatusPendencia(Pendencia pendencia, List<CampoMensagem> campos) {
		if (pendencia.getStatusPendencia() != null && pendencia.getStatusPendencia().getId() != null) {
			if (statusPendenciaRepository.findById(pendencia.getStatusPendencia().getId()) == null) {
				campos.add(new CampoMensagem("pendencia.status.invalido",
						messageSource.getMessage("pendencia.status.invalido", null, LocaleContextHolder.getLocale())));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RespostaPendenciaDTO> listarRespostas(Long pendenciaId) {
		return pendenciaRepository.listarRespostas(pendenciaId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TipoPendencia> listarTiposPendencia() {
		return tipoPendenciaRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void atualizarStatusDePendenciaParaRespondido(Long idPendencia) {
		this.atualizarStatusDePendencia(idPendencia, new StatusPendencia(StatusPendencias.RESPONDIDA.getId()));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Pendencia> listarPendenciasPorTipoEEmAbertoDaAvaliacaoAtualDeUmPaciente(Long rmr,
			Long idTipoPendencia) {

		if (idTipoPendencia == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}

		Avaliacao avaliacaoAtual = avaliacaoRepository.findLastByPacienteRmrOrderByDataCriacao(rmr);
		if (avaliacaoAtual != null && avaliacaoAtual.getPendencias() != null
				&& !avaliacaoAtual.getPendencias().isEmpty()) {
			List<Pendencia> pendencias = avaliacaoAtual.getPendencias().stream().filter(pendencia -> {
				return idTipoPendencia.equals(pendencia.getTipoPendencia().getId())
						&& StatusPendencias.ABERTA.getId().equals(pendencia.getStatusPendencia().getId());
			}).collect(Collectors.toList());

			return pendencias;
		}
		return new ArrayList<Pendencia>();
	}

	/**
	 * Mpetodo para cancelar uma lista de pendencias.
	 * 
	 * @param pendencias - lista de pendencia a ser cancelada.
	 */
	public void cancelarPendencias(List<Pendencia> pendencias) {
		pendencias.stream().filter(pendencia -> {
			return pendencia.getStatusPendencia().getId().equals(StatusPendencias.ABERTA.getId()) ||
					pendencia.getStatusPendencia().getId().equals(StatusPendencias.RESPONDIDA.getId());
		}).forEach(pendencia -> {
			atualizarStatusDePendenciaParaCancelado(pendencia.getId());
		});
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param validator the validator to set
	 */
	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	

}
