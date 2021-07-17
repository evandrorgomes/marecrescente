package br.org.cancer.modred.notificacao;

import java.time.LocalDateTime;
import java.util.List;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.model.domain.CategoriasNotificacao;

/**
 * Classe reponsável por marcar uma notificação como lida.
 * 
 * @author brunosousa
 *
 */
public class LerNotificacao implements ILerNotificacao {
	
	private Long idNotificacao;
	private Long idCategoriaNotificacao;
	private List<Long> parceiros;		
	private Long rmr;

	@Override
	public ILerNotificacao comId(Long id) {
		this.idNotificacao = id;
		return this;
	}
	
	public LerNotificacao(Long idCategoriaNotificao) {
		this.idCategoriaNotificacao = idCategoriaNotificao;
	}

	@Override
	public ILerNotificacao comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}


	@Override
	public ILerNotificacao comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ILerNotificacao comCategoria(Long categoriaNotificacao) {
		if (this.idCategoriaNotificacao != null) {
			throw new BusinessException("erro.interno");
		}
		this.idCategoriaNotificacao = categoriaNotificacao;
		return this;
	}

	@Override
	public NotificacaoDTO apply() {
		
		NotificacaoDTO notificacao = null;
		if (this.idCategoriaNotificacao != null) {
			notificacao = CategoriasNotificacao.get(this.idCategoriaNotificacao).getConfiguracao().obter()
					.comId(this.idNotificacao)
					.comParceiros(this.parceiros)
					.comRmr(this.rmr)
					.apply();
		}
		else {
			notificacao = CategoriasNotificacao.obter()
				.comCategoria(idCategoriaNotificacao)
				.comId(this.idNotificacao)
				.comParceiros(this.parceiros)
				.comRmr(this.rmr)
				.apply();			
		}
		
		if (notificacao == null) {
			throw new BusinessException("erro.mensagem.notificacao.nula");
		}
		
		if (notificacao.getLido()) {
			throw new BusinessException("erro.mensagem.notificacao.ja.lida");
		}
		
		
		notificacao.setLido(true);
		notificacao.setDataLeitura(LocalDateTime.now());
		
		return null; // repository.save(notificacao);
	}

	
	
}
