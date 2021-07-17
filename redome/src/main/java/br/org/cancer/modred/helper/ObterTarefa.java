/**
 * 
 */
package br.org.cancer.modred.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.modred.configuration.ApplicationContextProvider;
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

/**
 * Classe responsável por obter uma tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public class ObterTarefa implements IObterTarefa {
	
	public static IUsuarioService usuarioService;
	public static ITarefaFeign tarefaFeign;
	public static ObjectMapper objectMapper;
	private IGenericRepository repositorioFactory;

	private ListaTarefaDTO p = new ListaTarefaDTO();

	private Long idTarefa;
	//private Long idTipoTarefa;
	//private TipoProcesso tipoProcesso;
	private Class<?> entidade;
	
	public ObterTarefa() {
		super();
		repositorioFactory = ApplicationContextProvider.obterBean(IGenericRepository.class);
	}

	/**
	 * @param perfil - tarefa será criada usando esse perfil.
	 * @param idTipoTarefa - Identiicador do tipo da trefa.
	 * @param tipoProcesso - Tipo do processo ao qual a tarefa está associada. 
	 */
	public ObterTarefa(Perfis perfil, Long idTipoTarefa, TipoProcesso tipoProcesso) {
		this();
		p.setPerfilResponsavel(Arrays.asList(perfil.getId()));
		//this.perfil = perfil;
		p.setIdsTiposTarefa(Arrays.asList(idTipoTarefa));
		this.entidade = obterEntidade(idTipoTarefa);
		p.setTipoProcesso(tipoProcesso.getId());
	}
	
	/**
	 * Obtém a entidade associada a listagem de tarefas para esse tipo.
	 * Para os tipos de tarefa agrupadores, todos os tipos de tarefas agrupados devem
	 * ter a mesma classe relacionada. 
	 * 
	 * @param idsTipoTarefa ID (ou IDs, caso agrupados) de tipos de tarefa.
	 * @return classe do objeto relacionado.
	 */
	private Class<?> obterEntidade(Long idTipoTarefa) {
		Class<?> entidade = null;
		
		final TiposTarefa tipoTarefa = TiposTarefa.valueOf(idTipoTarefa);
			
		if(entidade == null){
			entidade = tipoTarefa.getEntidade();
		}
		
		return entidade;
	}
	
	public IObterTarefa comTarefa(Long idTarefa) {
		this.idTarefa = idTarefa;
		return this;
	}

	@Override
	public IObterTarefa comUsuario(Usuario usuario) {
		if (usuario != null) {
			p.setIdUsuarioResponsavel(usuario.getId());
		}
		return this;
	}

	@Override
	public IObterTarefa comParceiros(List<Long> parceiros) {
		p.setParceiros(parceiros);
		return this;
	}

	@Override
	public IObterTarefa comStatus(List<StatusTarefa> status) {
		if (CollectionUtils.isNotEmpty(status)) {
			p.setStatusTarefa(new ArrayList<>());	
			status.stream().map(st -> st.getId())
			.forEach(p.getStatusTarefa()::add);
		}
		
		return this;
	}

	@Override
	public IObterTarefa comObjetoRelacionado(Long objetoRelacionado) {
		p.setRelacaoEntidadeId(objetoRelacionado);
		return this;
	}

	/*
	 * @Override public IObterTarefa comProcessoId(Long processoId) {
	 * p.setProcessoId(processoId); return this; }
	 */
	
	@Override
	public IObterTarefa comRmr(Long rmr) {
		p.setRmr(rmr);
		return this;
	}
	
	@Override
	public IObterTarefa comIdDoador(Long idDoador) {
		p.setIdDoador(idDoador);
		return this;
	}

	@Override
	public IObterTarefa paraOutroUsuario(Boolean outroUsuario) {
		this.p.setAtribuidoQualquerUsuario(outroUsuario);
		return this;
	}

	@Override
	public IObterTarefa semAgendamento(Boolean semAgendamento) {
		p.setTarefaSemAgendamento(semAgendamento);
		return this;
	}
	
	@Override
	public IObterTarefa comTarefaPai(Long tarefaPai) {
		p.setTarefaPaiId(tarefaPai);
		return this;
	}
	
	@Override
	public TarefaDTO apply() {
				
		if (usuarioService == null || tarefaFeign == null || objectMapper == null) {
			AutowireHelper.autowire(this);
		}
			
		TarefaDTO tarefa = null;
		if (idTarefa != null) {
			tarefa = tarefaFeign.obterTarefa(idTarefa);	
		}
		else {
			if (p.getProcessoId() == null && p.getRmr() == null && p.getIdDoador() == null ) {
				throw new BusinessException("erro.mensagem.processo.id.invalido");
			}
		
			if(CollectionUtils.isEmpty(p.getStatusTarefa())){
				throw new BusinessException("erro.mensagem.status.id.obrigatorio");
			}
			
			p.setIdUsuarioLogado(usuarioService.obterUsuarioLogadoId());
		
			String filter;
			try {
				filter = objectMapper.writeValueAsString(p);
			} catch (JsonProcessingException e) {
				throw new BusinessException("");
			}
			
			Page<TarefaDTO> tarefas = tarefaFeign.listarTarefas(Base64Utils.encodeToString(filter.getBytes()));
			
			if (tarefas.getContent().size() > 1) {
				throw new BusinessException("erro.mensagem.tarefa.mais_de_uma_tarefa");
			}
		
			if (!tarefas.getContent().isEmpty()) {
				tarefa = tarefas.getContent().get(0);
			}
		}
		if (tarefa != null) {	
			if (entidade != null && tarefa.getRelacaoEntidade() != null) {
				@SuppressWarnings("unchecked")
				final JpaRepository<?, Long> entidadeRepositorio = (JpaRepository<?, Long>) repositorioFactory.getRepository(entidade);
				Object objetoRelacaoEntidade = entidadeRepositorio.findById(tarefa.getRelacaoEntidade());
			
				tarefa.setObjetoRelacaoEntidade(objetoRelacaoEntidade);
			}
			
			return tarefa;
		}

		return null;
	}
	
	/**
	 * @param usuarioService the usuarioService to set
	 */
	@Autowired
	public void setUsuarioService(IUsuarioService usuarioService) {
		ObterTarefa.usuarioService = usuarioService;
	}
	
	@Autowired
	@Lazy(true)
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		ObterTarefa.tarefaFeign = tarefaFeign;
	}

	@Autowired
	public void setObjectMapper(ObjectMapper objectMapper) {
		ObterTarefa.objectMapper = objectMapper;
	}


}