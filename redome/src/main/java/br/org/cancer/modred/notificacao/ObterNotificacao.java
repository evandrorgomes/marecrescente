package br.org.cancer.modred.notificacao;

import java.util.List;

import org.springframework.data.domain.Page;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;

/**
 * Classe para obter a notificação.
 * 
 * @author brunosousa
 *
 */
public class ObterNotificacao implements IObterNotificacao {
	
	private Long idNotificacao;
	private Long idCategoriaNotificacao;
	private List<Long> parceiros;		
	private Long rmr;

	@Override
	public IObterNotificacao comId(Long id) {
		this.idNotificacao = id;
		return this;
	}
	
	public ObterNotificacao(Long idCategoriaNotificao) {
		this.idCategoriaNotificacao = idCategoriaNotificao;
	}

	@Override
	public IObterNotificacao comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}


	@Override
	public IObterNotificacao comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IObterNotificacao comCategoria(Long categoriaNotificacao) {
		if (this.idCategoriaNotificacao != null) {
			throw new BusinessException("erro.interno");
		}
		this.idCategoriaNotificacao = categoriaNotificacao;
		return this;
	}

	@Override
	public NotificacaoDTO apply() {
		
		if (this.idNotificacao == null) {
			throw new BusinessException("erro.mensagem.notificacao.id.invalido");
		}
				
		Page<NotificacaoDTO> notificacoes = null;
		if (this.idCategoriaNotificacao != null) {
			notificacoes = CategoriasNotificacao.get(this.idCategoriaNotificacao).getConfiguracao().listar()
				.comCategoria(idCategoriaNotificacao)
				.comId(idNotificacao)
				.comParceiros(parceiros)
				.comRmr(rmr)
				.apply();
		}
		else {
			notificacoes = CategoriasNotificacao.listar()
					.comCategoria(idCategoriaNotificacao)
					.comId(idNotificacao)
					.comParceiros(parceiros)
					.comRmr(rmr)
					.apply();
		}
				
		if (notificacoes.getContent().size() > 1) {
			throw new BusinessException("erro.mensagem.notificacao.mais_de_uma_notificacao");
		}
		
		if (notificacoes.getContent().size() > 0) {
			return notificacoes.getContent().get(0);
		}
		
		return null;
	}

	
}
