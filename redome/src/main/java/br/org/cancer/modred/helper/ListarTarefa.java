/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.hibernate.internal.util.config.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
import br.org.cancer.modred.controller.page.TarefaJsonPage;
import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;
import br.org.cancer.modred.exception.BusinessException;
import br.org.cancer.modred.feign.client.ITarefaFeign;
import br.org.cancer.modred.feign.dto.ListaTarefaDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.Perfis;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.domain.TipoProcesso;
import br.org.cancer.modred.model.domain.TiposTarefa;
import br.org.cancer.modred.model.security.Usuario;
import br.org.cancer.modred.persistence.IGenericRepository;
import br.org.cancer.modred.service.IUsuarioService;
import br.org.cancer.modred.util.AppUtil;
import br.org.cancer.modred.util.PaginacaoUtil;

/**
 * Classe para centralizar a listagem de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public class ListarTarefa implements IListarTarefa{
	
	public static IUsuarioService usuarioService;
	public static ITarefaFeign tarefaFeign;
	public static ObjectMapper objectMapper;

	private IGenericRepository repositorioFactory;
	private ListaTarefaDTO p = new ListaTarefaDTO();
	
	private Predicate<TarefaDTO> filtro;
	private Comparator<TarefaDTO> ordenacao;
	private Class<?> entidade;
	private Class<? extends ListarPaginacao> jsonView;

	private ListarTarefa() {
		super();
		repositorioFactory = ApplicationContextProvider.obterBean(IGenericRepository.class);
		this.ordenacao = new TarefaOrdenacaoDefaultComparator();
	}

	/**
	 * Construtor para facilitar a criação de uma nova função.
	 * 
	 * @param perfil Perfil configurado para resolver o tipo de tarefa selecionado.
	 * @param idsTipoTarefa ID (ou IDs, caso agrupados) de tipos de tarefa.
	 * @param jsonView Define qual o json view utilizado para filtrar os atributos a serem serializados.
	 * @param tipoProcesso - Tipo do processo ao qual a terfa foi configurada. 
	 * @throws ConfigurationException exceção lançada quando a classe do objeto relacionado não é válido para o itens agrupados.
	 */
	private ListarTarefa(Perfis perfil, List<Long> idsTipoTarefa, Class<? extends ListarPaginacao> jsonView, TipoProcesso tipoProcesso) throws ConfigurationException {
		this();
		if (perfil != null) {
			p.setPerfilResponsavel(Arrays.asList(perfil.getId()));
		}
		this.p.setIdsTiposTarefa(idsTipoTarefa);
		this.entidade = obterEntidade(idsTipoTarefa);
		this.jsonView = jsonView;
		this.p.setTipoProcesso(tipoProcesso.getId());
	}

	/**
	 * Obtém a entidade associada a listagem de tarefas para esse tipo.
	 * Para os tipos de tarefa agrupadores, todos os tipos de tarefas agrupados devem
	 * ter a mesma classe relacionada. 
	 * 
	 * @param idsTipoTarefa ID (ou IDs, caso agrupados) de tipos de tarefa.
	 * @return classe do objeto relacionado.
	 */
	private Class<?> obterEntidade(List<Long> idsTipoTarefa) {
		Class<?> entidade = null;
		for (Long idTipoTarefa : idsTipoTarefa) {
			final TiposTarefa tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
			
			if(entidade == null){
				entidade = tipoTarefa.getEntidade();
			}
			else {
				if(!entidade.getCanonicalName().equals(tipoTarefa.getEntidade().getCanonicalName())){
					throw new ConfigurationException(
							"Classe do objeto relacionado deve ser a mesma para os itens agrupados.");
				}
			}
		}
		
		return entidade;
	}
	
	/**
	 * @param tipoProcesso
	 * @param perfil
	 * @param idTipoTarefa
	 * @param ordenacao
	 */
	public ListarTarefa(Perfis perfil, List<Long> idsTipoTarefa, Class<? extends ListarPaginacao> jsonView, TipoProcesso tipoProcesso, Comparator<TarefaDTO> ordenacao) {
		this(perfil, idsTipoTarefa, jsonView, tipoProcesso);
		this.ordenacao = ordenacao;
	}

	@Override
	public IListarTarefa comUsuario(Usuario usuario) {
		if (usuario != null) {
			this.p.setIdUsuarioResponsavel(usuario.getId());
		}
		return this;
	}

	@Override
	public IListarTarefa comParceiros(List<Long> parceiros) {
		this.p.setParceiros(parceiros);
		return this;
	}

	@Override
	public IListarTarefa comStatus(List<StatusTarefa> status) {
		if (status != null) {
			this.p.setStatusTarefa(new ArrayList<>());
			status.stream().map(s -> s.getId())
				.forEach(p.getStatusTarefa()::add);
		}
			
		return this;
	}

	@Override
	public IListarTarefa comPaginacao(PageRequest pageRequest) {
		this.p.setPageable(pageRequest);
		return this;
	}

	@Override
	public IListarTarefa comObjetoRelacionado(Long objetoRelacionado) {
		this.p.setRelacaoEntidadeId(objetoRelacionado);
		return this;
	}

	@Override
	public IListarTarefa comProcessoId(Long processoId) {
		this.p.setProcessoId(processoId);
		return this;
	}
	
	@Override
	public IListarTarefa comOrdenacao(Comparator<TarefaDTO> ordenacao) {
		this.ordenacao = ordenacao;
		return this;
	}
	
	@Override
	public IListarTarefa paraTodosUsuarios() {
		this.p.setAtribuidoQualquerUsuario(true);
		return this;
	}
	
	@Override
	public Class<? extends ListarPaginacao> getJsonView() {
		return jsonView;
	}
	
	/**
	 * Preenche o objeto entidade de acordo com o objeto contido na lista (o elo é pelo ID).
	 * 
	 * @param tarefa tarefa a ser preenchida.
	 * @param relacoesEntidades entidades associadas as tarefas.
	 */
	private void popularTarefaComObjetoRelacionado(TarefaDTO tarefa, List<Object> relacoesEntidades) {
		Optional<Object> objetoEncontrado = relacoesEntidades.stream().filter(objetoRelacaoEntidade -> tarefa
				.getRelacaoEntidade().equals(AppUtil.callGetter(objetoRelacaoEntidade, "id"))).findFirst();
		Object entidadeEncontrada = null;
		if (objetoEncontrado.isPresent()) {
			entidadeEncontrada = objetoEncontrado.get();
		}

		tarefa.setObjetoRelacaoEntidade(entidadeEncontrada);
	}

	@Override
	@SuppressWarnings("unchecked")
	public JsonViewPage<TarefaDTO> apply() {
		
		if (usuarioService == null || tarefaFeign == null || objectMapper == null) {
			AutowireHelper.autowire(this);
		}
				
		final boolean hasOrdenacaoCustomizada = ordenacao != null;
		final boolean hasFiltro = filtro != null;
		
		//parametros.
		p.setIdUsuarioLogado(usuarioService.obterUsuarioLogadoId());
		
		String filter;
		try {
			filter = objectMapper.writeValueAsString(this.p);
		} catch (JsonProcessingException e) {
			throw new BusinessException("");
		}
		
		Page<TarefaDTO> pageTarefas = tarefaFeign.listarTarefas(Base64Utils.encodeToString(filter.getBytes()));
		
		/*Page<TarefaDTO> pageTarefas = tarefaRepository.listarTarefas(perfis, usuario,
				parceiros,
				idsTipoTarefa,
				status,
				StatusProcesso.ANDAMENTO,
				processoId,
				null, hasOrdenacaoCustomizada || hasFiltro ? null : pageRequest, objetoRelacionado,
				rmr,
				tipoProcesso,
				todosUsuarios, null, false, usuarioService.obterUsuarioLogado());*/
		
		
		List<Long> idsRelacaoEntidade = pageTarefas.getContent().stream()
				.filter(tarefa -> tarefa.getRelacaoEntidade() != null)
				.map(tarefa -> tarefa.getRelacaoEntidade()).distinct().collect(Collectors.toList());
		
		if (entidade != null && !idsRelacaoEntidade.isEmpty()) {
			final JpaRepository<Object, Long> entidadeRepositorio = (JpaRepository<Object, Long>) repositorioFactory.getRepository(entidade);
			List<Object> relacoesEntidades = (List<Object>) entidadeRepositorio.findAllById(idsRelacaoEntidade);
		
			pageTarefas.getContent().stream().forEach(tarefa -> {
				popularTarefaComObjetoRelacionado(tarefa, relacoesEntidades);
			});
		}
		
		List<TarefaDTO> listaResultado = null;
		long totalRegistros = -1;
		if(hasFiltro){
			listaResultado = (List<TarefaDTO>) pageTarefas.getContent().stream().filter(filtro).collect(Collectors.toList());
			totalRegistros = listaResultado.size(); 
			
			if (!hasOrdenacaoCustomizada && p.getPageable() != null) {
				listaResultado = 
						PaginacaoUtil.retornarListaPaginada(
								listaResultado, p.getPageable().getPageNumber(), p.getPageable().getPageSize());
			}
			
		}
			
		if (listaResultado != null && hasOrdenacaoCustomizada) {
			List<TarefaDTO> modifiableListTarefa = new ArrayList<TarefaDTO>(listaResultado);
			Collections.sort(modifiableListTarefa, ordenacao);
			listaResultado = modifiableListTarefa;
			if(totalRegistros == -1) {
				totalRegistros = listaResultado.size();	
			}
			
			if(p.getPageable() != null){
				listaResultado = PaginacaoUtil.retornarListaPaginada(
						listaResultado, p.getPageable().getPageNumber(), 
						p.getPageable().getPageSize());
			}
			
			
		}
		
		if (listaResultado != null && (hasFiltro || hasOrdenacaoCustomizada)){
			pageTarefas = new TarefaJsonPage<>(listaResultado, p.getPageable(), totalRegistros);
		}
		
		JsonViewPage<TarefaDTO> paginacaoResultado = new JsonViewPage<TarefaDTO>(pageTarefas);
		paginacaoResultado.setSerializationView(getJsonView());
		return paginacaoResultado;
	}

	@Override
	public IListarTarefa comRmr(Long rmr) {
		this.p.setRmr(rmr);
		return this;
	}
	
	@Override
	public IListarTarefa comIdDoador(Long idDoador) {
		this.p.setIdDoador(idDoador);
		return this;
	}

	@Override
	public IListarTarefa comFiltro(Predicate<TarefaDTO> filtro) {
		this.filtro = filtro;
		return this;
	}
	
	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	@Lazy(true)
	public void setUsuarioService(IUsuarioService usuarioService) {
		ListarTarefa.usuarioService = usuarioService;
	}
	
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		ListarTarefa.tarefaFeign = tarefaFeign;
	}
	
	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		ListarTarefa.objectMapper = objectMapper;
	}


	
}