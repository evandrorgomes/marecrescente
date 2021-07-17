package br.org.cancer.modred.service;

import org.springframework.data.domain.PageRequest;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.AnaliseMedica;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.service.custom.IService;
import br.org.cancer.modred.vo.AnaliseMedicaFinalizadaVo;
import br.org.cancer.modred.vo.DetalheAnaliseMedicaVo;

/**
 * Interface de acesso as funcionalidades envolvendo a classe AnaliseMedica.
 * 
 * @author bruno.sousa
 */
public interface IAnaliseMedicaService extends IService<AnaliseMedica, Long> {

	/**
	 * Cria a analise médica viculada ao pedido de contato.
	 * 
	 * @param pedidoContato
	 */
	void criarAnaliseMedica(PedidoContato pedidoContato);
	
	/**
	 * Lista tarefas de analise médica paginada. 
	 * @param pageRequest objeto com as informações de paginação desejadas para listagem.
	 * @return lista paginada de tarefas.
	 */
	JsonViewPage<TarefaDTO> listarTarefas(PageRequest pageRequest);

	/**
	 * Método para obter o detalhe da analise Médica. 
	 * 
	 * @param id - identificador da analise médica
	 * @return retorna um vo com o detalhe da analise médica
	 */
	DetalheAnaliseMedicaVo obterDetalheAnaliseMedica(Long id);

	/**
	 * Método para finalizar a analise médica.
	 * 
	 * @param id - Identificador da analise médica
	 * @param analiseMedicaFinalizadaVo 
	 */
	void finalizar(Long id, AnaliseMedicaFinalizadaVo analiseMedicaFinalizadaVo);



}
