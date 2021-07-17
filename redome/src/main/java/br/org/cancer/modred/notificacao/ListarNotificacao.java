package br.org.cancer.modred.notificacao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.dto.NotificacaoDTO;
import br.org.cancer.modred.service.IUsuarioService;

/**
 * Classe para centralizar a listagem de notificações.
 * 
 * @author brunosousa
 *
 */
public class ListarNotificacao implements IListarNotificacao {
	
	private Long idNotificacao;
	private Long idCategoriaNotificacao;
	private List<Long> parceiros;
	private PageRequest pageRequest;
	private Long rmr;
	private Boolean lidas;
	private Boolean somentePacientesDoUsuario;
	
	private IUsuarioService usuarioService = (IUsuarioService) ApplicationContextProvider
					.obterBean(IUsuarioService.class);
	
	public ListarNotificacao(Long idCategoriaNotificao) {
		this.idCategoriaNotificacao = idCategoriaNotificao;
	}

	@Override
	public IListarNotificacao comId(Long id) {
		this.idNotificacao = id;
		return this;
	}

	@Override
	public IListarNotificacao comPaginacao(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
		return this;
	}

	@Override
	public IListarNotificacao somenteLidas() {
		this.lidas = true;
		return this;
	}
	
	@Override
	public IListarNotificacao somenteNaoLidas() {
		this.lidas = false;
		return this;
	}

	@Override
	public IListarNotificacao comParceiros(List<Long> parceiros) {
		this.parceiros = parceiros;
		return this;
	}


	@Override
	public IListarNotificacao comRmr(Long rmr) {
		this.rmr = rmr;
		return this;
	}
	
	@Override
	public IListarNotificacao somentePacientesDoUsuario() {
		this.somentePacientesDoUsuario = true;
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IListarNotificacao comCategoria(Long categoriaNotificacao) {
		if (this.idCategoriaNotificacao != null) {
			throw new BusinessException("erro.interno");
		}
		this.idCategoriaNotificacao = categoriaNotificacao;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<NotificacaoDTO> apply() {
		Page<NotificacaoDTO> pageNotificacoes = null;
//		Page<Notificacao> pageNotificacoes = repository.listarNotificacoes(this.idNotificacao,
//				this.idCategoriaNotificacao,
//				this.parceiros,
//				this.lidas,
//				this.rmr,
//				usuarioService.obterUsuarioLogadoId(),
//				this.somentePacientesDoUsuario,
//				this.pageRequest);
		
//		List<ParceiroDTO> idsParceiro = pageNotificacoes.getContent().stream()
//				.filter(notificacao -> notificacao.getParceiros() != null)
//				.map(notificacao ->  new ParceiroDTO(notificacao.getParceiros(), notificacao.getCategoriaNotificacao().getConfiguracao().getParceiro())).distinct().collect(Collectors.toList());
//		
//		if (idCategoriaNotificacao != null && !idsParceiro.isEmpty()) {
//			final Class<?> entidadeParceiro = CategoriasNotificacao.get(idCategoriaNotificacao).getConfiguracao().getParceiro();
//			final IGenericRepository repositorioFactory = (IGenericRepository) ApplicationContextProvider.obterBean(IGenericRepository.class);
//			final JpaRepository<?, Long> entidadeRepositorio = (JpaRepository<?, Long>) repositorioFactory.getRepository(entidadeParceiro);
//			List<Object> objectParceiros = (List<Object>) entidadeRepositorio.findAllById(
//					idsParceiro.stream().map(parceiroDTO -> parceiroDTO.getId()).collect(Collectors.toList()));
//		
//			pageNotificacoes.getContent().stream().forEach(notificacao -> {
//				popularNotificacaoComParceiro(notificacao, objectParceiros);
//			});
//		}
//		else if (idCategoriaNotificacao == null && !idsParceiro.isEmpty()) {
//			final IGenericRepository repositorioFactory = (IGenericRepository) ApplicationContextProvider.obterBean(IGenericRepository.class);
//			final List<Object> objectParceiros = new ArrayList<>();			
//			idsParceiro.stream().forEach(parceiroDTO -> {
//				final JpaRepository<?, Long> entidadeRepositorio = (JpaRepository<?, Long>) repositorioFactory.getRepository(parceiroDTO.getClazz());
//				objectParceiros.add(entidadeRepositorio.findById(parceiroDTO.getId()).get());				
//			});
//			
//			pageNotificacoes.getContent().stream().forEach(notificacao -> {
//				popularNotificacaoComParceiro(notificacao, objectParceiros);
//			});
//			
//		}
		
		return pageNotificacoes;
	}
	
	/**
	 * Preenche o objeto entidade de acordo com o objeto contido na lista (o elo é pelo ID).
	 * 
	 * @param notificacao notificacao a ser preenchida.
	 * @param relacoesEntidades parceiros associados a notificacao.
	 */
//	private void popularNotificacaoComParceiro(NotificacaoDTO notificacaoDto, List<Object> parceiros) {
//		Optional<Object> parceiroEncontrado = parceiros.stream().filter(parceiro -> notificacao
//				.getParceiro().equals(AppUtil.callGetter(parceiro, "id")) && 
//				parceiro.getClass().equals(notificacao.getCategoriaNotificacao().getConfiguracao().getParceiro())).findFirst();
//		if (parceiroEncontrado.isPresent()) {
//			notificacao.setObjetoParceiro(parceiroEncontrado.get());
//		}
//
//	}
	
	private class ParceiroDTO {
		
		private Long id;
		private Class<?> clazz;
		
		ParceiroDTO(Long id, Class<?> clazz) {
			this.id = id;
			this.clazz = clazz;
		}

		/**
		 * @return the id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * @return the clazz
		 */
		public Class<?> getClazz() {
			return clazz;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ParceiroDTO other = (ParceiroDTO) obj;			
			if (id == null) {
				if (other.id != null) {
					return false;
				}
			} 
			else if (!id.equals(other.id)) {
				return false;
			}
			if (clazz == null) {
				if (other.clazz != null) {
					return false;
				}
			} 
			else if (!clazz.getName().equals(other.clazz.getName())) {
				return false;
			}
			return true;
		}

		
		
		
		
	}

}
