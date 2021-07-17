package br.org.cancer.redome.workup.helper;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.org.cancer.redome.workup.dto.ListaTarefaDTO;
import br.org.cancer.redome.workup.dto.TarefaDTO;
import br.org.cancer.redome.workup.util.CustomPageImpl;

public interface ITarefaHelper {
	
	CustomPageImpl<TarefaDTO> pageTarefas(ListaTarefaDTO filtroDTO) throws JsonProcessingException;
	
	List<TarefaDTO> listarTarefas(ListaTarefaDTO filtroDTO) throws JsonProcessingException;
		
	TarefaDTO atribuirTarefa(ListaTarefaDTO filtroDTO, Long idUsuario) throws JsonProcessingException;	
	
	TarefaDTO atribuirTarefa(Long id, Long idUsuario);
	
	TarefaDTO atualizarTarefa(TarefaDTO tarefa);
	
	TarefaDTO encerrarTarefa(ListaTarefaDTO filtroDTO, boolean encerrarProcesso) throws JsonProcessingException;
	
	TarefaDTO encerrarTarefa(Long id, boolean encerrarProcesso);
	
	TarefaDTO criarTarefa(TarefaDTO tarefa);
	
	TarefaDTO obterTarefa(ListaTarefaDTO filtroDTO) throws JsonProcessingException;	
	
	TarefaDTO obterTarefa(Long id);
	
	TarefaDTO cancelarTarefa(ListaTarefaDTO filtroDTO, boolean encerrarProcesso) throws JsonProcessingException;
	
	TarefaDTO cancelarTarefa(Long id, boolean encerrarProcesso);

}