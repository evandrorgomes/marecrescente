package br.org.cancer.modred.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.exception.ValidationException;
import br.org.cancer.modred.feign.dto.ProcessoDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.Pendencia;
import br.org.cancer.modred.model.RespostaPendencia;
import br.org.cancer.modred.model.StatusPendencia;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusPendencias;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IPendenciaRepository;
import br.org.cancer.modred.persistence.IRespostaPendenciaRepository;
import br.org.cancer.modred.service.IPendenciaService;
import br.org.cancer.modred.service.IRespostaPendenciaService;
import br.org.cancer.modred.util.CampoMensagem;
import br.org.cancer.modred.util.ConstraintViolationTransformer;

/**
 * Classe para negócios de Resposta de Pendência.
 * 
 * @author Filipe Paes
 *
 */
@Service
@Transactional
public class RespostaPendenciaService implements IRespostaPendenciaService {

	@Autowired
	private IRespostaPendenciaRepository respostaPendenciaRepository;

	@Autowired
	private IPendenciaRepository pendenciaRepository;

	@Autowired
	private IPendenciaService pendenciaService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private Validator validator;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void anotarReabrir(RespostaPendencia resposta) {
		Boolean pedenciasValidas = false;
		if (resposta.getPendencias() != null) {
			pedenciasValidas = resposta.getPendencias().stream()
					.allMatch(p -> StatusPendencias.ABERTA.getId().equals(p.getStatusPendencia().getId())
							|| StatusPendencias.RESPONDIDA.getId().equals(p.getStatusPendencia().getId()));
		}
		if (!pedenciasValidas) {
			throw new BusinessException("respostaPendencia.erro.status.pendencia", "anotar/reabrir",
					"fechada/cancelada");
		}

		if (customUserDetailsService.usuarioLogadoPossuiPerfil(Perfis.MEDICO_AVALIADOR)) {
			salvar(resposta);
			final StatusPendencia[] statusAnterior = { null };
			resposta.getPendencias().forEach(p -> {
				statusAnterior[0] = p.getStatusPendencia();
				pendenciaService.atualizarStatusDePendenciaParaAberto(p.getId());
			});
			Pendencia pendencia = pendenciaRepository.findById(resposta.getPendencias().get(0).getId()).get();
			Usuario usuarioMedicoAvaliador = pendencia.getAvaliacao().getMedicoResponsavel().getUsuario();
			Usuario usuarioMedicoPaciente = pendencia.getAvaliacao().getPaciente().getMedicoResponsavel().getUsuario();

			if (StatusPendencias.RESPONDIDA.getId().equals(statusAnterior[0].getId())) {
				List<TarefaDTO> tarefasFechadas = fecharTarefa(pendencia, usuarioMedicoAvaliador);
				tarefasFechadas.forEach(tarefaFechada -> {
					criarTarefaParaUmUsuario(pendencia, tarefaFechada.getProcesso(), usuarioMedicoPaciente, Perfis.MEDICO);
				});

			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responder(RespostaPendencia resposta) {
		Boolean pedenciasValidas = false;
		if (resposta.getPendencias() != null) {
			pedenciasValidas = resposta.getPendencias().stream()
					.allMatch(p -> StatusPendencias.ABERTA.getId().equals(p.getStatusPendencia().getId()));
		}
		if (!pedenciasValidas) {
			throw new BusinessException("respostaPendencia.erro.status.pendencia", "reponder",
					"respondida/fechada/cancelada");
		}

		if (customUserDetailsService.usuarioLogadoPossuiPerfil(Perfis.MEDICO)) {
			salvar(resposta);
			if (resposta.getRespondePendencia()) {
				resposta.getPendencias().forEach(p -> {
					pendenciaService.atualizarStatusDePendenciaParaRespondido(p.getId());
					Pendencia pendencia = pendenciaRepository.findById(p.getId()).get();

					Usuario medicoAvaliador = pendencia.getAvaliacao().getMedicoResponsavel().getUsuario();
					Usuario medicoDoPaciente = pendencia.getAvaliacao().getPaciente().getMedicoResponsavel().getUsuario();

					List<TarefaDTO> tarefasFechadas = fecharTarefa(pendencia, medicoDoPaciente);

					tarefasFechadas.forEach(tarefaFechada -> {
						criarTarefaParaUmUsuario(pendencia, tarefaFechada.getProcesso(), medicoAvaliador,
								Perfis.MEDICO_AVALIADOR);
					});
				});
			}
		}
	}

	private void criarTarefaParaUmUsuario(Pendencia pendencia, ProcessoDTO processo, Usuario usuario, Perfis perfil) {
		TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().criarTarefa()
			.comRmr(processo.getPaciente().getRmr())
			.comProcessoId(processo.getId())
			.comUsuario(usuario)
			.comStatus(StatusTarefa.ATRIBUIDA)
			.comObjetoRelacionado(pendencia.getId())
			.apply();
		
	}

	private List<TarefaDTO> fecharTarefa(Pendencia pendencia, Usuario usuario) {
		final Long rmr = pendencia.getAvaliacao().getPaciente().getRmr(); 
		
		Page<TarefaDTO> tarefas = TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().listarTarefa()
			.comRmr(rmr)
			.comStatus(Arrays.asList(StatusTarefa.ATRIBUIDA))
			.comUsuario(usuario)
			.apply().getValue();
		
		if (tarefas.isEmpty()) {
			throw new BusinessException("respostaPendencia.erro.tarefa_nao_encontrata");
		}
		
		tarefas.forEach(tarefa -> {
			TiposTarefa.RESPONDER_PENDENCIA.getConfiguracao().fecharTarefa()
				.comTarefa(tarefa.getId())
				.apply();
			
		});
		return tarefas.getContent();
	}

	private void validar(RespostaPendencia resposta, List<CampoMensagem> campos) {
		campos.addAll(new ConstraintViolationTransformer(validator.validate(resposta)).transform());
	}

	private void salvar(RespostaPendencia resposta) {
		resposta.setDataCriacao(LocalDateTime.now());
		resposta.setUsuario(customUserDetailsService.obterUsuarioLogado());
		List<CampoMensagem> campos = new ArrayList<CampoMensagem>();
		validar(resposta, campos);
		if (!campos.isEmpty()) {
			throw new ValidationException("erro.validacao", campos);
		}
		respostaPendenciaRepository.save(resposta);
	}

}
