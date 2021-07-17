/**
 * 
 */
package br.org.cancer.modred.service.pagamento.impl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.Pagamento;
import br.org.cancer.modred.model.Registro;
import br.org.cancer.modred.model.StatusPagamento;
import br.org.cancer.modred.model.TipoServico;
import br.org.cancer.modred.model.domain.StatusPagamentos;
import br.org.cancer.modred.persistence.IPagamentoRepository;
import br.org.cancer.modred.service.pagamento.ICriarPagamento;

/**
 * Classe responsável por criar pagamentos.
 * 
 * @author bruno.sousa
 *
 */
public class CriarPagamento implements ICriarPagamento {

	private static final Logger LOGGER = LoggerFactory.getLogger(CriarPagamento.class);

	private Long idTipoServico;
	private Long objetoRelacionado;
	private StatusPagamentos status;
	private Long idRegistro;
	private Long idMatch;
	private boolean cobranca = false;

	public CriarPagamento() {
		super();
	}

	/**
	 * Construtor com passagem de parametros da configuração do tipo da serviço.
	 * 
	 * @param idTipoServico - tipo da serviço
	 */
	public CriarPagamento(Long idTipoServico) {
		this.idTipoServico = idTipoServico;
	}

	@Override
	public ICriarPagamento comObjetoRelacionado(Long objetoRelacionado) {
		this.objetoRelacionado = objetoRelacionado;
		return this;
	}

	@Override
	public ICriarPagamento comStatus(StatusPagamentos statusPagamento) {
		this.status = statusPagamento;
		return this;
	}
	
	@Override
	public ICriarPagamento comMatch(Long idMatch) {
		this.idMatch = idMatch;
		return this;
	}

	@Override
	public ICriarPagamento comRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
		return this;
	}
	
	@Override
	public ICriarPagamento comoCobranca() {
		this.cobranca = true;
		return this;
	}

	@Override
	public Pagamento apply() {

		if (idMatch == null || idMatch.longValue() <= 0) {
			throw new BusinessException("erro.mensagem.match.id.invalido");
		}
		if (idRegistro == null || idRegistro.longValue() <= 0) {
			throw new BusinessException("erro.mensagem.registro.id.invalido");
		}
		if (objetoRelacionado == null || objetoRelacionado.longValue() <= 0) {
			throw new BusinessException("erro.mensagem.objetorelacionado.id.invalido");
		}
				
		Pagamento pagamento = new Pagamento();
		pagamento.setDataCriacao(LocalDateTime.now());
		pagamento.setDataAtualizacao(LocalDateTime.now());

		if (status == null) {
			pagamento.setStatusPagamento(new StatusPagamento(StatusPagamentos.ABERTA.getId()));
		}
		else {
			pagamento.setStatusPagamento(new StatusPagamento(status.getId()));
		}
		pagamento.setIdObjetoRelacionado(objetoRelacionado);
		pagamento.setTipoServico(new TipoServico(idTipoServico));
		pagamento.setCobranca(cobranca);
		pagamento.setRegistro(new Registro(idRegistro));
		pagamento.setMatch(new Match(idMatch));

		IPagamentoRepository pagamentoRepository = (IPagamentoRepository) ApplicationContextProvider.obterBean(IPagamentoRepository.class);

		pagamento = pagamentoRepository.save(pagamento);

		LOGGER.info("Pagamento criado com sucesso: {}", pagamento);
		
		return pagamento;

	}


}