package br.org.cancer.modred.helper;

import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;

import br.org.cancer.modred.feign.dto.TarefaDTO;

/**
 * Define o json view padrão para a listagem de tarefas.
 * Utilizado para listar as tarefas de forma genérica pelo ListarTarefa.
 * 
 * @author Pizão
 * @param <T> Definido pelo tipo do retorno que se deseja filtrar.
 */
public class JsonViewPage<T extends TarefaDTO> extends MappingJacksonValue {

	/**
	 * Constrõe o componente para filtrar a partir da lista informada.
	 * @param lista itens a terem seus atributos filtrados.
	 */
	public JsonViewPage(Page<T> lista) {
		super(lista);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<T> getValue() {
		return (Page<T>) super.getValue();
	}

	@Override
	public void setSerializationView(Class<?> serializationView) {
		super.setSerializationView(serializationView);
	}
	
}
