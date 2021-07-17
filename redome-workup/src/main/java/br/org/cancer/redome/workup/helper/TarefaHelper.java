package br.org.cancer.redome.workup.helper;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.exception.BusinessException;
import br.org.cancer.redome.workup.feign.client.ITarefaFeign;
import br.org.cancer.redome.workup.util.CustomPageImpl;

@Component
@Lazy
public class TarefaHelper implements ITarefaHelper {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private ITarefaFeign tarefaFeign;
	
	@Override
	public CustomPageImpl<TarefaDTO> pageTarefas(ListaTarefaDTO filtroDTO) throws JsonProcessingException {
		String jsonFiltroDTO = objectMapper.writeValueAsString(filtroDTO); 
		String filtro = Base64Utils.encodeToString(jsonFiltroDTO.getBytes());
		return tarefaFeign.listarTarefas(filtro);
	}
	
	@Override
	public List<TarefaDTO> listarTarefas(ListaTarefaDTO filtroDTO) throws JsonProcessingException {
		String jsonFiltroDTO = objectMapper.writeValueAsString(filtroDTO); 
		String filtro = Base64Utils.encodeToString(jsonFiltroDTO.getBytes());
		return tarefaFeign.listarTarefas(filtro).getContent();
	}
	
	@Override
	public TarefaDTO atribuirTarefa(ListaTarefaDTO filtroDTO, Long idUsuario) throws JsonProcessingException {
		List<TarefaDTO> tarefas = listarTarefas(filtroDTO);
		if (CollectionUtils.isEmpty(tarefas)) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		return tarefaFeign.atribuirTarefaUsuario(tarefas.get(0).getId(), idUsuario);
	}
	
	@Override
	public TarefaDTO atribuirTarefa(Long id, Long idUsuario) {	
		return tarefaFeign.atribuirTarefaUsuario(id, idUsuario);
	}
	
	@Override
	public TarefaDTO atualizarTarefa(TarefaDTO tarefa) {	
		return tarefaFeign.atualizarTarefa(tarefa.getId(), tarefa);
	}
	
	@Override
	public TarefaDTO encerrarTarefa(ListaTarefaDTO filtroDTO, boolean encerrarProcesso) throws JsonProcessingException {
		List<TarefaDTO> tarefas = listarTarefas(filtroDTO);
		if (CollectionUtils.isEmpty(tarefas)) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		return tarefaFeign.encerrarTarefa(tarefas.get(0).getId(), encerrarProcesso);
	}
	
	@Override
	public TarefaDTO encerrarTarefa(Long id, boolean encerrarProcesso) {
		return tarefaFeign.encerrarTarefa(id, encerrarProcesso);
	}
	
	@Override
	public TarefaDTO criarTarefa(TarefaDTO tarefa) {		
		return tarefaFeign.criarTarefa(tarefa);
	}
	
	@Override
	public TarefaDTO obterTarefa(ListaTarefaDTO filtroDTO) throws JsonProcessingException {
		List<TarefaDTO> tarefas = listarTarefas(filtroDTO);
		if (CollectionUtils.isEmpty(tarefas)) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		return tarefas.get(0);
	}
	
	@Override
	public TarefaDTO obterTarefa(Long id) {	
		return tarefaFeign.obterTarefa(id);
	}
	
	@Override
	public TarefaDTO cancelarTarefa(ListaTarefaDTO filtroDTO, boolean encerrarProcesso) throws JsonProcessingException {
		List<TarefaDTO> tarefas = listarTarefas(filtroDTO);
		if (CollectionUtils.isEmpty(tarefas)) {
			throw new BusinessException("tarefa.nao.encontrada");
		}
		return tarefaFeign.cancelarTarefa(tarefas.get(0).getId(), encerrarProcesso);
	}
	
	@Override
	public TarefaDTO cancelarTarefa(Long id, boolean encerrarProcesso) {
		return tarefaFeign.cancelarTarefa(id, encerrarProcesso);
	}

	@Autowired
	public void setTarefaFeign(ITarefaFeign tarefaFeign) {
		this.tarefaFeign = tarefaFeign;
	}
	
	

}
