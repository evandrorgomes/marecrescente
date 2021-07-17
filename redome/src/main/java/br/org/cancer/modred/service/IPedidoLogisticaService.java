package br.org.cancer.modred.service;

import java.io.File;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import br.org.cancer.modred.controller.dto.DetalheMaterialDTO;
import br.org.cancer.modred.controller.dto.LogisticaDTO;
import br.org.cancer.modred.feign.dto.TarefaDTO;
import br.org.cancer.modred.helper.JsonViewPage;
import br.org.cancer.modred.model.Match;
import br.org.cancer.modred.model.PedidoColeta;
import br.org.cancer.modred.model.PedidoLogistica;
import br.org.cancer.modred.model.PedidoWorkup;
import br.org.cancer.modred.model.Solicitacao;
import br.org.cancer.modred.model.TipoPedidoLogistica;
import br.org.cancer.modred.model.domain.StatusPedidosLogistica;
import br.org.cancer.modred.model.domain.TiposPedidoLogistica;
import br.org.cancer.modred.service.custom.IService;

/**
 * Interface de acesso as funcionalidades envolvendo a classe PedidoLogistica.
 * 
 * @author Pizão
 */
public interface IPedidoLogisticaService extends IService<PedidoLogistica, Long> {

	/**
	 * Cria um pedido de logística com os dados informados.
	 * 
	 * @param pedido pedido de workup associado.
	 * @return retorna o pedido de logística criado.
	 */
	PedidoLogistica criarLogistica(PedidoWorkup pedido);

	/**
	 * Obtém o pedido de logística, de um determinado tipo, associado ao pedido workup informado. Por definição, só poderá existir
	 * um pedido de logística para um determinado tipo e pedido de workup.
	 * 
	 * @param pedido pedido de workup associado.
	 * @param tipoLogistica tipo de logística, se é doador ou material.
	 * @return retorna o pedido de logística encontrado.
	 */
	PedidoLogistica obterLogistica(PedidoWorkup pedido, TiposPedidoLogistica tipoLogistica);

	/**
	 * Obtém a solicitação associada ao pedido de logística.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return solicitação associada ao pedido.
	 */
	Solicitacao obterSolicitacaoWorkup(Long pedidoLogisticaId);

	/**
	 * Obtém o detalhe da logística informada para o doador associado ao pedido logística informado no parâmetro.
	 * 
	 * @param pedidoLogisticaId pedido de logística associado a logística que está sendo consultada.
	 * @return logística associada ao pedido.
	 */
	LogisticaDTO obterLogisticaDoador(Long pedidoLogisticaId);

	/**
	 * Método para fazer upload de arquivos de voucher.
	 * 
	 * @param pedidoLogisticaId - id do pedido de logistica
	 * @param file
	 * @return O caminho do arquivo salvo no storage
	 */
	String adicionarVoucher(Long pedidoLogisticaId, MultipartFile file);

	/**
	 * Salva a logística, atualizando o usuário responsável e atualiza os dados de
	 * transporte terrestre, áereo e hospedagens que, por ventura, tenha sido informados na logística do doador.
	 * 
	 * @param logistica DTO contendo as informações atualizadas de logística.
	 * @param status do pedido de logistica.
	 */
	void salvar(LogisticaDTO logistica, StatusPedidosLogistica status);
	
	/**
	 * Salva a logística, atualizando o usuário responsável, associa e finaliza a tarefa envolvida e atualiza os dados de
	 * transporte terrestre, áereo e hospedagens que, por ventura, tenha sido informados na logística do doador 
	 * @param logistica
	 */
	void finalizarLogistica(LogisticaDTO logistica);

	/**
	 * Atribui a tarefa logística para o usuário logado. A atribuição somente se dará se a tarefa estiver ABERTA, caso contrário,
	 * será lançado um erro. A atribuição poderá ser retirada, após um time-out pré-configurado na aplicação.
	 * 
	 * @param tarefaId ID da tarefa a ser associada.
	 */
	void atribuirTarefa(Long tarefaId);

	/**
	 * Cria um pedido de logística com os dados informados.
	 * 
	 * @param pedido pedido de Coleta associado.
	 * @param tipo - Tipo de pedido de logistica.
	 * @return retorna o pedido de logística criado.
	 */
	PedidoLogistica criarLogistica(PedidoColeta pedido, TiposPedidoLogistica tipo);

	/**
	 * Remove os arquivos não utilizados durante o upload na tela de detalhe da logística.
	 * 
	 * @param pedidoLogistica - pedido de logistica.
	 * 
	 */
	void excluirArquivosNaoVinculados(PedidoLogistica pedidoLogistica);

	/**
	 * Lista todas as tarefas de pedido de logística (workup e coleta), definidas para o doador, em que o usuário responsável é o
	 * usuário logado ou todas elas.
	 * 
	 * @param pageRequest paginação definida pelo front-end.
	 * 
	 * @return lista de tarefas relacionadas com pedido de logística.
	 */
	JsonViewPage<TarefaDTO> listarTarefasPedidoLogistica(PageRequest pageRequest);

	/**
	 * Cancela o pedido de coleta e as tarefas relacionadas.
	 * 
	 * @param idPedidoColeta - id do pedido de coleta que se deseja cancelar.
	 */
	void cancelarPedidoLogistica(Long idPedidoColeta);

	/**
	 * Atualiza o pedido de logistica com o novo evento.
	 * 
	 * @param tipoPedidoLogistica
	 * @param pedido a ser atualizado.
	 */
	void atualizarEvento(Long idPedidoLogistica, TipoPedidoLogistica tipoPedidoLogistica);
	
