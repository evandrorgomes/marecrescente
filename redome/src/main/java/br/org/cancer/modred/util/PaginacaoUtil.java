package br.org.cancer.modred.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.controller.page.TarefaJsonPage;
import br.org.cancer.modred.feign.dto.TarefaDTO;

/**
 * Facilitador para paginação de resultados no back-end. 
 * 
 * @author Pizão
 */
public class PaginacaoUtil {

	/**
	 * Pagina a lista de tarefas de acordo com os parâmetros informados.
	 * 
	 * @param tarefas lista de tarefas.
	 * @param pagina página que deverá ser exibida.
	 * @param qtdPorPagina
	 *            quantidade de registros por página (fatia do resultado).
	 * @param <T> tipo para tarefas.           
	 * @return lista de tarefas paginada.
	 */
	public static <T> List<T> retornarListaPaginada(List<T> tarefas, int pagina, int qtdPorPagina) {
		List<T> tarefasPaginada = new ArrayList<T>();

		int valorInicialBusca = (pagina + 1) * qtdPorPagina - qtdPorPagina;
		int valorFinalBusca = (pagina + 1) * qtdPorPagina;

		tarefasPaginada = IntStream.range(valorInicialBusca, valorFinalBusca).mapToObj(indice -> {
			if (indice >= tarefas.size()) {
				return null;
			}
			return tarefas.get(indice);
		}).filter(tarefa -> tarefa != null).collect(Collectors.toList());

		return tarefasPaginada;
	}
	
	/**
	 * Obtém lista paginada de tarefas em formato JSON.
	 * 
	 * @param <T> tipo das tarefas.
	 * @param tarefas tarefas localizadas.
	 * @param pagina pagina referida.
	 * @param qtdPorPagina quantidade de itens.
	 * @return lista paginada de tarefas.
	 */
	public static <T extends TarefaDTO> Page<T> retornarListaJsonPaginada(List<T> tarefas, int pagina, int qtdPorPagina){
		PageRequest pageRequest = new PageRequest(pagina, qtdPorPagina);
		List<T> listaPaginada = PaginacaoUtil.retornarListaPaginada(tarefas, pageRequest.getPageNumber(), pageRequest.getPageSize());
		return new TarefaJsonPage<T>(listaPaginada, pageRequest, tarefas.size());
	}

}
