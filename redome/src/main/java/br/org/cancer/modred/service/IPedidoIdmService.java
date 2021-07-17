package br.org.cancer.modred.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.model.PedidoIdm;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoIdm.
 * 
 * @author bruno.sousa
 */
public interface IPedidoIdmService extends IService<PedidoIdm, Long>{
	
	/**
	 * Método que cria um pedido de IDM internacional. 
	 * 
	 * @param solicitacao
	 */
	void criarPedidoIdmInternacional(Solicitacao solicitacao);
	
	/**
	 * Busca um pedido de IDM associado a solicitação.
	 * 
	 * @param idSolicitacao ID da solicitação.
	 * @return pedido IDM.
	 */
	PedidoIdm findBySolicitacaoId(Long idSolicitacao);

	/**
	 * Cancela o pedido de IDM caso não tenha sido cadastrado.
	 * @param solicitacao
	 * @param dataCancelamento 
	 */
	void cancelarPedido(Solicitacao solicitacao, LocalDate dataCancelamento);
	
	/**
	 * Salvar o resultado (laudo) do pedido de exame IDM para o doador. 
	 * 
	 * @param idPedidoIdm ID do pedido de IDM solicitado para o doador.
	 * @param listaArquivosLaudo lista de arquivos (neste caso, um só) contendo o laudo.
	 */
	void salvarResultadoPedidoIdmDoadorInternacional(Long idPedidoIdm, List<MultipartFile> listaArquivosLaudo);
	
	/**
	 * Obtém a tarefa associada ao pedido de IDM que esteja em aberto.
	 * Isso é necessário porque, se a tarefa estiver atribuída, alguém pode estar resolvendo.
	 * 
	 * @param pedidoIdm pedido de IDM a ser pesquisado.
	 * @return tarefa associada ao pedido.
	 */
	TarefaDTO obterTarefaPorPedidoEmAberto(PedidoIdm pedidoIdm);
	
	/**
	 * Lista pedidos IDM com resultado por doador internacional.
	 * @param idDoador identificação do doador.
	 * @return lista de pedidos com resultado.
	 */
	List<PedidoIdm> listarPedidosPorDoador(Long idDoador);
	

	
	/**
	 * Baixa o resultado de exame de IDM.
	 * @param idPedido de IDM.
	 * @return arquivo localizado do storage.
	 */
	File downloadArquivoIdm(Long idPedido);

}