	/**
	 * Obtem DTO do detalhe de logistica de material.
	 * @param idPedido - id do pedido de material.
	 * @return dto de detalhe do pedido de material.
	 */
	DetalheMaterialDTO obterPedidoLogisticaMaterial(Long idPedido);
	
	/**
	 * Salva a logística de material (agenda a retirada do material).
	 * 
	 * @param detalhe detalhes envolvendo a geração do pedido de transporte.
	 * @param finalizar flag para indicar término da tarefa
	 */
	void salvarInformacoesMaterialInternacional(DetalheMaterialDTO detalhe);
	
	
	/**
	 * Finaliza o pedido de logística de material internacional.
	 * Todo o processo posterior a esta finalização é realizado fora do sistema, bastando ao
	 * analista de logística acompanhar o processo, gerando documentos necessários para transporte e 
	 * realizando contatos por telefone e e-mail.
	 * 
	 * @param idPedidoLogistica ID do pedido de logística a ser finalizado.
	 */
	void finalizarLogisticaMaterialInternacional(Long idPedidoLogistica);
	
	
	/**
	 * Lista todos os pedidos de transporte e seus devidos andamentos.
	 * @param pageRequest configuração para paginação.
	 * 
	 * @return listagem de pedidos de logistica em andamento (associadas a pedido de transporte) paginada.
	 */
	Page<PedidoLogistica> listarLogisticaTransporteEmAndamento(PageRequest pageRequest);
	
	/**
	 * Lista a logística de transporte internacional e informações relativas ao andamento.
	 * @param pageRequest configuração para paginação.
	 * 
	 * @return lista de pedidos de logística paginada.
	 */
	Page<PedidoLogistica> listarLogisticaInternacionalTransporteEmAndamento(PageRequest pageRequest);
	
	/**
	 * Obtém o pedido de workup associado ao pedido de logística.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return pedido de workup associado.
	 */
	PedidoWorkup obterWorkup(Long pedidoLogisticaId);
	
	/**
	 * Obtém o pedido de coleta associado ao pedido de logística.
	 * 
	 * @param pedidoLogisticaId ID do pedido de logística.
	 * @return pedido de coleta associado.
	 */
	PedidoColeta obterColeta(Long pedidoLogisticaId);

	/**
	 * Obtém o pedido de logistica.
	 * 
	 * @param match - Match ao qual a  solicitação de worup ou coleta está associada
	 * @return PedidoLogistica
	 */
	PedidoLogistica obterPedidoLogisticaPeloMatch(Match match);

	/**
	 * Gera o arquivo para download dos documentos de uma logistica.
	 * 
	 * @param idPedidoLogistica - id do pedido de logistica.
	 * @param codigoRelatorio - código do relatorio(documento) solicitado
	 * @param docxExtensaoRelatorio - extensão do arquivo do relatório é docx
	 * @return Arquivo gerado.
	 */
	File obterDocumento(Long idPedidoLogistica, String codigoRelatorio, boolean docxExtensaoRelatorio);
	
	/**
	 * Lista tarefas internacionais de pedido de coleta..
	 * 
	 * @param pageRequest paginação definida pelo front-end.
	 * @return lista de tarefas relacionadas com pedido de logística internacional.
	 */
	JsonViewPage<TarefaDTO> listarTarefasPedidoLogisticaInternacional(PageRequest pageRequest);
	
	/**
	 * Finaliza e salva a logística de material nacional, gerando um pedido de transporte
	 * direcionado a transportadora escolhida.
	 * 
	 * @param idPedidoLogistica ID do pedido de logística finalizado.
	 * @param detalhe DTO contendo as informações definidas na tela ao finalizar a logística.
	 */
	void finalizarLogisticaMaterialNacional(Long idPedidoLogistica, DetalheMaterialDTO detalhe);
	
	
	/**
	 * Salva a logística de material nacional.
	 * 
	 * @param idPedidoLogistica ID do pedido de logística finalizado.
	 * @param detalhe DTO contendo as informações definidas na tela ao finalizar a logística.
	 */
	void salvarLogisticaMaterialNacional(Long idPedidoLogistica, DetalheMaterialDTO detalhe);
	
	/**
	 * Gera o arquivo para download da autorização do paciente destinado ao processo
	 * de logística internacional.
	 * 
	 * @param idPedidoLogistica - ID do pedido de logistica.
	 * @return Arquivo gerado.
	 */
	File obterAutorizacaoPaciente(Long idPedidoLogistica);
	
	/**
	 * Altera a logistica de material quando esta for aérea. Através deste método é possível fechar a tarefa do courier atual,
	 * abrir uma nova tarefa com uma nova carta e uma nova data.
	 * @param idPedidoLogistica - identificacao do pedido de logística.
	 * @param tipoAlteracao - utilize o enum de TipoAlteracaoPedidoLogistica.
	 * @param cartaCnt - carta cnt para ser enviada para o courier com novos dados da viajem.
	 * @param descricao - descrição explicando o porquê da alteração.
	 * @param data - nova data para o translado do courier.
	 * @param rmr - identificação do paciente.
	 */
	void alterarLogisticaMaterialAereo(Long idPedidoLogistica, Long tipoAlteracao, MultipartFile cartaCnt, String descricao, LocalDate data, Long rmr);

	/**
	 * Altera o status do pedido de logistica para "FECHADO". 
	 * 
	 * @param idPedidoLogistica - identificador do Pedido de logistica a ser fechada.
	 */
	void fecharPedidoLogistica(Long idPedidoLogistica);

}
