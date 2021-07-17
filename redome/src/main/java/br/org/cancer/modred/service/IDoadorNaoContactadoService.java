package br.org.cancer.modred.service;

import br.org.cancer.modred.model.DoadorNaoContactado;
import br.org.cancer.modred.model.PedidoContato;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe DoadorNaoContactado.
 * 
 * @author bruno.sousa
 */
public interface IDoadorNaoContactadoService extends IService<DoadorNaoContactado, Long> {

	/**
	 * Método que cria a entiry com pedido de contato.
	 * 
	 * @param pedidoContato
	 */
	void criarDoadorNaoContactado(PedidoContato pedidoContato); 
}
