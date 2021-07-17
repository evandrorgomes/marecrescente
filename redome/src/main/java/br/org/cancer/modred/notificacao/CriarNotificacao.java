package br.org.cancer.modred.notificacao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.INotificacaoFeign;
import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.helper.AutowireHelper;
import br.org.cancer.modred.model.domain.Perfis;

/**
 * Classe responsável por criar uma notificação.
 * 
 * @author ergomes
 *
 */
public class CriarNotificacao implements ICriarNotificacao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CriarNotificacao.class);
	
	public static INotificacaoFeign notificacaoFeign;
	
	//private Class<?> parceiro;
	
	NotificacaoDTO dto = new NotificacaoDTO();
	
	/**
	 * Construtor com passagem de parametros da configuração da categoria de notificação.
	 * 
	 * @param idCategoriaNotificacao - identificador da categoria de notificacao
	 * @param classe CriarNotificacao - classe a ser utilizada para criar a notificação
	 */
	public CriarNotificacao(Long categoriaId, Class<?> parceiro) {
		this.dto.setCategoriaId(categoriaId);
		if (parceiro != null) {
			this.dto.setNomeClasseParceiro(parceiro.getSimpleName());
		}
	}
	

	@Override
	public ICriarNotificacao comParceiro(Long idParceiro) {
		this.dto.setParceiro(idParceiro);
		return this;
	}

	@Override
	public ICriarNotificacao paraUsuario(Long usuario) {
		this.dto.setUsuarioId(usuario);
		return this;
	}
	
	@Override
	public ICriarNotificacao paraPerfil(Perfis perfil) {
		this.dto.setIdPerfil(perfil.getId());
		return this;
	}

	@Override
	public ICriarNotificacao comDescricao(String descricao) {
		this.dto.setDescricao(descricao);
		return this;
	}

	@Override
	public ICriarNotificacao comPaciente(Long rmr) {
		this.dto.setRmr(rmr);
		return this;
	}
	
	@Override
	public List<NotificacaoDTO> apply() {
		
		if ((dto.getUsuarioId() == null && dto.getIdPerfil() == null) || dto.getRmr() == null || dto.getDescricao() == null) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		if ((dto.getUsuarioId() != null && dto.getIdPerfil() != null)) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		
		if ((this.dto.getNomeClasseParceiro() == null && dto.getParceiro() != null)) {
			throw new BusinessException("erro.parametros.invalidos");
		}
		
		if (notificacaoFeign == null) {
			AutowireHelper.autowire(this);
		}
		
		return notificacaoFeign.criarNotificacao(dto);
	}

	/**
	 * @param notificacaoFeign the notificacaoFeign to set
	 */
	@Autowired
	@Lazy(true)
	public void setNotificacaoFeign(INotificacaoFeign notificacaoFeign) {
		CriarNotificacao.notificacaoFeign = notificacaoFeign;
	}

}
