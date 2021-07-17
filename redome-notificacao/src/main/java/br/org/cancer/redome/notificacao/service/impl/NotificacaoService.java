package br.org.cancer.redome.notificacao.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.cancer.redome.notificacao.dto.ListaNotificacaoDTO;
import br.org.cancer.redome.notificacao.dto.NotificacaoDTO;
import br.org.cancer.redome.notificacao.dto.UsuarioDTO;
import br.org.cancer.redome.notificacao.exception.BusinessException;
import br.org.cancer.redome.notificacao.feign.IPerfilFeign;
import br.org.cancer.redome.notificacao.model.Notificacao;
import br.org.cancer.redome.notificacao.persistence.INotificacaoRepository;
import br.org.cancer.redome.notificacao.persistence.IRepository;
import br.org.cancer.redome.notificacao.service.ICategoriaNotificacaoService;
import br.org.cancer.redome.notificacao.service.INotificacaoService;
import br.org.cancer.redome.notificacao.service.IUsuarioService;
import br.org.cancer.redome.notificacao.service.impl.custom.AbstractService;
import br.org.cancer.redome.notificacao.util.AppUtil;
import br.org.cancer.redome.notificacao.util.CampoMensagem;

/**
 * Classe de implementacao de negócio de Notificacao.
 * 
 * @author ergomes
 *
 */
@Service
@Transactional
public class NotificacaoService extends AbstractService<Notificacao, Long> implements INotificacaoService {

	@Autowired
	private INotificacaoRepository notificacaoRepository;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICategoriaNotificacaoService categoriaNotificacaoService;
	
	@Autowired
	@Lazy(true)
	private IPerfilFeign perfilFeign;

	@Override
	public IRepository<Notificacao, Long> getRepository() {
		return notificacaoRepository;
	}

	@Override
	public Page<Notificacao> listarNotificacoes(ListaNotificacaoDTO parametros) {		
		parametros.setIdUsuarioLogado(usuarioService.obterIdUsuarioLogado());
		return notificacaoRepository.listarNotificacoes(parametros);
	}
	
	@Override
	public Long contarNotificacoes(ListaNotificacaoDTO parametros) {
		parametros.setIdUsuarioLogado(usuarioService.obterIdUsuarioLogado());
		return notificacaoRepository.contarNotificacoes(parametros);
	}

	@Override
	public CampoMensagem marcarNotificacaoComoLida(Long idNotificacao) {
		
		if (idNotificacao == null) {
			throw new BusinessException("erro.mensagem.notificacao.nula");
		}
		
		Notificacao notificacao = notificacaoRepository.findById(idNotificacao).get();

		if (!notificacao.getLido()) {
			throw new BusinessException("erro.mensagem.notificacao.ja.lida");
		}
				
		Notificacao notificacaoNova = new Notificacao(
				notificacao.getId(), 
				notificacao.getDescricao(), 
				notificacao.getCategoria(), 
				notificacao.getRmr(), 
				false,
				notificacao.getDataCriacao(), 
				LocalDateTime.now(), 
				notificacao.getParceiro(), 
				usuarioService.obterIdUsuarioLogado()
		);
		notificacaoRepository.save(notificacaoNova);
		
		return new CampoMensagem(AppUtil.getMensagem(messageSource, "notificacao.lida"));
	}

	@Override
	public List<NotificacaoDTO> criarNotificacao(NotificacaoDTO notificacaoDto) {

		if (notificacaoDto != null) {

			if (notificacaoDto.getDataCriacao() == null) {
				notificacaoDto.setDataCriacao(LocalDateTime.now());
			}
			if (notificacaoDto.getDataLeitura() == null) {
				notificacaoDto.setDataLeitura(LocalDateTime.now());
			}
			
			if (notificacaoDto.getRmr() == null) {
				throw new BusinessException("erro.mensagem.notificacao.rmr.nulo");
			}
			
			if (notificacaoDto.getNomeClasseParceiro() == null && notificacaoDto.getParceiro() != null) {
				throw new BusinessException("erro.mensagem.notificacao.possui.parceiro.sem.classe");
			}

			if (notificacaoDto.getCategoriaId() == null) {
				throw new BusinessException("erro.mensagem.notificacao.categoria.invalido");
			}
			
			if (notificacaoDto.getUsuarioId() == null && notificacaoDto.getIdPerfil() == null) {
				throw new BusinessException("erro.mensagem.notificacao.sem.usuario.e.perfil");
			}
			
			List<NotificacaoDTO> notificacoesCriadas = new ArrayList<>();
			if (notificacaoDto.getUsuarioId() != null) {			
			    notificacoesCriadas.add(criarNotificacaoParaUsuario(notificacaoDto));
			}
			if (notificacaoDto.getIdPerfil() != null && notificacaoDto.getParceiro() != null) {
				notificacoesCriadas = criarNotificacaoParaUsuariosDoPerfilEParceiro(notificacaoDto);
			}
			if (notificacaoDto.getIdPerfil() != null && notificacaoDto.getParceiro() == null) {
				notificacoesCriadas = criarNotificacaoParaUsuariosDoPerfil(notificacaoDto);
			}
						
			return notificacoesCriadas;
		}
		
		throw new BusinessException("erro.mensagem.tarefa.nulo");
	}
	
	private List<NotificacaoDTO> criarNotificacaoParaUsuariosDoPerfilEParceiro(NotificacaoDTO notificacaoDto) {
		final Long idPerfil = notificacaoDto.getIdPerfil();
		final String parceiro = notificacaoDto.getNomeClasseParceiro();
		final Long idParceiro = notificacaoDto.getParceiro();
		List<UsuarioDTO> usuarios = perfilFeign.listarUsuarios(idPerfil, parceiro, idParceiro);
		return criarNotificacoes(usuarios, notificacaoDto);
	}
	
	private List<NotificacaoDTO> criarNotificacaoParaUsuariosDoPerfil(NotificacaoDTO notificacaoDto) {
		Long idPerfil = notificacaoDto.getIdPerfil();
		List<UsuarioDTO> usuarios = perfilFeign.listarUsuarios(idPerfil, null, null);				
		return criarNotificacoes(usuarios, notificacaoDto);
	}
	
	private List<NotificacaoDTO> criarNotificacoes(List<UsuarioDTO> usuarios, NotificacaoDTO notificacaoDto) {
		List<NotificacaoDTO> notificacoesCriadas = new ArrayList<>();
		if (usuarios != null && !usuarios.isEmpty()) {			
			usuarios.forEach(usuario -> {
				NotificacaoDTO notificacaoClone = notificacaoDto.toBuilder().usuarioId(usuario.getId()).build();
				notificacoesCriadas.add(criarNotificacaoParaUsuario(notificacaoClone));
			});
		}
		return notificacoesCriadas;
	}

	private NotificacaoDTO criarNotificacaoParaUsuario(NotificacaoDTO notificacaoDto) {
		Notificacao notificacao = Notificacao.parse(notificacaoDto);
		notificacao.setUsuario(notificacaoDto.getUsuarioId());
	    notificacao.setCategoria(categoriaNotificacaoService.obterCategoriaNotificacao(notificacaoDto.getCategoriaId()));			    			
	    notificacaoRepository.save(notificacao);
	    return NotificacaoDTO.parse(notificacao);
	}
	
	
	
}
