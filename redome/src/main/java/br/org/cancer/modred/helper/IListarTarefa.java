package br.org.cancer.modred.helper;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.view.PageView.ListarPaginacao;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.domain.StatusTarefa;
import br.org.cancer.modred.model.security.Usuario;

/**
 * Classe para centralizar a listagem de tarefa.
 * 
 * @author fillipe.queiroz
 *
 */
public interface IListarTarefa {

	/**
	 * Executar a consulta da lista de tarefas.
	 * @return Page<TarefaDTO> lista de tarefa
	 */
	JsonViewPage<TarefaDTO> apply();

	IListarTarefa comProcessoId(Long processoId);

	IListarTarefa comObjetoRelacionado(Long objetoRelacionado);

	IListarTarefa comPaginacao(PageRequest pageRequest);

	IListarTarefa comStatus(List<StatusTarefa> status);
	
	IListarTarefa comParceiros(List<Long> parceiros);

	IListarTarefa comUsuario(Usuario usuario);
	
	IListarTarefa comRmr(Long rmr);
	
	IListarTarefa comIdDoador(Long idDoador);
	
	IListarTarefa comFiltro(Predicate<TarefaDTO> filtro);
	
	IListarTarefa comOrdenacao(Comparator<TarefaDTO> ordenacao);
	
	IListarTarefa paraTodosUsuarios();
		
	Class<? extends ListarPaginacao> getJsonView();
	
}
