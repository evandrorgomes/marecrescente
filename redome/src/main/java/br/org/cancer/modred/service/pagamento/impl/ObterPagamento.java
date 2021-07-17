/**
 * 
 */
package br.org.cancer.modred.service.pagamento.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.domain.StatusPagamentos;
import br.org.cancer.modred.persistence.IPagamentoRepository;
import br.org.cancer.modred.service.pagamento.IObterPagamento;

/**
 * Classe responsável por obter um pagamento.
 * 
 * @author bruno.sousa
 *
 */
public class ObterPagamento implements IObterPagamento{

	private Long idMatch;
	private Long idRegistro;
	private Long objetoRelacionado;
	private List<StatusPagamentos> status;
	private Long idTipoServico;

	public ObterPagamento() {
		super();
	}

	/**
	 * @param idTipoServico
	 */
	public ObterPagamento(Long idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	@Override
	public IObterPagamento comStatus(List<StatusPagamentos> status) {
		this.status = status;
		return this;
	}

	@Override
	public IObterPagamento comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public Pagamento apply() {
		
		if (idMatch == null) {
			throw new BusinessException("erro.mensagem.match.id.invalido");
		}
				
		IPagamentoRepository pagamentoRepository = (IPagamentoRepository) ApplicationContextProvider.obterBean(IPagamentoRepository.class);
		
		Page<Pagamento> pagamentos = pagamentoRepository.listarPagamentos(idMatch, idRegistro,
				idTipoServico,				
				status,
				objetoRelacionado,
				PageRequest.of(0, 9999999) );

		if (pagamentos.getContent().size() > 1) {
			throw new BusinessException("erro.mensagem.pagamento.mais_de_um_pagamento");
		}
		
		if (pagamentos.getContent().size() > 0) {
			return pagamentos.getContent().get(0);
		}

		return null;
	}

	@Override
	public IObterPagamento comMatch(Long idMatch) {
		this.idMatch = idMatch;
		return this;
	}

	@Override
	public IObterPagamento comRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
		return this;
	}

}