package br.org.cancer.modred.service.pagamento.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.StatusPagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;
import br.org.cancer.modred.persistence.IPagamentoRepository;
import br.org.cancer.modred.service.pagamento.IAtualizarPagamento;

/**
 * Classe de cancelamento de pagamento de serviços internacionais.
 * 
 * @author Filipe Paes
 *
 */
public class AtualizarPagamento implements IAtualizarPagamento {

	private static final Logger LOGGER = LoggerFactory.getLogger(AtualizarPagamento.class);

	private Long idTipoServico;
	private Long objetoRelacionado;
	private Long idMatch;
	private Long idRegistro;
	private StatusPagamentos statusPagamentos;

	public AtualizarPagamento() {
		super();
	}

	/**
	 * @param idTipoServico
	 */
	public AtualizarPagamento(Long idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	@Override
	public IAtualizarPagamento comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public IAtualizarPagamento comMatch(Long idMatch) {
		this.idMatch = idMatch;
		return this;
	}

	@Override
	public IAtualizarPagamento comRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
		return this;
	}

	@Override
	public IAtualizarPagamento comStatusPagamentos(StatusPagamentos statusPagamentos) {
		this.statusPagamentos = statusPagamentos;
		return this;
	}

	
	@Override
	public Pagamento apply() {
		if (idMatch == null) {
			throw new BusinessException("erro.mensagem.match.id.invalido");
		}

		IPagamentoRepository pagamentoRepository = (IPagamentoRepository) ApplicationContextProvider.obterBean(
				IPagamentoRepository.class);

		Page<Pagamento> pagamentos = pagamentoRepository.listarPagamentos(idMatch, idRegistro,
				idTipoServico,
				Arrays.asList(StatusPagamentos.ABERTA, StatusPagamentos.CREDITO),
				objetoRelacionado,
				null);

		if (pagamentos.getContent().size() > 1) {
			throw new BusinessException("erro.mensagem.pagamento.mais_de_um_pagamento");
		}
		Pagamento pagamento = null;
		if (!pagamentos.getContent().isEmpty()) {

			pagamento = pagamentoRepository.findById(pagamentos.getContent().get(0).getId()).get();
			pagamento.setStatusPagamento(new StatusPagamento(statusPagamentos.getId()));
			pagamentoRepository.save(pagamento);
			LOGGER.info("Pagamento cancelado com sucesso: {}", pagamento);
		}

		return pagamento;
	}
}
