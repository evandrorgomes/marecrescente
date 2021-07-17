package br.org.cancer.modred.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.ConfirmacaoTransporteDTO;
import br.org.cancer.modred.controller.dto.DetalheMaterialDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Doador;
import br.org.cancer.modred.model.Paciente;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoTransporte;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface para negócios de pedido de transporte. Pedido de transporte de material gerado para um processo de logística. Neste
 * pedido são definidas a data de retirada e a transportadora responsável por levar o material até o centro de transplante.
 * 
 * @author Filipe Paes
 *
 */
public interface IPedidoTransporteService extends IService<PedidoTransporte, Long>{

	/**
	 * Método para enviar o arquivo de pedido de transporte para o storage e para atualizar o objeto de pedido de transporte com a
	 * referencia do arquivo.
	 * 
	 * @param pedidoTransporte - objeto a ser atualizado.
	 * @param listaArquivosLaudo - arquivo a ser enviao para o storage.
	 * @return nome do arquivo.
	 */
	List<String> salvarPedidoComArquivo(PedidoTransporte pedidoTransporte, List<MultipartFile> listaArquivosLaudo);
	
	/**
	 *Atualiza o arquivo de carta de CNT. 
	 * 
	 * @param id do pedido de logistica.
	 * @param listaArquivosLaudo - arquivo a ser enviao para o storage.
	 * @param descricaoAlteracao - descrição para caso de alteração do arquivo.
	 */
	void atualizarArquivoCartaCnt(Long idPedidoLogistica, List<MultipartFile> listaArquivosLaudo, String descricaoAlteracao);

	/**
	 * Método para confirmar o pedido de transporte a partir do usuário de uma transportadora.
	 * 
	 * @param id - identificador do pedido de transporte.
	 * @param confirmacaoTransporteDTO - dto com informações do courier e voo.
	 * @return Mensagem de sucesso
	 */
	PedidoTransporte confirmarPedidoTransporte(Long id, ConfirmacaoTransporteDTO confirmacaoTransporteDTO);

	/**
	 * Metodo para obter o pedido de transporte.
	 * 
	 * @param id - identificador do pedido de transporte
	 * @return PedidoTransporte entidade pedido de transporte
	 */
	PedidoTransporte obterPedidoTransporte(Long id);

	/**
	 * Listar pedidos de transporte pendentes.
	 * 
	 * @return List<PedidoTransporte> lista de entidade
	 */
	List<PedidoTransporte> listarPedidosTransportePendentes();
	
	/**
	 * Salva um novo pedido de transporte, garantindo os campos devidamente preenchidos.
	 * 
	 * @param pedidoTransporte pedido a ser salvo.
	 * @return
	 */
	PedidoTransporte salvar(PedidoTransporte pedidoTransporte);

	/**
	 * Obtém a carta para o pedido de transporte MO.
	 * 
	 * @param idPedidoTransporte
	 * @return
	 */
	File obterCartaTransporteMO(Long idPedidoTransporte);

	/**
	 * Obtém o relatório de transporte.
	 * 
	 * @param idPedidoTransporte
	 * @return
	 */
	File obterRelatorioTransporte(Long idPedidoTransporte);

	/**
	 * Confirma a retirada do pedido de transporte com a data e hora informados.
	 * 
	 * @param pedidoTransporteId ID do pedido de transporte.
	 * @param dataHoraRetirada data e hora da retirada.
	 * @return pedido de transporte atualizado.
	 */
	PedidoTransporte registrarRetirada(Long pedidoTransporteId, LocalDateTime dataHoraRetirada);
	
	/**
	 * Confirma a retirada do pedido de transporte com a data e hora informados.
	 * 
	 * @param pedidoTransporteId ID do pedido de transporte.
	 * @param dataHoraRetirada data e hora da retirada.
	 * @return pedido de transporte atualizado.
	 */
	PedidoTransporte registrarEntrega(Long pedidoTransporteId, LocalDateTime dataHoraEntrega);
	
	/**
	 * Obtém o paciente, de acordo com o procedimento que originou o pedido de transporte
	 * (workup ou coleta).
	 * 
	 * @param pedidoTransporte pedido de transporte solicitado.
	 * @return paciente relacionado ao processo de match/workup ou coleta.
	 */
	Paciente obterPacientePorPedidoTransporte(PedidoTransporte pedidoTransporte);
	
	/**
	 * Obtém o doador, de acordo com o procedimento que originou o pedido de transporte
	 * (workup ou coleta).
	 * 
	 * @param pedidoTransporte pedido de transporte solicitado.
	 * @return doador relacionado ao processo de match/workup ou coleta.
	 */
	Doador obterDoador(PedidoTransporte pedidoTransporte);
	
	/**
	 * Lista as tarefas de retirada de material internacional 
	 * associada(s) ao usuário logado.
	 * @param paginacao paginação do resultado.
	 * 
	 * @return lista de tarefas de retirada de material internacional.
	 */
	JsonViewPage<TarefaDTO> listarTarefasRetiradaMaterialInternacional(PageRequest paginacao);
	
	
	/**
	 * Lista todos as tarefas de pedido de transporte para a transportadora(2) 
	 * associada(s) ao usuário logado.
	 * @param paginacao paginação do resultado.
	 * 
	 * @return lista de tarefas de pedido de logística.
	 */
	JsonViewPage<TarefaDTO> listarTarefasParaTransportadora(PageRequest paginacao);
	
	/**
	 * Obtém todos os arquivos de um determinado pedido de transporte zipados.
	 * 
	 * @param idPedidoTransporte ID do pedido de transporte.
	 * @return arquivo (file) zipado com o conteúdo.
	 */
	File obterVoucherLiberacaoViagemCNT(Long idPedidoTransporte);

	
	/**
	 * Obtém pedido de transporte por id do pedido de logistica.
	 * 
	 * @param idPedidoLogistica id do pedido de logistica.
	 * @return pedido de transporte localizado.
	 */
	PedidoTransporte obterPedidoPorLogistica(Long idPedidoLogistica);

	/**
	 * Cancela o pedido de transporte.
	 * 
	 * @param idPedidoLogistoca identificador do pedido de logistica
	 */
	void cancelarPedidoTransporte(Long idPedidoLogistoca);
	
	/**
	 * Atualiza os dados do pedido de transporte.
	 * 
	 * @param pedidoTransporte pedido de Transporte a ser atualizado.
	 * @param detalhe - Detalhe do pedido de logistica para atualizar o pedido de transporte.
	 */
	void atualizaPedidoTransporte(PedidoTransporte pedidoTransporte, DetalheMaterialDTO detalhe);
	
	
	/**
	 * Cria o pedido de transporte associado ao pedidod de logistica com os detalhes da mesma.
	 * 
	 * @param pedidoLogistica - pedido de logistica ao quao o pedido de transporte pertence.
	 * @param detalhe - detalhe da logistica.
	 * @return Pedido de transporte criado.
	 */
	PedidoTransporte criarPedidoTransporte(PedidoLogistica pedidoLogistica, DetalheMaterialDTO detalhe);

}
